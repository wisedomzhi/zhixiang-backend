package com.wisewind.zhixiangcommon.service;

import com.wisewind.zhixiangcommon.model.entity.InterfaceInfo;

/**
 * @author ffz
 * @description 针对表【interface_info(接口信息表)】的数据库操作Service
 * @createDate 2024-08-07 17:02:39
 */
public interface InnerInterfaceInfoService {

    /**
     * 查询要调用的接口是否存在
     * @param path
     * @param method
     * @return
     */
    InterfaceInfo getInterfaceInfo(String path, String method);
}
