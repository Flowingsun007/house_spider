package com.flowingbit.data.collect.house_spider.service;

import com.flowingbit.data.collect.house_spider.utils.IOUtil;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Selectable;

import java.util.List;

public class SecondHandHouseProcessor implements PageProcessor {

    private static int count = 1;
    // 部分一：抓取网站的相关配置，包括编码、抓取间隔、重试次数等
    private Site site = Site.me()
            .setRetryTimes(3)
            .setSleepTime(1000)
            .addHeader("User-Agent","Mozilla/5.0 (Macintosh; Intel Mac OS X 10_13_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/73.0.3683.103 Safari/537.36");

    /**
     * process是定制爬虫逻辑的核心接口，在这里编写抽取逻辑
     */
    @Override
    public void process(Page page) {
        // 部分二：定义如何抽取页面信息，并保存下来
        System.out.println("================process() running...====================");
        count++ ;
        page.putField("title",page.getHtml().xpath("//div[@class='item_main']/text()").toString());
        //将html输出到文件
        try{
            // C:/Users/flowi/Desktop/lianjia.html
            // /Users/zhaoluyang/Desktop/lianjia-cookie.html
            IOUtil.outFile(page.getHtml().toString(),"C:/Users/flowi/Desktop/lianjia.html");
        }catch (Exception e){
            e.printStackTrace();
        }
        if (page.getResultItems().get("title") == null) {
            //skip this page
            page.setSkip(true);
        }else{
            //开始提取页面信息
            System.out.println("url:" + page.getUrl().toString());
            System.out.println(page.getResultItems().get("title").toString());
            List<Selectable> targets = page.getHtml().xpath("//li[@class='pictext']").nodes();
            targets.forEach(e->{
                String s = e.xpath("//a[1]/@href").toString();
                String houseUrl = "https://nj.lianjia.com/ershoufang" + s.substring(s.lastIndexOf("/"), s.length());
                System.out.println("houseUrl:" + houseUrl);
                String title = e.xpath("//div[@class='item_main']/text()").toString();
                String img = e.xpath("//div[@class='media_main']/img/@origin-src").toString();
                String[] sl = e.xpath("//div[@class='item_other text_cut']/text()").toString().split("/");
                String roomCount = sl[0];
                Float houseArea = Float.valueOf(sl[1].split("m")[0]);
                String towards = sl[2];
                String community = sl[3];
                String totalPrice = e.xpath("//span[@class='price_total']/em/text()").toString();
                String averagePrice = e.xpath("//span[@class='unit_price']/text()").toString();
                System.out.println("房源图片：" + img);
                System.out.println("title：" + title + " roomCount: " + roomCount + " houseArea: "
                        + houseArea + "(平方米) towards: " + towards + " community: " + community
                        + " totalPrice: " + totalPrice + "(万) averagePrice: " + averagePrice );
            });
            //stringList.stream().forEach(System.out::println);
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

        Spider.create(new SecondHandHouseProcessor())
                //从"https://github.com/code4craft"开始抓
                .addUrl("https://nj.lianjia.com/ershoufang/pg1")
                //开启2个线程抓取
                .thread(2)
                //启动爬虫
                .run();
    }

}
