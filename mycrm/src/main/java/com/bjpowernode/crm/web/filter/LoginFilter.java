package com.bjpowernode.crm.web.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LoginFilter implements Filter {

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {

        // 和登录相关的需求直接放行
        if ("/settings/user/login.do".equals(((HttpServletRequest) req).getServletPath()) ||
                "/login.jsp".equals(((HttpServletRequest) req).getServletPath())) {
//            System.out.println("放行login");
            chain.doFilter(req, resp);
            return;
        }

        // 如果有session也放行
        if (((HttpServletRequest) req).getSession(false) != null) {
            chain.doFilter(req, resp);
//            System.out.println("放行session");
            return;
        }

        // 打回登录页面
        ((HttpServletResponse) resp).sendRedirect(((HttpServletRequest) req).getContextPath() + "/login.jsp");
    }

}
