package com.tomdroid.platformscience.interview.data.local

import android.content.Context
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.tomdroid.platformscience.interview.data.remote.ShipmentDriverResponseEntity
import io.mockk.every
import io.mockk.mockk
import org.junit.Assert.assertEquals
import org.junit.Test
import java.io.ByteArrayInputStream
import java.io.InputStream

class MoshiJsonLoaderTest {

    private val context = mockk<Context>()
    private val moshi = mockk<Moshi>()
    private val moshiJsonLoader = MoshiJsonLoaderImpl(context, moshi)

    @Test
    fun `readJsonFile returns file content`() {
        val fileName = "test.json"
        val fileContent = """
            {"key": "value"}
        """.trimIndent()

        val inputStream: InputStream = ByteArrayInputStream(fileContent.toByteArray())

        every { context.assets.open(fileName) } returns inputStream

        val result = moshiJsonLoader.readJsonFile(fileName)

        assertEquals(fileContent, result)
    }

    @Test
    fun `convertJsonStringToShipmentAndDriverTypes returns correct data`() {
        val jsonString = """
            {"shipment": "shipment_value", "driver": "driver_value"}
        """.trimIndent()

        val expected = ShipmentDriverResponseEntity(
            drivers = listOf("shipment_value"),
            shipments = listOf("driver_value")
        )

        val jsonAdapter = mockk<JsonAdapter<ShipmentDriverResponseEntity>>()

        every { moshi.adapter(ShipmentDriverResponseEntity::class.java) } returns jsonAdapter
        every { jsonAdapter.fromJson(jsonString) } returns expected

        val result = moshiJsonLoader.convertJsonStringToShipmentAndDriverTypes(jsonString)

        assertEquals(expected, result)
    }
}