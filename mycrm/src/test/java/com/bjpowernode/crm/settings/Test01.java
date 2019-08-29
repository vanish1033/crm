package com.bjpowernode.crm.settings;

import com.bjpowernode.crm.settings.service.impl.UserServiceImpl;
import com.bjpowernode.crm.utils.DateTimeUtil;
import com.bjpowernode.crm.utils.MD5Util;
import com.bjpowernode.crm.utils.ProxyFactory;
import com.bjpowernode.crm.utils.UUIDUtil;
import org.junit.Test;

/**
 * @author:whr 2019/8/29
 */
public class Test01 {

    @Test //测试输出数据
    public void test1() {
        System.out.println(123);
    }

    @Test //测试失效时间
    public void test2() {
        String currentSysTime = DateTimeUtil.getSysTime();
        System.out.println(currentSysTime.compareTo("2019-12-25 12:23:12"));
    }

    @Test //测试账号锁定状态
    public void test3() {
        String lockState = "1";
        System.out.println("0".equals(lockState) ? "账号已冻结" : "账号可以使用");
    }

    @Test //测试ip地址有效性
    public void test4() {
        // 从请求包解析的ip地址
        String ip = "192.168.100.2";

        // 允许访问的ip地址
        String allowIps = "192.168.100.4,192.168.134.23,192.168.231.13";

        System.out.println(allowIps.contains(ip) ? "ip允许访问" : "ip地址不允许访问");

    }

    @Test // 测试MD5加密
    public void test5() {
        String passWord = "a6359481749afd910ed5fc692e7940bd";
        System.out.println("a6359481749afd910ed5fc692e7940bd = " + MD5Util.getMD5(passWord));
    }

    @Test // 测试动态代理是否好用
    public void test6() {
        Object proxyInstance = ProxyFactory.getProxyInstance(UserServiceImpl.class);
        System.out.println(proxyInstance.toString());
    }

    @Test // 测试UUID是否好使
    public void test7() {
        System.out.println("UUID = " + UUIDUtil.getUUID());
    }
}





