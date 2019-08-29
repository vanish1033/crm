package com.bjpowernode.crm.utils;

import org.apache.ibatis.session.SqlSession;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Proxy;

/**
 * @author:whr 2019/8/28
 */
public class ProxyFactory {
    public static Object getProxyInstance(Class aClass) {
        Object o = null;
        try {
            o = aClass.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }

        final Object FINALO = o;
        Object proxy = Proxy.newProxyInstance(
                aClass.getClassLoader(),
                aClass.getInterfaces(),
                (proxy1, method, args) -> {
                    SqlSession sqlSession = null;
                    Object invoke = null;
                    try {
                        sqlSession = SqlSessionUtil.getSqlSession();
                        System.out.println("进入动态代理");
                        invoke = method.invoke(FINALO, args);
                        sqlSession.commit();
                    } catch (IllegalAccessException | InvocationTargetException | IllegalArgumentException e) {
                        sqlSession.rollback();
                        e.printStackTrace();
                    } finally {
                        SqlSessionUtil.closeSqlSession(sqlSession);
                        return invoke;
                    }
                });

        return proxy;
    }
}
