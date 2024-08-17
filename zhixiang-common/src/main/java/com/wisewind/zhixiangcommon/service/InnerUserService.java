package com.wisewind.zhixiangcommon.service;


import com.wisewind.zhixiangcommon.model.entity.User;


/**
 * 用户服务
 *
 * @author wisewind
 */
public interface InnerUserService {

    /**
     * 查询是否已经给该用户分配ak、sk
     * @param accessKey
     * @return
     */
    User getInvokeUser(String accessKey);


}
