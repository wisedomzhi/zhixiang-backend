package com.wisewind.zhixiang.model.dto.userinterfaceinfo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.wisewind.zhixiang.common.PageRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

/**
 * 查询请求
 *
 * @author wisewind
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class UserInterfaceInfoQueryRequest extends PageRequest implements Serializable {

    /**
     * id
     */
    private Long id;

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
