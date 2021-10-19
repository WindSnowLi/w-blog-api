package com.hiyj.blog.mapper;

import com.hiyj.blog.object.Comment;
import com.hiyj.blog.object.base.CommentBase;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Mapper
@Repository
public interface CommentMapper {
    /**
     * 添加评论
     *
     * @param comment 评论对象
     */
    void addComment(@Param("comment") Comment comment);

    /**
     * 获取目标评论
     *
     * @param targetId    评论的目标ID
     * @param sessionType 会话类型
     * @param status      评论状态
     * @return 评论对象列表
     */
    List<Comment> getTargetComments(@Param("targetId") Integer targetId, @Param("sessionType") CommentBase.SessionType sessionType, @Param("status") CommentBase.Status status);


    /**
     * 根据记录ID查找某一条记录
     *
     * @param commentId 记录ID
     * @return Comment对象
     */
    Comment findComment(@Param("commentId") int commentId);

    /**
     * 设置评论状态
     *
     * @param commentId 评论ID
     * @param status    新状态
     */
    void setCommentStatus(@Param("commentId") int commentId, @Param("status") CommentBase.Status status);

    /**
     * 逆序分页获取文章
     *
     * @param limit  限制量
     * @param offset 偏移量
     * @param sort   排序方式 默认-id,
     * @param status 评论状态
     * @return 评论列表
     */
    List<Comment> getCommentList(@Param("limit") int limit, @Param("offset") int offset, @Param("sort") String sort, @Param("status") CommentBase.Status status);

    /**
     * 获取最近评论趋势
     *
     * @param limit  限制最近多少天，没评论的天忽略不计
     * @param status 查询的评论状态
     * @return List<Map>, 日期数值键值对{total=int, day_time=String}
     */
    List<Map<String, Object>> getCommentLogByDay(@Param("limit") int limit, @Param("status") CommentBase.Status status);

    /**
     * 获取当前状态评论总量
     *
     * @param status 评论状态
     * @return 总数
     */
    int getCommentCount(@Param("status") CommentBase.Status status);

    /**
     * 获取所有文章最新的评论
     *
     * @param limit 条数限制
     * @return Comment list
     */
    List<Comment> getRecentComment(int limit);
}
