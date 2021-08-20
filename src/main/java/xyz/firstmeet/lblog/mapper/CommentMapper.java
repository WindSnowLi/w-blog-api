package xyz.firstmeet.lblog.mapper;

import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;
import xyz.firstmeet.lblog.object.Comment;
import xyz.firstmeet.lblog.object.base.CommentBase;

import java.util.List;

@Mapper
@Repository
public interface CommentMapper {
    /**
     * 添加评论
     *
     * @param comment 评论对象
     */
    @Insert("INSERT INTO comment" +
            "(target_id, session_type, from_user, parent_id, content, to_user)" +
            "VALUES(" +
            "#{comment.targetId}, #{comment.sessionType}, #{comment.fromUser}, " +
            "#{comment.parentId}, #{comment.content}, #{comment.toUser}" +
            ");")
    void addComment(@Param("comment") Comment comment);

    /**
     * 获取目标评论
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
    @Update("UPDATE comment SET status=#{status} WHERE id=#{commentId}")
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
}
