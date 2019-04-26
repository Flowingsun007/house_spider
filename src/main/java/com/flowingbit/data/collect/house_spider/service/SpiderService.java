package com.flowingbit.data.collect.house_spider.service;

import org.springframework.stereotype.Service;

@Service
public class SpiderService {
    public void runSpider(){
        HouseProcessor processor = new HouseProcessor();
        processor.main(new String[3]);
    }
}
