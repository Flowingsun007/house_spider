package com.flowingbit.data.collect.house_spider.service;

import org.springframework.stereotype.Service;

@Service
public class SpiderService {



    public void runSpider(){
        //HouseProcessor processor = new HouseProcessor();
        //processor.main(new String[3]);

//        CityProcessor processor = new CityProcessor();
//        processor.main(new String[3]);

        RegionProcessor processor = new RegionProcessor();
        processor.main(new String[3]);
    }
}
