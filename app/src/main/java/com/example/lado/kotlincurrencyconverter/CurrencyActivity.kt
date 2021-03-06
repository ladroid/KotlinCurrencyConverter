package com.example.lado.kotlincurrencyconverter

import android.app.AlertDialog
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.Editable
import android.util.Log
import android.view.View
import android.widget.*
import org.w3c.dom.Text
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import com.beust.klaxon.Parser
import com.google.gson.Gson
import com.google.gson.JsonElement
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import com.beust.klaxon.JsonObject
import dmax.dialog.SpotsDialog

open class CurrencyActivity : AppCompatActivity() {

    val URL_LINK: String = "http://data.fixer.io/api/"

    open var textView_msg: TextView ?= null
    var textView: TextView ?= null
    var editText1: EditText ?= null
    var editText2: EditText ?= null
    var button: Button ?= null
    var textViewResult: TextView ?= null

    var numberInput: Double ?= null
    var number1: Double ?= null
    var number2: Double ?= null

    var currencyList = arrayOf(
    "AED",
    "AFN",
    "ALL",
    "AMD",
    "ANG",
    "AOA",
    "ARS",
    "AUD",
    "AWG",
    "AZN",
    "BAM",
    "BBD",
    "BDT",
    "BGN",
    "BHD",
    "BIF",
    "BMD",
    "BND",
    "BOB",
    "BRL",
    "BSD",
    "BTC",
    "BTN",
    "BWP",
    "BYN",
    "BYR",
    "BZD",
    "CAD",
    "CDF",
    "CHF",
    "CLF",
    "CLP",
    "CNY",
    "COP",
    "CRC",
    "CUC",
    "CUP",
    "CVE",
    "CZK",
    "DJF",
    "DKK",
    "DOP",
    "DZD",
    "EGP",
    "ERN",
    "ETB",
    "EUR",
    "FJD",
    "FKP",
    "GBP",
    "GEL",
    "GGP",
    "GHS",
    "GIP",
    "GMD",
    "GNF",
    "GTQ",
    "GYD",
    "HKD",
    "HNL",
    "HRK",
    "HTG",
    "HUF",
    "IDR",
    "ILS",
    "IMP",
    "INR",
    "IQD",
    "IRR",
    "ISK",
    "JEP",
    "JMD",
    "JOD",
    "JPY",
    "KES",
    "KGS",
    "KHR",
    "KMF",
    "KPW",
    "KRW",
    "KWD",
    "KYD",
    "KZT",
    "LAK",
    "LBP",
    "LKR",
    "LRD",
    "LSL")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.currency_activty)
        setUpSpinnerData()

        button = findViewById(R.id.count) as Button

        button?.setOnClickListener(object : View.OnClickListener {
            override fun onClick(p0: View?) {
                counting()
            }

        })
    }

    //This method will be invoked to setup data of the spinner views
    //to show lists of currency types for selection
    fun setUpSpinnerData() {

        val spFrom: Spinner = findViewById(R.id.fromCurrency)
        spFrom.onItemSelectedListener = onItemSelectedListener0
        val afrom: ArrayAdapter<String> = ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, currencyList)
        spFrom.adapter = afrom

        val spTo: Spinner = findViewById(R.id.toCurrency)
        spTo.onItemSelectedListener = onItemSelectedListener1
        val ato: ArrayAdapter<String> = ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, currencyList)
        spTo.adapter = ato
    }

    var onItemSelectedListener0: OnItemSelectedListener = object : OnItemSelectedListener {
        override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
            textView_msg = findViewById(R.id.LALALALA)
            textView_msg?.text = "${currencyList[position]}"

            Log.e("RATE", textView_msg?.text.toString())
            jsonByRate1(textView_msg?.text.toString())
        }

        override fun onNothingSelected(parent: AdapterView<*>) {}
    }

    var onItemSelectedListener1: OnItemSelectedListener = object : OnItemSelectedListener {

        override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
            textView = findViewById(R.id.NANANANA)
            textView?.text = "${currencyList[position]}"
            jsonByRate2(textView?.text.toString())
        }

        override fun onNothingSelected(parent: AdapterView<*>) {}

    }

    fun jsonByRate1(Rate: String) {
        editText1 = findViewById(R.id.enterFromCurrency)
        val retrofit = Retrofit.Builder()
                .baseUrl(URL_LINK)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

        val messageAPI = retrofit.create(CurrencyAPI::class.java)

        Log.e("RATE!!!", textView_msg?.text.toString())

        val calling: Call<JsonElement> = messageAPI.getCurrenciess(textView_msg?.text.toString())
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
                        var json1: JsonObject? = json.obj("rates")
                        Log.e("CURRENCY", "CURRENCY: ${json1?.double("${textView_msg?.text.toString()}")}" )

                        number1 = json1?.double("${textView_msg?.text.toString()}")

                        Log.e("JSON", jsonElement.toString())
                        editText1?.text = Editable.Factory.getInstance().newEditable(
                                json1?.double("${textView_msg?.text.toString()}").toString())
                    }
                    else {
                        Log.d("CODE1", "CODE IS $response.code()");
                    }
                }
            }

        })
    }

    fun jsonByRate2(Rate: String) {
        //editText2 = findViewById(R.id.enterToCurrency)
        val retrofit = Retrofit.Builder()
                .baseUrl(URL_LINK)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

        val messageAPI = retrofit.create(CurrencyAPI::class.java)

        Log.e("RATE!!!", textView?.text.toString())

        val calling: Call<JsonElement> = messageAPI.getCurrenciess(textView?.text.toString())
        calling.enqueue(object : Callback<JsonElement> {
            override fun onFailure(call: Call<JsonElement>?, t: Throwable?) {
                Log.d("FAIL1", t.toString())
            }

            override fun onResponse(call: Call<JsonElement>?, response: Response<JsonElement>?) {
                if(response != null) {
                    if (response.isSuccessful) {
                        val jsonElement: JsonElement? = response?.body()
                        //val enums = Gson().fromJson(jsonElement, Array<GetCurrency>::class.java)
                        Log.e("JSON", jsonElement.toString())

                        //parsing JsonElement and getting currency value
                        var parser: Parser = Parser()
                        var stringBuilder: StringBuilder = StringBuilder(jsonElement.toString())
                        var json: JsonObject = parser.parse(stringBuilder) as JsonObject
                        Log.e("SASA", "Age : ${json.string("date")}")
                        var json1: JsonObject? = json.obj("rates")
                        Log.e("CURRENCY2", "CURRENCY: ${json1?.double("${textView?.text.toString()}")}" )

                        number2 = json1?.double("${textView?.text.toString()}")

                        Log.e("JSON", jsonElement.toString())
                        //editText2?.text = Editable.Factory.getInstance().newEditable(
                                //json1?.double("${textView?.text.toString()}").toString())
                    }
                    else {
                        Log.d("CODE1", "CODE IS $response.code()");
                    }
                }
            }

        })
    }

    fun counting() {
        editText2 = findViewById(R.id.enterToCurrency)

        textViewResult = findViewById(R.id.result)

        var result: Double
        val one = 1.0
        numberInput = java.lang.Double.parseDouble(editText2?.text.toString())

        Log.e("INPUT", numberInput.toString())
        Log.e("NUMBER1", number1.toString())
        Log.e("NUMBER2", number2.toString())
        result = numberInput!! * ((one / this!!.number1!!) * this!!.number2!!)
        Log.e("KUSOK", result.toString())
        textViewResult?.text = result.toString()
    }
}