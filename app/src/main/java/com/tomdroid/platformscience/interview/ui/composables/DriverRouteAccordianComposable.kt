package com.tomdroid.platformscience.interview.ui.composables

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.tomdroid.platformscience.interview.models.Route

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DriverRouteAccordianItem(
    route: Route,
) {

    val isExpanded: MutableState<Boolean> =
        remember {
            mutableStateOf(false)
        }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        onClick = {
            isExpanded.value = !isExpanded.value
        }
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                modifier = Modifier.fillMaxWidth().height(56.dp),
                text = route.driver.name,
                textAlign = TextAlign.Center
            )

            AnimatedVisibility(visible = isExpanded.value) {

                Column(modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,) {

                    Text(
                        modifier = Modifier
                            .height(56.dp)
                            .wrapContentHeight(Alignment.CenterVertically)
                            .fillMaxWidth(),
                        text = route.shipment.getStreetName(),
                        textAlign = TextAlign.Center
                    )

                    Text(
                        modifier = Modifier
                            .height(56.dp)
                            .wrapContentHeight(Alignment.CenterVertically)
                            .fillMaxWidth(),
                        text = "Score is: ${route.suitabilityScore}",
                        textAlign = TextAlign.Center
                    )
                }


            }
        }

    }
}