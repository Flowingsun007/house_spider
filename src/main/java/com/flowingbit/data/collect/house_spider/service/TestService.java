package com.flowingbit.data.collect.house_spider.service;

import org.springframework.stereotype.Service;

@Service
public class TestService {
    public void runSpider(){
       // SecondHandHouseProcessor secondHandHouseProcessor = new SecondHandHouseProcessor();
//        SecondHandHouseProcessorCookie processor = new SecondHandHouseProcessorCookie();
        SecondHandHouseProcessorHeader processor = new SecondHandHouseProcessorHeader();
        processor.main(new String[3]);
    }
}
