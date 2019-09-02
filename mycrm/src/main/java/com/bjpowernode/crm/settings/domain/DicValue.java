package com.bjpowernode.crm.settings.domain;

import lombok.Data;

/**
 * 数据字典类型对应的值
 *
 * @author:whr 2019/9/2
 */
@Data
public class DicValue {

    private String id;
    private String value;
    private String text;
    private String orderNo;
    private String typeCode;

    public DicValue() {
    }

    public DicValue(String id, String value, String text, String orderNo, String typeCode) {
        this.id = id;
        this.value = value;
        this.text = text;
        this.orderNo = orderNo;
        this.typeCode = typeCode;
    }
}
