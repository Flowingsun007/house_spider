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
    private String enName;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEnName() {
        return enName;
    }

    public void setEnName(String enName) {
        this.enName = enName;
    }
}
