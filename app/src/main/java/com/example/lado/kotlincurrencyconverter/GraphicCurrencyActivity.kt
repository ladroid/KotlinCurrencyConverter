package com.example.lado.kotlincurrencyconverter

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.Editable
import android.util.Log
import android.view.View
import com.anychart.anychart.*
import com.anychart.anychart.AnyChart.pie
import java.util.*
import kotlin.collections.ArrayList
import com.anychart.anychart.DataEntry
import com.beust.klaxon.JsonObject
import com.beust.klaxon.Klaxon
import com.beust.klaxon.Parser
import com.beust.klaxon.PathMatcher
import com.google.gson.JsonElement
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.regex.Pattern
import com.anychart.anychart.TooltipPositionMode
import com.anychart.anychart.AnyChart
import com.anychart.anychart.Cartesian
import com.anychart.anychart.AnyChart.cartesian
import com.anychart.anychart.EnumsAnchor
import com.anychart.anychart.MarkerType
import com.anychart.anychart.AnyChart.line
import com.anychart.anychart.CartesianSeriesLine
import com.anychart.anychart.Set


class GraphicCurrencyActivity : AppCompatActivity() {
    val URL_LINK: String = "http://data.fixer.io/api/"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.graphiccurrency_activity)
        Draw()
    }

    fun Draw() {
        val retrofit = Retrofit.Builder()
                .baseUrl(URL_LINK)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

        val messageAPI = retrofit.create(CurrencyAPI::class.java)

        val calling: Call<JsonElement> = messageAPI.json()
        calling.enqueue(object : Callback<JsonElement> {
            override fun onFailure(call: Call<JsonElement>?, t: Throwable?) {
                Log.d("FAIL1", t.toString())
            }

            override fun onResponse(call: Call<JsonElement>?, response: Response<JsonElement>?) {
                if(response != null) {
                    if (response.isSuccessful) {
                        val jsonElement: JsonElement? = response?.body()

                        //parsing JsonElement and getting currency value
                        var parser: Parser = Parser()
                        var stringBuilder: StringBuilder = StringBuilder(jsonElement.toString())
                        var json: JsonObject = parser.parse(stringBuilder) as JsonObject
                        Log.e("SASA", "Age : ${json.string("date")}")
                        var json1: JsonObject? = json.obj("rates")

                        val cartesian = AnyChart.line()

                        cartesian.setAnimation(true)

                        cartesian.setPadding(10.0, 20.0, 5.0, 20.0)

                        cartesian.crosshair.setEnabled(true)
                        cartesian.crosshair
                                .setYLabel(true)
                                .setYStroke(null as Stroke?, null, null, null, null)

                        cartesian.tooltip.setPositionMode(TooltipPositionMode.POINT)

                        cartesian.setTitle("Currency today ${json.string("date")}")

                        cartesian.yAxis.setTitle("The value of currency")
                        cartesian.xAxis.labels.setPadding(5.0, 5.0, 5.0, 5.0)

                        var data = ArrayList<DataEntry>()
                        data.add(ValueDataEntry("AED",json1?.double("AED")))
                        data.add(ValueDataEntry("AFN", json1?.double("AFN")))
                        data.add(ValueDataEntry("ALL", json1?.double("ALL")))
                        data.add(ValueDataEntry("AMD", json1?.double("AMD")))
                        data.add(ValueDataEntry("ANG", json1?.double("ANG")))
                        data.add(ValueDataEntry("AOA", json1?.double("AOA")))
                        data.add(ValueDataEntry("ARS", json1?.double("ARS")))
                        data.add(ValueDataEntry("AWG", json1?.double("AWG")))
                        data.add(ValueDataEntry("AUD", json1?.double("AUD")))
                        data.add(ValueDataEntry("AZN", json1?.double("AZN")))
                        data.add(ValueDataEntry("BAM", json1?.double("BAM")))
                        data.add(ValueDataEntry("BBD", json1?.double("BBD")))
                        data.add(ValueDataEntry("BDT", json1?.double("BDT")))
                        data.add(ValueDataEntry("BGN", json1?.double("BGN")))
                        data.add(ValueDataEntry("BHD", json1?.double("BHD")))
                        data.add(ValueDataEntry("BIF", json1?.double("BIF")))
                        data.add(ValueDataEntry("BMD", json1?.double("BMD")))
                        data.add(ValueDataEntry("BND", json1?.double("BND")))
                        data.add(ValueDataEntry("BOB", json1?.double( "BOB")))

                        val set = Set(data)
                        val series3Mapping = set.mapAs("{ x: 'x', value: 'value3' }")

                        val series3 = cartesian.line(series3Mapping)
                        series3.setName("Currency")
                        series3.getHovered().getMarkers().setEnabled(true)
                        series3.getHovered().getMarkers()
                                .setType(MarkerType.CIRCLE)
                                .setSize(4.0)
                        series3.getTooltip()
                                .setPosition("right")
                                .setAnchor(EnumsAnchor.LEFT_CENTER)
                                .setOffsetX(5.0)
                                .setOffsetY(5.0)

                        cartesian.legend.setEnabled(true)
                        cartesian.legend.setFontSize(13.0)
                        cartesian.legend.setPadding(0.0, 0.0, 10.0, 0.0)

                        var anyChartView = findViewById<View>(R.id.any_chart_view) as AnyChartView
                        anyChartView.setChart(cartesian)

                        Log.e("JSON", jsonElement.toString())
                    }
                    else {
                        Log.d("CODE1", "CODE IS $response.code()");
                    }
                }
            }

        })
    }
}