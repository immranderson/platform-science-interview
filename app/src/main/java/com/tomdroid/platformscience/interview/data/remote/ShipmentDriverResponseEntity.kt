package com.tomdroid.platformscience.interview.data.remote
import com.squareup.moshi.Json


data class ShipmentDriverResponseEntity(
    @Json(name = "drivers")
    val drivers: List<String>,

    @Json(name = "shipments")
    val shipments: List<String>
)