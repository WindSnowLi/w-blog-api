package com.hiyj.blog.services;

import com.alibaba.fastjson.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import com.hiyj.blog.model.request.ReqCommentModel;
import com.hiyj.blog.object.Msg;
import com.hiyj.blog.object.base.CommentBase;

@SpringBootTest
public class CommentServiceTest {
    private CommentJsonService commentJsonService;

    @Autowired
    public void setCommentService(CommentJsonService commentJsonService) {
        this.commentJsonService = commentJsonService;
    }

    /**
     * 获取目标的评论
     */
    @Test
    public void testGetTargetComments() {
        ReqCommentModel reqCommentModel = JSONObject.parseObject("{ \"sessionType\": \"ARTICLE\", \"status\": \"PASS\", \"targetId\": 85 }", ReqCommentModel.class);
        System.out.println(Msg.makeJsonMsg(Msg.CODE_SUCCESS, Msg.MSG_SUCCESS,
                commentJsonService.getTargetCommentsJson(reqCommentModel.getTargetId(),
                        reqCommentModel.getSessionType(),
                        reqCommentModel.getStatus())));
    }

    /**
     * 获取评论对象列表
     */
    @Test
    public void testGetCommentListJson() {
        System.out.println(commentJsonService.getCommentListJson(5, 2 * 5, "-id", CommentBase.Status.ALL));
    }
}
