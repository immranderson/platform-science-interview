package com.tomdroid.platformscience.interview.data.local

import android.content.Context
import com.squareup.moshi.Moshi
import com.tomdroid.platformscience.interview.data.remote.ShipmentDriverResponseEntity

interface MoshiJsonLoader {
    fun readJsonFile(fileName: String): String
    fun convertJsonStringToShipmentAndDriverTypes(jsonString: String): ShipmentDriverResponseEntity?
}

class MoshiJsonLoaderImpl(
    val context: Context,
    val moshi: Moshi
) : MoshiJsonLoader {

    override fun readJsonFile(fileName: String): String {
        return context.assets.open(fileName)
            .bufferedReader()
            .use { it.readText() }
    }

    override fun convertJsonStringToShipmentAndDriverTypes(jsonString: String): ShipmentDriverResponseEntity? {
        return moshi.adapter(ShipmentDriverResponseEntity::class.java).fromJson(jsonString)
    }

}