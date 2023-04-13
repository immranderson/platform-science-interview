# platform-science-interview-tomdroid

Hello code reviewer! Welcome to my coding submission.

### Cool things to know:
The app utilizes Stateflow and Jetpack Compose. Moshi is used for deserializing the included data.json file and deserialized into `ShipmentDriverResponseEntity.kt`
From there, I have some rudimentary repo implementations to provide the Drivers and Shipments.

Afterwards, you'll see in the `strategies` package there are implementations around how a suitability score is generated, and how routes will be assigned.

I included a no-op bruteforce matching class called `BruteforceAssignmentStrategy` but the `HungarianAssignmentStrategy` is the one which is used.

In `RouteCalculator.kt` the drivers and shipments models are processed and the sustainability scores are generated applying the DefaultSuitabilityScoreStrategy. Ultimately, this outputs a Set of Routes.

Afterwards, I use the `jgrapht` library to generate a bigraph and find the maximum possible sustainability score. The reduced set of routes is output.

The ViewModel receives the results from the RouteCalculator and displays them.

### There's unit tests!

Check them out :-)
