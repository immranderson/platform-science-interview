package com.tomdroid.platformscience.interview.strategies.assignment

import com.tomdroid.platformscience.interview.models.Route

interface RouteAssignmentStrategy {
    fun assignRoutes(routes: Set<Route>): Set<Route>
}