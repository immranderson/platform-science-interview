package com.tomdroid.platformscience.interview.extensions

import org.junit.Assert.assertEquals
import org.junit.Test

class StringExtensionsTest {

    @Test
    fun `xGetNumberOfVowels returns correct number of vowels`() {
        assertEquals(1, "Tom".xGetNumberOfVowels())
        assertEquals(1, "TOM".xGetNumberOfVowels())
        assertEquals(0, "123 456".xGetNumberOfVowels())
        assertEquals(3, "aAa".xGetNumberOfVowels())
        assertEquals(0, "!@#$%^&*()_+".xGetNumberOfVowels())
    }

    @Test
    fun `getNumberOfConsonants returns correct number of consonants`() {
        assertEquals(2, "Tom".xGetNumberOfConsonants())
        assertEquals(2, "TOM".xGetNumberOfConsonants())
        assertEquals(0, "123 456".xGetNumberOfConsonants())
        assertEquals(0, "aAa".xGetNumberOfConsonants())
        assertEquals(0, "!@#$%^&*()_+".xGetNumberOfConsonants())
    }
}