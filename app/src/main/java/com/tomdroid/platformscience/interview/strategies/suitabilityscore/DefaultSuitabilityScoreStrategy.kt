package com.tomdroid.platformscience.interview.strategies.suitabilityscore

import com.tomdroid.platformscience.interview.extensions.xGetNumberOfConsonants
import com.tomdroid.platformscience.interview.extensions.xGetNumberOfVowels
import com.tomdroid.platformscience.interview.extensions.xHasCommonFactor
import com.tomdroid.platformscience.interview.extensions.xIsEven
import com.tomdroid.platformscience.interview.models.Driver
import com.tomdroid.platformscience.interview.models.Shipment


class DefaultSuitabilityScoreStrategy: SuitabilityScoreStrategy {

    override fun calculateSuitabilityScore(
        driver: Driver,
        shipment: Shipment
    ): Double {

        val baseSuitabilityScore = if (shipment.getStreetName().length.xIsEven()) {
            1.5 * driver.name.xGetNumberOfVowels()
        } else {
            1.0 * driver.name.xGetNumberOfConsonants()
        }

        val finalSuitabilityScore = if (xHasCommonFactor(shipment.getStreetName().length, driver.name.length)) {
            baseSuitabilityScore * 1.5
        } else {
            baseSuitabilityScore
        }

        return finalSuitabilityScore
    }

}