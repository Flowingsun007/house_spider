# house_spider
Lianjia second house spider链家二手房爬虫~ Springboot + Webmagic + Mysql + Redis
## 简介
#### - 基于Java8、Java开源爬虫框架WebMagic、Springboot的一个链家爬虫，上手即用，可以指定需要爬取的城市名称、或者爬取全国。
#### - 数据存Mysql，建表sql在resource下已经提供。Redis是用来存放爬取过程中已抓取的城市/行政区信息，翻页，防止重复爬取等
#### - 没有用WebMagic的Pipeline来存DB，而是直接用JDBC存的，默认爬虫线程数为1，每翻页1页批量插入Mysql。
#### - 由于链家限制爬取页数为100页，所以一个城市下，先查出所有行政区，再遍历行政区下的所有街道，以每个街道为单位，进行最大100页的爬取，这样基本上能爬下城市的所有房源数据。（部分别墅、车位直接忽略没存DB）
