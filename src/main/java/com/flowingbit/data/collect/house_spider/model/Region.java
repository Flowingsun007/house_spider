package com.flowingbit.data.collect.house_spider.model;

import java.io.Serializable;

public class Region implements Serializable {
    /**
     * 区域名称
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
        return "Region{" +
                "name='" + name + '\'' +
                ", briefName='" + briefName + '\'' +
                '}';
    }
}
