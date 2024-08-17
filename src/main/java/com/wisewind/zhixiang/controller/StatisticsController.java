package com.wisewind.zhixiang.controller;

import com.wisewind.zhixiang.annotation.AuthCheck;
import com.wisewind.zhixiang.common.BaseResponse;
import com.wisewind.zhixiang.common.ResultUtils;
import com.wisewind.zhixiang.constant.UserConstant;
import com.wisewind.zhixiang.model.vo.InterfaceInfoInvokeCountVO;
import com.wisewind.zhixiang.service.StatisticsService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/statistics")
public class StatisticsController {
    @Resource
    private StatisticsService statisticsService;

    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    @GetMapping("/list")
    public BaseResponse<List<InterfaceInfoInvokeCountVO>> listInvokeCount(Integer topN){
        if(topN == null || topN <= 0){
            topN = 5;
        }
        List<InterfaceInfoInvokeCountVO> topNInvokeInterface = statisticsService.getTopNInvokeInterface(topN);
        return ResultUtils.success(topNInvokeInterface);
    }
}
