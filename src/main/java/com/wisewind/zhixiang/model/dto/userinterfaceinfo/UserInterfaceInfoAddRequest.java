package com.wisewind.zhixiang.model.dto.userinterfaceinfo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 创建请求
 *
 * @TableName product
 */
@Data
public class UserInterfaceInfoAddRequest implements Serializable {


    /**
     * 接口调用者id
     */
    private Long userId;

    /**
     * 接口id
     */
    private Long interfaceInfoId;

    /**
     * 调用总次数
     */
    private Integer totalNum;

    /**
     * 剩余调用次数
     */
    private Integer remainNum;

    /**
     * 0-正常 1-禁用
     */
    private Integer status;


    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}
