package com.flowingbit.data.collect.house_spider.controller;

import com.flowingbit.data.collect.house_spider.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/test")
public class TestController {

    @Autowired
    TestService testService;

    @GetMapping(path = "/github")
    public String test(){
        return testService.test();
    }
}
