package com.flowingbit.data.collect.house_spider.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Timestamp;
import java.util.Date;
import java.util.UUID;

/**
 * @author Lyon
 * @date 2019/4/25 18:42
 * @description House
 **/
@Entity
@Table(name="house")
public class House {
    @Id
    @Column
    private String id;
    private String title;
    private String url;
    private String city;
    private String region;
    private String street;
    private String community;
    private String floor;
    private Double totalPrice;
    private Double averagePrice;
    private String image;
    private Integer watch;
    private Integer view;
    private String releaseDate;
    private String roomCount;
    private String towards;
    private Double houseArea;
    private String decoration;
    private String elevator;
    private Date createDate;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCommunity() {
        return community;
    }

    public void setCommunity(String community) {
        this.community = community;
    }

    public String getFloor() {
        return floor;
    }

    public void setFloor(String floor) {
        this.floor = floor;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Double getAveragePrice() {
        return averagePrice;
    }

    public void setAveragePrice(Double averagePrice) {
        this.averagePrice = averagePrice;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Integer getWatch() {
        return watch;
    }

    public void setWatch(Integer watch) {
        this.watch = watch;
    }

    public Integer getView() {
        return view;
    }

    public void setView(Integer view) {
        this.view = view;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getRoomCount() {
        return roomCount;
    }

    public void setRoomCount(String roomCount) {
        this.roomCount = roomCount;
    }

    public String getTowards() {
        return towards;
    }

    public void setTowards(String towards) {
        this.towards = towards;
    }

    public Double getHouseArea() {
        return houseArea;
    }

    public void setHouseArea(Double houseArea) {
        this.houseArea = houseArea;
    }

    public String getDecoration() {
        return decoration;
    }

    public void setDecoration(String decoration) {
        this.decoration = decoration;
    }

    public String getElevator() {
        return elevator;
    }

    public void setElevator(String elevator) {
        this.elevator = elevator;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    @Override
    public String toString() {
        return "House{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", url='" + url + '\'' +
                ", city='" + city + '\'' +
                ", region='" + region + '\'' +
                ", street='" + street + '\'' +
                ", community='" + community + '\'' +
                ", floor='" + floor + '\'' +
                ", totalPrice=" + totalPrice +
                ", averagePrice=" + averagePrice +
                ", image='" + image + '\'' +
                ", watch=" + watch +
                ", view=" + view +
                ", releaseDate='" + releaseDate + '\'' +
                ", roomCount='" + roomCount + '\'' +
                ", towards='" + towards + '\'' +
                ", houseArea=" + houseArea +
                ", decoration='" + decoration + '\'' +
                ", elevator='" + elevator + '\'' +
                ", createDate=" + createDate +
                '}';
    }
}
