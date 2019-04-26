package com.flowingbit.data.collect.house_spider.dao;

import com.flowingbit.data.collect.house_spider.model.House;

import java.sql.*;

public class HouseDao {
    private Connection conn = null;
    private Statement stmt = null;

    public HouseDao() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            String url = "jdbc:mysql://47.110.50.248:3306/house_spider?"
                    + "user=root&password=mysql920726zly&useUnicode=true&characterEncoding=UTF8";
            conn = DriverManager.getConnection(url);
            stmt = conn.createStatement();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public int add(House house) {
        try {
            String sql = "INSERT IGNORE INTO `house_spider`.`house` (`id`, `title`, `url` , `community`, `region`, `floor`, `total_price`, `average_price`, `image`, `watch`, `view`, `release_date`, `room_count`, `towards`, `house_area`, `decoration`, `elevator`) VALUES (?, ?, ?, ?, ?, ?, ?, ?,?,?, ?, ?, ?, ?, ?, ?, ?);";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, house.getId());
            ps.setString(2, house.getTitle());
            ps.setString(3, house.getUrl());
            ps.setString(4, house.getCommunity());
            ps.setString(5, house.getRegion());
            ps.setString(6, house.getFloor());
            ps.setDouble(7, house.getTotalPrice());
            ps.setDouble(8, house.getAveragePrice());
            ps.setString(9, house.getImage());
            ps.setInt(10, house.getWatch());
            ps.setInt(11, house.getView());
            ps.setString(12, house.getReleaseDate());
            ps.setString(13, house.getRoomCount());
            ps.setString(14, house.getTowards());
            ps.setDouble(15, house.getHouseArea());
            ps.setString(16, house.getDecoration());
            ps.setString(17, house.getElevator());
            return ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

}
