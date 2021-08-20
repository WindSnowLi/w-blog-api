package xyz.firstmeet.lblog.services.base;

import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.firstmeet.lblog.mapper.CommentMapper;
import xyz.firstmeet.lblog.model.response.RspCommentsModel;
import xyz.firstmeet.lblog.object.Comment;
import xyz.firstmeet.lblog.object.base.CommentBase;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service("commentService")
public class CommentService {
    protected CommentMapper commentMapper;

    @Autowired
    public void setCommentMapper(CommentMapper commentMapper) {
        this.commentMapper = commentMapper;
    }

    protected UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    /**
     * 添加评论
     *
     * @param comment 评论对象
     */
    public void addComment(Comment comment) {
        commentMapper.addComment(comment);
    }

    /**
     * @param targetId    评论的目标ID
     * @param sessionType 会话类型
     * @param status      评论状态
     * @return 评论对象列表
     */
    public List<RspCommentsModel> getTargetComments(Integer targetId, CommentBase.SessionType sessionType, CommentBase.Status status) {
        List<Comment> allComments = commentMapper.getTargetComments(targetId, sessionType, status);
        //筛选独立评论
        List<Comment> parentComments = allComments.stream().filter((Comment comment) ->
                comment.getParentId() == null
        ).collect(Collectors.toList());
        //筛选各独立评论的子评论
        ArrayList<RspCommentsModel> rspCommentsModels = new ArrayList<>();
        for (Comment comment : parentComments) {
            RspCommentsModel rspCommentsModel = JSONObject.parseObject(JSONObject.toJSONString(comment), RspCommentsModel.class);
            rspCommentsModel.setChildList(
                    allComments.stream()
                            .filter((Comment temp) -> temp.getParentId() != null &&
                                    temp.getParentId() == comment.getId())
                            .collect(Collectors.toList()));
            rspCommentsModels.add(rspCommentsModel);
        }
        return rspCommentsModels;
    }

    /**
     * 根据记录ID查找某一条记录
     *
     * @param commentId 记录ID
     * @return Comment对象
     */
    public Comment findComment(int commentId) {
        return commentMapper.findComment(commentId);
    }

    /**
     * 设置评论状态
     *
     * @param commentId 评论ID
     * @param status    新状态
     */
    public void setCommentStatus(int commentId, CommentBase.Status status) {
        commentMapper.setCommentStatus(commentId, status);
    }

    /**
     * 逆序分页获取文章
     *
     * @param limit  限制量
     * @param offset 偏移量
     * @param sort   排序方式 默认-id,
     * @param status 评论状态
     * @return 评论列表
     */
    public List<Comment> getCommentList(int limit, int offset, String sort, CommentBase.Status status) {
        return commentMapper.getCommentList(limit, offset, sort, status);
    }
}
