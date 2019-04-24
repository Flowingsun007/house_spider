package com.flowingbit.data.collect.house_spider.service;

import com.flowingbit.data.collect.house_spider.util.IOUtil;
import org.apache.commons.lang3.StringUtils;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;

public class GithubRepoPageProcessor implements PageProcessor {

    private static int count = 1;
    // 部分一：抓取网站的相关配置，包括编码、抓取间隔、重试次数等
    private Site site = Site.me()
            .setRetryTimes(3)
            .setSleepTime(1000)
            .addHeader("User-Agent","-Mozilla/5.0 (Linux; Android 6.0; Nexus 5 Build/MRA58N) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/60.0.3095.5 Mobile Safari/537.36");

    /**
     * process是定制爬虫逻辑的核心接口，在这里编写抽取逻辑
     */
    @Override
    public void process(Page page) {
        System.out.println("进入process()方法");
        // 部分二：定义如何抽取页面信息，并保存下来
        count++ ;
        page.putField("title",page.getHtml().xpath("//div[@class='item_main']/text()"));
//        // 将html输出到文件
//        try{
//            IOUtil.outFile(page.getHtml().toString(),"C:/Users/flowi/Desktop/lianjia.html");
//        }catch (Exception e){
//            e.printStackTrace();
//        }
        if (page.getResultItems().get("title") == null) {
            //skip this page
            page.setSkip(true);
        }else{
            System.out.print("房屋title: ");
            System.out.println(page.getHtml().xpath("//div[@class='item_main']/text()").toString());
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

        Spider.create(new GithubRepoPageProcessor())
                //从"https://github.com/code4craft"开始抓
                .addUrl("https://nj.lianjia.com/ershoufang/pg1")
                //开启2个线程抓取
                .thread(2)
                //启动爬虫
                .run();
    }

}
