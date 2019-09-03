package com.bjpowernode.crm.workbench.dao;

import com.bjpowernode.crm.workbench.domain.Activity;

import java.util.HashMap;
import java.util.List;

/**
 * @author:whr 2019/8/30
 */
public interface ActivityDao {

    int saveActivity(Activity activity);

    List<Activity> getActivityList(HashMap<String, Object> map);

    int getTotal(HashMap<String, Object> map);

    int deleteById(String[] ids);

    Activity selectActivityById(String id);

    int updateActivity(Activity activity);

    Activity detail(String id);

    List<Activity> searchActivityByName(HashMap<String, String> map);

    List<Activity> searchActivityByNameVague(String aname);

}
