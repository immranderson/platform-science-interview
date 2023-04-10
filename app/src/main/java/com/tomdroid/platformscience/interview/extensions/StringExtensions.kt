package com.tomdroid.platformscience.interview.extensions


private val alphabet = 'a'..'z'
private val vowels = setOf('a', 'e', 'i', 'o', 'u')
private val consonants = alphabet.toSet().minus(vowels)

//Prefix Extensions with x to signify whether the extension has been developed internally.
fun String.xGetNumberOfVowels(): Int {
    return this.lowercase().count { vowels.contains(it) }
}

fun String.xGetNumberOfConsonants(): Int {
    return this.lowercase().count { consonants.contains(it) }
}