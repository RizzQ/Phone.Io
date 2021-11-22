package com.example.myapplication8

data class OnlineModel (
    val result: ArrayList<Result>
) {
    data class Result (val id: Int, val title: String, val image: String)
}