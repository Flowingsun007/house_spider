package com.flowingbit.data.collect.house_spider.controller;

import com.flowingbit.data.collect.house_spider.service.HouseProcessor;
import com.flowingbit.data.collect.house_spider.service.SpiderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/house")
public class SpiderController {

    @Autowired
    SpiderService spiderService;

    @Autowired
    HouseProcessor houseProcessor;

    @GetMapping(path = "/lianjia")
    public void runSpider(){
        spiderService.runSpider();
    }
}
