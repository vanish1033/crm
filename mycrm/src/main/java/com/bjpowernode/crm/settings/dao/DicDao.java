package com.bjpowernode.crm.settings.dao;

import com.bjpowernode.crm.settings.domain.DicType;
import com.bjpowernode.crm.settings.domain.DicValue;

import java.util.List;

/**
 * @author:whr 2019/9/2
 */
public interface DicDao {

    List<DicType> getAllDictype();

    List<DicValue> getAllByDicType(String code);

}
