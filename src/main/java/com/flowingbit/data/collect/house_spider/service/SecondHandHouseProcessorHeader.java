package com.flowingbit.data.collect.house_spider.service;

import com.flowingbit.data.collect.house_spider.model.House;
import com.flowingbit.data.collect.house_spider.utils.IOUtil;
import org.apache.commons.lang3.StringUtils;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Selectable;

import java.util.Arrays;
import java.util.List;

public class SecondHandHouseProcessorHeader implements PageProcessor {

    private static int count = 1;
    // 部分一：抓取网站的相关配置，包括编码、抓取间隔、重试次数等
    private Site site = Site.me()
            .setRetryTimes(3)
            .setSleepTime(1000)
            .addHeader("Accept-Encoding","gzip, deflate, br")
            .addHeader("Accept-Language","zh-CN,zh;q=0.9")
            .addHeader("Accept","text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3")
            .addHeader("User-Agent","Mozilla/5.0 (Macintosh; Intel Mac OS X 10_13_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/73.0.3683.103 Safari/537.36");

    /**
     * process是定制爬虫逻辑的核心接口，在这里编写抽取逻辑
     */
    @Override
    public void process(Page page) {
        System.out.println("=============process()================");
        // 部分二：定义如何抽取页面信息，并保存下来
        count++ ;
        if(page.getHtml().xpath("//ul[@class='sellListContent']").match())
            page.putField("target","Target html is exist!");
        //将html输出到文件
//        try{
//            // C:/Users/flowi/Desktop/lianjia.html
//            IOUtil.outFile(page.getHtml().toString(),"/Users/zhaoluyang/Desktop/lianjia-cookie.html");
//        }catch (Exception e){
//            e.printStackTrace();
//        }
        if (page.getResultItems().get("target")==null) {
            System.out.println("=============target==null================");
            //skip this page
            page.setSkip(true);
        }else{
            //开始提取页面信息
            House house = new House();
            System.out.println("================url:=================\n" + page.getUrl().toString());
            List<Selectable> targets = page.getHtml().xpath("//li[@class='clear LOGCLICKDATA']").nodes();
            targets.forEach(e->{
                String title = e.xpath("//div[@class='title']/a[1]/text()").toString();
                String url = e.xpath("//a[@class='noresultRecommend img ']/@href").toString();
                String image = e.xpath("//div[@class='houseInfo']/text()").toString();
                String s = e.xpath("//div[@class='houseInfo']/a/text()").toString();
                String community = e.xpath("//div[@class='houseInfo']/a/text()").toString();
                String floor = e.xpath("//div[@class='positionInfo']/text()").toString();
                String region = e.xpath("//div[@class='positionInfo']/a[1]/text()").toString();
                String totolPrice = e.xpath("//div[@class='totalPrice']/span[1]/text()").toString();
                String averagePrice = e.xpath("//div[@class='unitPrice']/span[1]/text()").toString();

                String followInfo = e.xpath("//div[@class='followInfo']/text()").toString();
                String[] sl = followInfo.split("/");
                String watch = sl[0];
                String view = sl[1];
                String releaseDate = sl[2];

                String ss = StringUtils.strip(s.strip(),"|").strip();
                String[] houseInfo = StringUtils.split(ss,"|");
                String roomCount = houseInfo[0].strip();
                System.out.println("houseInfo[1].strip():"+houseInfo[1].strip());
                Double houseArea = Double.valueOf(houseInfo[1].strip());
                String towards = houseInfo[2].strip();
                String decoration = houseInfo[3].strip();
                String elevator = houseInfo[4].strip();

                house.setTitle(title);
                house.setUrl(url);
                house.setCommunity(community);
                house.setRegion(region);
                house.setFloor(floor);
                house.setTotalPrice(Integer.valueOf(totolPrice));
                house.setAveragePrice(Double.valueOf(averagePrice));
                house.setImage(image);
                house.setWatch(Integer.valueOf(watch));
                house.setView(Integer.valueOf(view));
                house.setReleaseDate(releaseDate);
                house.setRoomCount(roomCount);
                house.setHouseArea(houseArea);
                house.setTowards(towards);
                house.setDecoration(decoration);
                house.setElevator(elevator);
                System.out.println(house.toString());
                page.putField("house",house);

            });
        }
        // 部分三：从页面发现后续的url地址来抓取
        int index = page.getUrl().toString().indexOf("pg");
        String newPage = page.getUrl().toString().substring(0,index) + "pg" + count + "/";
        page.addTargetRequest(newPage);
    }
    @Override
    public Site getSite() {
        return site;
    }

    public static void main(String[] args) {

        Spider.create(new SecondHandHouseProcessorHeader())
                //从"https://github.com/code4craft"开始抓
                .addUrl("https://nj.lianjia.com/ershoufang/pg1")
                //开启2个线程抓取
                .thread(2)
                //启动爬虫
                .run();
    }

}
