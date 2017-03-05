package com.sellsystem.entity.searchmodel;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by zhangwei on 2017/3/4/004.
 */
public interface Sortable {

    //static deafult
    static String getOrderByString(LinkedHashMap<String, Boolean> map) {
        String orderBy = "";
        Map.Entry entry = null;
        String order = "";
        for (Iterator iterator = map.entrySet().iterator(); iterator.hasNext(); order = orderBy + (String)entry.getKey() + " " + order + ",") {
            entry = (Map.Entry) iterator.next();
            if (((Boolean)entry.getValue()).booleanValue()) {
                order = "asc";
            } else {
                order = "desc";
            }
        }
        if (orderBy.length() > 0) {
            orderBy = orderBy.substring(0, orderBy.length() - 1);
        }
        return orderBy;
    }
}
