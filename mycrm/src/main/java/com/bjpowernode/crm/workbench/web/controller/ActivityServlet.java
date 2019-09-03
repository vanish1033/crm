package com.bjpowernode.crm.workbench.web.controller;

import com.bjpowernode.crm.settings.domain.User;
import com.bjpowernode.crm.settings.service.UserService;
import com.bjpowernode.crm.settings.service.impl.UserServiceImpl;
import com.bjpowernode.crm.utils.DateTimeUtil;
import com.bjpowernode.crm.utils.PrintJson;
import com.bjpowernode.crm.utils.ProxyFactory;
import com.bjpowernode.crm.utils.UUIDUtil;
import com.bjpowernode.crm.workbench.domain.Activity;
import com.bjpowernode.crm.workbench.domain.ActivityRemark;
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
import java.util.Map;

public class ActivityServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String servletPath = request.getServletPath();
        System.out.println(servletPath);
        if ("/workbench/Activity/getUserList.do".equals(servletPath)) {
            getUserList(request, response);
        } else if ("/workbench/Activity/saveActivity.do".equals(servletPath)) {
            saveActivity(request, response);
        } else if ("/workbench/Activity/getActivityList.do".equals(servletPath)) {
            getActivityList(request, response);
        } else if ("/workbench/Activity/deleteActivity.do".equals(servletPath)) {
            deleteActivity(request, response);
        } else if ("/workbench/Activity/selectActivityAndUserList.do".equals(servletPath)) {
            selectActivityAndUserList(request, response);
        } else if ("/workbench/Activity/updateActivity.do".equals(servletPath)) {
            updateActivity(request, response);
        } else if ("/workbench/activity/detail.do".equals(servletPath)) {
            detail(request, response);
        } else if ("/workbench/activity/getRemarkListByAid.do".equals(servletPath)) {
            getRemarkListByAid(request, response);
        } else if ("/workbench/activity/deleteRemark.do".equals(servletPath)) {
            deleteRemark(request, response);
        } else if ("/workbench/activity/updateRemark.do".equals(servletPath)) {
            updateRemark(request, response);
        } else if ("/workbench/activity/saveRemark.do".equals(servletPath)) {
            saveRemark(request, response);
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

        //<editor-fold desc="获取参数">
        String id = UUIDUtil.getUUID();
        String owner = request.getParameter("owner");
        String name = request.getParameter("name");
        String startDate = request.getParameter("startDate");
        String endDate = request.getParameter("endDate");
        String cost = request.getParameter("cost");
        String description = request.getParameter("description");
        String createTime = DateTimeUtil.getSysTime();
        String createBy = ((User) request.getSession().getAttribute("user")).getName();
        //</editor-fold>

        // 将参数封装到Activity中
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
        //<editor-fold desc="获取前端传来的数据">
        Integer pageNo = Integer.valueOf(request.getParameter("pageNo"));
        Integer pageSize = Integer.valueOf(request.getParameter("pageSize"));
        String name = request.getParameter("name");
        String owner = request.getParameter("owner");
        String startDate = request.getParameter("startDate");
        String endDate = request.getParameter("endDate");
        //</editor-fold>

        int skipCount = (pageNo - 1) * pageSize;

        HashMap<String, Object> map = new HashMap<>();
        //<editor-fold desc="将前端传来的数据封装到map里">
        map.put("skipCount", skipCount);
        map.put("pageSize", pageSize);
        map.put("name", name);
        map.put("owner", owner);
        map.put("startDate", startDate);
        map.put("endDate", endDate);
        //</editor-fold>

        ActivityService proxyInstance = (ActivityService) ProxyFactory.getProxyInstance(ActivityServiceImpl.class);
        DataListVo<Activity> activityListVo = proxyInstance.getActivityList(map);
        PrintJson.printJsonObj(response, activityListVo);

    }

    /**
     * 查询userList和Activity放到修改Activity的模态窗口里
     *
     * @param request
     * @param response
     */
    private void selectActivityAndUserList(HttpServletRequest request, HttpServletResponse response) {
        String id = request.getParameter("id");
        ActivityService proxyInstance = (ActivityService) ProxyFactory.getProxyInstance(ActivityServiceImpl.class);
        Map<String, Object> map = proxyInstance.selectActivityAndUserList(id);
        PrintJson.printJsonObj(response, map);
    }

    /**
     * 修改Activity的信息
     *
     * @param request
     * @param response
     */
    private void updateActivity(HttpServletRequest request, HttpServletResponse response) {
        // 取值
        String id = request.getParameter("id");
        String owner = request.getParameter("owner");
        String name = request.getParameter("name");
        String startDate = request.getParameter("startDate");
        String endDate = request.getParameter("endDate");
        String cost = request.getParameter("cost");
        String description = request.getParameter("describe");

        // 封装为Activity对象
        Activity activity = new Activity();
        activity.setId(id);
        activity.setOwner(owner);
        activity.setName(name);
        activity.setStartDate(startDate);
        activity.setEndDate(endDate);
        activity.setCost(cost);
        activity.setDescription(description);
        activity.setEditTime(DateTimeUtil.getSysTime());
        activity.setEditBy(((User) request.getSession().getAttribute("user")).getName());

        ActivityService proxyInstance = (ActivityService) ProxyFactory.getProxyInstance(ActivityServiceImpl.class);
        boolean flag = proxyInstance.updateActivity(activity);
        PrintJson.printJsonFlag(response, flag);
    }

    /**
     * 展示市场活动详情页面
     *
     * @param request
     * @param response
     */
    private void detail(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("备注");
        String id = request.getParameter("id");

        ActivityService proxyInstance = (ActivityService) ProxyFactory.getProxyInstance(ActivityServiceImpl.class);

        Activity a = proxyInstance.detail(id);

        request.setAttribute("a", a);

        System.out.println("备注" + a);
        request.getRequestDispatcher("/workbench/activity/detail.jsp").forward(request, response);
    }

    private void getRemarkListByAid(HttpServletRequest request, HttpServletResponse response) {
        String aid = request.getParameter("Aid");
        ActivityService proxyInstance = (ActivityService) ProxyFactory.getProxyInstance(ActivityServiceImpl.class);
        List<ActivityRemark> remarkList = proxyInstance.getRemarkListByAid(aid);
        PrintJson.printJsonObj(response, remarkList);
    }

    /**
     * 删除市场活动备注
     *
     * @param request
     * @param response
     */
    private void deleteRemark(HttpServletRequest request, HttpServletResponse response) {
        String rid = request.getParameter("rid");
        ActivityService proxyInstance = (ActivityService) ProxyFactory.getProxyInstance(ActivityServiceImpl.class);
        boolean flag = proxyInstance.deleteRemark(rid);
        PrintJson.printJsonFlag(response, flag);
    }

    private void updateRemark(HttpServletRequest request, HttpServletResponse response) {
        String id = request.getParameter("id");
        String noteContent = request.getParameter("noteContent");
        String editTime = DateTimeUtil.getSysTime();
        String userName = ((User) request.getSession().getAttribute("user")).getName();
        ActivityRemark activityRemark = new ActivityRemark();

        activityRemark.setEditFlag("1");
        activityRemark.setEditTime(editTime);
        activityRemark.setEditBy(((User) request.getSession().getAttribute("user")).getName());

        ActivityService proxyInstance = (ActivityService) ProxyFactory.getProxyInstance(ActivityServiceImpl.class);
        boolean flag = proxyInstance.updateRemark(id, noteContent, editTime, userName);

        HashMap<String, Object> map = new HashMap<>();
        map.put("success", flag);
        map.put("ar", activityRemark);

        PrintJson.printJsonObj(response, map);
    }


    private void saveRemark(HttpServletRequest request, HttpServletResponse response) {

        String activityId = request.getParameter("activityId");
        String noteContent = request.getParameter("noteContent");
        String id = UUIDUtil.getUUID();
        String createTime = DateTimeUtil.getSysTime();
        String createBy = ((User) request.getSession().getAttribute("user")).getName();
        String editFlag = "0";

        ActivityRemark activityRemark = new ActivityRemark(id, noteContent, createTime, createBy, null, null, editFlag, activityId);


        ActivityService proxyInstance = (ActivityService) ProxyFactory.getProxyInstance(ActivityServiceImpl.class);
        boolean flag = proxyInstance.saveRemark(activityRemark);
        Map<String, Object> map = new HashMap<>();
        map.put("success", flag);
        map.put("ar", activityRemark);
        PrintJson.printJsonObj(response, map);
    }
}



