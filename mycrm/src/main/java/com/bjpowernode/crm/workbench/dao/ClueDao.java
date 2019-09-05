package com.bjpowernode.crm.workbench.dao;

import com.bjpowernode.crm.workbench.domain.Activity;
import com.bjpowernode.crm.workbench.domain.Clue;

import java.util.List;

public interface ClueDao {


    int saveClud(Clue clue);

    List<Clue> getClueList();

    Clue detail(String cid);

    List<Activity> getActivityBycid(String cid);


    Clue selectById(String cid);


    int deleteById(String id);

}
