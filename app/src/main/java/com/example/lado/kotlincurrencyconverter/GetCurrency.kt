package com.example.lado.kotlincurrencyconverter

import com.google.gson.annotations.SerializedName

data class GetCurrency(@SerializedName("rates") val Rates: String)