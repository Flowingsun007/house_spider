package com.flowingbit.data.collect.house_spider.service;

import com.flowingbit.data.collect.house_spider.model.Region;
import com.flowingbit.data.collect.house_spider.model.Street;
import com.flowingbit.data.collect.house_spider.service.email.EmailService;
import com.flowingbit.data.collect.house_spider.utils.IOUtil;
import net.minidev.json.JSONArray;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Selectable;

import java.util.ArrayList;
import java.util.List;

@Component
public class StreetProcessor implements PageProcessor {

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
            System.out.println("=============process()================");
            // 部分二：定义如何抽取页面信息，并保存下来
            if (!page.getHtml().xpath("//div[@data-role='ershoufang']").match()){
                page.setSkip(true);
            }
            //将html输出到文件
            // C:/Users/flowi/Desktop/lianjia.html
            //IOUtil.outFile(page.getHtml().toString(), "/Users/zhaoluyang/Desktop/lianjia-header.html");
            List<Street> streetList = new ArrayList<>();
            //开始提取页面信息
            System.out.println(page.getUrl().toString());
            List<Selectable> streets = page.getHtml().xpath("//div[@data-role='ershoufang']/div[2]/a").nodes();
            streets.forEach(e -> {
                Street street = new Street();
                String streetName = e.xpath("a/text()").toString();
                String streetUrl = e.xpath("a/@href").toString();
                street.setName(streetName);
                street.setEnName(StringUtils.substringBetween(streetUrl, "/ershoufang/", "/"));
                System.out.println("街道：" + streetName);
                System.out.println("  |——链接：" + streetUrl);
                streetList.add(street);
            });
            //存成json文件
            String jsonstr = JSONArray.toJSONString(streetList);
            IOUtil.outFile(jsonstr, "streets.json");

        }catch (Exception eee){
            eee.printStackTrace();
            EmailService.sendMail("769010256@qq.com", page.getUrl().toString(), eee.toString());
        }
    }


    @Override
    public Site getSite() {
        return site;
    }


    public static void main(String[] args){
        Spider.create(new StreetProcessor())
                //从"https://www.lianjia.com/city/"开始抓
                .addUrl("https://nj.lianjia.com/ershoufang/jianye/")
                //开启2个线程抓取
                .thread(1)
                //启动爬虫
                .run();
    }

}
