package com.wisewind.zhixianggateway.gateway;

import cn.hutool.http.ContentType;
import cn.hutool.http.Header;

import com.wisewind.zhixiangclientsdk.utils.SignUtils;
import com.wisewind.zhixiangcommon.model.entity.InterfaceInfo;
import com.wisewind.zhixiangcommon.model.entity.User;
import com.wisewind.zhixiangcommon.service.InnerInterfaceInfoService;
import com.wisewind.zhixiangcommon.service.InnerUserInterfaceInfoService;
import com.wisewind.zhixiangcommon.service.InnerUserService;
import io.micrometer.common.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.reactivestreams.Publisher;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.filter.headers.observation.GatewayContext;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.core.io.buffer.DefaultDataBufferFactory;
import org.springframework.http.*;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.http.server.reactive.ServerHttpResponseDecorator;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.net.InetSocketAddress;
import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Component
@Slf4j
public class ZhiXiangGlobalFilter implements GlobalFilter, Ordered {
    @DubboReference
    private InnerInterfaceInfoService innerInterfaceInfoService;
    @DubboReference
    private InnerUserInterfaceInfoService innerUserInterfaceInfoService;
    @DubboReference
    private InnerUserService innerUserService;
    private static final List<String> WHITE_LIST = Arrays.asList("127.0.0.1", "localhost");

    private static final String INTERFACE_HOST = "http://localhost:8900";

    private static final long FIVE_MINUTES = 5L * 60;
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
//        1. 打印请求日志
        ServerHttpRequest request = exchange.getRequest();
        String id = request.getId();
        String path = request.getPath().value();
        HttpMethod method = request.getMethod();
        MultiValueMap<String, String> queryParams = request.getQueryParams();
        URI uri = request.getURI();
        log.info("请求id：{}", id);
        log.info("请求路径：{}", path);
        log.info("请求方法：{}", method);
        log.info("请求参数：{}", queryParams);
        log.info("请求地址：{}", uri);
        ServerHttpResponse response = exchange.getResponse();
//        2. 黑白名单
        if(!WHITE_LIST.contains(uri.getHost())){
           return forbid(response);
        }
//        3. 用户鉴权
        HttpHeaders headers = request.getHeaders();
        String accessKey = headers.getFirst("accessKey");
        String sign = headers.getFirst("sign");
//        String nonce = headers.getFirst("nonce");
        String timeStamp = headers.getFirst("timeStamp");
        long currentTime = System.currentTimeMillis() / 1000;
        if (timeStamp == null || currentTime - Long.parseLong(timeStamp) >= FIVE_MINUTES) {
            return forbid(response);
        }
        User invokeUser = null;
        try {
            invokeUser = innerUserService.getInvokeUser(accessKey);
        }catch (Exception e){
            log.info("getInvokeUser error");
        }
        if(invokeUser == null){
            return forbid(response);
        }
        String secretKey = invokeUser.getSecretKey();

        String serverSign = SignUtils.getSign(accessKey, secretKey);
        if(!serverSign.equals(sign)){
            return forbid(response);
        }
        //todo 实现防止重放攻击的逻辑
//        4. 判断请求的接口是否存在
        InterfaceInfo interfaceInfo = null;
        try {
            interfaceInfo = innerInterfaceInfoService.getInterfaceInfo(INTERFACE_HOST + path, method.toString());
        }catch (Exception e){
            log.info("getInterfaceInfo error");
        }
        if(interfaceInfo == null){
            return forbid(response);
        }
        //todo 校验是否还有请求次数
//        5. 请求转发，调用接口
        Mono<Void> filter = chain.filter(exchange);
//        6. 响应日志
        return handleResponse(exchange, chain, interfaceInfo.getId(), invokeUser.getId());
//        HttpStatusCode statusCode = response.getStatusCode();
//        log.info("响应码：{}", statusCode);
//        7. 调用成功，调用次数+1
//        8. 调用失败，返回错误码
//       if(statusCode != HttpStatus.OK){
//          return error(response);
//       }
//        return filter;
    }


    public Mono<Void> handleResponse(ServerWebExchange exchange, GatewayFilterChain chain, Long interfaceId, Long userId) {
        try {
            ServerHttpResponse originalResponse = exchange.getResponse();
            DataBufferFactory bufferFactory = originalResponse.bufferFactory();
            HttpStatusCode statusCode = originalResponse.getStatusCode();
            HttpHeaders headers = originalResponse.getHeaders();
            if (statusCode != HttpStatus.OK) {
                return chain.filter(exchange);//降级处理返回数据
            }
            ServerHttpResponseDecorator decoratedResponse = new ServerHttpResponseDecorator(originalResponse) {
                @Override
                public Mono<Void> writeWith(Publisher<? extends DataBuffer> body) {
                    if (body instanceof Flux) {
                        Flux<? extends DataBuffer> fluxBody = Flux.from(body);
                        try {
                            innerUserInterfaceInfoService.invokeCount(interfaceId, userId);
                        }catch (Exception e){
                            log.info("invokeCount error");
                        }
                        return super.writeWith(fluxBody.buffer().map(dataBuffers -> {

                            // 合并多个流集合，解决返回体分段传输
                            DataBufferFactory dataBufferFactory = new DefaultDataBufferFactory();
                            DataBuffer buff = dataBufferFactory.join(dataBuffers);
                            byte[] content = new byte[buff.readableByteCount()];
                            buff.read(content);
                            DataBufferUtils.release(buff);//释放掉内存

                            //排除Excel导出，不是application/json不打印。若请求是上传图片则在最上面判断。
                            MediaType contentType = originalResponse.getHeaders().getContentType();
//                            if (!MediaType.APPLICATION_JSON.isCompatibleWith(contentType)) {
//                                return bufferFactory.wrap(content);
//                            }

                            // 构建返回日志
                            String joinData = new String(content);
//                            String result = modifyBody(joinData);
                            List<Object> rspArgs = new ArrayList<>();
                            rspArgs.add(originalResponse.getStatusCode().value());
                            rspArgs.add(exchange.getRequest().getURI());
                            rspArgs.add(joinData);
                            log.info("<-- {} {}\n{}", rspArgs.toArray());

                            getDelegate().getHeaders().setContentLength(joinData.getBytes().length);
                            return bufferFactory.wrap(joinData.getBytes());
                        }));
                    } else {
                        log.error("<-- {} 响应code异常", getStatusCode());
                    }
                    return super.writeWith(body);
                }
            };
            return chain.filter(exchange.mutate().response(decoratedResponse).build());

        } catch (Exception e) {
            log.error("gateway log exception.\n" + e);
            return chain.filter(exchange);
        }
    }





    @Override
    public int getOrder() {
        return -1;
    }


    private Mono<Void> forbid(ServerHttpResponse response){
        response.setStatusCode(HttpStatus.FORBIDDEN);
        return response.setComplete();
    }

    private Mono<Void> error(ServerHttpResponse response){
        response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
        return response.setComplete();
    }
}
