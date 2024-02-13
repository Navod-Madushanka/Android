package com.navod.etradeadmin.entity;

import android.net.Uri;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class Product implements Serializable {
    private String id;
    private String category;
    private String brand;
    private String model;
    private String description;
    private Date addedAt;
    private Date updatedAt;
    private double price;
    private double discount;
    private int quantity;
    private List<String> images;
    private boolean status;
    private Uri imageUri;
    private List<String> imageUriList;

    public Product() {
    }

    public Product(String category, String brand, String model, String description, Date addedAt, Date updatedAt, double price, double discount, int quantity, List<String> images, boolean status) {
        this.category = category;
        this.brand = brand;
        this.model = model;
        this.description = description;
        this.addedAt = addedAt;
        this.updatedAt = updatedAt;
        this.price = price;
        this.discount = discount;
        this.quantity = quantity;
        this.images = images;
        this.status = status;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    public Date getAddedAt() {
        return addedAt;
    }

    public void setAddedAt(Date addedAt) {
        this.addedAt = addedAt;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Uri getImageUri() {
        return imageUri;
    }

    public void setImageUri(Uri imageUri) {
        this.imageUri = imageUri;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<String> getImageUriList() {
        return imageUriList;
    }

    public void setImageUriList(List<String> imageUriList) {
        this.imageUriList = imageUriList;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id='" + id + '\'' +
                ", category='" + category + '\'' +
                ", brand='" + brand + '\'' +
                ", model='" + model + '\'' +
                ", description='" + description + '\'' +
                ", addedAt=" + addedAt +
                ", updatedAt=" + updatedAt +
                ", price=" + price +
                ", discount=" + discount +
                ", quantity=" + quantity +
                ", images=" + images +
                ", status=" + status +
                ", imageUri=" + imageUri +
                ", imageUriList=" + imageUriList +
                '}';
    }
}
