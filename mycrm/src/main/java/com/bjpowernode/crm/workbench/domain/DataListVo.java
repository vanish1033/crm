package com.bjpowernode.crm.workbench.domain;

import lombok.Data;

import java.util.List;

/**
 * @author:whr 2019/8/31
 */
@Data
public class DataListVo<T> {
    List<T> dataList;
    int total;

    public DataListVo(List<T> dataList, int total) {
        this.dataList = dataList;
        this.total = total;
    }

    public DataListVo() {
    }
}
