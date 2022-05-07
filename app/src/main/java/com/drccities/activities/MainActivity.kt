package com.drccities.activities

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.recyclerview.widget.LinearLayoutManager
import com.drccities.R
import com.drccities.adapter.CitiesAdapter
import com.drccities.extras.Utils.getJsonDataFromAsset
import com.drccities.models.CitiesData
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    lateinit var citiesData: MutableList<CitiesData>
    lateinit var citiesAdapter: CitiesAdapter
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val jsonFileString = getJsonDataFromAsset(applicationContext, "cities.json")
//        println("data $jsonFileString")

        val gson = Gson()
        val listPersonType = object : TypeToken<List<CitiesData>>() {}.type
        val persons: List<CitiesData> = gson.fromJson(jsonFileString, listPersonType)
//        persons.forEachIndexed { idx, person ->
//            println("data -> Item $idx:\n$person")
//        }

        rcv_cities.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        citiesAdapter = CitiesAdapter(this, persons as MutableList<CitiesData>)
        rcv_cities.adapter = citiesAdapter

        cities_filter.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                return
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                return
            }

            override fun afterTextChanged(p0: Editable?) {
                citiesAdapter.filter.filter(p0.toString())
            }
        })
    }
}