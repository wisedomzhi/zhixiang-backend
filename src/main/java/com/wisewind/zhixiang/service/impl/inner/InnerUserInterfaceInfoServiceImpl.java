package com.wisewind.zhixiang.service.impl.inner;

import com.wisewind.zhixiang.service.UserInterfaceInfoService;
import com.wisewind.zhixiangcommon.service.InnerUserInterfaceInfoService;
import jakarta.annotation.Resource;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.stereotype.Service;

@DubboService
public class InnerUserInterfaceInfoServiceImpl implements InnerUserInterfaceInfoService {
    @Resource
    private UserInterfaceInfoService userInterfaceInfoService;

    @Override
    public boolean invokeCount(Long interfaceInfoId, Long userId) {
        return userInterfaceInfoService.invokeCount(interfaceInfoId, userId);
    }
}
