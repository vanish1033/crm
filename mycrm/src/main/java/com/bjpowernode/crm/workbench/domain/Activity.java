package com.bjpowernode.crm.workbench.domain;

import lombok.Data;

/**
 * @author:whr 2019/8/30
 */
@Data
public class Activity {

    String id;          //编号
    String owner;       //所有者，在数据库中存的是user表的id
    String name;        //市场活动的名称
    String startDate;   //活动开始时间
    String endDate;     //活动结束的时间
    String cost;        //活动的成本
    String description; //活动的描述
    String createTime;  //创建时间
    String createBy;    //创建者
    String editTime;    //修改时间
    String editBy;      //修改人

    // 无参构造
    public Activity() {
    }

    // 全参构造
    public Activity(String id, String owner, String name, String startDate, String endDate, String cost, String description, String createTime, String createBy, String editTime, String editBy) {
        this.id = id;
        this.owner = owner;
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.cost = cost;
        this.description = description;
        this.createTime = createTime;
        this.createBy = createBy;
        this.editTime = editTime;
        this.editBy = editBy;
    }
}
