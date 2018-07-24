package com.example.lado.kotlincurrencyconverter

import com.google.gson.JsonElement
import retrofit2.Call
import retrofit2.http.*

interface CurrencyAPI {
    @GET("latest?access_key=YOUR_API_KEY&format=1/")
    fun json() : Call<JsonElement>

    @GET("latest?access_key=YOUR_API_KEY&symbols=")
    fun getCurrenciess(@Query("symbols") date: String) : Call<JsonElement>
}