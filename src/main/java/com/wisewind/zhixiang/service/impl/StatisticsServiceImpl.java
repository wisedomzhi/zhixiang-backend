package com.wisewind.zhixiang.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.wisewind.zhixiang.common.ErrorCode;
import com.wisewind.zhixiang.exception.BusinessException;
import com.wisewind.zhixiang.mapper.UserInterfaceInfoMapper;
import com.wisewind.zhixiang.model.vo.InterfaceInfoInvokeCountVO;
import com.wisewind.zhixiang.service.InterfaceInfoService;
import com.wisewind.zhixiang.service.StatisticsService;
import com.wisewind.zhixiangcommon.model.entity.InterfaceInfo;
import com.wisewind.zhixiangcommon.model.entity.UserInterfaceInfo;
import jakarta.annotation.Resource;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class StatisticsServiceImpl implements StatisticsService {
    @Resource
    private UserInterfaceInfoMapper userInterfaceInfoMapper;
    @Resource
    private InterfaceInfoService interfaceInfoService;

    @Override
    public List<InterfaceInfoInvokeCountVO> getTopNInvokeInterface(Integer topN) {
        List<UserInterfaceInfo> userInterfaceInfos = userInterfaceInfoMapper.sumByInterfaceId(topN);
        if (userInterfaceInfos == null) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR);
        }
        List<Long> interfaceIdList = userInterfaceInfos.stream().map(UserInterfaceInfo::getInterfaceInfoId).toList();
        Map<Long, Integer> interfaceIdListMap = userInterfaceInfos.stream().collect(Collectors.toMap(UserInterfaceInfo::getInterfaceInfoId, UserInterfaceInfo::getTotalNum, (v1, v2) -> v1));
        LambdaQueryWrapper<InterfaceInfo> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.in(InterfaceInfo::getId, interfaceIdList);
        List<InterfaceInfo> list = interfaceInfoService.list(lambdaQueryWrapper);
        List<InterfaceInfoInvokeCountVO> interfaceInfoInvokeCountVOS = list.stream().map(interfaceInfo -> {
            InterfaceInfoInvokeCountVO interfaceInfoInvokeCountVO = new InterfaceInfoInvokeCountVO();
            BeanUtils.copyProperties(interfaceInfo, interfaceInfoInvokeCountVO);
            interfaceInfoInvokeCountVO.setTotalCount(interfaceIdListMap.get(interfaceInfo.getId()));
            return interfaceInfoInvokeCountVO;
        }).collect(Collectors.toList());
        return interfaceInfoInvokeCountVOS;
    }
}
