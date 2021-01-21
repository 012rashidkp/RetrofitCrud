package com.example.crudapi.Interface;

import com.example.crudapi.Model.Result;


import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiInterface {

    @FormUrlEncoded
    @POST("task-create/")
    Call<Result> createTask(
            @Field("title") String title,
            @Field("body") String body);


    @GET("task-list/")
    Call<Result>getdatas();

    @GET("task-detail/")
    Call<Result>getdetails(@Query("id")String id);


    @POST("task-delete/")
    Call<Result>getdelete(@Query("id")String id);

@GET("task-pagination/")
    Call<Result>getsearchitems(@Query("search")String search);
@FormUrlEncoded
@POST("task-update/")
    Call<Result>updateTask(@Query("id")String id,@Field("title")String title,@Field("body")String body);

@GET("products-list/")
    Call<Result>getproducts();

@GET("growth-list/")
    Call<Result>getChart();

}
