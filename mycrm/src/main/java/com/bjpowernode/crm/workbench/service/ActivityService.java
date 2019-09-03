package com.bjpowernode.crm.workbench.service;

import com.bjpowernode.crm.workbench.domain.Activity;
import com.bjpowernode.crm.workbench.domain.ActivityRemark;
import com.bjpowernode.crm.workbench.domain.DataListVo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author:whr 2019/8/30
 */
public interface ActivityService {

    boolean saveActivity(Activity activity);

    DataListVo<Activity> getActivityList(HashMap<String, Object> map);

    boolean deleteActivityAndActivityremark(String[] ids);

    Map<String, Object> selectActivityAndUserList(String id);

    boolean updateActivity(Activity activity);

    Activity detail(String id);

    List<ActivityRemark> getRemarkListByAid(String aid);

    boolean deleteRemark(String rid);

    boolean updateRemark(String id, String noteContent, String sysTime, String userName);

    boolean saveRemark(ActivityRemark activityRemark);

    List<Activity> searchActivity(String name, String cid);

    List<Activity> searchActivityByNameVague(String aname);

}
