package com.flowingbit.data.collect.house_spider.service.processor;

import com.flowingbit.data.collect.house_spider.dao.RedisDAO;
import com.flowingbit.data.collect.house_spider.model.Street;
import com.flowingbit.data.collect.house_spider.utils.IOUtil;
import net.minidev.json.JSONArray;
import org.apache.commons.lang3.StringUtils;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Selectable;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class StreetProcessor implements PageProcessor {

    private Set<Street> streetSet;

    private String name;

    private String briefName;

    private String cityName;

    public StreetProcessor(){}

    public StreetProcessor(String cityName, String name,  String briefName){
        this.streetSet = new LinkedHashSet<Street>();
        this.cityName = cityName;
        this.name = name;
        this.briefName = briefName;
    }

    RedisDAO redisDAO = new RedisDAO();

    // 部分一：抓取网站的相关配置，包括编码、抓取间隔、重试次数等
    private Site site = Site.me()
            .setRetryTimes(3)
            .setSleepTime(1000)
            .addHeader("Accept-Encoding", "gzip, deflate, br")
            .addHeader("Accept-Language", "zh-CN,zh;q=0.9")
            .addHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3")
            .addHeader("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_13_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/73.0.3683.103 Safari/537.36");

    /**
     * process是定制爬虫逻辑的核心接口，在这里编写抽取逻辑
     */
    @Override
    public void process(Page page) {
        try {
            // 部分二：定义如何抽取页面信息，并保存下来
            if (!page.getHtml().xpath("//div[@data-role='ershoufang']").match()){
                page.setSkip(true);
            }
            List<String> cityStreeNames = redisDAO.getList(cityName + ":all:all");
            //开始提取页面信息
            System.out.println(page.getUrl().toString());
            List<Selectable> streets = page.getHtml().xpath("//div[@data-role='ershoufang']/div[2]/a").nodes();
            System.out.println("行政区域：" + name);
            streets.forEach(e -> {
                Street street = new Street();
                String streetName = e.xpath("a/text()").toString();
                String streetUrl = e.xpath("a/@href").toString();
                street.setName(streetName);
                street.setBriefName(StringUtils.substringBetween(streetUrl, "/ershoufang/", "/"));
                if(!cityStreeNames.contains(streetName)){
                    cityStreeNames.add(streetName);
                    streetSet.add(street);
                    System.out.println("街道：" + streetName);
                    System.out.println("  |——链接：" + streetUrl);
                }else {
                    System.out.println("====================街道已存在：=======================" + street.toString());
                }
            });
            //存redis
            redisDAO.setSet(cityName + ":" + name, streetSet);
            redisDAO.setList(cityName + ":all:all", cityStreeNames);
            //存成json文件
            List<Street> ll = new ArrayList<>(streetSet);
            String jsonstr = JSONArray.toJSONString(ll);
            IOUtil.outFile(jsonstr, "streets.json");

        }catch (Exception eee){
            eee.printStackTrace();
            //EmailService.sendMail("769010256@qq.com", page.getUrl().toString(), eee.toString());
        }
    }


    @Override
    public Site getSite() {
        return site;
    }

    public void startProcessor(String url, String cityName, String name, String briefName){
        Spider.create(new StreetProcessor(cityName, name, briefName))
                //从"https://nj.lianjia.com/ershoufang/"开始抓
                .addUrl(url)
                //开启1个线程抓取
                .thread(1)
                //启动爬虫
                .run();
    }

    public static void main(String[] args){
    }

//    public static void main(String[] args){
//        Spider.create(new StreetProcessor())
//                //从"https://nj.lianjia.com/ershoufang/jianye/"开始抓
//                .addUrl("https://nj.lianjia.com/ershoufang/jianye/")
//                //开启2个线程抓取
//                .thread(1)
//                //启动爬虫
//                .run();
//    }

}
