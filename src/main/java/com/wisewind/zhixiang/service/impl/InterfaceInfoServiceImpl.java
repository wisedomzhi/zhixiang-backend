package com.wisewind.zhixiang.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wisewind.zhixiang.common.ErrorCode;
import com.wisewind.zhixiang.exception.BusinessException;
import com.wisewind.zhixiang.model.enums.PostReviewStatusEnum;
import com.wisewind.zhixiang.service.InterfaceInfoService;
import com.wisewind.zhixiang.mapper.InterfaceInfoMapper;
import com.wisewind.zhixiangcommon.model.entity.InterfaceInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

/**
* @author ffz
* @description 针对表【interface_info(接口信息表)】的数据库操作Service实现
* @createDate 2024-08-07 17:02:39
*/
@Service
public class InterfaceInfoServiceImpl extends ServiceImpl<InterfaceInfoMapper, InterfaceInfo>
    implements InterfaceInfoService{

    @Override
    public void validInterfaceInfo(InterfaceInfo interfaceInfo, boolean add) {
        if (interfaceInfo == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }

        String apiName = interfaceInfo.getApiName();
        String apiDescription = interfaceInfo.getApiDescription();
        String apiUrl = interfaceInfo.getApiUrl();
        String requestHeader = interfaceInfo.getRequestHeader();
        String responseHeader = interfaceInfo.getResponseHeader();
        Integer status = interfaceInfo.getStatus();
        String method = interfaceInfo.getMethod();
                // 创建时，所有参数必须非空
        if (add) {
            if (StringUtils.isAnyBlank(apiName, apiDescription, apiUrl, method)) {
                throw new BusinessException(ErrorCode.PARAMS_ERROR);
            }
        }
        if (StringUtils.isNotBlank(apiDescription) && apiDescription.length() > 8192) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "内容过长");
        }
        if (status != null && !PostReviewStatusEnum.getValues().contains(status)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
    }
}




