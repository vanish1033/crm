package com.bjpowernode.crm.workbench.web.controller;

import com.bjpowernode.crm.settings.domain.User;
import com.bjpowernode.crm.settings.service.UserService;
import com.bjpowernode.crm.settings.service.impl.UserServiceImpl;
import com.bjpowernode.crm.utils.PrintJson;
import com.bjpowernode.crm.utils.ProxyFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class ClueServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String servletPath = request.getServletPath();
        System.out.println(servletPath);
        if ("/workbench/clue/getUserList.do".equals(servletPath)) {
            getUserList(request, response);
        }
    }

    private void getUserList(HttpServletRequest request, HttpServletResponse response) {
        UserService proxyInstance = (UserService) ProxyFactory.getProxyInstance(UserServiceImpl.class);
        List<User> userList = proxyInstance.getUserList();
        PrintJson.printJsonObj(response, userList);
    }
}
