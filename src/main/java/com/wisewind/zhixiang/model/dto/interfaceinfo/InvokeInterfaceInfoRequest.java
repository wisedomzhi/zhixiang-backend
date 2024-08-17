package com.wisewind.zhixiang.model.dto.interfaceinfo;

import lombok.Data;

@Data
public class InvokeInterfaceInfoRequest {

    /**
     * 接口id
     */
    private Long id;

    /**
     * 请求参数
     */
    private String requestParams;

    /**
     * 请求地址
     */
    private String apiUrl;
}
