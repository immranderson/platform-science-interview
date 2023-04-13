package com.tomdroid.platformscience.interview

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tomdroid.platformscience.interview.data.repos.DriverRepo
import com.tomdroid.platformscience.interview.data.repos.ShipmentsRepo
import com.tomdroid.platformscience.interview.models.Route
import com.tomdroid.platformscience.interview.models.RouteCalculator
import com.tomdroid.platformscience.interview.strategies.assignment.HungarianAssignmentStrategy
import com.tomdroid.platformscience.interview.strategies.suitabilityscore.DefaultSuitabilityScoreStrategy
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainVM @Inject constructor(

    private val routeCalculator: RouteCalculator,
    private val driversRepo: DriverRepo,
    private val shipmentsRepo: ShipmentsRepo

): ViewModel() {

    private val _viewStateFlow = MutableStateFlow<ViewState>(ViewState.Loading)
    fun viewStateFlow(): StateFlow<ViewState> = _viewStateFlow.asStateFlow()

    init {
        fetchRoutesToDisplay()
    }

    private fun fetchDataForScreenFlow(): Flow<List<Route>> {

        return combine(
            driversRepo.provideDrivers(),
            shipmentsRepo.provideShipments()
        ) { theDrivers, theShipments ->

            val allPossibleRoutes = routeCalculator.generateRoutesByStrategy(
                drivers = theDrivers,
                shipments = theShipments,
                suitabilityScoreStrategy = DefaultSuitabilityScoreStrategy()
            )

            val solvedRoutes = routeCalculator.assignRoutes(
                assignmentStrategy = HungarianAssignmentStrategy(),
                routes = allPossibleRoutes
            )

            solvedRoutes.toList()
        }

    }

    fun fetchRoutesToDisplay() {
        viewModelScope.launch {
            fetchDataForScreenFlow()
                .collect {
                    if (it.isEmpty()) {
                        _viewStateFlow.value = ViewState.Empty
                    } else {
                        _viewStateFlow.value = ViewState.Content(it)
                    }
                }
        }
    }


    sealed class ViewState {
        object Empty: ViewState()
        object Loading: ViewState()
        data class Content(val routes: List<Route>): ViewState()
    }
}