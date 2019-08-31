package com.bjpowernode.crm.workbench.service;

import com.bjpowernode.crm.workbench.domain.Activity;
import com.bjpowernode.crm.workbench.domain.DataListVo;

import java.util.HashMap;

/**
 * @author:whr 2019/8/30
 */
public interface ActivityService {

    boolean saveActivity(Activity activity);

    DataListVo<Activity> getActivityList(HashMap<String, Object> map);

    boolean deleteActivityAndActivityremark(String[] ids);

}
