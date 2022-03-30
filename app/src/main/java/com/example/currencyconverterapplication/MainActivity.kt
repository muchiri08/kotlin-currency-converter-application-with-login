package com.example.currencyconverterapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.net.URL
import kotlin.Exception

class MainActivity : AppCompatActivity() {

    private lateinit var et_amount: EditText
    private lateinit var button_convert: Button
    private lateinit var text_result: TextView
    private lateinit var button_clear: Button
    private lateinit var button_logout: Button

    private var baseCurrency = "EUR"
    private var convertedCurrency = "USD"
    private var conversionRate = 0f

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        et_amount = findViewById(R.id.editTextAmount)
        button_convert = findViewById(R.id.button_convert)
        text_result = findViewById(R.id.textViewResult)
        button_clear = findViewById(R.id.button_clear)
        button_logout = findViewById(R.id.button_logout)

        spinnerSetup()

        button_convert.setOnClickListener {
            try {
                getApiResult()
            }
            catch (e: Exception){
                Log.e("Main", "$e")
            }
        }

        button_clear.setOnClickListener {
            et_amount.setText("")
            text_result.text = ""
        }

        button_logout.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
        }

    }

    private fun spinnerSetup() {
        val spinnerCurrencyFrom: Spinner = findViewById(R.id.spinnerCurrencyFrom)
        val spinnerCurrencyTo: Spinner = findViewById(R.id.spinnerCurrencyTo)

        ArrayAdapter.createFromResource(this, R.array.currenciesFrom, android.R.layout.simple_spinner_item)
            .also { adapter -> adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                spinnerCurrencyFrom.adapter = adapter
            }
        ArrayAdapter.createFromResource(this, R.array.currenciesTo, android.R.layout.simple_spinner_item)
            .also { adapter -> adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                spinnerCurrencyTo.adapter = adapter
            }

        spinnerCurrencyFrom.onItemSelectedListener = (object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                baseCurrency = parent?.getItemAtPosition(position).toString()
                getApiResult()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        })

        spinnerCurrencyTo.onItemSelectedListener = (object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                convertedCurrency = parent?.getItemAtPosition(position).toString()
                getApiResult()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        })

    }

    private fun getApiResult() {
        if (et_amount.text.isNotEmpty() && et_amount.text.isNotBlank()){
            val API = "https://api.exchangerate.host/latest?base=$baseCurrency&symbols=$convertedCurrency"

            if (baseCurrency == convertedCurrency){
                Toast.makeText(applicationContext, "Please select a currency to convert", Toast.LENGTH_SHORT).show()
            }
            else{
                GlobalScope.launch(Dispatchers.IO){
                    try {
                        val apiResult = URL(API).readText()
                        val jsonObject = JSONObject(apiResult)

                        conversionRate = jsonObject.getJSONObject("rates").getString(convertedCurrency).toFloat()

                        withContext(Dispatchers.Main){
                            val text = ((et_amount.text.toString().toFloat()) * conversionRate).toString()
                            text_result.text = text
                        }
                    }
                    catch (e: Exception){
                        Log.e("Main", "$e")
                    }
                }
            }

        }
    }

}