package com.tomdroid.platformscience.interview.data.repos

import com.tomdroid.platformscience.interview.data.local.MoshiJsonLoader
import com.tomdroid.platformscience.interview.models.Driver
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

interface DriverRepo {
    fun provideDrivers(): Flow<List<Driver>>
}

class DriverRepoImpl(
    val moshiJsonLoader: MoshiJsonLoader
): DriverRepo {

    override fun provideDrivers(): Flow<List<Driver>> {

        val jsonString = moshiJsonLoader.readJsonFile("data.json")
        val shipmentsAndDrivers = moshiJsonLoader.convertJsonStringToShipmentAndDriverTypes(jsonString)

        return flowOf(
            shipmentsAndDrivers?.drivers?.map { Driver(name = it) } ?: emptyList()
        )
    }


}