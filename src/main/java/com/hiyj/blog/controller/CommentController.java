package com.hiyj.blog.controller;

import com.alibaba.fastjson.JSONObject;
import com.hiyj.blog.model.request.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.hiyj.blog.annotation.PassToken;
import com.hiyj.blog.annotation.Permission;
import com.hiyj.blog.model.response.RspCommentsModel;
import com.hiyj.blog.object.Comment;
import com.hiyj.blog.object.Msg;
import com.hiyj.blog.object.base.CommentBase;
import com.hiyj.blog.services.CommentJsonService;
import com.hiyj.blog.utils.JwtUtils;

import java.util.List;

@Slf4j
@RestController
@Api(tags = "评论相关", value = "评论相关")
@RequestMapping(value = "/api/comment", produces = "application/json;charset=UTF-8")
public class CommentController {
    private CommentJsonService commentJsonService;

    @Autowired
    public void setCommentService(CommentJsonService commentJsonService) {
        this.commentJsonService = commentJsonService;
    }

    /**
     * 添加评论
     *
     * @param tokenTypeModel 评论对象和token
     * @return Msg
     */
    @ApiOperation(value = "通过token添加评论")
    @ApiResponses({
            @ApiResponse(code = 20000, message = Msg.MSG_SUCCESS),
            @ApiResponse(code = -1, message = Msg.MSG_FAIL)
    })
    @PostMapping(value = "addComment")
    @Permission(value = {"COMMENT"})
    public String addComment(@RequestBody TokenTypeModel<Comment> tokenTypeModel) {
        int userId = JwtUtils.getTokenUserId(tokenTypeModel.getToken());
        tokenTypeModel.getContent().setFromUser(userId);
        if (tokenTypeModel.getContent().getContent() == null || tokenTypeModel.getContent().getContent().length() == 0) {
            Msg.makeJsonMsg(Msg.CODE_FAIL, "内容不可为空", null);
        }
        commentJsonService.addComment(tokenTypeModel.getContent());
        return Msg.makeJsonMsg(Msg.CODE_SUCCESS, "评论成功，审核通过后可见。", null);
    }

    /**
     * 根据评论目标ID、会话类型、评论状态获取评论
     *
     * @param reqCommentModel 目标ID、会话类型、评论状态
     * @return Msg
     */
    @ApiOperation(value = "获取评论")
    @ApiResponses({
            @ApiResponse(code = 20000, message = Msg.MSG_SUCCESS, response = RspCommentsModel.class),
            @ApiResponse(code = -1, message = Msg.MSG_FAIL)
    })
    @PostMapping(value = "getTargetComments")
    @PassToken
    public String getTargetComments(@RequestBody ReqCommentModel reqCommentModel) {
        return Msg.makeJsonMsg(Msg.CODE_SUCCESS, Msg.MSG_SUCCESS,
                commentJsonService.getTargetCommentsJson(reqCommentModel.getTargetId(),
                        reqCommentModel.getSessionType(),
                        reqCommentModel.getStatus()));
    }

    /**
     * 修改评论状态
     *
     * @param idTypeModel { id: "评论ID", content:"Status" }
     * @return Msg
     */
    @ApiOperation(value = "管理员修改评论状态")
    @ApiResponses({
            @ApiResponse(code = 20000, message = Msg.MSG_SUCCESS),
            @ApiResponse(code = -1, message = Msg.MSG_FAIL)
    })
    @PostMapping(value = "setCommentStatus")
    @Permission(value = {"VERIFY-COMMENT"})
    public String setCommentStatus(@RequestBody IdTypeModel<CommentBase.Status> idTypeModel) {
        commentJsonService.setCommentStatus(idTypeModel.getId(), idTypeModel.getContent());
        return Msg.getSuccessMsg();
    }

    /**
     * 分页获取评论列表
     *
     * @param pageModel 分页查询对象
     * @return Msg
     */
    @ApiOperation(value = "分页获取评论列表")
    @ApiResponses({
            @ApiResponse(code = 20000, message = Msg.MSG_SUCCESS),
            @ApiResponse(code = -1, message = Msg.MSG_FAIL)
    })
    @PostMapping(value = "getCommentList")
    @Permission(value = {"VERIFY-COMMENT"})
    public String getCommentListJson(@RequestBody PageModel<CommentBase.Status> pageModel) {
        return Msg.getSuccessMsg(commentJsonService.getCommentListJson(
                pageModel.getLimit(),
                (pageModel.getPage() - 1) * pageModel.getLimit(),
                pageModel.getSort(),
                pageModel.getStatus()));
    }

    /**
     * 获取所有文章最新的评论
     *
     * @param limit 条数限制 {"limit": "int"}
     * @return Comment json list
     */
    @ApiOperation(value = "获取所有文章最新的评论")
    @ApiResponses({
            @ApiResponse(code = 20000, message = Msg.MSG_SUCCESS),
            @ApiResponse(code = -1, message = Msg.MSG_FAIL)
    })
    @PostMapping(value = "getRecentComment")
    @PassToken
    public String getRecentComment(@RequestBody ContentModel<Integer> limit) {
        return Msg.getSuccessMsg(commentJsonService.getRecentComment(limit.getContent()));
    }
}
