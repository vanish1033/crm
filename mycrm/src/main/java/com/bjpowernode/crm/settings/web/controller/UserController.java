package com.bjpowernode.crm.settings.web.controller;

import com.bjpowernode.crm.exception.LoginException;
import com.bjpowernode.crm.settings.domain.User;
import com.bjpowernode.crm.settings.service.UserService;
import com.bjpowernode.crm.settings.service.impl.UserServiceImpl;
import com.bjpowernode.crm.utils.MD5Util;
import com.bjpowernode.crm.utils.PrintJson;
import com.bjpowernode.crm.utils.ProxyFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;

/**
 * @author:whr 2019/8/29
 */
public class UserController extends HttpServlet {
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String servletPath = request.getServletPath();
        if ("/settings/user/login.do".equals(servletPath)) {
            login(request, response);
        } else if ("/settings/user/xxx.do".equals(servletPath)) {
            method2();
        }
    }

    private void login(HttpServletRequest request, HttpServletResponse response) {
//        接收账号密码
        String loginAct = request.getParameter("loginAct");
        String loginPwd = request.getParameter("loginPwd");
        String ip = request.getRemoteAddr();
        loginPwd = MD5Util.getMD5(loginPwd);
        UserService proxyInstance = (UserService) ProxyFactory.getProxyInstance(UserServiceImpl.class);
        try {
            User user = proxyInstance.login(loginAct, loginPwd, ip);
            request.getSession().setAttribute("user", user);
            PrintJson.printJsonFlag(response, true);

        } catch (LoginException e) {
            e.printStackTrace();
            //登录失败
            //{"success":false,"msg":?}

//            需要同时为前端提供两个或两个以上的值
//
//            做法1：
//            我们可以将这些值保存到一个map对象，返回map
//
//            做法2：
//            我们可以创建一个VO类，将信息封装到vo对象中，返回vo
            HashMap<String, Object> map = new HashMap<>();
            map.put("success", false);
            map.put("message", e.getMessage());
            PrintJson.printJsonObj(response, map);
        }
    }

    private void method2() {

    }
}
