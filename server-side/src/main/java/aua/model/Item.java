package aua.model;

public class Item {
    private int id;
    private String name;
    private String description;
    private double price;
    private String picture_url;

    public Item() {
    }

    public int getId() {
        return id;
    }

    public Item setId(int id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public Item setName(String name) {
        this.name = name;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public Item setDescription(String description) {
        this.description = description;
        return this;
    }

    public double getPrice() {
        return price;
    }

    public Item setPrice(double price) {
        this.price = price;
        return this;
    }

    public String getPicture_url() {
        return picture_url;
    }

    public Item setPicture_url(String picture_url) {
        this.picture_url = picture_url;
        return this;
    }
}
