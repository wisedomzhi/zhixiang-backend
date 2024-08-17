package com.wisewind.zhixiang.service.impl.inner;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.wisewind.zhixiang.common.ErrorCode;
import com.wisewind.zhixiang.exception.BusinessException;
import com.wisewind.zhixiang.service.UserService;
import com.wisewind.zhixiangcommon.model.entity.InterfaceInfo;
import com.wisewind.zhixiangcommon.model.entity.User;
import com.wisewind.zhixiangcommon.service.InnerUserService;
import jakarta.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.DubboService;

@DubboService
public class InnerUserServiceImpl implements InnerUserService {
    @Resource
    private UserService userService;

    @Override
    public User getInvokeUser(String accessKey) {
        if(StringUtils.isAnyBlank(accessKey)){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        LambdaUpdateWrapper<User> lambdaUpdateWrapper = new LambdaUpdateWrapper<>();
        lambdaUpdateWrapper.eq(User::getAccessKey, accessKey);
        return userService.getOne(lambdaUpdateWrapper);
    }
}
