package com.bjpowernode.crm.workbench.web.controller;

import com.bjpowernode.crm.settings.domain.User;
import com.bjpowernode.crm.settings.service.UserService;
import com.bjpowernode.crm.settings.service.impl.UserServiceImpl;
import com.bjpowernode.crm.utils.DateTimeUtil;
import com.bjpowernode.crm.utils.PrintJson;
import com.bjpowernode.crm.utils.ProxyFactory;
import com.bjpowernode.crm.utils.UUIDUtil;
import com.bjpowernode.crm.workbench.domain.Activity;
import com.bjpowernode.crm.workbench.domain.DataListVo;
import com.bjpowernode.crm.workbench.service.ActivityService;
import com.bjpowernode.crm.workbench.service.impl.ActivityServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

public class ActivityServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String servletPath = request.getServletPath();
        if ("/workbench/Activity/getUserList.do".equals(servletPath)) {
            getUserList(request, response);
        } else if ("/workbench/Activity/saveActivity.do".equals(servletPath)) {
            saveActivity(request, response);
        } else if ("/workbench/Activity/getActivityList.do".equals(servletPath)) {
            getActivityList(request, response);
        } else if ("/workbench/Activity/deleteActivity.do".equals(servletPath)) {
            deleteActivity(request, response);
        }
    }

    private void deleteActivity(HttpServletRequest request, HttpServletResponse response) {
        String[] ids = request.getParameterValues("id");
        ActivityService proxyInstance = (ActivityService) ProxyFactory.getProxyInstance(ActivityServiceImpl.class);
        boolean flag = proxyInstance.deleteActivityAndActivityremark(ids);
        PrintJson.printJsonFlag(response, flag);
    }

    /**
     * 获取全部用户
     *
     * @param request
     * @param response
     */
    private void getUserList(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("获取用户信息列表");
        UserService proxyInstance = (UserService) ProxyFactory.getProxyInstance(UserServiceImpl.class);
        List<User> userList = proxyInstance.getUserList();
        PrintJson.printJsonObj(response, userList);
    }

    /**
     * 保存市场活动
     *
     * @param request
     * @param response
     */
    private void saveActivity(HttpServletRequest request, HttpServletResponse response) {
        String id = UUIDUtil.getUUID();
        String owner = request.getParameter("owner");
        String name = request.getParameter("name");
        String startDate = request.getParameter("startDate");
        String endDate = request.getParameter("endDate");
        String cost = request.getParameter("cost");
        String description = request.getParameter("description");
        String createTime = DateTimeUtil.getSysTime();
        String createBy = ((User) request.getSession().getAttribute("user")).getName();
        Activity activity = new Activity(id, owner, name, startDate, endDate, cost, description, createTime, createBy, null, null);
        ActivityService proxyInstance = (ActivityService) ProxyFactory.getProxyInstance(ActivityServiceImpl.class);
        boolean flag = proxyInstance.saveActivity(activity);
        PrintJson.printJsonFlag(response, flag);
    }


    /**
     * 分页获取Activity数据，用来刷新前端页面
     *
     * @param request
     * @param response
     */
    private void getActivityList(HttpServletRequest request, HttpServletResponse response) {
        Integer pageNo = Integer.valueOf(request.getParameter("pageNo"));
        Integer pageSize = Integer.valueOf(request.getParameter("pageSize"));
        String name = request.getParameter("name");
        String owner = request.getParameter("owner");
        String startDate = request.getParameter("startDate");
        String endDate = request.getParameter("endDate");

        int skipCount = (pageNo - 1) * pageSize;

        HashMap<String, Object> map = new HashMap<>();
        map.put("skipCount", skipCount);
        map.put("pageSize", pageSize);
        map.put("name", name);
        map.put("owner", owner);
        map.put("startDate", startDate);
        map.put("endDate", endDate);
        ActivityService proxyInstance = (ActivityService) ProxyFactory.getProxyInstance(ActivityServiceImpl.class);
        DataListVo<Activity> activityListVo = proxyInstance.getActivityList(map);
        System.out.println(activityListVo);
        PrintJson.printJsonObj(response, activityListVo);

    }
}
