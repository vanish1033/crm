package com.bjpowernode.crm.workbench.domain;

import lombok.Data;

/**
 * @author:whr 2019/8/30
 */
@Data
public class ActivityRemark {

    String id;    //编号
    String noteContent;   //备注信息
    String createTime;    //
    String createBy;      //
    String editTime;      //
    String editBy;    //
    String editFlag;      //判断是否修改过得标记
    String activityId;    //关联Activity表，保存的是Activity表的id

    // 无参构造
    public ActivityRemark() {
    }

    // 全参构造
    public ActivityRemark(String id, String noteContent, String createTime, String createBy, String editTime, String editBy, String editFlag, String activityId) {
        this.id = id;
        this.noteContent = noteContent;
        this.createTime = createTime;
        this.createBy = createBy;
        this.editTime = editTime;
        this.editBy = editBy;
        this.editFlag = editFlag;
        this.activityId = activityId;
    }
}
