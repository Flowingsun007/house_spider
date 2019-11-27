package com.flowingbit.data.collect.house_spider.controller;

import com.flowingbit.data.collect.house_spider.service.processor.HouseProcessor;
import com.flowingbit.data.collect.house_spider.service.SpiderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/house")
public class SpiderController {

    @Autowired
    SpiderService spiderService;

    @Autowired
    HouseProcessor houseProcessor;

    /**
     * 爬取指定城市的链家二手房,存到tableName的mysql表中
     */
    @PostMapping(path = "/city")
    public void runCitySpider(String cityName){
        String tableName = spiderService.generateTableName(cityName);
        spiderService.runCitySpider(cityName, tableName);
    }

    /**
     * 爬取批量城市的链家二手房
     */
    @PostMapping(path = "/citys")
    public void runCitysSpider(@RequestBody List<String> cityNames){
        spiderService.runCitysSpider(cityNames);
    }

    /**
     * 爬取全国的链家二手房
     */
    @GetMapping(path = "/nation")
    public void runNationSpider(){
        spiderService.runNationSpider();
    }

    /**
     * 爬取全国的链家二手房(排除指定城市)
     */
    @PostMapping(path = "/nation/exclude")
    public void runNationSpiderExclude(@RequestBody List<String> cityNames){
        spiderService.runNationSpider(cityNames);
    }
}
