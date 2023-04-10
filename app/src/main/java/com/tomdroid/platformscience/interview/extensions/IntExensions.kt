package com.tomdroid.platformscience.interview.extensions


fun Int.xIsEven(): Boolean {
    return this % 2 == 0
}

fun xGreatestCommonDenominator(a: Int, b: Int): Int {
    return if (b != 0) {
         xGreatestCommonDenominator(b, a % b)
    } else {
         a
    }
}

fun xHasCommonFactor(a: Int, b: Int): Boolean {
    return xGreatestCommonDenominator(a, b) > 1
}