package com.wisewind.zhixiang.service;

import com.wisewind.zhixiang.service.impl.inner.InnerUserInterfaceInfoServiceImpl;
import com.wisewind.zhixiangcommon.service.InnerUserInterfaceInfoService;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserInterfaceInfoServiceTest {
    @Resource
    private UserInterfaceInfoService userInterfaceInfoService;

    @Resource
    private InnerUserInterfaceInfoService innerUserInterfaceInfoService;

    @Test
    void invokeCount() {
//        userInterfaceInfoService.invokeCount(1l, 1l);

        innerUserInterfaceInfoService.invokeCount(1l, 1l);
    }
}
