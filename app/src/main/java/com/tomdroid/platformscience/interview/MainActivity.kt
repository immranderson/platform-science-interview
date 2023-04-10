package com.tomdroid.platformscience.interview

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.tomdroid.platformscience.interview.ui.composables.DriverRouteAccordianItem
import com.tomdroid.platformscience.interview.ui.theme.PlatformScienceTomdroidTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val vm: MainVM by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PlatformScienceTomdroidTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {

                    when (val viewState = vm.rootFlow().collectAsState(initial = MainVM.ViewState.Empty).value) {
                        is MainVM.ViewState.Content -> {
                                LazyColumn {
                                    items(viewState.routes) { theItem ->
                                        DriverRouteAccordianItem(
                                            route = theItem,
                                        )
                                    }

                                    item {
                                        Text(text = "Total Score: ${viewState.routes.fold(0.0) { acc, route -> acc + route.suitabilityScore }}")
                                    }
                                }

                        }
                        MainVM.ViewState.Empty -> {
                            Greeting(name = "EMPTY STATE")
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    PlatformScienceTomdroidTheme {
        Greeting("Android")
    }
}