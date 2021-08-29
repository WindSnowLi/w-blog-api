package com.hiyj.blog.services;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class FileServiceTest {

    private FileJsonService fileJsonService;

    @Autowired
    public void setFileJsonService(FileJsonService fileJsonService) {
        this.fileJsonService = fileJsonService;
    }

    @Test
    public void testGetUploadAvatarMap() {
        System.out.println(fileJsonService.getUploadAvatarMap("token"));
    }
}
