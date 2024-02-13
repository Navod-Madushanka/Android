package com.navod.networing.model;

public class Product {
    private int id;
    private String title;
    private String description;
    private String brand;
    private String category;
    private double price;
    private int active;

    public Product() {
    }

    public Product(int id, String title, String description, String brand, String category, double price, int active) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.brand = brand;
        this.category = category;
        this.price = price;
        this.active = active;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getActive() {
        return active;
    }

    public void setActive(int active) {
        this.active = active;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", brand='" + brand + '\'' +
                ", category='" + category + '\'' +
                ", price='" + price + '\'' +
                ", active=" + active +
                '}';
    }
}
