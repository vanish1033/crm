package com.bjpowernode.crm.workbench.dao;

import com.bjpowernode.crm.workbench.domain.Tran;

public interface TranDao {

    int save(Tran tran);

    Tran getTranById(String id);

    int update(Tran tran);

}
