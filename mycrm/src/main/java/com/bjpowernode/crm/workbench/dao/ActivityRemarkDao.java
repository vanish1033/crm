package com.bjpowernode.crm.workbench.dao;

import com.bjpowernode.crm.workbench.domain.ActivityRemark;

import java.util.HashMap;
import java.util.List;

/**
 * @author:whr 2019/8/30
 */
public interface ActivityRemarkDao {
    int selectByActivityId(String[] ids);

    int deleteByActivityId(String[] ids);

    List<ActivityRemark> getRemarkListByAid(String aid);

    int deleteRemarkById(String rid);

    int updateRemark(HashMap<String, String> map);

    int saveRemark(ActivityRemark activityRemark);

}
