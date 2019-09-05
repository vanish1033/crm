package com.bjpowernode.crm.workbench.dao;

import com.bjpowernode.crm.workbench.domain.Customer;

import java.util.List;

public interface CustomerDao {

    Customer selectByName(String company);

    int save(Customer customer);

    List<String> getNameList(String name);

}
