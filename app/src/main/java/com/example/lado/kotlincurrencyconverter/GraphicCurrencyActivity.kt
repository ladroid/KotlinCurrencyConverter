package com.example.lado.kotlincurrencyconverter

import android.app.AlertDialog
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
import dmax.dialog.SpotsDialog


class GraphicCurrencyActivity : AppCompatActivity() {
    val URL_LINK: String = "http://data.fixer.io/api/"
    var dialog: AlertDialog ?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.graphiccurrency_activity)
        Start()
        if(Start()) {
            dialog = SpotsDialog.Builder().setContext(this@GraphicCurrencyActivity).build().apply { dismiss() }
            Draw()
        }
    }

    fun Start(): Boolean {
        dialog = SpotsDialog.Builder()
                .setContext(this@GraphicCurrencyActivity)
                .setMessage("Loading")
                .setCancelable(true)
                .build()
                .apply {
                    show()
                }
        return true
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

                        //Draw a graphic
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
                        data.add(ValueDataEntry("AED", json1?.double("AED")))
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
                        data.add(ValueDataEntry("BOB", json1?.double("BOB")))
                        data.add(ValueDataEntry("BRL", json1?.double("BRL")))
                        data.add(ValueDataEntry("BSD", json1?.double("BSD")))
                        data.add(ValueDataEntry("BTC", json1?.double("BTC")))
                        data.add(ValueDataEntry("BTN", json1?.double("BTN")))
                        data.add(ValueDataEntry("BWP", json1?.double("BWP")))
                        data.add(ValueDataEntry("BYN", json1?.double("BYN")))
                        data.add(ValueDataEntry("BYR", json1?.double("BYR")))
                        data.add(ValueDataEntry("BZD", json1?.double("BZD")))
                        data.add(ValueDataEntry("CAD", json1?.double("CAD")))
                        data.add(ValueDataEntry("CDF", json1?.double("CDF")))
                        data.add(ValueDataEntry("CHF", json1?.double("CHF")))
                        data.add(ValueDataEntry("CLF", json1?.double("CLF")))
                        data.add(ValueDataEntry("CLP", json1?.double("CLP")))
                        data.add(ValueDataEntry("CNY", json1?.double("CNY")))
                        data.add(ValueDataEntry("COP", json1?.double("COP")))
                        data.add(ValueDataEntry("CRC", json1?.double("CRC")))
                        data.add(ValueDataEntry("CUC", json1?.double("CUC")))
                        data.add(ValueDataEntry("CUP", json1?.double("CUP")))
                        data.add(ValueDataEntry("CVE", json1?.double("CVE")))
                        data.add(ValueDataEntry("CZK", json1?.double("CZK")))
                        data.add(ValueDataEntry("DJF", json1?.double("DJF")))
                        data.add(ValueDataEntry("DKK", json1?.double("DKK")))
                        data.add(ValueDataEntry("DOP", json1?.double("DOP")))
                        data.add(ValueDataEntry("DZD", json1?.double("DZD")))
                        data.add(ValueDataEntry("EGP", json1?.double("EGP")))
                        data.add(ValueDataEntry("ERN", json1?.double("ERN")))
                        data.add(ValueDataEntry("ETB", json1?.double("ETB")))
                        data.add(ValueDataEntry("FJD", json1?.double("FJD")))
                        data.add(ValueDataEntry("FKP", json1?.double("FKP")))
                        data.add(ValueDataEntry("GBP", json1?.double("GBP")))
                        data.add(ValueDataEntry("GEL", json1?.double("GEL")))
                        data.add(ValueDataEntry("GGP", json1?.double("GGP")))
                        data.add(ValueDataEntry("GHS", json1?.double("GHS")))
                        data.add(ValueDataEntry("GIP", json1?.double("GIP")))
                        data.add(ValueDataEntry("GMD", json1?.double("GMD")))
                        data.add(ValueDataEntry("GNF", json1?.double("GNF")))
                        data.add(ValueDataEntry("GTQ", json1?.double("GTQ")))
                        data.add(ValueDataEntry("GYD", json1?.double("GYD")))
                        data.add(ValueDataEntry("HKD", json1?.double("HKD")))
                        data.add(ValueDataEntry("HNL", json1?.double("HNL")))
                        data.add(ValueDataEntry("HRK", json1?.double("HRK")))
                        data.add(ValueDataEntry("HTG", json1?.double("HTG")))
                        data.add(ValueDataEntry("HUF", json1?.double("HUF")))
                        data.add(ValueDataEntry("IDR", json1?.double("IDR")))
                        data.add(ValueDataEntry("ILS", json1?.double("ILS")))
                        data.add(ValueDataEntry("IMP", json1?.double("IMP")))
                        data.add(ValueDataEntry("INR", json1?.double("INR")))
                        data.add(ValueDataEntry("IQD", json1?.double("IQD")))
                        data.add(ValueDataEntry("IRR", json1?.double("IRR")))
                        data.add(ValueDataEntry("ISK", json1?.double("ISK")))
                        data.add(ValueDataEntry("JEP", json1?.double("JEP")))
                        data.add(ValueDataEntry("JMD", json1?.double("JMD")))
                        data.add(ValueDataEntry("JOD", json1?.double("JOD")))
                        data.add(ValueDataEntry("JPY", json1?.double("JPY")))
                        data.add(ValueDataEntry("KES", json1?.double("KES")))
                        data.add(ValueDataEntry("KGS", json1?.double("KGS")))
                        data.add(ValueDataEntry("KHR", json1?.double("KHR")))
                        data.add(ValueDataEntry("KMF", json1?.double("KMF")))
                        data.add(ValueDataEntry("KPW", json1?.double("KPW")))
                        data.add(ValueDataEntry("KRW", json1?.double("KRW")))
                        data.add(ValueDataEntry("KWD", json1?.double("KWD")))
                        data.add(ValueDataEntry("KYD", json1?.double("KYD")))
                        data.add(ValueDataEntry("KZT", json1?.double("KZT")))
                        data.add(ValueDataEntry("LAK", json1?.double("LAK")))
                        data.add(ValueDataEntry("LBP", json1?.double("LBP")))
                        data.add(ValueDataEntry("LKR", json1?.double("LKR")))
                        data.add(ValueDataEntry("LRD", json1?.double("LRD")))
                        data.add(ValueDataEntry("LSL", json1?.double("LSL")))

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