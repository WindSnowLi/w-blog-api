package com.hiyj.blog.services;

import com.hiyj.blog.services.base.OtherService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class OtherServiceTest {
    private OtherService otherService;

    @Autowired
    public void setOtherService(OtherService otherService) {
        this.otherService = otherService;
    }

    /**
     * 获取仪表盘折线图和panel-group部分
     */
    @Test
    public void testGetPanel() {
        System.out.println(otherService.getPanel());
    }

    /**
     * 获取图表信息
     */
    @Test
    public void testGetChart() {
        System.out.println(otherService.getChart());
    }
}
