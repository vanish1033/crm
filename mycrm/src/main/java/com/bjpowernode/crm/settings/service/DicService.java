package com.bjpowernode.crm.settings.service;

import com.bjpowernode.crm.settings.domain.DicValue;

import java.util.List;
import java.util.Map;

/**
 * @author:whr 2019/9/2
 */
public interface DicService {
    Map<String, List<DicValue>> getDicTypeAndDicValue();

}
