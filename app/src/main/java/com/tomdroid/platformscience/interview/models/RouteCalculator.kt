package com.tomdroid.platformscience.interview.models

import com.tomdroid.platformscience.interview.strategies.assignment.RouteAssignmentStrategy
import com.tomdroid.platformscience.interview.strategies.suitabilityscore.SuitabilityScoreStrategy

interface RouteCalculator {

    fun generateRoutesByStrategy(drivers: List<Driver>, shipments: List<Shipment>, suitabilityScoreStrategy: SuitabilityScoreStrategy): Set<Route>
    fun assignRoutes(assignmentStrategy: RouteAssignmentStrategy, routes: Set<Route>): Set<Route>

}

class RouteCalculatorImpl : RouteCalculator {

    override fun generateRoutesByStrategy(
        drivers: List<Driver>,
        shipments: List<Shipment>,
        suitabilityScoreStrategy: SuitabilityScoreStrategy
    ): Set<Route> {

        return drivers.map { theDriver ->
            shipments.map { theShipment ->
                Route(
                    driver = theDriver,
                    shipment = theShipment,
                    suitabilityScore = suitabilityScoreStrategy.calculateSuitabilityScore(theDriver, theShipment)
                )
            }
        }
            .flatten()
            .toSet()
    }

    override fun assignRoutes(
        assignmentStrategy: RouteAssignmentStrategy,
        routes: Set<Route>
    ): Set<Route> {

        return assignmentStrategy.assignRoutes(routes)

    }

}

