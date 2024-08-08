package com.wisewind.zhixiang.model.dto.interfaceinfo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.wisewind.zhixiang.common.PageRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * 查询请求
 *
 * @author wisewind
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class InterfaceInfoQueryRequest extends PageRequest implements Serializable {

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
     * 创建人
     */
    private Long userId;


    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}
