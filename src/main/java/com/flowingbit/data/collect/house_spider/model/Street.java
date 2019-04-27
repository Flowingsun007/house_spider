package com.flowingbit.data.collect.house_spider.model;

import java.io.Serializable;
import java.util.*;

public class Street implements Serializable {
    /**
     * 名称
     */
    private String name;
    /**
     * 英文名称
     */
    private String briefName;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBriefName() {
        return briefName;
    }

    public void setBriefName(String briefName) {
        this.briefName = briefName;
    }

    @Override
    public String toString() {
        return "Street{" +
                "name='" + name + '\'' +
                ", briefName='" + briefName + '\'' +
                '}';
    }
}
