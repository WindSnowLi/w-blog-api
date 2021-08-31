/*
 * OtherService.java, 2021 - 8 - 31
 *
 * Copyright 2021 by WindSnowLi, Inc. All rights reserved.
 */

package com.hiyj.blog.services.base;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hiyj.blog.object.CakeChartData;
import com.hiyj.blog.object.PanelData;
import com.hiyj.blog.object.base.CommentBase;
import com.hiyj.blog.services.ArticleJsonService;

import java.math.BigDecimal;
import java.util.*;

@Service("otherService")
public class OtherService {

    private ArticleJsonService articleJsonService;

    private CommentService commentService;

    private ArticleLabelService articleLabelService;

    @Autowired
    public void setArticleJsonService(ArticleJsonService articleJsonService) {
        this.articleJsonService = articleJsonService;
    }

    @Autowired
    public void setCommentService(CommentService commentService) {
        this.commentService = commentService;
    }

    @Autowired
    public void setArticleLabelService(ArticleLabelService articleLabelService) {
        this.articleLabelService = articleLabelService;
    }

    // 自定义的回调接口
    interface Call {
        int callback(String key);
    }


    /**
     * 根据数据生成Panel所需数据格式
     *
     * @param title    线型图标题
     * @param total    总数
     * @param logByDay 每日变化表
     * @return JSONObject
     */
    protected PanelData getPanelOfData(String title, int total, List<Map<String, Object>> logByDay, String timeKey) {
        JSONObject data = new JSONObject();
        ArrayList<String> dataX = new ArrayList<>();
        ArrayList<Integer> dataY = new ArrayList<>();
        if (logByDay != null && !logByDay.contains(null)) {
            for (Map<String, Object> temp : logByDay) {
                dataX.add((String) temp.get(timeKey));
                // 被BigDecimal和Long搞无语了
                dataY.add(Integer.parseInt(temp.get("total").toString()));
            }
        }
        data.put("total", total);
        data.put("title", title);
        //图从左至右，数据应逆序
        Collections.reverse(dataX);
        Collections.reverse(dataY);
        data.put("x", dataX);
        data.put("y", dataY);
        return JSONObject.parseObject(data.toJSONString(), PanelData.class);
    }


    /**
     * 获取Panel的访问量部分折线图
     *
     * @param userId 用户ID
     * @return PanelData
     */
    public PanelData getPanelOfVisits(int userId) {
        return getPanelOfData("浏览量",
                articleJsonService.getVisitsCountByUserId(userId),
                articleJsonService.getVisitLogByDay(userId),
                "day_time");
    }

    /**
     * 获取Panel的文章创建历史部分折线图
     *
     * @param userId 用户ID
     * @return PanelData
     */
    public PanelData getPanelOfArticle(int userId) {
        return getPanelOfData("创作篇",
                articleJsonService.getArticleCountByUserId(userId),
                articleJsonService.getAddArticleLogByWeek(userId),
                "week_time");
    }

    /**
     * 获取Panel的评论历史部分折线图
     *
     * @return PanelData
     */
    public PanelData getPanelOfComments() {
        return getPanelOfData("创作篇",
                commentService.getCommentCount(CommentBase.Status.ALL),
                commentService.getCommentLogByDay(10, CommentBase.Status.PASS),
                "day_time");
    }

    /**
     * 获取Panel的需要验证的评论历史部分折线图
     *
     * @return PanelData
     */
    public PanelData getPanelOfVerifyComments() {
        return getPanelOfData("创作篇",
                commentService.getCommentCount(CommentBase.Status.VERIFY),
                commentService.getCommentLogByDay(10, CommentBase.Status.VERIFY),
                "day_time");
    }

    /**
     * 获取仪表盘折线图和panel-group部分
     *
     * @param userId 用户ID
     * @return JSONObject
     */
    public JSONObject getPanel(int userId) {
        JSONObject panelJson = new JSONObject();
        panelJson.put("visits", getPanelOfVisits(userId));
        panelJson.put("articles", getPanelOfArticle(userId));
        panelJson.put("comments", getPanelOfComments());
        panelJson.put("verifyComments", getPanelOfVerifyComments());
        return panelJson;
    }

    /**
     * 获取圆饼图数据格式
     *
     * @param listData 原始list数据格式
     * @param sum      总计数，用于判断是否追加“其他”项
     * @param countKey 单个数据计数key值
     * @return CakeChartData
     */
    protected CakeChartData getCakeChartOfData(List<JSONObject> listData, int sum, String countKey) {
        // 圆饼图数据
        ArrayList<String> dataName = new ArrayList<>();
        final ArrayList<JSONObject> data = new ArrayList<>();
        int top9 = 0;
        for (JSONObject temp : listData) {
            dataName.add(temp.getString("name"));
            JSONObject single = new JSONObject();
            top9 += temp.getIntValue(countKey);
            single.put("value", temp.getIntValue(countKey));
            single.put("name", temp.getString("name"));
            data.add(single);
        }
        int other = sum - top9;
        if (data.size() == 9 && other > 0) {
            JSONObject temp = new JSONObject();
            temp.put("value", other);
            dataName.add("其它");
            temp.put("name", "其它");
            data.add(temp);
        }
        JSONObject cakeChartData = new JSONObject();
        cakeChartData.put("dataName", dataName);
        cakeChartData.put("data", data);
        return JSONObject.parseObject(cakeChartData.toJSONString(), CakeChartData.class);
    }

    /**
     * 访问量圆饼图数据
     *
     * @param userId 用户ID
     * @return CakeChartData
     */
    public CakeChartData getChartOfVisit(int userId) {
        // 访问量圆饼图数据
        return getCakeChartOfData(
                JSON.parseObject(JSONObject.toJSONString(
                        articleLabelService.getVisitCountByTypeByUserId(userId, 9)), new TypeReference<>() {
                }),
                articleJsonService.getVisitsCountByUserId(userId), "visitsCount");
    }

    /**
     * 各分类文章数量圆饼图数据
     *
     * @return CakeChartData
     */
    public CakeChartData getChartOfArticle() {
        // 各分类文章数量圆饼图数据
        return getCakeChartOfData(
                JSON.parseObject(JSONObject.toJSONString(
                        articleLabelService.getArticleCountByType(9)), new TypeReference<>() {
                }),
                articleJsonService.getArticleCountByUserId(1), "value");
    }

    /**
     * 评论组成线型图
     *
     * @return JSONObject
     */
    public JSONObject getChartOfCommentLineSeries() {
        // 评论组成线型图
        JSONObject commentLineSeries = new JSONObject();
        ArrayList<String> category = new ArrayList<>();

        for (Map<String, Object> map : commentService.getCommentLogByDay(7, CommentBase.Status.ALL)) {
            category.add((String) map.get("day_time"));
        }
        //图从左至右，数据应逆序
        Collections.reverse(category);
        commentLineSeries.put("category", category);

        CommentBase.Status[] status = new CommentBase.Status[]{CommentBase.Status.PASS, CommentBase.
                Status.VERIFY, CommentBase.Status.DELETE};
        String[] titles = {"已审核", "待审核", "已删除"};

        ArrayList<JSONObject> verifyCommentsYs = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            List<Map<String, Object>> logByDay = commentService.getCommentLogByDay(7, status[i]);
            JSONObject logData = new JSONObject();
            ArrayList<Integer> verifyCommentsY = new ArrayList<>();
            logData.put("name", titles[i]);
            for (String day : category) {
                Call call = key -> {
                    for (Map<String, Object> temp : logByDay) {
                        if (temp.get("day_time").equals(key)) {
                            return ((BigDecimal) temp.get("total")).intValue();
                        }
                    }
                    return 0;
                };
                verifyCommentsY.add(call.callback(day));
            }
            logData.put("data", verifyCommentsY);
            verifyCommentsYs.add(logData);
        }

        commentLineSeries.put("data", verifyCommentsYs);
        return commentLineSeries;
    }

    /**
     * 获取图表信息
     *
     * @param userId 用户ID
     * @return JSONObject
     */
    public JSONObject getChart(int userId) {
        JSONObject chartJson = new JSONObject();
        chartJson.put("visits", getChartOfVisit(userId));
        chartJson.put("article", getChartOfArticle());
        chartJson.put("commentLineSeries", getChartOfCommentLineSeries());
        return chartJson;
    }
}
