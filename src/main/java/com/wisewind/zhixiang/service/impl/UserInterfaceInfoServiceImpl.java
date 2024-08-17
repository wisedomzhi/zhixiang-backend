package com.wisewind.zhixiang.service.impl;

import java.util.Date;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wisewind.zhixiang.common.ErrorCode;
import com.wisewind.zhixiang.exception.BusinessException;
import com.wisewind.zhixiang.model.enums.UserInterfaceInfoStatusEnum;
import com.wisewind.zhixiang.service.InterfaceInfoService;
import com.wisewind.zhixiang.service.UserInterfaceInfoService;
import com.wisewind.zhixiang.mapper.UserInterfaceInfoMapper;
import com.wisewind.zhixiang.service.UserService;
import com.wisewind.zhixiangcommon.model.entity.InterfaceInfo;
import com.wisewind.zhixiangcommon.model.entity.User;
import com.wisewind.zhixiangcommon.model.entity.UserInterfaceInfo;
import jakarta.annotation.Resource;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author ffz
 * @description 针对表【user_interface_info(用户接口调用关系表)】的数据库操作Service实现
 * @createDate 2024-08-10 16:02:12
 */
@Service
public class UserInterfaceInfoServiceImpl extends ServiceImpl<UserInterfaceInfoMapper, UserInterfaceInfo>
        implements UserInterfaceInfoService {

    @Resource
    private UserService userService;

    @Resource
    private InterfaceInfoService interfaceInfoService;

    @Override
    public void validUserInterfaceInfo(UserInterfaceInfo userInterfaceInfo, boolean add) {
        if (userInterfaceInfo == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }


        Long userId = userInterfaceInfo.getUserId();
        Long interfaceInfoId = userInterfaceInfo.getInterfaceInfoId();
        Integer totalNum = userInterfaceInfo.getTotalNum();
        Integer status = userInterfaceInfo.getStatus();
        // 创建时，所有参数必须非空
        if (add) {
            if (ObjectUtils.anyNull(userId, interfaceInfoId)) {
                throw new BusinessException(ErrorCode.PARAMS_ERROR);
            }
        }
        if (status != null && !UserInterfaceInfoStatusEnum.getValues().contains(status)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
    }

    @Transactional
    @Override
    public boolean invokeCount(Long interfaceInfoId, Long userId) {
        if (interfaceInfoId == null || userId == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        if (interfaceInfoId <= 0 || userId <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }

        // 查询是否有该用户调用该接口的记录
        LambdaQueryWrapper<UserInterfaceInfo> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(UserInterfaceInfo::getUserId, userId);
        lambdaQueryWrapper.eq(UserInterfaceInfo::getInterfaceInfoId, interfaceInfoId);
        UserInterfaceInfo userInterfaceInfo = this.getOne(lambdaQueryWrapper);
        // 如果没有插入一条新的，否则调用次数加一
        if (userInterfaceInfo == null) {
            UserInterfaceInfo newRecord = new UserInterfaceInfo();
            newRecord.setUserId(userId);
            newRecord.setInterfaceInfoId(interfaceInfoId);
            newRecord.setTotalNum(1);
            this.save(newRecord);
        } else {
            Integer totalNum = userInterfaceInfo.getTotalNum();
            userInterfaceInfo.setTotalNum(totalNum + 1);
            this.updateById(userInterfaceInfo);
        }

        // 扣除用户积分
        User user = userService.getById(userId);
        Integer remainPoint = user.getRemainPoint();
        InterfaceInfo interfaceInfo = interfaceInfoService.getById(interfaceInfoId);
        Integer reducePoint = interfaceInfo.getReducePoint();
        // 接口总调用次数加1
        interfaceInfo.setTotalInvokeCount(interfaceInfo.getTotalInvokeCount() + 1);
        interfaceInfoService.updateById(interfaceInfo);
        if (remainPoint < reducePoint) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "剩余点数不足");
        }
        remainPoint -= reducePoint;
        user.setRemainPoint(remainPoint);
        return userService.updateById(user);
    }
}




