package com.tomdroid.platformscience.interview

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tomdroid.platformscience.interview.data.repos.DriverRepo
import com.tomdroid.platformscience.interview.data.repos.ShipmentsRepo
import com.tomdroid.platformscience.interview.models.Route
import com.tomdroid.platformscience.interview.models.RouteCalculator
import com.tomdroid.platformscience.interview.strategies.assignment.HungarianAssignmentStrategy
import com.tomdroid.platformscience.interview.strategies.suitabilityscore.DefaultSuitabilityScoreStrategy
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.merge
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MainVM @Inject constructor(

    private val routeCalculator: RouteCalculator,
    private val driversRepo: DriverRepo,
    private val shipmentsRepo: ShipmentsRepo

): ViewModel() {

    init {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                rootFlow().collect()
            }
        }
    }

    private val _textInputStateFlow = MutableStateFlow("")

    fun rootFlow(): Flow<ViewState> =
        merge(
            mapDataToViewState()
        )

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

    private fun mapDataToViewState(): Flow<ViewState> {
        return fetchDataForScreenFlow()
            .map {
                Log.d(this::class.java.toString(), "$it")
                ViewState.Content(it)
            }
    }

    fun onTextEventReceived(textInput: String) {

        _textInputStateFlow.value = textInput

    }


    sealed class ViewState {
        object Empty: ViewState()
        data class Content(val routes: List<Route>): ViewState()
    }
}