package com.example.crudapi.Model;

import com.google.gson.annotations.SerializedName;

public class Products {
    @SerializedName("prod_id")
    private String productid;
    @SerializedName("title")
    private String name;
   @SerializedName("desc")
    private String desc;
   @SerializedName("image")
   private String image;
   @SerializedName("available")
   private Boolean available;

    public Products(String productid, String name, String desc, String image, Boolean available) {
        this.productid = productid;
        this.name = name;
        this.desc = desc;
        this.image = image;
        this.available = available;
    }

    public String getProductid() {
        return productid;
    }

    public String getName() {
        return name;
    }

    public String getDesc() {
        return desc;
    }

    public String getImage() {
        return image;
    }

    public Boolean getAvailable() {
        return available;
    }
}
