package com.bjpowernode.crm.workbench.web.controller;

import com.bjpowernode.crm.settings.domain.User;
import com.bjpowernode.crm.settings.service.UserService;
import com.bjpowernode.crm.settings.service.impl.UserServiceImpl;
import com.bjpowernode.crm.utils.PrintJson;
import com.bjpowernode.crm.utils.ProxyFactory;
import com.bjpowernode.crm.workbench.domain.Tran;
import com.bjpowernode.crm.workbench.domain.TranHistory;
import com.bjpowernode.crm.workbench.service.TransactionService;
import com.bjpowernode.crm.workbench.service.impl.TransactionServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TransactionServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String servletPath = request.getServletPath();
        System.out.println(servletPath);
        if ("/workbench/transaction/save.do".equals(servletPath)) {
            save(request, response);
        } else if ("/workbench/transaction/getCustomerName.do".equals(servletPath)) {
            getCustomerName(request, response);
        } else if ("/workbench/transaction/detail.do".equals(servletPath)) {
            detail(request, response);
        } else if ("/workbench/transaction/getTranHistoryListByTranId.do".equals(servletPath)) {
            getTranHistoryListByTranId(request, response);
        } else if ("/workbench/transaction/changeStage.do".equals(servletPath)) {
            changeStage(request, response);
        }
    }

    private void changeStage(HttpServletRequest request, HttpServletResponse response) {
//        stage: s,
//                num: s2,
//                tid: "${t.id}",
//                expectedDate: "${t.expectedDate}"
        String stage = request.getParameter("stage");
        String tid = request.getParameter("tid");
        String expectedDate = request.getParameter("expectedDate");
        String user = ((User) request.getSession().getAttribute("user")).getName();

        TransactionService proxyInstance = (TransactionService) ProxyFactory.getProxyInstance(TransactionServiceImpl.class);
        HashMap<String, Object> map = proxyInstance.changeStage(tid, stage, expectedDate, request.getServletContext(), user);
        System.out.println(map);
        PrintJson.printJsonObj(response, map);

    }

    private void save(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        UserService proxyInstance = (UserService) ProxyFactory.getProxyInstance(UserServiceImpl.class);
        List<User> userList = proxyInstance.getUserList();
        request.setAttribute("userList", userList);
        request.getRequestDispatcher("/workbench/transaction/save.jsp").forward(request, response);
    }

    private void getCustomerName(HttpServletRequest request, HttpServletResponse response) {
        String name = request.getParameter("name");
        TransactionService proxyInstance = (TransactionService) ProxyFactory.getProxyInstance(TransactionServiceImpl.class);
        List<String> nameList = proxyInstance.getNameList(name);
        PrintJson.printJsonObj(response, nameList);
    }

    private void detail(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String id = request.getParameter("id");
        TransactionService proxyInstance = (TransactionService) ProxyFactory.getProxyInstance(TransactionServiceImpl.class);
        Tran tran = proxyInstance.getTranById(id);
        Map<String, String> pMap = (Map<String, String>) request.getServletContext().getAttribute("pMap");
        tran.setPossibility(pMap.get(tran.getStage()));
        request.setAttribute("t", tran);
        request.getRequestDispatcher("/workbench/transaction/detail.jsp").forward(request, response);
    }

    private void getTranHistoryListByTranId(HttpServletRequest request, HttpServletResponse response) {
        String id = request.getParameter("id");
        TransactionService proxyInstance = (TransactionService) ProxyFactory.getProxyInstance(TransactionServiceImpl.class);
        List<TranHistory> tranHistoryList = proxyInstance.getTranHistoryListByTranId(id);
        Map<String, String> pMap = (Map<String, String>) request.getServletContext().getAttribute("pMap");
        for (TranHistory history : tranHistoryList) {
            history.setPossibility(pMap.get(history.getStage()));
        }
        PrintJson.printJsonObj(response, tranHistoryList);
    }
}
