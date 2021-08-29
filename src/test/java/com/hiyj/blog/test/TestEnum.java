package com.hiyj.blog.test;

import com.alibaba.fastjson.JSON;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

enum Size {
    Big, Small
}


@SpringBootTest
public class TestEnum {
    @Test
    public void testEnum() {
        Size size = JSON.parseObject("\"Big\"", Size.class);
        Size size1 = JSON.parseObject("\"Large\"", Size.class);
        System.out.println(size);
        System.out.println(size1);
    }
}
