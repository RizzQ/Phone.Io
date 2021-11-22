package com.example.myapplication8.retrofit

import com.example.myapplication8.AppleDescriptionModel
import com.example.myapplication8.AppleModel
import com.example.myapplication8.OnlineModel
import retrofit2.Call
import retrofit2.http.*

interface ApiEndpoint {

    @GET("brands/apple-phones-48")
    fun data(): Call<AppleModel>

    @GET("{slug}")
    fun datadetail(@Path("slug") slug: String): Call<AppleDescriptionModel>

}