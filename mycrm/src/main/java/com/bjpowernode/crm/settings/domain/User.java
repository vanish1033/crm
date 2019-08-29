package com.bjpowernode.crm.settings.domain;

import lombok.Data;

/**
 * @author:whr 2019/8/29
 */
@Data
public class User {

    private String id;             //主键
    private String loginAct;       //登录账号
    private String name;           //用户姓名
    private String loginPwd;       //登录密码
    private String email;          //邮箱
    private String expireTime;     //账号失效时间  yyyy-MM-dd HH:mm:ss
    private String lockState;      //账号锁定状态
    private String deptNo;         //部门编号
    private String allowIps;       //账号允许登录的IP地址
    private String createTime;     //账号创建时间  yyy-MM-dd HH:mm:ss
    private String createBy;       //账号创建人
    private String editTime;       //账号修改时间  yyy-MM-dd HH:mm:ss
    private String editBy;         //账号修改人

    // 无参构造
    public User() {
    }

    // 全参构造
    public User(String id, String loginAct, String name, String loginPwd, String email, String expireTime, String lockState, String deptNo, String allowIps, String createTime, String createBy, String editTime, String editBy) {
        this.id = id;
        this.loginAct = loginAct;
        this.name = name;
        this.loginPwd = loginPwd;
        this.email = email;
        this.expireTime = expireTime;
        this.lockState = lockState;
        this.deptNo = deptNo;
        this.allowIps = allowIps;
        this.createTime = createTime;
        this.createBy = createBy;
        this.editTime = editTime;
        this.editBy = editBy;
    }
}
