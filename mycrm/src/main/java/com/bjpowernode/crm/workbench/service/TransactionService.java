package com.bjpowernode.crm.workbench.service;

import com.bjpowernode.crm.workbench.domain.Tran;
import com.bjpowernode.crm.workbench.domain.TranHistory;

import javax.servlet.ServletContext;
import java.util.HashMap;
import java.util.List;

/**
 * @author:whr 2019/9/5
 */
public interface TransactionService {

    List<String> getNameList(String name);

    Tran getTranById(String id);

    List<TranHistory> getTranHistoryListByTranId(String id);

    HashMap<String, Object> changeStage(String tid, String stage, String expectedDate, ServletContext servletContext, String user);

}
