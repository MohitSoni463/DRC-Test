package com.drccities.models

data class CitiesData(val country: String, val name: String, val _id: Int, val coord: Data) {
    data class Data(val lon: Double, val lat: Double)
}
