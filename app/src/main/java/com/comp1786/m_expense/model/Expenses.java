package com.comp1786.m_expense.model;

import java.io.Serializable;

public class Expenses implements Serializable {
    private Integer id;
    private Integer type_id;
    private Float amount;
    private String name;
    private String date;
    private String time;
    private String comment;
    private String location;
    private String image;
    private Integer trip_id;

    public Expenses() {

    }

    @Override
    public String toString() {
        return "Expenses{" +
                "id=" + id +
                ", type_id=" + type_id +
                ", amount=" + amount +
                ", date='" + date + '\'' +
                ", time='" + time + '\'' +
                ", comment='" + comment + '\'' +
                ", location='" + location + '\'' +
                ", image='" + image + '\'' +
                ", trip_id=" + trip_id +
                '}';
    }

    public Expenses(Integer id, Integer type_id, Float amount, String date, String time, String comment, String location, String image, String name,Integer trip_id) {
        this.id = id;
        this.type_id = type_id;
        this.amount = amount;
        this.date = date;
        this.time = time;
        this.comment = comment;
        this.location = location;
        this.image = image;
        this.name=name;
        this.trip_id = trip_id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


    public Integer getType_id() {
        return type_id;
    }

    public void setType_id(Integer type_id) {
        this.type_id = type_id;
    }

    public Float getAmount() {
        return amount;
    }

    public void setAmount(Float amount) {
        this.amount = amount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Integer getTrip_id() {
        return trip_id;
    }

    public void setTrip_id(Integer trip_id) {
        this.trip_id = trip_id;
    }


}
