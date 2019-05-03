package com.flowingbit.data.collect.house_spider.dao;

import com.flowingbit.data.collect.house_spider.model.House;

import java.sql.*;
import java.util.List;

public class HouseDao {
    private Connection conn = null;
    private Statement stmt = null;
    private static final String DRIVER = "com.mysql.jdbc.Driver";
    private static final String URL = "jdbc:mysql://47.110.50.248:3306/house_spider?"  + "user=root&password=mysql920726zly&useUnicode=true&characterEncoding=UTF8";

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

    public int insert(House house) {
        PreparedStatement ps = null;
        try {
            String sql = "INSERT IGNORE INTO `house_spider`.`house` (`id`, `title`, `url` ,`city`,`region`, `street`,`community`, `floor`, `total_price`, `average_price`, `image`, `watch`, `view`, `release_date`, `room_count`, `towards`, `house_area`, `decoration`, `elevator`) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);";
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
            ps.setInt(13, house.getView());
            ps.setString(14, house.getReleaseDate());
            ps.setString(15, house.getRoomCount());
            ps.setString(16, house.getTowards());
            ps.setDouble(17, house.getHouseArea());
            ps.setString(18, house.getDecoration());
            ps.setString(19, house.getElevator());
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

    public void batchInsert(List<House> houseList) throws Exception{
        PreparedStatement ps = null;
        String sql = "INSERT IGNORE INTO `house_spider`.`house` (`id`, `title`, `url` ,`city`,`region`, `street`,`community`, `floor`, `total_price`, `average_price`, `image`, `watch`, `view`, `release_date`, `room_count`, `towards`, `house_area`, `decoration`, `elevator`) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);";
        try {
            //优化插入第一步       设置手动提交
            if(conn.isClosed()){
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
                ps.setInt(13, house.getView());
                ps.setString(14, house.getReleaseDate());
                ps.setString(15, house.getRoomCount());
                ps.setString(16, house.getTowards());
                ps.setDouble(17, house.getHouseArea());
                ps.setString(18, house.getDecoration());
                ps.setString(19, house.getElevator());
                //批量添加sql，并执行
                ps.addBatch();
                if(i==len-1){
                    System.out.println("=================ps.executeBatch() starting.....==============");
                    ps.executeBatch();
                    conn.commit();
                    ps.clearBatch();
                    System.out.println("=================ps.executeBatch() finished.....==============");
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
