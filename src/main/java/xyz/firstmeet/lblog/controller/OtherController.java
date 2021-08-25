package xyz.firstmeet.lblog.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import xyz.firstmeet.lblog.annotation.PassToken;
import xyz.firstmeet.lblog.model.request.TokenModel;
import xyz.firstmeet.lblog.object.Msg;
import xyz.firstmeet.lblog.services.OtherJsonService;
import xyz.firstmeet.lblog.utils.JwtUtils;

@Slf4j
@RestController
@RequestMapping(value = "/api/other", produces = "application/json;charset=UTF-8")
public class OtherController {
    private OtherJsonService otherJsonService;

    @Autowired
    public void setOtherJsonService(OtherJsonService otherJsonService) {
        this.otherJsonService = otherJsonService;
    }

    /**
     * 获取仪表盘折线图和panel-group部分
     *
     * @param tokenModel 验证信息
     * @return Msg
     */
    @PostMapping(value = "getPanel")
    @ResponseBody
    @PassToken
    public String getPanel(@RequestBody TokenModel tokenModel) {
        int userId = JwtUtils.getTokenUserId(tokenModel.getToken());
        log.info("getPanel");
        if (userId != 1) {
            Msg.getFailMsg();
        }
        return Msg.makeJsonMsg(Msg.CODE_SUCCESS, Msg.MSG_SUCCESS, otherJsonService.getPanel(userId));
    }

    /**
     * 获取图表信息
     *
     * @param tokenModel 验证信息
     * @return Msg
     */
    @PostMapping(value = "getChart")
    @ResponseBody
    @PassToken
    public String getChart(@RequestBody TokenModel tokenModel) {
        int userId = JwtUtils.getTokenUserId(tokenModel.getToken());
        log.info("getChart");
        if (userId != 1) {
            Msg.getFailMsg();
        }
        return Msg.makeJsonMsg(Msg.CODE_SUCCESS, Msg.MSG_SUCCESS, otherJsonService.getChart(userId));
    }
}
