package com.bjpowernode.crm.settings.domain;

import lombok.Data;

/**
 * 数据字典类型
 *
 * @author:whr 2019/9/2
 */
@Data
public class DicType {

    private String code;

    private String name;

    private String description;

    public DicType() {
    }

    public DicType(String code, String name, String description) {
        this.code = code;
        this.name = name;
        this.description = description;
    }
}
