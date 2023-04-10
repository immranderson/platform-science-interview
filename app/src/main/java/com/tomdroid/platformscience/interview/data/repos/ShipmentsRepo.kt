package com.tomdroid.platformscience.interview.data.repos

import com.tomdroid.platformscience.interview.data.local.MoshiJsonLoader
import com.tomdroid.platformscience.interview.models.Shipment
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

interface ShipmentsRepo {
    fun provideShipments(): Flow<List<Shipment>>
}

class ShipmentsRepoImpl(
    val moshiJsonLoader: MoshiJsonLoader
): ShipmentsRepo {

    override fun provideShipments(): Flow<List<Shipment>> {

        val jsonString = moshiJsonLoader.readJsonFile("data.json")
        val shipmentsAndDrivers = moshiJsonLoader.convertJsonStringToShipmentAndDriverTypes(jsonString)

        return flowOf(
            shipmentsAndDrivers?.shipments?.map { Shipment(rawAddress = it) } ?: emptyList()
        )
    }
}