package com.wisewind.zhixiang.service;

import com.wisewind.zhixiang.model.entity.InterfaceInfo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author ffz
* @description 针对表【interface_info(接口信息表)】的数据库操作Service
* @createDate 2024-08-07 17:02:39
*/
public interface InterfaceInfoService extends IService<InterfaceInfo> {

    void validInterfaceInfo(InterfaceInfo interfaceInfo, boolean add);
}
