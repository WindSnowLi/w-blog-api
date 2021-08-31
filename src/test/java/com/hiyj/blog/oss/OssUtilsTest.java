package com.hiyj.blog.oss;

import com.hiyj.blog.services.base.FileService;
import com.hiyj.blog.utils.CodeUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;

@SpringBootTest
public class OssUtilsTest {
    private FileService fileService;

    @Autowired
    public void setFileService(FileService fileService) {
        this.fileService = fileService;
    }


    /**
     * 获取PUT上传签名Url
     */
    @Test
    public void testGetUploadUrl() {
//        OssUtils ossUtils = fileService.getOssUtils();
//        HashMap<String, String> headMap = new HashMap<>();
//        final HashMap<String, Object> uploadUrl = ossUtils.getUploadUrl("Blog/image/avatar/" + CodeUtils.getUUID(), false);
//
//        System.out.println(uploadUrl);
//
//        String url = "http://windsnowli.oss-cn-beijing.aliyuncs.com/Blog/image/avatar/b5af4dc0-dd32-11eb-973a-43ad923ac904?Expires=1625453143&OSSAccessKeyId=LTAI4GGJHNi11CxiSdxvq3QB&Signature=zek6pEPo5rksfvfVV1tX2ZrW8%2B4%3D";
//        String objectName = uploadUrl.get("GetUrl");
//        try {
//            ossUtils.uploadBySignedUrl(url, "E:\\Desktop\\test.txt", headMap);
//        } catch (FileNotFoundException | MalformedURLException e) {
//            e.printStackTrace();
//        }

    }
}
