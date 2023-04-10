package com.tomdroid.platformscience.interview.extensions

import org.junit.Assert.assertEquals
import org.junit.Test

class IntExensionsTest {

    @Test
    fun `test xGreatestCommonDenominator is correct`() {

        assertEquals(2, xGreatestCommonDenominator(2,2))
        assertEquals(1, xGreatestCommonDenominator(2,5))
        assertEquals(2, xGreatestCommonDenominator(2,4))
        assertEquals(2, xGreatestCommonDenominator(4, 2))
        assertEquals(1, xGreatestCommonDenominator(2,3))
        assertEquals(1, xGreatestCommonDenominator(3, 2))

    }

    @Test
    fun xHasCommonFactorWith() {

        assertEquals(false, xHasCommonFactor(2, 1))
        assertEquals(true, xHasCommonFactor(2, 2))
        assertEquals(true, xHasCommonFactor(2, 4))
        assertEquals(true, xHasCommonFactor(4,2))
        assertEquals(false, xHasCommonFactor(2,3))
        assertEquals(false, xHasCommonFactor(3,2))

    }
}