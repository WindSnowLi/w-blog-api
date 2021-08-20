package xyz.firstmeet.lblog.services;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import org.springframework.stereotype.Service;
import xyz.firstmeet.lblog.object.Comment;
import xyz.firstmeet.lblog.object.base.CommentBase;
import xyz.firstmeet.lblog.services.base.CommentService;

import java.util.List;

@Service("commentJsonService")
public class CommentJsonService extends CommentService {

    //无奈的回调
    interface Call {
        //回调
        void callback(Object o);
    }

    /**
     * 评论对象列表并把用户ID转换为对象
     *
     * @param targetId    评论的目标ID
     * @param sessionType 会话类型
     * @param status      评论状态
     * @return 评论对象列表 Json
     */
    public JSONArray getTargetCommentsJson(Integer targetId, CommentBase.SessionType sessionType, CommentBase.Status status) {
        JSONArray jsonArray = JSONArray.parseArray(JSONObject.toJSONString(getTargetComments(targetId, sessionType, status), SerializerFeature.WriteMapNullValue));
        //Test使用ValueFilter一直没问题，但是从控制层调用一直错误，无奈自己回调
        Call call = new Call() {
            @Override
            public void callback(Object o) {
                JSONObject comment = (JSONObject) o;
                if (comment.containsKey("fromUser") && comment.getInteger("fromUser") != null) {
                    comment.put("fromUser", userService.findUserById(comment.getInteger("fromUser")));
                }
                if (comment.containsKey("toUser") && comment.getInteger("toUser") != null) {
                    comment.put("toUser", userService.findUserById(comment.getInteger("toUser")));
                }
                if (comment.containsKey("parentId") && comment.get("parentId") != null) {
                    comment.put("parentId", findComment(comment.getInteger("parentId")));
                }
                if (comment.containsKey("childList") && comment.getJSONArray("childList").size() != 0) {
                    for (Object temp : comment.getJSONArray("childList")) {
                        JSONObject tempComment = (JSONObject) temp;
                        this.callback(tempComment);
                    }
                }
            }
        };
        for (Object o : jsonArray) {
            JSONObject comment = (JSONObject) o;
            call.callback(comment);
        }
        return jsonArray;
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
    public JSONArray getCommentListJson(int limit, int offset, String sort, CommentBase.Status status) {
        List<Comment> commentList = getCommentList(limit, offset, sort, status);
        JSONArray jsonArray = JSONArray.parseArray(JSONArray.toJSONString(commentList));
        for (Object o : jsonArray) {
            ((JSONObject) o).put("fromUser", userService.findUserById(((JSONObject) o).getInteger("fromUser")));
            if (((JSONObject) o).containsKey("toUser") && ((JSONObject) o).getInteger("toUser") != null) {
                ((JSONObject) o).put("toUser", userService.findUserById(((JSONObject) o).getInteger("toUser")));
            }
        }
        return jsonArray;
    }
}
