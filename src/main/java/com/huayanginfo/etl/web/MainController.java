package com.huayanginfo.etl.web;

import com.huayanginfo.etl.BaseController;
import com.huayanginfo.etl.annotation.RecordAccess;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author wangrd 北京华洋峻峰信息工程股份公司
 * https://www.huayanginfo.com ©2008-2021 huayanginfo.com
 * All Rights Reserved.
 * @since 2021年08月18日 星期三 11:27:09
 * 主控制类:提供访问首页,API,登录和登出逻辑
 */
@Log4j2
@Controller
public class MainController extends BaseController {
    /**
     * 系统首页访问
     *
     * @param view
     * @return
     */
    @RecordAccess
    @RequestMapping(value = {"/", "/home", "/index"}, method = {RequestMethod.GET, RequestMethod.POST})
    public String index(ModelAndView view) {
        view.addObject("welcome", "欢迎访问华洋ETL系统服务...");
        return "index";
    }

    /**
     * 跳转到swagger-ui页面
     *
     * @return
     */
    @RecordAccess
    @GetMapping(value = "/api")
    public String api() {
        return "redirect:/swagger-ui/";
    }

    @RecordAccess
    @GetMapping(value = {"/login/login"})
    public String login() {
        return "login/login";
    }
}
