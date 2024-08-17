package com.wisewind.zhixiang.model.vo;

import lombok.Data;

@Data
public class InterfaceInfoInvokeCountVO {


    /**
     * 接口id
     */
    private Long id;

    /**
     * 接口名称
     */
    private String apiName;

    /**
     * 总调用次数
     */
    private Integer totalCount;


    private static final long serialVersionUID = 1L;
}
