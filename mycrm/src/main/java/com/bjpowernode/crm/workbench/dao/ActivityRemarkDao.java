package com.bjpowernode.crm.workbench.dao;

/**
 * @author:whr 2019/8/30
 */
public interface ActivityRemarkDao {
    int selectByActivityId(String[] ids);

    int deleteByActivityId(String[] ids);

}
