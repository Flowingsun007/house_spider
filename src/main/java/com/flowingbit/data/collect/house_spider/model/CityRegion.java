package com.flowingbit.data.collect.house_spider.model;

import java.util.*;

public class CityRegion {
    /**
     * 存放所有城市及其对应的英文字段
     */
    private Map<String,String> cityMap;
    /**
     * 存放所有行政区域及其对应的英文字段
     */
    private Map<String,String> regionMap;
    /**
     * 存放所有街道及其对应的英文字段
     */
    private Map<String,String> streetMap;
    /**
     * 所有待爬取的城市列表
     */
    private List<String> citys;
    /**
     * 城市下所有待爬取的区域列表
     */
    private List<LinkedHashMap<String,String>> regions;

    public CityRegion(){
        this.cityMap = new HashMap<>();
        this.regionMap = new HashMap<>();
        this.streetMap = new HashMap<>();
        this.citys = new ArrayList<>();
        this.regions = new ArrayList<>();
    }

    public Map<String, String> getCityMap() {
        return cityMap;
    }

    public void setCityMap(Map<String, String> cityMap) {
        this.cityMap = cityMap;
    }

    public Map<String, String> getRegionMap() {
        return regionMap;
    }

    public void setRegionMap(Map<String, String> regionMap) {
        this.regionMap = regionMap;
    }

    public Map<String, String> getStreetMap() {
        return streetMap;
    }

    public void setStreetMap(Map<String, String> streetMap) {
        this.streetMap = streetMap;
    }

    public List<String> getCitys() {
        return citys;
    }

    public void setCitys(List<String> citys) {
        this.citys = citys;
    }

    public List<LinkedHashMap<String, String>> getRegions() {
        return regions;
    }

    public void setRegions(List<LinkedHashMap<String, String>> regions) {
        this.regions = regions;
    }

    @Override
    public String toString() {
        return "CityRegion{" +
                "cityMap=" + cityMap +
                ", regionMap=" + regionMap +
                ", streetMap=" + streetMap +
                ", citys=" + citys +
                ", regions=" + regions +
                '}';
    }
}
