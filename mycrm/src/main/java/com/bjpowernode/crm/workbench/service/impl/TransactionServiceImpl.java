package com.bjpowernode.crm.workbench.service.impl;

import com.bjpowernode.crm.utils.DateTimeUtil;
import com.bjpowernode.crm.utils.SqlSessionUtil;
import com.bjpowernode.crm.utils.UUIDUtil;
import com.bjpowernode.crm.workbench.dao.CustomerDao;
import com.bjpowernode.crm.workbench.dao.TranDao;
import com.bjpowernode.crm.workbench.dao.TranHistoryDao;
import com.bjpowernode.crm.workbench.domain.Tran;
import com.bjpowernode.crm.workbench.domain.TranHistory;
import com.bjpowernode.crm.workbench.service.TransactionService;

import javax.servlet.ServletContext;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author:whr 2019/9/5
 */
public class TransactionServiceImpl implements TransactionService {
    TranDao tranDao = SqlSessionUtil.getSqlSession().getMapper(TranDao.class);
    CustomerDao customerDao = SqlSessionUtil.getSqlSession().getMapper(CustomerDao.class);
    TranHistoryDao tranHistoryDao = SqlSessionUtil.getSqlSession().getMapper(TranHistoryDao.class);

    @Override
    public List<String> getNameList(String name) {
        return customerDao.getNameList(name);
    }

    @Override
    public Tran getTranById(String id) {

        return tranDao.getTranById(id);
    }

    @Override
    public List<TranHistory> getTranHistoryListByTranId(String id) {
        return tranHistoryDao.getTranHistoryListByTranId(id);
    }

    @Override
    public HashMap<String, Object> changeStage(String tid, String stage, String expectedDate, ServletContext application, String user) {
        boolean flag = true;
        String sysTime = DateTimeUtil.getSysTime();

        Tran tran = tranDao.getTranById(tid);
        TranHistory tranHistory = new TranHistory();
        tranHistory.setStage(stage);
        tranHistory.setMoney(tran.getMoney());
        tranHistory.setPossibility((String) ((Map) application.getAttribute("pMap")).get(stage));
        tranHistory.setExpectedDate(expectedDate);
        tranHistory.setCreateTime(sysTime);
        tranHistory.setCreateBy(user);
        tranHistory.setId(UUIDUtil.getUUID());
        tranHistory.setTranId(tran.getId());

        int save = tranHistoryDao.save(tranHistory);
        if (save != 1) {
            flag = false;
        }

        tran.setStage(stage);
        tran.setPossibility((String) ((Map) application.getAttribute("pMap")).get(stage));
        tran.setEditBy(user);
        tran.setEditTime(sysTime);
        int i = tranDao.update(tran);
        if (i != 1) {
            flag = false;
        }

        HashMap<String, Object> map = new HashMap<>();
        map.put("success", flag);
        map.put("t", tran);

        return map;
    }
}
