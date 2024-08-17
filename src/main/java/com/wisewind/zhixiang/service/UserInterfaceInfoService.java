package com.wisewind.zhixiang.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wisewind.zhixiangcommon.model.entity.UserInterfaceInfo;

/**
* @author ffz
* @description 针对表【user_interface_info(用户接口调用关系表)】的数据库操作Service
* @createDate 2024-08-10 16:02:12
*/
public interface UserInterfaceInfoService extends IService<UserInterfaceInfo> {

    void validUserInterfaceInfo(UserInterfaceInfo userInterfaceInfo, boolean add);

    boolean invokeCount(Long interfaceInfoId, Long userId);
}
