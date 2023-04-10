package com.tomdroid.platformscience.interview.strategies.suitabilityscore

import com.tomdroid.platformscience.interview.models.Driver
import com.tomdroid.platformscience.interview.models.Shipment

interface SuitabilityScoreStrategy {
    fun calculateSuitabilityScore(driver: Driver, shipment: Shipment): Double
}

