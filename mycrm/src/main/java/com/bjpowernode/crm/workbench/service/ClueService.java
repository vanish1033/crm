package com.bjpowernode.crm.workbench.service;

import com.bjpowernode.crm.workbench.domain.Activity;
import com.bjpowernode.crm.workbench.domain.Clue;
import com.bjpowernode.crm.workbench.domain.Tran;

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

    boolean deleteCarById(String id);

    boolean bund(String cid, String[] aids);

    boolean convert(String cid, Tran tran, String createBy);

}
