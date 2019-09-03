package com.bjpowernode.crm.workbench.service.impl;

import com.bjpowernode.crm.utils.SqlSessionUtil;
import com.bjpowernode.crm.workbench.dao.ClueDao;
import com.bjpowernode.crm.workbench.domain.Activity;
import com.bjpowernode.crm.workbench.domain.Clue;
import com.bjpowernode.crm.workbench.service.ClueService;

import java.util.List;

/**
 * 线索业务实现类
 *
 * @author:whr 2019/9/2
 */
public class ClueServiceImpl implements ClueService {

    // 操作线索表的Dao层对象
    ClueDao clueDao = SqlSessionUtil.getSqlSession().getMapper(ClueDao.class);

    @Override
    public boolean saveClud(Clue clue) {
        boolean flag = false;
        try {
            flag = clueDao.saveClud(clue) == 1;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }

    @Override
    public List<Clue> getClueList() {
        return clueDao.getClueList();
    }

    @Override
    public Clue detail(String cid) {
        return clueDao.detail(cid);
    }

    @Override
    public List<Activity> getActivityBycid(String cid) {
        return clueDao.getActivityBycid(cid);
    }
}
