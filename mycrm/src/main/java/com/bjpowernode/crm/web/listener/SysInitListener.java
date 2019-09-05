package com.bjpowernode.crm.web.listener;

import com.bjpowernode.crm.settings.domain.DicValue;
import com.bjpowernode.crm.settings.service.DicService;
import com.bjpowernode.crm.settings.service.UserService;
import com.bjpowernode.crm.settings.service.impl.DicServiceImpl;
import com.bjpowernode.crm.settings.service.impl.UserServiceImpl;
import com.bjpowernode.crm.utils.ProxyFactory;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.util.*;

public class SysInitListener implements ServletContextListener {

    // Public constructor is required by servlet spec
    public SysInitListener() {
    }

    // -------------------------------------------------------
    // ServletContextListener implementation
    // 服务器中application域创建时调用，即服务器启动时调用
    // -------------------------------------------------------
    public void contextInitialized(ServletContextEvent event) { //参数event就是监听器所监听的域对象
      /* This method is called when the servlet context is
         initialized(when the Web application is deployed). 
         You can initialize servlet context related data here.
      */
        // 先让Service查出来，返回map
        DicService proxyInstance = (DicService) ProxyFactory.getProxyInstance(DicServiceImpl.class);
        Map<String, List<DicValue>> listMap = proxyInstance.getDicTypeAndDicValue();

        // 再保存到application域中(转化为application域中的 key 和 value )
        ServletContext servletContext = event.getServletContext();

        for (String key : listMap.keySet()) {
            servletContext.setAttribute(key, listMap.get(key));
        }

//        System.out.println("------------------------");
//        List<DicValue> clueState = (List<DicValue>) event.getServletContext().getAttribute("clueStateList");
//        clueState.forEach(System.out::println);

        ResourceBundle bundle = ResourceBundle.getBundle("Stage2Possibility");
        Enumeration<String> keys = bundle.getKeys();
        HashMap<String, String> map = new HashMap<>();
        while (keys.hasMoreElements()) {
            String key = keys.nextElement();
            map.put(key, bundle.getString(key));
        }
        event.getServletContext().setAttribute("pMap", map);
    }

}
