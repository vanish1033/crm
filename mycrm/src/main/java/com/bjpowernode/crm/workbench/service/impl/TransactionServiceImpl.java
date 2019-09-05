package com.bjpowernode.crm.workbench.service.impl;

import com.bjpowernode.crm.utils.SqlSessionUtil;
import com.bjpowernode.crm.workbench.dao.CustomerDao;
import com.bjpowernode.crm.workbench.dao.TranDao;
import com.bjpowernode.crm.workbench.service.TransactionService;

import java.util.List;

/**
 * @author:whr 2019/9/5
 */
public class TransactionServiceImpl implements TransactionService {
    TranDao tranDao = SqlSessionUtil.getSqlSession().getMapper(TranDao.class);
    CustomerDao customerDao = SqlSessionUtil.getSqlSession().getMapper(CustomerDao.class);

    @Override
    public List<String> getNameList(String name) {
        return customerDao.getNameList(name);
    }
}
