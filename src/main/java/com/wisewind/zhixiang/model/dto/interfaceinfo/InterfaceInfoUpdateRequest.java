package com.wisewind.zhixiang.model.dto.interfaceinfo;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.io.Serializable;

/**
 * 更新请求
 *
 * @TableName product
 */
@Data
public class InterfaceInfoUpdateRequest implements Serializable {
    /**
     * 主键
     */
    private Long id;

    /**
     * 接口名
     */
    private String apiName;

    /**
     * 接口描述
     */
    private String apiDescription;

    /**
     * 接口地址
     */
    private String apiUrl;

    /**
     * 请求头
     */
    private String requestHeader;

    /**
     * 响应头
     */
    private String responseHeader;

    /**
     * 接口状态 0-默认
     */
    private Integer status;

    /**
     * 请求类型
     */
    private String method;

    /**
     *示例请求
     */
    private String requestExample;

    /**
     *调用接口扣除积分
     */
    private Integer reducePoint;

    /**
     *返回格式
     */
    private String returnFormat;

    /**
     *头像地址
     */
    private String avatarUrl;

    /**
     * 请求参数
     */
    private String requestParams;


    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}
