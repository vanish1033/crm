package com.bjpowernode.crm.settings.service.impl;

import com.bjpowernode.crm.exception.LoginException;
import com.bjpowernode.crm.settings.dao.UserDao;
import com.bjpowernode.crm.settings.domain.User;
import com.bjpowernode.crm.settings.service.UserService;
import com.bjpowernode.crm.utils.DateTimeUtil;
import com.bjpowernode.crm.utils.SqlSessionUtil;

import java.util.HashMap;
/**
 * 类加载器顺序：启动类加载器 -> 扩展类加载器 -> 应用类加载器 -> 用户自定义类加载器
 * <p>
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

    @Override
    public User login(String loginAct, String loginPwd, String ip) throws LoginException {
        HashMap<String, String> map = new HashMap<>();
        map.put("loginAct", loginAct);
        map.put("loginPwd", loginPwd);

        User user = userDao.login(map);
        if (user == null) {
            throw new LoginException("账号密码不正确！");
        }

        /**
         * 能走到这一步说明数据库里有这个用户
         */
        // 校验账号有效期
        String expireTime = user.getExpireTime();
        String sysTime = DateTimeUtil.getSysTime();
        if (expireTime.compareTo(sysTime) < 0) {
            throw new LoginException("账号有效期已过！");
        }

        // 校验账号是否被冻结
        String lockState = user.getLockState();
        if ("0".equals(lockState)) {
            throw new LoginException("账号已被冻结");
        }

        // 校验ip地址是否允许登录
        String allowIps = user.getAllowIps();
        if (!allowIps.contains(ip)) {
            throw new LoginException("ip地址受限制");
        }

        /*
            走到这一步说明没出问题账号允许登录
         */
        return user;
    }
}
