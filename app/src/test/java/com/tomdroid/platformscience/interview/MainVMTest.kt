package com.tomdroid.platformscience.interview

import com.tomdroid.platformscience.interview.data.repos.DriverRepo
import com.tomdroid.platformscience.interview.data.repos.ShipmentsRepo
import com.tomdroid.platformscience.interview.models.Driver
import com.tomdroid.platformscience.interview.models.Route
import com.tomdroid.platformscience.interview.models.RouteCalculator
import com.tomdroid.platformscience.interview.models.Shipment
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Assert.assertEquals
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class MainVMTest {

    private val mockRouteCalculator = mockk<RouteCalculator>()
    private val mockDriversRepo = mockk<DriverRepo>()
    private val mockShipmentsRepo = mockk<ShipmentsRepo>()

    private lateinit var sut: MainVM

    private val drivers = listOf(
        Driver(name = "Driver 1")
    )

    private val shipments = listOf(
        Shipment(rawAddress = "Shipment 1")
    )

    private val routes = setOf(
        Route(driver = drivers.first(), shipment = shipments.first(), suitabilityScore = 0.0)
    )


    @Test
    fun `fetchRoutesToDisplay maps non-empty data to ViewState Content`() = runTest {

        val testDispatcher = UnconfinedTestDispatcher(testScheduler)
        Dispatchers.setMain(testDispatcher)

        coEvery { mockDriversRepo.provideDrivers() } returns flowOf(drivers)
        coEvery { mockShipmentsRepo.provideShipments() } returns flowOf(shipments)
        every { mockRouteCalculator.generateRoutesByStrategy(any(), any(), any()) } returns routes
        every { mockRouteCalculator.assignRoutes(any(), any()) } returns routes

        sut = MainVM(
            routeCalculator = mockRouteCalculator,
            driversRepo = mockDriversRepo,
            shipmentsRepo = mockShipmentsRepo
        )

        assertEquals(MainVM.ViewState.Content(routes = routes.toList()), sut.viewStateFlow().value)

    }

    @Test
    fun `fetchRoutesToDisplay maps empty data to ViewState Empty`() = runTest {

        val testDispatcher = UnconfinedTestDispatcher(testScheduler)
        Dispatchers.setMain(testDispatcher)

        coEvery { mockDriversRepo.provideDrivers() } returns flowOf(emptyList())
        coEvery { mockShipmentsRepo.provideShipments() } returns flowOf(emptyList())
        every { mockRouteCalculator.generateRoutesByStrategy(any(), any(), any()) } returns emptySet()
        every { mockRouteCalculator.assignRoutes(any(), any()) } returns emptySet()

        sut = MainVM(
            routeCalculator = mockRouteCalculator,
            driversRepo = mockDriversRepo,
            shipmentsRepo = mockShipmentsRepo
        )

        assertEquals(MainVM.ViewState.Empty, sut.viewStateFlow().value)

    }

}