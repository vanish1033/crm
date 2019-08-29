package com.bjpowernode.crm.settings.web.controller;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author:whr 2019/8/29
 */
public class UserController extends HttpServlet {
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String servletPath = request.getServletPath();
        if ("/settings/user/xxx.do".equals(servletPath)) {
            method1();
        } else if ("/settings/user/xxx.do".equals(servletPath)) {
            method2();
        }
    }

    private void method2() {

    }

    private void method1() {

    }
}
