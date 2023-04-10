package com.tomdroid.platformscience.interview.strategies.suitabilityscore

import com.tomdroid.platformscience.interview.models.Driver
import com.tomdroid.platformscience.interview.models.Shipment
import org.junit.Assert.*
import org.junit.Before

import org.junit.Test

class DefaultSuitabilityScoreStrategyTest {

    private lateinit var sut: SuitabilityScoreStrategy
    private val tom = Driver(name = "Tom")
    private val bill = Driver(name = "Bill")

    private val even = Shipment(rawAddress = "even")
    private val odd = Shipment(rawAddress = "odd")

    @Before
    fun setup() {
        sut = DefaultSuitabilityScoreStrategy()
    }

    @Test
    fun `test even address + vowels case with no greatest common denominator`() {

        val actual = sut.calculateSuitabilityScore(
            driver = tom,
            shipment = even
        )

        val expected = 1.5
        assertTrue(actual == expected)
    }

    @Test
    fun `test odd address + consonants case with no greatest common denominator`() {
        val actual = sut.calculateSuitabilityScore(
            driver = bill,
            shipment = odd
        )

        val expected = 3.0
        assertTrue(actual == expected)
    }

    @Test
    fun `test even address + vowels case with greatest common denominator`() {
        val actual = sut.calculateSuitabilityScore(
            driver = bill,
            shipment = even
        )

        val expected = 2.25
        assertTrue(actual == expected)
    }

    @Test
    fun `test odd address + consonants case with greatest common denominator`() {

        val actual = sut.calculateSuitabilityScore(
            driver = tom,
            shipment = odd
        )

        val expected = 3.0
        assertTrue(actual == expected)

    }
}