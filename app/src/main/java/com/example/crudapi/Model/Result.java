package com.example.crudapi.Model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Result {
    @SerializedName("error")
    private Boolean error;
    @SerializedName("message")
    private String message;
    @SerializedName("datas")
    private List<Datas> datas;
    @SerializedName("data")
    private Details details;
    @SerializedName("results")
    private List<SearchItems>searchItems;
    @SerializedName("products")
    private List<Products>products;

    public Result(Boolean error, String message, List<Datas> datas, Details details, List<SearchItems> searchItems, List<Products> products) {
        this.error = error;
        this.message = message;
        this.datas = datas;
        this.details = details;
        this.searchItems = searchItems;
        this.products = products;
    }

    public Boolean getError() {
        return error;
    }

    public String getMessage() {
        return message;
    }

    public List<Datas> getDatas() {
        return datas;
    }

    public Details getDetails() {
        return details;
    }

    public List<SearchItems> getSearchItems() {
        return searchItems;
    }

    public List<Products> getProducts() {
        return products;
    }
}
