package com.comp1786.m_expense.model;

public class Trip {
    private Integer id;
    private String name;
    private String destination;
    private Integer type;
    private String start_Date;
    private String end_Date;
    private Integer risk;
    private String description;
//    private ArrayList<Expenses> expenses;

    @Override
    public String toString() {
        return "Trip{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", destination='" + destination + '\'' +
                ", type=" + type +
                ", start_Date='" + start_Date + '\'' +
                ", end_Date='" + end_Date + '\'' +
                ", risk=" + risk +
                ", description='" + description + '\'' +
                '}';
    }

    public Trip(Integer id, String name, String destination, String start_Date, String end_Date, Integer risk, String description, Integer type) {
        this.id = id;
        this.name = name;
        this.destination = destination;
        this.start_Date = start_Date;
        this.end_Date = end_Date;
        this.risk = risk;
        this.type=type;
        this.description = description;
    }

    public Trip() {
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getStart_Date() {
        return start_Date;
    }

    public void setStart_Date(String start_Date) {
        this.start_Date = start_Date;
    }

    public String getEnd_Date() {
        return end_Date;
    }

    public void setEnd_Date(String end_Date) {
        this.end_Date = end_Date;
    }

    public Integer getRisk() {
        return risk;
    }

    public void setRisk(Integer risk) {
        this.risk = risk;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
