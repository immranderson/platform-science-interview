package com.tomdroid.platformscience.interview

import com.tomdroid.platformscience.interview.models.*
import com.tomdroid.platformscience.interview.strategies.assignment.HungarianAssignmentStrategy
import com.tomdroid.platformscience.interview.strategies.suitabilityscore.DefaultSuitabilityScoreStrategy
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before

import org.junit.Test

class RouteCalculatorTest {

    private lateinit var sut: RouteCalculator


    @Before
    fun setup() {
        sut = RouteCalculatorImpl()
    }

    @Test
    fun `unique routes are generated with correct suitability scores`() {
        val drivers = listOf(
            Driver(name = "Tom"),
            Driver(name = "Thomas")
        )

        val shipments = listOf(
            Shipment(rawAddress = "odd"),
            Shipment(rawAddress = "even"),
        )

        val actualRoutes = sut.generateRoutesByStrategy(
            drivers = drivers,
            shipments = shipments,
            suitabilityScoreStrategy = DefaultSuitabilityScoreStrategy()
        )

        val expectedRoutes = setOf(
            Route(driver = Driver("Tom"), shipment = Shipment(rawAddress = "odd"), suitabilityScore = 3.0),
            Route(driver = Driver("Tom"), shipment = Shipment(rawAddress = "even"), suitabilityScore = 1.5),
            Route(driver = Driver("Thomas"), shipment = Shipment(rawAddress = "even"), suitabilityScore = 4.5),
            Route(driver = Driver("Thomas"), shipment = Shipment(rawAddress = "odd"), suitabilityScore = 6.0),
        )

        assertEquals(expectedRoutes, actualRoutes)

    }


    @Test
    fun `route assignment works with simple set data`() {
        val simpleTestRouteSet = setOf(
            Route(driver = Driver(name = "Tom"), shipment = Shipment(rawAddress = "Chicago"), suitabilityScore = 30.0),
            Route(driver = Driver(name = "Angela"), shipment = Shipment(rawAddress = "Schaumburg"), suitabilityScore = 30.0),
            Route(driver = Driver(name = "Tom"), shipment = Shipment(rawAddress = "Prospect"), suitabilityScore = 20.0),
            Route(driver = Driver(name = "Angela"), shipment = Shipment(rawAddress = "Prospect"), suitabilityScore = 10.0)
        )

        val actualAssignedRoutes = sut.assignRoutes(
            assignmentStrategy = HungarianAssignmentStrategy(),
            routes = simpleTestRouteSet
        )

        val expectedRoutes = setOf(
            Route(driver = Driver(name = "Angela"), shipment = Shipment(rawAddress = "Schaumburg"), suitabilityScore = 30.0),
            Route(driver = Driver(name = "Tom"), shipment = Shipment(rawAddress = "Chicago"), suitabilityScore = 30.0),
        )

        assertEquals(expectedRoutes, actualAssignedRoutes)
    }

    @Test
    fun `route assignment works with set data that has multiple correct answers`() {
        val multipleAnswersTestRouteSet: Set<Route> = setOf(
            Route(driver = Driver(name = "Ang"), shipment = Shipment(rawAddress ="Prospect"), suitabilityScore = 50.0),
            Route(driver = Driver(name = "Tom"), shipment = Shipment(rawAddress ="Prospect"), suitabilityScore = 50.0),
            Route(driver = Driver(name = "Tom"), shipment = Shipment(rawAddress ="Arlington"), suitabilityScore = 20.0),
            Route(driver = Driver(name = "Ang"), shipment = Shipment(rawAddress ="Arlington"), suitabilityScore = 20.0),
            Route(driver = Driver(name = "Ryan"), shipment = Shipment(rawAddress ="Arlington"), suitabilityScore = 20.0),
            Route(driver = Driver(name = "Ang"), shipment = Shipment(rawAddress ="Chicago"), suitabilityScore = 7.0),
            Route(driver = Driver(name = "Aav"), shipment = Shipment(rawAddress ="Chicago"), suitabilityScore = 6.0),
            Route(driver = Driver(name = "Ryan"), shipment = Shipment(rawAddress ="Schaumburg"), suitabilityScore= 4.0),
            Route(driver = Driver(name = "Ryan"), shipment = Shipment(rawAddress ="Prospect"), suitabilityScore = 4.0),
            Route(driver = Driver(name = "Aav"), shipment = Shipment(rawAddress ="Prospect"), suitabilityScore = 3.0),
            Route(driver = Driver(name = "Ryan"), shipment = Shipment(rawAddress ="Chicago"), suitabilityScore = 2.0),
            Route(driver = Driver(name = "Ang"), shipment = Shipment(rawAddress ="Schaumburg"), suitabilityScore = 2.0),
            Route(driver = Driver(name = "Tom"), shipment = Shipment(rawAddress ="Chicago"), suitabilityScore = 2.0),
            Route(driver = Driver(name = "Tom"), shipment = Shipment(rawAddress ="Schaumburg"), suitabilityScore = 1.0),
            Route(driver = Driver(name = "Aav"), shipment = Shipment(rawAddress ="Schaumburg"), suitabilityScore = 1.0),
            Route(driver = Driver(name = "Aav"), shipment = Shipment(rawAddress ="Arlington"), suitabilityScore = 1.0)
        )

        val actualAssignedRoutes = sut.assignRoutes(
            assignmentStrategy = HungarianAssignmentStrategy(),
            routes = multipleAnswersTestRouteSet
        )

        val expectedRoutes1 = setOf(
            Route(driver = Driver(name = "Ang"), shipment = Shipment(rawAddress ="Prospect"), suitabilityScore = 50.0),
            Route(driver = Driver(name = "Ryan"), shipment = Shipment(rawAddress ="Schaumburg"), suitabilityScore= 4.0),
            Route(driver = Driver(name = "Tom"), shipment = Shipment(rawAddress ="Arlington"), suitabilityScore = 20.0),
            Route(driver = Driver(name = "Aav"), shipment = Shipment(rawAddress ="Chicago"), suitabilityScore = 6.0),
        )

        val expectedRoutes2 = setOf(
            Route(driver = Driver(name = "Tom"), shipment = Shipment(rawAddress ="Prospect"), suitabilityScore = 50.0),
            Route(driver = Driver(name = "Ang"), shipment = Shipment(rawAddress ="Chicago"), suitabilityScore = 7.0),
            Route(driver = Driver(name = "Ryan"), shipment = Shipment(rawAddress ="Arlington"), suitabilityScore = 20.0),
            Route(driver = Driver(name = "Aav"), shipment = Shipment(rawAddress ="Prospect"), suitabilityScore = 3.0),
        )

        assertTrue(actualAssignedRoutes.intersect(expectedRoutes1).count() == 4 || actualAssignedRoutes.intersect(expectedRoutes2).count() == 4)

    }
}