package com.tomdroid.platformscience.interview.strategies.assignment

import com.tomdroid.platformscience.interview.models.Driver
import com.tomdroid.platformscience.interview.models.Route
import com.tomdroid.platformscience.interview.models.Shipment
import org.jgrapht.alg.matching.MaximumWeightBipartiteMatching
import org.jgrapht.graph.DefaultUndirectedWeightedGraph
import org.jgrapht.graph.DefaultWeightedEdge

class HungarianAssignmentStrategy : RouteAssignmentStrategy {

    override fun assignRoutes(routes: Set<Route>): Set<Route> {
        val assignment = mutableMapOf<Driver, Shipment>()
        val assignedRoutes = mutableSetOf<Route>()

        val biGraph = DefaultUndirectedWeightedGraph<Any, DefaultWeightedEdge>(DefaultWeightedEdge::class.java)
        routes.forEach { theRoute ->
            biGraph.addVertex(theRoute.driver)
            biGraph.addVertex(theRoute.shipment)
            biGraph.setEdgeWeight(biGraph.addEdge(theRoute.driver, theRoute.shipment), theRoute.suitabilityScore)
        }

        val bipartiteMatching = MaximumWeightBipartiteMatching(
            biGraph,
            routes.map { it.driver }.toSet(),
            routes.map { it.shipment }.toSet()
        )

        val matching = bipartiteMatching.matching

        for (edge in matching.edges) {
            val driver = biGraph.getEdgeSource(edge)
            val shipment = biGraph.getEdgeTarget(edge)

            if (driver is Driver && shipment is Shipment) {
                assignment[driver] = shipment

                routes.find { it.driver == driver && it.shipment == shipment }?.let {
                    assignedRoutes.add(it)
                }

            } else if (driver is Shipment && shipment is Driver) {

                routes.find { it.driver == driver && it.shipment == shipment }?.let {
                    assignedRoutes.add(it)
                }

            }
        }

        return assignedRoutes
    }

}