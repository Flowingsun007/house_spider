package com.flowingbit.data.collect.house_spider.service;

import org.springframework.stereotype.Service;

@Service
public class TestService {
    public String test(){
//        SecondHouseProcessor secondHouseProcessor = new SecondHouseProcessor();
//        secondHouseProcessor.main(new String[3]);

        SecondHandHouseProcessor secondHandHouseProcessor = new SecondHandHouseProcessor();
        secondHandHouseProcessor.main(new String[3]);
        return "";
    }
}
