package com.flowingbit.data.collect.house_spider.service;

import com.flowingbit.data.collect.house_spider.dao.HouseDao;
import com.flowingbit.data.collect.house_spider.dao.RedisDAO;
import com.flowingbit.data.collect.house_spider.model.City;
import com.flowingbit.data.collect.house_spider.model.Region;
import com.flowingbit.data.collect.house_spider.model.Street;
import com.flowingbit.data.collect.house_spider.service.processor.CityProcessor;
import com.flowingbit.data.collect.house_spider.service.processor.HouseProcessor;
import com.flowingbit.data.collect.house_spider.service.processor.RegionProcessor;
import com.flowingbit.data.collect.house_spider.service.processor.StreetProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Service
public class SpiderService {

    private RedisDAO redisDAO = new RedisDAO();

    private  Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 根据城市名称生成数据库表名：
     * 如 cityName = “南京” tableName = “南京_20191127”
     */
    public String generateTableName(String cityName){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String date = sdf.format(new Date());
        String tableName = cityName + "_" + date;
        return tableName;
    }

    /**
     * 爬取指定城市的二手房
     */
    @Async
    public void runCitySpider(String cityName, String tableName) {
        List<City> cityList = redisDAO.getList("citys");
        if(cityList==null||cityList.size()==0){
            CityProcessor processor = new CityProcessor();
            processor.startProcessor("https://www.lianjia.com/city/");
        }
        if(cityList==null){
            throw new NullPointerException(">>>>>>>>>>>>>>>>cityList==null<<<<<<<<<<<<<<<<");
        }
        //获取该城市所有行政区域，如cityName = 南京，区域：鼓楼、玄武、江宁、雨花、浦口...
        City city = cityList.parallelStream().filter(e->e.getName().equals(cityName)).findAny().orElseThrow(()->new NullPointerException("没找到此城市名"));
        RegionProcessor processor = new RegionProcessor();
        String url = "https://" + city.getBriefName() + ".lianjia.com/ershoufang/";
        processor.startProcessor(url, city.getName(), city.getBriefName());
        List<Region> regionList = redisDAO.getList(cityName);
        if(regionList==null){
            throw new NullPointerException("没找到该城市下的行政区域");
        }

        List<String> cityStreeNames = new ArrayList<>();
        redisDAO.setList(cityName+":all:all", cityStreeNames);
        //根据区域名获取该区域下所有街道
        String str = "https://" + city.getBriefName() + ".lianjia.com/ershoufang/";
        regionList.forEach(System.out::println);
        regionList.stream().forEach(f->{
            String regionUrl = str + f.getBriefName();
            StreetProcessor streetProcessor = new StreetProcessor();
            streetProcessor.startProcessor(regionUrl, cityName, f.getName(), f.getBriefName());
            // 此处用Set而不用List的原因：有部分部分行政区域下存在相同的街道，导致重复爬取
            Set<Street> streetSet = redisDAO.getSet(cityName + ":" + f.getName());
            if(streetSet==null){
                logger.warn("没找到该行政区域下的街道");
            }else{
                streetSet.stream().forEach(g->{
                    String streetUrl = str + g.getBriefName() + "/pg1";
                    HouseProcessor houseProcessor = new HouseProcessor();
                    houseProcessor.startProcessor(streetUrl, cityName, f.getName(), tableName);
                });
            }
        });



    }


    /**
     * 爬取批量城市的链家二手房
     */
    @Async
    public void runCitysSpider(List<String> cityNames) {
        cityNames.stream().forEach(e->{
            String tableName = generateTableName(e);
            if(new HouseDao().createHouseTable(tableName)){
                runCitySpider(e, tableName);
            }
        });
    }

    /**
     * 爬取全国的链家二手房
     */
    @Async
    public void runNationSpider(){
        String tableName = generateTableName("nation");
        if(!(new HouseDao().createHouseTable(tableName))){
            logger.error("runNationSpider() >> 创建nation表失败");
            return;
        }
        CityProcessor processor = new CityProcessor();
        processor.startProcessor("https://www.lianjia.com/city/");
        List<City> cityList = redisDAO.getList("citys");
        if(cityList==null){
            logger.warn("获取城市列表失败！");
        }
        cityList.forEach(e->{
            String name = e.getName();
            try{
                runCitySpider(name, tableName);
            }catch (Exception e1){
                logger.error("爬取城市：" + name + "发生异常：",e1);
            }
        });
    }

    /**
     * 爬取全国的链家二手房(排除指定城市)
     */
    @Async
    public void runNationSpider(List<String> cityNames){
        String tableName = generateTableName("nation");
        if(!(new HouseDao().createHouseTable(tableName))){
            logger.error("runNationSpider() >> 创建nation表失败");
            return;
        }
        CityProcessor processor = new CityProcessor();
        processor.startProcessor("https://www.lianjia.com/city/");
        List<City> cityList = redisDAO.getList("citys");
        if(cityList==null){
            logger.warn("获取城市列表失败！");
        }
        cityList.forEach(e->{
            String name = e.getName();
            if(!cityNames.contains(name)){
                try{
                    runCitySpider(name, tableName);
                }catch (Exception e2){
                    logger.error("爬取城市：" + name + "发生异常：",e2);
                }
            }else {
                logger.info("不爬取该城市：" + name);
            }
        });
    }
}
