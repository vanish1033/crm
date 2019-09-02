package com.bjpowernode.crm.workbench.service.impl;

import com.bjpowernode.crm.utils.SqlSessionUtil;
import com.bjpowernode.crm.workbench.dao.ClueDao;
import com.bjpowernode.crm.workbench.service.ClueService;

/**
 * 线索业务实现类
 *
 * @author:whr 2019/9/2
 */
public class ClueServiceImpl implements ClueService {

    // 操作线索表的Dao层对象
    ClueDao clueDao = SqlSessionUtil.getSqlSession().getMapper(ClueDao.class);


}
