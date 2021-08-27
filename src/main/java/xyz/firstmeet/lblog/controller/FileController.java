package xyz.firstmeet.lblog.controller;

import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import xyz.firstmeet.lblog.annotation.PassToken;
import xyz.firstmeet.lblog.annotation.Permission;
import xyz.firstmeet.lblog.object.Msg;
import xyz.firstmeet.lblog.services.FileJsonService;
import xyz.firstmeet.lblog.services.UserJsonService;
import xyz.firstmeet.lblog.utils.JwtUtils;

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
    @PostMapping(value = "callback")
    @ResponseBody
    @PassToken
    public String uploadCallback(@RequestHeader Map<String, String> header, @RequestParam Map<String, String> bodyMap, @RequestBody String ossCallbackBody) {
        String fileName = bodyMap.get("fileName");
        header.put("uri", "/api/file/callback");
        int userId = JwtUtils.getTokenUserId(bodyMap.get("token"));
        if (fileJsonService.getOssUtils().verifyOssCallbackRequest(header, ossCallbackBody)) {
            return Msg.getFailMsg();
        }
        userJsonService.addUserFile(userId, fileName);
        log.info("upload file\t用户ID：{}\t文件：{}", userId, fileName);
        return Msg.makeJsonMsg(Msg.CODE_SUCCESS, Msg.MSG_SUCCESS, null);
    }

    /**
     * 用户上传头像
     *
     * @param tokenJson {"token":"token"}
     * @return Msg  {urlParams: 上传签名Url对象, GetUrl: Get请求Url}
     */
    @PostMapping(value = "getUploadAvatarUrl")
    @Permission(value = {"UPLOAD-FILE"})
    public String getUploadAvatarUrl(@RequestBody JSONObject tokenJson) {
        int userId = JwtUtils.getTokenUserId(tokenJson.getString("token"));
        log.info("getUploadAvatarUrl\t用户ID：{}", userId);
        return Msg.makeJsonMsg(Msg.CODE_SUCCESS, Msg.MSG_SUCCESS, fileJsonService.getUploadAvatarMap(tokenJson.getString("token")));
    }

    /**
     * 文章封面上传
     *
     * @param tokenJson {"token":"token"}
     * @return Msg  {host: 上传服务的url, urlParams: 上传签名Url对象, GetUrl: Get请求Url}
     */
    @PostMapping(value = "getUploadArticleCoverImageUrl")
    @Permission(value = {"UPLOAD-FILE"})
    public String getUploadArticleCoverImageUrl(@RequestBody JSONObject tokenJson) {
        int userId = JwtUtils.getTokenUserId(tokenJson.getString("token"));
        log.info("getUploadArticleCoverImageUrl\t用户ID：{}", userId);
        return Msg.makeJsonMsg(Msg.CODE_SUCCESS, Msg.MSG_SUCCESS, fileJsonService.getUploadArticleCoverImageUrl(tokenJson.getString("token")));
    }

    /**
     * 获取文章图片上传url和Object路径
     *
     * @param tokenJson {"token":"token"}
     * @return Msg  {host: 上传服务的url, urlParams: 上传签名Url对象, GetUrl: Get请求Url}
     */
    @PostMapping(value = "getUploadArticleImageUrl")
    @Permission(value = {"UPLOAD-FILE"})
    public String getUploadArticleImageUrl(@RequestBody JSONObject tokenJson) {
        int userId = JwtUtils.getTokenUserId(tokenJson.getString("token"));
        log.info("getUploadArticleImageUrl\t用户ID：{}", userId);
        return Msg.makeJsonMsg(Msg.CODE_SUCCESS, Msg.MSG_SUCCESS, fileJsonService.getUploadArticleImageUrl(tokenJson.getString("token")));
    }

    /**
     * 删除文件
     *
     * @param json {"token":String, objectName: filePath}
     */
    @PostMapping(value = "deleteObject")
    @Permission(value = {"DELETE-FILE"})
    public String deleteObject(@RequestBody JSONObject json) {
        int userId = JwtUtils.getTokenUserId(json.getString("token"));
        log.info("deleteObject\t用户ID：{}", userId);
        fileJsonService.deleteUserFile(userId, json.getString("objectName"));
        return Msg.getSuccessMsg();
    }
}
