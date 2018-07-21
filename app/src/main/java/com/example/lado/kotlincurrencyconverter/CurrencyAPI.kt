package com.example.lado.kotlincurrencyconverter

import com.google.gson.JsonElement
import retrofit2.Call
import retrofit2.http.*

interface CurrencyAPI {
    @GET("latest?access_key=777f19cddfb2d3523372aa02ba171ebb&format=1/")
    fun json() : Call<JsonElement>

    @GET("latest?access_key=777f19cddfb2d3523372aa02ba171ebb&symbols=")
    fun getCurrenciess(@Query("symbols") date: String) : Call<JsonElement>
}