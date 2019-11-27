package com.flowingbit.data.collect.house_spider.service.cron;

import com.flowingbit.data.collect.house_spider.dao.HouseDao;
import com.flowingbit.data.collect.house_spider.service.SpiderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;


@Component
@EnableScheduling
public class CronSpider {

    @Autowired
    SpiderService spiderService;

    // 每天22点执行一次
    @Scheduled(cron = "0 0 22 * * ?")
    public void nanjingTask() {
        String tableName = spiderService.generateTableName("南京");
        if(new HouseDao().createHouseTable(tableName)){
            // House spider >> 创建新表成功!
            spiderService.runCitySpider("南京",tableName);
        }
    }
}
