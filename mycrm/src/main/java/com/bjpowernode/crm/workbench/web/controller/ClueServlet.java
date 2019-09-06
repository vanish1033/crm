package com.bjpowernode.crm.workbench.web.controller;

import com.bjpowernode.crm.settings.domain.User;
import com.bjpowernode.crm.settings.service.UserService;
import com.bjpowernode.crm.settings.service.impl.UserServiceImpl;
import com.bjpowernode.crm.utils.DateTimeUtil;
import com.bjpowernode.crm.utils.PrintJson;
import com.bjpowernode.crm.utils.ProxyFactory;
import com.bjpowernode.crm.utils.UUIDUtil;
import com.bjpowernode.crm.workbench.domain.Activity;
import com.bjpowernode.crm.workbench.domain.Clue;
import com.bjpowernode.crm.workbench.domain.Tran;
import com.bjpowernode.crm.workbench.service.ActivityService;
import com.bjpowernode.crm.workbench.service.ClueService;
import com.bjpowernode.crm.workbench.service.impl.ActivityServiceImpl;
import com.bjpowernode.crm.workbench.service.impl.ClueServiceImpl;

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
        } else if ("/workbench/clue/saveClud.do".equals(servletPath)) {
            saveClud(request, response);
        } else if ("/workbench/clue/getClueList.do".equals(servletPath)) {
            getClueList(request, response);
        } else if ("/workbench/clue/detail.do".equals(servletPath)) {
            detail(request, response);
        } else if ("/workbench/clue/getActivityBycid.do".equals(servletPath)) {
            getActivityBycid(request, response);
        } else if ("/workbench/clue/deleteCarById.do".equals(servletPath)) {
            deleteCarById(request, response);
        } else if ("/workbench/clue/searchActivity.do".equals(servletPath)) {
            searchActivity(request, response);
        } else if ("/workbench/clue/bund.do".equals(servletPath)) {
            bund(request, response);
        } else if ("/workbench/clue/searchActivityByNameVague.do".equals(servletPath)) {
            searchActivityByNameVague(request, response);
        } else if ("/workbench/clue/convert.do".equals(servletPath)) {
            convert(request, response);
        }
    }

    private void getUserList(HttpServletRequest request, HttpServletResponse response) {
        UserService proxyInstance = (UserService) ProxyFactory.getProxyInstance(UserServiceImpl.class);
        List<User> userList = proxyInstance.getUserList();
        PrintJson.printJsonObj(response, userList);
    }

    private void saveClud(HttpServletRequest request, HttpServletResponse response) {

        //<editor-fold desc="接收参数并set到Clue对象中">
        String id = UUIDUtil.getUUID();
        String fullname = request.getParameter("fullname");
        String appellation = request.getParameter("appellation");
        String owner = request.getParameter("owner");
        String company = request.getParameter("company");
        String job = request.getParameter("job");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");
        String website = request.getParameter("website");
        String mphone = request.getParameter("mphone");
        String state = request.getParameter("state");
        String source = request.getParameter("source");
        String description = request.getParameter("description");
        String contactSummary = request.getParameter("contactSummary");
        String nextContactTime = request.getParameter("nextContactTime");
        String address = request.getParameter("address");
        String createBy = ((User) request.getSession().getAttribute("user")).getName();
        String createTime = DateTimeUtil.getSysTime();

        Clue clue = new Clue();
        clue.setId(id);
        clue.setFullname(fullname);
        clue.setAppellation(appellation);
        clue.setOwner(owner);
        clue.setCompany(company);
        clue.setJob(job);
        clue.setEmail(email);
        clue.setPhone(phone);
        clue.setWebsite(website);
        clue.setMphone(mphone);
        clue.setState(state);
        clue.setSource(source);
        clue.setDescription(description);
        clue.setContactSummary(contactSummary);
        clue.setNextContactTime(nextContactTime);
        clue.setAddress(address);
        clue.setCreateBy(createBy);
        clue.setCreateTime(createTime);
        //</editor-fold>

        ClueService proxyInstance = (ClueService) ProxyFactory.getProxyInstance(ClueServiceImpl.class);
        boolean flag = proxyInstance.saveClud(clue);
        PrintJson.printJsonFlag(response, flag);
    }

    /**
     * 获取线索页面下方的线索列表
     *
     * @param request
     * @param response
     */
    private void getClueList(HttpServletRequest request, HttpServletResponse response) {

        ClueService proxyInstance = (ClueService) ProxyFactory.getProxyInstance(ClueServiceImpl.class);
        List<Clue> clueList = proxyInstance.getClueList();
        PrintJson.printJsonObj(response, clueList);
    }

    private void detail(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String cid = request.getParameter("cid");

        ClueService proxyInstance = (ClueService) ProxyFactory.getProxyInstance(ClueServiceImpl.class);
        Clue clue = proxyInstance.detail(cid);
        request.setAttribute("clue", clue);
        request.getRequestDispatcher("/workbench/clue/detail.jsp").forward(request, response);
    }

    private void getActivityBycid(HttpServletRequest request, HttpServletResponse response) {
        String cid = request.getParameter("cid");

        ClueService proxyInstance = (ClueService) ProxyFactory.getProxyInstance(ClueServiceImpl.class);

        List<Activity> activityList = proxyInstance.getActivityBycid(cid);

        PrintJson.printJsonObj(response, activityList);
    }

    private void deleteCarById(HttpServletRequest request, HttpServletResponse response) {
        String id = request.getParameter("id");
        ClueService proxyInstance = (ClueService) ProxyFactory.getProxyInstance(ClueServiceImpl.class);
        boolean flag = proxyInstance.deleteCarById(id);
        PrintJson.printJsonFlag(response, flag);
    }

    private void searchActivity(HttpServletRequest request, HttpServletResponse response) {
        String name = request.getParameter("name");
        String cid = request.getParameter("cid");
        ActivityService proxyInstance = (ActivityService) ProxyFactory.getProxyInstance(ActivityServiceImpl.class);
        List<Activity> activityList = proxyInstance.searchActivity(name, cid);
        System.out.println(activityList);
        PrintJson.printJsonObj(response, activityList);
    }

    private void bund(HttpServletRequest request, HttpServletResponse response) {
        String cid = request.getParameter("cid");
        String[] aids = request.getParameterValues("aid");
        ClueService proxyInstance = (ClueService) ProxyFactory.getProxyInstance(ClueServiceImpl.class);
        boolean flad = proxyInstance.bund(cid, aids);
        PrintJson.printJsonFlag(response, flad);
    }

    private void searchActivityByNameVague(HttpServletRequest request, HttpServletResponse response) {
        String aname = request.getParameter("aname");

        ActivityService proxyInstance = (ActivityService) ProxyFactory.getProxyInstance(ActivityServiceImpl.class);

        List<Activity> activityList = proxyInstance.searchActivityByNameVague(aname);

        PrintJson.printJsonObj(response, activityList);
    }

    private void convert(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String cid = request.getParameter("cid");

        Tran tran = null;
        if ("post".equalsIgnoreCase(request.getMethod())) {
            // 说明是需要创建交易的
            tran = new Tran();
            String money = request.getParameter("money");
            String tranName = request.getParameter("tranName");
            String expectedDate = request.getParameter("predictDate");
            String stage = request.getParameter("stage");
            String activityId = request.getParameter("activityId");

            tran.setId(UUIDUtil.getUUID());
            tran.setMoney(money);
            tran.setCreateTime(DateTimeUtil.getSysTime());
            tran.setCreateBy(((User) request.getSession().getAttribute("user")).getName());
            tran.setName(tranName);
            tran.setExpectedDate(expectedDate);
            tran.setStage(stage);
            tran.setActivityId(activityId);
        }

        ClueService proxyInstance = (ClueService) ProxyFactory.getProxyInstance(ClueServiceImpl.class);
        boolean flag = proxyInstance.convert(cid, tran, ((User) request.getSession().getAttribute("user")).getName());

        if (flag) {
            response.sendRedirect(request.getContextPath() + "/workbench/clue/index.jsp");
        } else {
            response.sendRedirect(request.getContextPath() + "/error.jsp");
        }

    }

}



