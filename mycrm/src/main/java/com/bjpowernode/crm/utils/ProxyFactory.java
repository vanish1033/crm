package com.bjpowernode.crm.utils;

import com.bjpowernode.crm.exception.LoginException;
import org.apache.ibatis.session.SqlSession;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
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
                new InvocationHandler() {
                    @Override
                    public Object invoke(Object proxy1, Method method, Object[] args) throws Throwable {
                        SqlSession sqlSession = null;
                        Object invoke = null;
                        try {
                            sqlSession = SqlSessionUtil.getSqlSession();
//                            System.out.println("进入动态代理");
                            invoke = method.invoke(FINALO, args);
                            sqlSession.commit();
                        } catch (Exception e) {
                            sqlSession.rollback();
                            e.printStackTrace();
                            throw e.getCause(); //继续把异常暴露给controller
                        } finally {
                            SqlSessionUtil.closeSqlSession(sqlSession);
                        }
                        return invoke;
                    }
                });

        return proxy;
    }
}
