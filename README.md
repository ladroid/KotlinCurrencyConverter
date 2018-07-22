# KotlinCurrencyConverter
This is currency converter in Kotlin

Hello everybody. I decided to write a currency converter using Kotlin and some APIs.

At first add this in **gradle**

```
implementation 'com.squareup.retrofit2:retrofit:2.3.0'
implementation 'com.squareup.retrofit2:converter-gson:2.3.0'
implementation 'com.beust:klaxon:3.0.1'
implementation 'com.github.AnyChart:AnyChart-Android:0.2.9'
```

And now let's start. So for getting currency I used [fixer.io](https://fixer.io/) this website shows all currencies and also has a BTC and Etherium(separate).

Now when you registered there and got API_KEY you got a json link where you have all currencies. So how I work with this json. For this I used Retrofit API. 

 1) Interface. This is my interface.
 
 ```kotlin
 interface CurrencyAPI {
    @GET("latest?access_key=777f19cddfb2d3523372aa02ba171ebb&format=1/")
    fun json() : Call<JsonElement>

    @GET("latest?access_key=777f19cddfb2d3523372aa02ba171ebb&symbols=")
    fun getCurrenciess(@Query("symbols") date: String) : Call<JsonElement>
}
```

2) Make a class which will get all currencies. Here it is

```kotlin
data class GetCurrency(@SerializedName("rates") val Rates: String)
```

3) Add spinner for choosing currency

```kotlin
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
```

4) We should handle all datas

```kotlin
fun jsonByRate1(Rate: String) {
        val retrofit = Retrofit.Builder()
                .baseUrl(URL_LINK)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

        val messageAPI = retrofit.create(CurrencyAPI::class.java)

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
                    }
                    else {
                        Log.d("CODE1", "CODE IS $response.code()");
                    }
                }
            }

        })
    }
```

5) And the last it's my feature. Drawing a graphic

```kotlin
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
```

**Soon I will make it better(drawing a graphic)**

And that's it. Thanks.
