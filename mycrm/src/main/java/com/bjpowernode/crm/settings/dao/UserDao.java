package com.bjpowernode.crm.settings.dao;

import com.bjpowernode.crm.settings.domain.User;

import java.util.Map;

/**
 * @author:whr 2019/8/29
 */
public interface UserDao {

    User login(Map<String, String> map);
}
