package com.hiyj.blog.mapper;

import com.alibaba.fastjson.JSON;
import com.hiyj.blog.object.Comment;
import com.hiyj.blog.object.base.CommentBase;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class CommentMapperTest {
    private CommentMapper commentMapper;

    @Autowired
    public void setCommentMapper(CommentMapper commentMapper) {
        this.commentMapper = commentMapper;
    }

    /**
     * 添加评论
     */
    @Test
    public void testAddComment() {
        Comment comment = new Comment();
        comment.setTargetId(90);
        comment.setContent("评论测试");
        comment.setFromUser(1);
        comment.setStatus(CommentBase.Status.PASS);
        comment.setSessionType(CommentBase.SessionType.ARTICLE);
        commentMapper.addComment(comment);
    }

    /**
     * 获取目标评论
     */
    @Test
    public void testGetTargetComments() {
        System.out.println(commentMapper.getTargetComments(90, CommentBase.SessionType.ARTICLE, CommentBase.Status.ALL));
    }

    /**
     * 逆序分页获取文章
     */
    @Test
    public void testGetCommentList() {
        System.out.println(JSON.toJSONString(commentMapper.getCommentList(5, 2 * 5, "-id", CommentBase.Status.ALL)));
    }

    /**
     * 获取最近评论趋势
     */
    @Test
    public void testGetCommentLogByDay() {
        System.out.println(commentMapper.getCommentLogByDay(10, CommentBase.Status.PASS));
        System.out.println(commentMapper.getCommentLogByDay(10, CommentBase.Status.VERIFY));
        System.out.println(commentMapper.getCommentLogByDay(10, CommentBase.Status.DELETE));
        System.out.println(commentMapper.getCommentLogByDay(10, CommentBase.Status.ALL));
    }
}
