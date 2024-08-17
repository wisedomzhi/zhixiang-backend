package com.wisewind.zhixianginterface.controller;

import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/")
public class ServiceController {

    @GetMapping("/name")
    public String getName(String name) {
        HashMap<String, String> res = new HashMap<>();
        res.put("name", name);
        return JSONUtil.toJsonStr(res);
    }

    @GetMapping("/loveTalk")
    public String randomLoveTalk() {
        String content = HttpUtil.get("https://api.vvhan.com/api/text/love");
        HashMap<String, String> res = new HashMap<>();
        res.put("content", content);
        return JSONUtil.toJsonStr(res);
    }

    @GetMapping("/bdics")
    public String getBaiDuIndexNum(String domain) {
        Map<String, Object> params = new HashMap<>();
        params.put("domain", domain);
        String response = HttpUtil.get("https://api.btstu.cn/bdics/api.php", params);
        JSONObject entries = JSONUtil.parseObj(response);
        entries.remove("code");
        return JSONUtil.toJsonStr(entries);
    }

    @GetMapping("/bilibili")
    public String getBilibilHotList() {
        String response = HttpUtil.get("https://api.vvhan.com/api/hotlist/bili");
        JSONObject entries = JSONUtil.parseObj(response);
        entries.remove("success");
        return JSONUtil.toJsonStr(entries);
    }

    @GetMapping("/weather")
    public String getWeather() {
        String response = HttpUtil.get("https://api.vvhan.com/api/weather");
        JSONObject entries = JSONUtil.parseObj(response);
        entries.remove("success");
        return JSONUtil.toJsonStr(entries);
    }

    @GetMapping("/bing")
    public String getBingImg(String rand, String size) {
        Map<String, Object> params = new HashMap<>();
        if(StringUtils.isNotBlank(rand)){
            params.put("rand", rand);
        }
        if(StringUtils.isNotBlank(size)){
            params.put("size", size);
        }
        String response = HttpUtil.get("https://api.vvhan.com/api/bing?type=json", params);
        JSONObject entries = JSONUtil.parseObj(response);
        return JSONUtil.toJsonStr(entries.get("data"));
    }

    @GetMapping("/ipInfo")
    public String getIpInfo(String ip) {
        Map<String, Object> params = new HashMap<>();
        if(StringUtils.isNotBlank(ip)){
            params.put("ip", ip);
        }
        String response = HttpUtil.get("https://api.vvhan.com/api/ipInfo", params);
        JSONObject entries = JSONUtil.parseObj(response);
        entries.remove("success");
        return JSONUtil.toJsonStr(entries);
    }

    @GetMapping("/visitor")
    public String getVisitorInfo() {
        String response = HttpUtil.get("https://api.vvhan.com/api/visitor.info");
        JSONObject entries = JSONUtil.parseObj(response);
        entries.remove("success");
        return JSONUtil.toJsonStr(entries);
    }



}
