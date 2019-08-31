package com.bjpowernode.crm.workbench.service.impl;

import com.bjpowernode.crm.utils.SqlSessionUtil;
import com.bjpowernode.crm.workbench.dao.ActivityDao;
import com.bjpowernode.crm.workbench.dao.ActivityRemarkDao;
import com.bjpowernode.crm.workbench.domain.Activity;
import com.bjpowernode.crm.workbench.domain.DataListVo;
import com.bjpowernode.crm.workbench.service.ActivityService;

import java.util.HashMap;
import java.util.List;

/**
 * @author:whr 2019/8/30
 */
public class ActivityServiceImpl implements ActivityService {
    ActivityDao activityDao = SqlSessionUtil.getSqlSession().getMapper(ActivityDao.class);
    ActivityRemarkDao activityRemarkDao = SqlSessionUtil.getSqlSession().getMapper(ActivityRemarkDao.class);

    @Override
    public boolean saveActivity(Activity activity) {
        boolean flag = false;
        int i = activityDao.saveActivity(activity);
        flag = i > 0;
        return flag;
    }

    @Override
    public DataListVo<Activity> getActivityList(HashMap<String, Object> map) {
        //获取数据list
        List<Activity> activityList = activityDao.getActivityList(map);
        //获取total
        int total = activityDao.getTotal(map);
        //分装为DataListVo返回
        return new DataListVo(activityList, total);
    }

    @Override
    public boolean deleteActivityAndActivityremark(String[] ids) {
        boolean flag = false;
        /*
         先干掉ActivityRemark表中的关联数据
         */
        // 先查询ActivityRemark表中的关联数据的数量
        int count1 = activityRemarkDao.selectByActivityId(ids);
        // 再删除
        int count2 = activityRemarkDao.deleteByActivityId(ids);


        /*
           干掉Activity表中的数据
         */
        int count3 = activityDao.deleteById(ids);

        flag = (count1 == count2) && count3 == ids.length;

        return flag;
    }
}
