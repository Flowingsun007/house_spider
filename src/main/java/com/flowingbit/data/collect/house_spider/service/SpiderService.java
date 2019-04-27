package com.flowingbit.data.collect.house_spider.service;

import com.flowingbit.data.collect.house_spider.dao.RedisDAO;
import com.flowingbit.data.collect.house_spider.model.City;
import com.flowingbit.data.collect.house_spider.model.House;
import com.flowingbit.data.collect.house_spider.model.Region;
import com.flowingbit.data.collect.house_spider.model.Street;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SpiderService {

    private RedisDAO redisDAO = new RedisDAO();

    private  Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 爬取全国的链家二手房
     */
    public void runNationSpider(){
        //HouseProcessor processor = new HouseProcessor();
        //processor.startProcessor("https://nj.lianjia.com/ershoufang/pg1");

        CityProcessor processor = new CityProcessor();
        processor.startProcessor("https://www.lianjia.com/city/");
        List<City> cityList = redisDAO.getList("citys");
        if(cityList==null){
            logger.warn("获取城市列表失败！");
        }
    }

    /**
     * 爬取指定城市的二手房
     */
    public void runCitySpider(String cityName) {
        List<City> cityList = redisDAO.getList("citys");
        if(cityList==null){
            CityProcessor processor = new CityProcessor();
            processor.startProcessor("https://www.lianjia.com/city/");
        }
        //获取该城市所有行政区域，如cityName = 南京，区域：鼓楼、玄武、江宁、雨花、浦口...
        City city = cityList.parallelStream().filter(e->e.getName().equals(cityName)).findFirst().orElseThrow(()->new NullPointerException("没找到此城市名"));
        RegionProcessor processor = new RegionProcessor();
        String url = "https://" + city.getBriefName() + ".lianjia.com/ershoufang/";
        processor.startProcessor(url, city.getName(), city.getBriefName());
        List<Region> regionList = redisDAO.getList(cityName);
        if(regionList==null){
            throw new NullPointerException("没找到该城市下的行政区域");
        }
        //根据区域名获取该区域下所有街道
        String str = "https://" + city.getBriefName() + ".lianjia.com/ershoufang/";
        regionList.forEach(System.out::println);
        regionList.stream().forEach(f->{
            String regionUrl = str + f.getBriefName();
            StreetProcessor streetProcessor = new StreetProcessor();
            streetProcessor.startProcessor(regionUrl, f.getName(), f.getBriefName());
            List<Street> streetList = redisDAO.getList(f.getName());
            if(streetList==null){
                logger.warn("没找到该行政区域下的街道");
            }else{
                streetList.stream().forEach(g->{
                    String streetUrl = str + g.getBriefName() + "/pg1";
                    HouseProcessor houseProcessor = new HouseProcessor();
                    houseProcessor.startProcessor(streetUrl, cityName, f.getName());
                });
            }
        });



    }
}
