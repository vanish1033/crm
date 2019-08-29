package com.bjpowernode.crm.settings.service.impl;

import com.bjpowernode.crm.settings.dao.UserDao;
import com.bjpowernode.crm.settings.service.UserService;
import com.bjpowernode.crm.utils.SqlSessionUtil;
/**
 * 加载：将类的.class文件加载到内存中方法区
 * ------并在内存中生成一个Class对象作为方法区中类数据的入口
 * 验证：检验加载进来的.class文件中的信息符合当前JVM的要求，不会有有害的数据
 * 准备：为类数据分配内存空间，并赋予初始值 (非程序中的定义值)
 * 解析：将类中的符号引用转化为直接引用
 * 初始化：为类变量进行初始化，为其赋予程序中定义的值
 */

/**
 * @author:whr 2019/8/29
 */
public class UserServiceImpl implements UserService {

    private UserDao userDao = SqlSessionUtil.getSqlSession().getMapper(UserDao.class);


}
