package com.flowingbit.data.collect.house_spider.dao;

import com.flowingbit.data.collect.house_spider.model.House;

import java.sql.*;
import java.util.List;

public class HouseDao {
    private Connection conn = null;
    private Statement stmt = null;
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String URL = "jdbc:mysql://localhost:3306/house?" + "user=root&password=password&useUnicode=true&characterEncoding=UTF8&serverTimezone=UTC";
    private static final String CREATE_TABLE_SQL =
            "  (`id` varchar(15) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '房源编号id',\n" +
                    "  `title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '标题',\n" +
                    "  `url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '链接',\n" +
                    "  `city` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '城市',\n" +
                    "  `region` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '行政区域',\n" +
                    "  `street` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '街道',\n" +
                    "  `community` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '小区',\n" +
                    "  `floor` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '楼层',\n" +
                    "  `total_price` double DEFAULT NULL COMMENT '总价(万)',\n" +
                    "  `average_price` double DEFAULT NULL COMMENT '均价(元/平米)',\n" +
                    "  `image` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '首图链接',\n" +
                    "  `watch` int(1) DEFAULT NULL COMMENT '关注',\n" +
                    "  `release_date` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '发布时间',\n" +
                    "  `room_count` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '房间数量',\n" +
                    "  `towards` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '朝向',\n" +
                    "  `house_area` double DEFAULT NULL COMMENT '面积(平米)',\n" +
                    "  `decoration` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '装修',\n" +
                    "  `house_age` int(4) DEFAULT NULL COMMENT '房屋建设年代',\n" +
                    "  `create_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',\n" +
                    "  PRIMARY KEY (`id`) USING BTREE\n" +
                    ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=COMPACT;";

    private static final String INSERT_SQL = "(`id`, `title`, `url` ,`city`,`region`, `street`,`community`, `floor`, `total_price`, `average_price`, `image`, `watch`, `release_date`, `room_count`, `towards`, `house_area`, `decoration`, `house_age`) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);";
    private static final String BATCH_INSERT_SQL = "(`id`, `title`, `url` ,`city`,`region`, `street`,`community`, `floor`, `total_price`, `average_price`, `image`, `watch`, `release_date`, `room_count`, `towards`, `house_area`, `decoration`, `house_age`) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);";
    public HouseDao() {
        try {
            Class.forName(DRIVER);
            conn = DriverManager.getConnection(URL);
            stmt = conn.createStatement();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public boolean createHouseTable(String tableName){
        String sql =  "CREATE TABLE " + tableName + CREATE_TABLE_SQL;
        try(Statement stmt = conn.createStatement()){
            if(0 == stmt.executeUpdate(sql)){
                return true;
            }
        }catch (SQLException s){}
        return false;
    }

    public int insert(House house, String tableName) {
        PreparedStatement ps = null;
        String sql = "INSERT IGNORE INTO `house`.`" + tableName + "`" + INSERT_SQL;
        try {
            ps = conn.prepareStatement(sql);
            ps.setString(1, house.getId());
            ps.setString(2, house.getTitle());
            ps.setString(3, house.getUrl());
            ps.setString(4, house.getCity());
            ps.setString(5, house.getRegion());
            ps.setString(6, house.getStreet());
            ps.setString(7, house.getCommunity());
            ps.setString(8, house.getFloor());
            ps.setDouble(9, house.getTotalPrice());
            ps.setDouble(10, house.getAveragePrice());
            ps.setString(11, house.getImage());
            ps.setInt(12, house.getWatch());
            ps.setString(13, house.getReleaseDate());
            ps.setString(14, house.getRoomCount());
            ps.setString(15, house.getTowards());
            ps.setDouble(16, house.getHouseArea());
            ps.setString(17, house.getDecoration());
            ps.setInt(18, house.getHouseAge());
            return ps.executeUpdate();
        } catch (SQLException e) {
            try {
                if(ps!=null){
                    ps.close();
                }
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            e.printStackTrace();
            return -1;
        }
    }

    public void batchInsert(List<House> houseList, String tableName) throws Exception{
        PreparedStatement ps = null;
        String sql = "INSERT IGNORE INTO `house`.`" + tableName + "`"+ BATCH_INSERT_SQL;
        try {
            //优化插入第一步       设置手动提交
            if(conn==null||conn.isClosed()){
                System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>onn==null||conn.isClosed()<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
                conn = DriverManager.getConnection(URL);
            }
            ps = conn.prepareStatement(sql);
            conn.setAutoCommit(false);
            int len = houseList.size();
            for(int i=0; i<len; i++){
                House house = houseList.get(i);
                ps.setString(1, house.getId());
                ps.setString(2, house.getTitle());
                ps.setString(3, house.getUrl());
                ps.setString(4, house.getCity());
                ps.setString(5, house.getRegion());
                ps.setString(6, house.getStreet());
                ps.setString(7, house.getCommunity());
                ps.setString(8, house.getFloor());
                ps.setDouble(9, house.getTotalPrice());
                ps.setDouble(10, house.getAveragePrice());
                ps.setString(11, house.getImage());
                ps.setInt(12, house.getWatch());
                ps.setString(13, house.getReleaseDate());
                ps.setString(14, house.getRoomCount());
                ps.setString(15, house.getTowards());
                ps.setDouble(16, house.getHouseArea());
                ps.setString(17, house.getDecoration());
                ps.setInt(18, house.getHouseAge());
                //批量添加sql，并执行
                ps.addBatch();
                if(i==len-1){
                    ps.executeBatch();
                    conn.commit();
                    ps.clearBatch();
                }
            }
            ps.close();
            System.out.println("=======================JDBC批量插入成功====================");
        }catch (Exception e){
            e.printStackTrace();
            try {
                if(ps!=null){
                    ps.close();
                }
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            throw new Exception("批量插入失败");
        }


    }

}
