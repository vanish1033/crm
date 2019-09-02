package com.bjpowernode.crm.workbench.web.controller;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ClueServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String servletPath = request.getServletPath();
        System.out.println(servletPath);
        if ("/workbench/Clue/xxx.do".equals(servletPath)) {
            xxx(request, response);
        }
    }

    private void xxx(HttpServletRequest request, HttpServletResponse response) {


    }
}
