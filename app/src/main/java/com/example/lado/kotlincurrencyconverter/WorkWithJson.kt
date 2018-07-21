package com.example.lado.kotlincurrencyconverter

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.Editable
import android.util.Log
import android.widget.Toast
import com.google.gson.JsonElement
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import com.google.gson.GsonBuilder
import com.google.gson.Gson

class WorkWithJson : CurrencyActivity() {

    //val URL_LINK: String = "http://data.fixer.io/api/"
    //val URL_LINK: String = "http://data.fixer.io/api/latest?access_key=777f19cddfb2d3523372aa02ba171ebb&format=1"
    fun json() {

        Log.e("WARN", CurrencyActivity().textView_msg?.text.toString())

        val gson = GsonBuilder()
                .setDateFormat("dd-MM-yyyy'T'HH:mm:ssZ")
                .create()

        val retrofit = Retrofit.Builder()
                .baseUrl(URL_LINK)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()

        val messageAPI = retrofit.create(CurrencyAPI::class.java)

        val calling: Call<JsonElement> = messageAPI.json()

        calling.enqueue(object : Callback<JsonElement> {
            override fun onFailure(call: Call<JsonElement>?, t: Throwable?) {
                Log.d("FAIL1", t.toString())
            }

            override fun onResponse(call: Call<JsonElement>?, response: Response<JsonElement>?) {
                if(response != null) {
                    if(response.isSuccessful) {
                        val jsonElement: JsonElement? = response?.body()
                        Log.d("JSON", jsonElement.toString())
                    }
                    else {
                        //Toast.makeText(this@WorkWithJson, "CODE IS $response.code()", Toast.LENGTH_SHORT).show()
                        Log.d("CODE1", "CODE IS $response.code()")
                    }
                }
            }

        })
    }


}
