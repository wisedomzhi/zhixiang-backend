package com.wisewind.zhixiangcommon.service;

/**
 * @author ffz
 * @description 针对表【user_interface_info(用户接口调用关系表)】的数据库操作Service
 * @createDate 2024-08-10 16:02:12
 */
public interface InnerUserInterfaceInfoService {

    /**
     * 接口调用次数加一
     * @param interfaceInfoId
     * @param userId
     * @return
     */
    boolean invokeCount(Long interfaceInfoId, Long userId);

}
