package com.hiyj.blog.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.hiyj.blog.annotation.PassToken;
import com.hiyj.blog.annotation.Permission;
import com.hiyj.blog.model.request.TokenModel;
import com.hiyj.blog.model.request.TokenTypeModel;
import com.hiyj.blog.object.Msg;
import com.hiyj.blog.services.FileJsonService;
import com.hiyj.blog.services.UserJsonService;
import com.hiyj.blog.utils.JwtUtils;

import java.util.Map;

@Slf4j
@RestController
@Api(tags = "文件相关", value = "文件相关")
@RequestMapping(value = "/api/file", produces = "application/json;charset=UTF-8")
public class FileController {
    private FileJsonService fileJsonService;
    protected UserJsonService userJsonService;

    @Autowired
    public void setFileJsonService(FileJsonService fileJsonService) {
        this.fileJsonService = fileJsonService;
    }

    @Autowired
    public void setUserJsonService(UserJsonService userJsonService) {
        this.userJsonService = userJsonService;
    }

    /**
     * 文件上传回调
     *
     * @return 请求信息
     */
    @ApiOperation(value = "文件上传回调")
    @ApiResponses({
            @ApiResponse(code = 20000, message = Msg.MSG_SUCCESS),
            @ApiResponse(code = -1, message = Msg.MSG_FAIL)
    })
    @PostMapping(value = "callback")
    @PassToken
    public String uploadCallback(@RequestHeader Map<String, String> header, @RequestParam Map<String, String> bodyMap, @RequestBody String ossCallbackBody) {
        String fileName = bodyMap.get("fileName");
        header.put("uri", "/api/file/callback");
        int userId = JwtUtils.getTokenUserId(bodyMap.get("token"));
        if (fileJsonService.getOssUtils().verifyOssCallbackRequest(header, ossCallbackBody)) {
            return Msg.getFailMsg();
        }
        userJsonService.addUserFile(userId, fileName);
        
        return Msg.makeJsonMsg(Msg.CODE_SUCCESS, Msg.MSG_SUCCESS, null);
    }

    /**
     * 用户上传头像
     *
     * @param tokenModel {"token":"token"}
     * @return Msg  {urlParams: 上传签名Url对象, GetUrl: Get请求Url}
     */
    @ApiOperation(value = "用户上传头像")
    @ApiResponses({
            @ApiResponse(code = 20000, message = Msg.MSG_SUCCESS),
            @ApiResponse(code = -1, message = Msg.MSG_FAIL)
    })
    @PostMapping(value = "getUploadAvatarUrl")
    @Permission(value = {"UPLOAD-FILE"})
    public String getUploadAvatarUrl(@RequestBody TokenModel tokenModel) {
        int userId = JwtUtils.getTokenUserId(tokenModel.getToken());
        
        return Msg.makeJsonMsg(Msg.CODE_SUCCESS, Msg.MSG_SUCCESS, fileJsonService.getUploadAvatarMap(tokenModel.getToken()));
    }

    /**
     * 文章封面上传
     *
     * @param tokenModel {"token":"token"}
     * @return Msg  {host: 上传服务的url, urlParams: 上传签名Url对象, GetUrl: Get请求Url}
     */
    @ApiOperation(value = "文章封面上传")
    @ApiResponses({
            @ApiResponse(code = 20000, message = Msg.MSG_SUCCESS),
            @ApiResponse(code = -1, message = Msg.MSG_FAIL)
    })
    @PostMapping(value = "getUploadArticleCoverImageUrl")
    @Permission(value = {"UPLOAD-FILE"})
    public String getUploadArticleCoverImageUrl(@RequestBody TokenModel tokenModel) {
        return Msg.makeJsonMsg(Msg.CODE_SUCCESS, Msg.MSG_SUCCESS, fileJsonService.getUploadArticleCoverImageUrl(tokenModel.getToken()));
    }

    /**
     * 获取文章图片上传url和Object路径
     *
     * @param tokenModel {"token":"token"}
     * @return Msg  {host: 上传服务的url, urlParams: 上传签名Url对象, GetUrl: Get请求Url}
     */
    @ApiOperation(value = "获取文章图片上传url和Object路径")
    @ApiResponses({
            @ApiResponse(code = 20000, message = Msg.MSG_SUCCESS),
            @ApiResponse(code = -1, message = Msg.MSG_FAIL)
    })
    @PostMapping(value = "getUploadArticleImageUrl")
    @Permission(value = {"UPLOAD-FILE"})
    public String getUploadArticleImageUrl(@RequestBody TokenModel tokenModel) {
        return Msg.makeJsonMsg(Msg.CODE_SUCCESS, Msg.MSG_SUCCESS, fileJsonService.getUploadArticleImageUrl(tokenModel.getToken()));
    }

    /**
     * 删除文件
     *
     * @param tokenTypeModel {
     *                       "token":String,
     *                       "content": filePath
     *                       }
     */
    @ApiOperation(value = "删除文件")
    @ApiResponses({
            @ApiResponse(code = 20000, message = Msg.MSG_SUCCESS),
            @ApiResponse(code = -1, message = Msg.MSG_FAIL)
    })
    @PostMapping(value = "deleteObject")
    @Permission(value = {"DELETE-FILE"})
    public String deleteObject(@RequestBody TokenTypeModel<String> tokenTypeModel) {
        int userId = JwtUtils.getTokenUserId(tokenTypeModel.getToken());
        fileJsonService.deleteUserFile(userId, tokenTypeModel.getContent());
        return Msg.getSuccessMsg();
    }
}
