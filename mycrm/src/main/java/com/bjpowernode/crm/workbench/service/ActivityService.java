package com.bjpowernode.crm.workbench.service;

import com.bjpowernode.crm.workbench.domain.Activity;
import com.bjpowernode.crm.workbench.domain.DataListVo;

import java.util.HashMap;
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

}
