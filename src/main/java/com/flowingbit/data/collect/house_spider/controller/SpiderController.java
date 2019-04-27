package com.flowingbit.data.collect.house_spider.controller;

import com.flowingbit.data.collect.house_spider.service.HouseProcessor;
import com.flowingbit.data.collect.house_spider.service.SpiderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/house")
public class SpiderController {

    @Autowired
    SpiderService spiderService;

    @Autowired
    HouseProcessor houseProcessor;

    /**
     * 爬取全国的链家二手房
     */
    @GetMapping(path = "/nation")
    public void runNationSpider(){
        spiderService.runNationSpider();
    }

    /**
     * 爬取全国的链家二手房
     */
    @PostMapping(path = "/city")
    public void runCitySpider(String cityName){
        spiderService.runCitySpider(cityName);
    }
}
