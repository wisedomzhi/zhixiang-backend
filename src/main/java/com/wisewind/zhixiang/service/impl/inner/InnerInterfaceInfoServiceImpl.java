package com.wisewind.zhixiang.service.impl.inner;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.wisewind.zhixiang.common.ErrorCode;
import com.wisewind.zhixiang.exception.BusinessException;
import com.wisewind.zhixiang.service.InterfaceInfoService;
import com.wisewind.zhixiangcommon.model.entity.InterfaceInfo;
import com.wisewind.zhixiangcommon.service.InnerInterfaceInfoService;
import jakarta.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.DubboService;

@DubboService
public class InnerInterfaceInfoServiceImpl implements InnerInterfaceInfoService {
    @Resource
    private InterfaceInfoService interfaceInfoService;

    @Override
    public InterfaceInfo getInterfaceInfo(String path, String method) {
        if(StringUtils.isAnyBlank(path, method)){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }

        LambdaUpdateWrapper<InterfaceInfo> lambdaUpdateWrapper = new LambdaUpdateWrapper<>();
        lambdaUpdateWrapper.eq(InterfaceInfo::getApiUrl, path);
        lambdaUpdateWrapper.eq(InterfaceInfo::getMethod, method);
        return interfaceInfoService.getOne(lambdaUpdateWrapper);
    }
}
