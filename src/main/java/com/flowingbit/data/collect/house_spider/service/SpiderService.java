package com.flowingbit.data.collect.house_spider.service;

import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class SpiderService {



    public void runSpider(){
        //HouseProcessor processor = new HouseProcessor();
        //processor.main(new String[3]);

        CityRegionProcessor processor = new CityRegionProcessor();
        processor.main(new String[3]);
    }
}
