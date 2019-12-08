# house_spider
Lianjia second house spider链家二手房爬虫~ Springboot + Webmagic + Mysql + Redis
## 简介
#### - 基于Java8 +、开源爬虫框架WebMagic、Springboot的一个链家爬虫，上手即用，可以指定需要爬取的城市名称、或者爬取全国。
#### - 数据存Mysql，默认根据搜索城市+日期自动建表，如搜索“南京”，则会通过JDBC新建表：“南京_20191127”
#### - 没有用WebMagic的Pipeline来存DB，而是直接用JDBC存的，默认爬虫线程数为1，每翻页1页批量插入Mysql。
#### - 由于链家限制爬取页数为100页，所以一个城市下，先查出所有行政区，再遍历行政区下的所有街道，以每个街道为单位，进行最大100页的爬取，这样基本上能爬下城市的所有房源数据。（部分别墅、车位直接忽略没存DB）

## 使用
#### 基础依赖：安装mysql + redis,并在新建数据库：house
#### a.git clone https://github.com/Flowingsun007/house_spider.git  或下载项目zip包至本地
#### b.修改application.properties和HouseDao.java中的JDBC连接，配置用户名密码为你自己的。
#### c.[POST]http://localhost:8009/house/spider?cityName=南京 即可开启自动爬取和存储。
