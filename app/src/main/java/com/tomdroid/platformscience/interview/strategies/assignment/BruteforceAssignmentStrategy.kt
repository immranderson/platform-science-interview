package com.tomdroid.platformscience.interview.strategies.assignment

import com.tomdroid.platformscience.interview.models.Route

class BruteforceAssignmentStrategy : RouteAssignmentStrategy {

    // Just demonstrating the ability to swap out strategies if needed
    override fun assignRoutes(routes: Set<Route>): Set<Route> {
        return emptySet()
    }
}