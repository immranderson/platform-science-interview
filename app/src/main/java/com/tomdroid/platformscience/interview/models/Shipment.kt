package com.tomdroid.platformscience.interview.models

data class Shipment(
    val rawAddress: String,
) {

    fun getStreetName(): String {
        //clarify if street name extraction from the raw json address is needed.
        return rawAddress
    }

}
