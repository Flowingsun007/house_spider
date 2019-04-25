package com.flowingbit.data.collect.house_spider.service;

import com.flowingbit.data.collect.house_spider.utils.IOUtil;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Selectable;

import java.util.List;

public class SecondHandHouseProcessorCookie implements PageProcessor {

    private static int count = 1;
    // 部分一：抓取网站的相关配置，包括编码、抓取间隔、重试次数等
    private Site site = Site.me()
            .setRetryTimes(3)
            .setSleepTime(1000)
            .setUserAgent("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_13_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/73.0.3683.103 Safari/537.36")
            .addHeader("Accept-Encoding","gzip, deflate, br")
            .addHeader("Accept-Language","zh-CN,zh;q=0.9")
            .addCookie("Cookie","mediav=%7B%22eid%22%3A%22202234%22%2C%22ep%22%3A%22%22%2C%22vid%22%3A%22v9sDr%24pFI(%3Aa-S_t%5E8OX%22%2C%22ctn%22%3A%22%22%7D; lianjia_uuid=d31f57ac-2c7c-465f-8fc3-3e274cf9fefa; _smt_uid=5cbc02c3.1ab59423; UM_distinctid=16a3e6acc465c9-02fb1c4353ad53-366b7e03-13c680-16a3e6acc4a39f; _ga=GA1.2.720027541.1555825353; _gid=GA1.2.1682246099.1556026453; _jzqx=1.1556028767.1556028767.1.jzqsr=nj%2Elianjia%2Ecom|jzqct=/.-; _jzqckmp=1; lianjia_ssid=13abb53a-7c76-4d60-a6e3-a7cc58ae8dc6; Hm_lvt_9152f8221cb6243a53c83b956842be8a=1555825347,1556026450,1556115620,1556148700; _jzqa=1.805181070544132100.1555825348.1556119263.1556148700.7; _jzqc=1; _jzqy=1.1555825348.1556148700.1.jzqsr=baidu|jzqct=%E9%93%BE%E5%AE%B6.-; sensorsdata2015jssdkcross=%7B%22distinct_id%22%3A%2216a3e6ad48d19f-03ae652152f642-366b7e03-1296000-16a3e6ad496287%22%2C%22%24device_id%22%3A%2216a3e6ad48d19f-03ae652152f642-366b7e03-1296000-16a3e6ad496287%22%2C%22props%22%3A%7B%22%24latest_traffic_source_type%22%3A%22%E4%BB%98%E8%B4%B9%E5%B9%BF%E5%91%8A%E6%B5%81%E9%87%8F%22%2C%22%24latest_referrer%22%3A%22https%3A%2F%2Fsp0.baidu.com%2F9q9JcDHa2gU2pMbgoY3K%2Fadrc.php%3Ft%3D06KL00c00fZg9KY0z7kB0nVfAsjNtTKT00000DdAc7C00000I3cmht.THLKVQ1i0A3qmh7GuZNCUvd-gLKM0Znqmy7BrHNBmvmsnj0dnj6vrfKd5HuarDDdPWKDnYcsPDm3wWR1fR7An1fLnYN%22%2C%22%24latest_referrer_host%22%3A%22sp0.baidu.com%22%2C%22%24latest_search_keyword%22%3A%22%E9%93%BE%E5%AE%B6%22%2C%22%24latest_utm_source%22%3A%22baidu%22%2C%22%24latest_utm_medium%22%3A%22pinzhuan%22%2C%22%24latest_utm_campaign%22%3A%22sousuo%22%2C%22%24latest_utm_content%22%3A%22biaotimiaoshu%22%2C%22%24latest_utm_term%22%3A%22biaoti%22%7D%7D; select_city=320100; all-lj=ba32fa4540e52c45d4c94b9a16e82078; TY_SESSION_ID=408eeb5c-00bb-4f81-bad7-0b4a1ba1f83e; Qs_lvt_200116=1556026486%2C1556116569%2C1556148718; CNZZDATA1253492138=721136662-1556021541-https%253A%252F%252Fwww.lianjia.com%252F%7C1556144122; _qzjc=1; CNZZDATA1254525948=2127128895-1556024104-https%253A%252F%252Fwww.lianjia.com%252F%7C1556147367; CNZZDATA1255633284=1780028733-1556024087-https%253A%252F%252Fwww.lianjia.com%252F%7C1556144247; CNZZDATA1255604082=739021441-1556021144-https%253A%252F%252Fwww.lianjia.com%252F%7C1556144347; mediav=%7B%22eid%22%3A%22202234%22%2C%22ep%22%3A%22%22%2C%22vid%22%3A%22v9sDr%24pFI(%3Aa-S_t%5E8OX%22%2C%22ctn%22%3A%22%22%7D; Qs_pv_200116=1116876091986812400%2C3635238705366177000%2C138735552494451070%2C4237905853180534300%2C3979656174236260400; Hm_lpvt_9152f8221cb6243a53c83b956842be8a=1556148844; _qzja=1.273364070.1556026487141.1556119262994.1556148718909.1556148835372.1556148844528.0.0.0.20.6; _qzjto=5.1.0")
            .addHeader("Accept","text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3");

    /**
     * process是定制爬虫逻辑的核心接口，在这里编写抽取逻辑
     */
    @Override
    public void process(Page page) {
        System.out.println("=============process()================");
        // 部分二：定义如何抽取页面信息，并保存下来
        count++ ;
        page.putField("target",page.getHtml().xpath("//ul[@class='sellListContent']").toString());
        //将html输出到文件
        try{
            // C:/Users/flowi/Desktop/lianjia.html
            IOUtil.outFile(page.getHtml().toString(),"/Users/zhaoluyang/Desktop/lianjia-cookie.html");
        }catch (Exception e){
            e.printStackTrace();
        }
        if (page.getResultItems().get("target")==null) {
            System.out.println("=============target==null================");
            //skip this page
            page.setSkip(true);
        }else{
            //开始提取页面信息
            System.out.println("================url:=================\n" + page.getUrl().toString());
            List<Selectable> targets = page.getHtml().xpath("//li[@class='clear LOGCLICKDATA']").nodes();
            targets.forEach(e->{
                String title = e.xpath("//div[@class='title']/a[1]/text()").toString();
                String houseUrl = e.xpath("//a[@class='noresultRecommend img ']/@href").toString();
                String img = e.xpath("//img[@class='lj-lazy']/@data-original").toString();
                String s = e.xpath("//div[@class='houseInfo']/text()").toString();
                String community = e.xpath("//div[@class='houseInfo']/a/text()").toString();
                String floor = e.xpath("//div[@class='positionInfo']/text()").toString();
                String region = e.xpath("//div[@class='positionInfo']/a[1]/text()").toString();
                String totolPrice = e.xpath("//div[@class='totalPrice']/span[1]/text()").toString();
                String averagePrice = e.xpath("//div[@class='unitPrice']/span[1]/text()").toString();

                String followInfo = e.xpath("//div[@class='followInfo']/text()").toString();
                String[] sl = followInfo.split("/");
                String whtch = sl[0];
                String view = sl[1];
                String releaseDate = sl[2];

                System.out.println("title"+title);
                System.out.println("houseUrl:"+houseUrl);
                System.out.println("img:"+img);
                System.out.println("s:"+s);
                System.out.println("followInfo:"+followInfo);

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

        Spider.create(new SecondHandHouseProcessorCookie())
                //从"https://nj.lianjia.com/ershoufang/pg1"开始抓
                .addUrl("https://nj.lianjia.com/ershoufang/pg1")
                //开启2个线程抓取
                .thread(2)
                //启动爬虫
                .run();
    }

}
