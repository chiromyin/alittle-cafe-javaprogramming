package com.example.littlecafeshop;

public class Items {

    private String title;
    private String description;
    private String imgURL;
    private String price;

    public Items(String title, String description, String imgURL, String price) {
        this.title = title;
        this.description = description;
        this.imgURL = imgURL;
        this.price = price;
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

    public String getImgURL() {
        return imgURL;
    }

    public void setImgURL(String imgURL) {
        this.imgURL = imgURL;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
