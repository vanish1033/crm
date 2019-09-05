package com.bjpowernode.crm.workbench.dao;

import com.bjpowernode.crm.workbench.domain.ClueActivityRelation;
import com.bjpowernode.crm.workbench.domain.ContactsActivityRelation;

public interface ContactsActivityRelationDao {

    int deleteCarById(String id);

    int bund(ClueActivityRelation car);

    int save(ContactsActivityRelation contactsActivityRelation);

}
