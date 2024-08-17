package com.wisewind.zhixiang.service;

import com.wisewind.zhixiang.model.vo.InterfaceInfoInvokeCountVO;

import java.util.List;

public interface StatisticsService {

    List<InterfaceInfoInvokeCountVO> getTopNInvokeInterface(Integer topN);
}
