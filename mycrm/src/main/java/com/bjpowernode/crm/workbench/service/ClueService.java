package com.bjpowernode.crm.workbench.service;

import com.bjpowernode.crm.workbench.domain.Activity;
import com.bjpowernode.crm.workbench.domain.Clue;

import java.util.List;

/**
 * 线索业务接口
 *
 * @author:whr 2019/9/2
 */
public interface ClueService {
    boolean saveClud(Clue clue);

    List<Clue> getClueList();

    Clue detail(String cid);

    List<Activity> getActivityBycid(String cid);

}
