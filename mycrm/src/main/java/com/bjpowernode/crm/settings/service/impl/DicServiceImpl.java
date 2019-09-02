package com.bjpowernode.crm.settings.service.impl;

import com.bjpowernode.crm.settings.dao.DicDao;
import com.bjpowernode.crm.settings.domain.DicType;
import com.bjpowernode.crm.settings.domain.DicValue;
import com.bjpowernode.crm.settings.service.DicService;
import com.bjpowernode.crm.utils.SqlSessionUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author:whr 2019/9/2
 */
public class DicServiceImpl implements DicService {

    private DicDao dicDao = SqlSessionUtil.getSqlSession().getMapper(DicDao.class);


    @Override
    public Map<String, List<DicValue>> getDicTypeAndDicValue() {
        List<DicType> dicTypes = dicDao.getAllDictype();

        HashMap<String, List<DicValue>> map = new HashMap<>();

        dicTypes.forEach((dicType) -> {
            List<DicValue> dicValueList = dicDao.getAllByDicType(dicType.getCode());
            map.put(dicType.getCode() + "List", dicValueList);
        });
        return map;
    }
}
