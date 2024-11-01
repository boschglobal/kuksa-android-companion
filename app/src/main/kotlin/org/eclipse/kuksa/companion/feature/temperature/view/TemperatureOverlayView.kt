/*
 * Copyright (c) 2023 Contributors to the Eclipse Foundation
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * SPDX-License-Identifier: Apache-2.0
 *
 */

package org.eclipse.kuksa.companion.feature.temperature.view

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import org.eclipse.kuksa.companion.application.PREVIEW_HEIGHT_DP
import org.eclipse.kuksa.companion.application.PREVIEW_WIDTH_DP
import org.eclipse.kuksa.companion.extension.windowSizeClass
import org.eclipse.kuksa.companion.feature.temperature.viewmodel.TemperatureViewModel
import org.eclipse.kuksa.companion.ramses.DriverBackDoorAnchor
import org.eclipse.kuksa.companion.ramses.DriverFrontDoorAnchor
import org.eclipse.kuksa.companion.ramses.PassengerBackDoorAnchor
import org.eclipse.kuksa.companion.ramses.PassengerFrontDoorAnchor

@Composable
fun TemperatureOverlayView(
    viewModel: TemperatureViewModel,
    windowSizeClass: WindowSizeClass,
    modifier: Modifier = Modifier,
) {
    ConstraintLayout(
        modifier = modifier
            .fillMaxSize(),
    ) {
        val (driverSideRef, driverSideBackRef) = createRefs()
        val (passengerSideRef, passengerSideBackRef) = createRefs()

        val temperatureDriverSideFront = viewModel.temperatureDriverSideFront
        val temperaturePassengerSideFront = viewModel.temperaturePassengerSideFront
        val temperatureDriverSideBack = viewModel.temperatureDriverSideBack
        val temperaturePassengerSideBack = viewModel.temperaturePassengerSideBack

        val anchorPoint = createRef()
        Spacer(
            Modifier
                .size(2.dp)
                .constrainAs(anchorPoint) {
                    centerHorizontallyTo(parent)
                    centerVerticallyTo(parent)
                },
        )

        val driverFrontDoorAnchor = DriverFrontDoorAnchor(windowSizeClass, anchorPoint)
        val passengerFrontDoorAnchor = PassengerFrontDoorAnchor(windowSizeClass, anchorPoint)
        val driverBackDoorAnchor = DriverBackDoorAnchor(windowSizeClass, anchorPoint)
        val passengerBackDoorAnchor = PassengerBackDoorAnchor(windowSizeClass, anchorPoint)

        val unit = "°C"
        Text(
            text = "$temperatureDriverSideFront $unit",
            fontSize = MaterialTheme.typography.titleLarge.fontSize,
            color = viewModel.getTemperatureColor(temperatureDriverSideFront),
            modifier = Modifier
                .constrainAs(driverSideRef) {
                    driverFrontDoorAnchor.align(this)
                },
        )
        Text(
            text = "$temperaturePassengerSideFront $unit",
            fontSize = MaterialTheme.typography.titleLarge.fontSize,
            color = viewModel.getTemperatureColor(temperaturePassengerSideFront),
            modifier = Modifier
                .constrainAs(passengerSideRef) {
                    passengerFrontDoorAnchor.align(this)
                },
        )
        Text(
            text = "$temperatureDriverSideBack $unit",
            fontSize = MaterialTheme.typography.titleLarge.fontSize,
            color = viewModel.getTemperatureColor(temperatureDriverSideBack),
            modifier = Modifier
                .constrainAs(driverSideBackRef) {
                    driverBackDoorAnchor.align(this)
                },
        )
        Text(
            text = "$temperaturePassengerSideBack $unit",
            fontSize = MaterialTheme.typography.titleLarge.fontSize,
            color = viewModel.getTemperatureColor(temperaturePassengerSideBack),
            modifier = Modifier
                .constrainAs(passengerSideBackRef) {
                    passengerBackDoorAnchor.align(this)
                },
        )
    }
}

@Preview(widthDp = PREVIEW_WIDTH_DP, heightDp = PREVIEW_HEIGHT_DP)
@Preview(widthDp = PREVIEW_HEIGHT_DP, heightDp = PREVIEW_WIDTH_DP)
@Composable
private fun TemperatureControlPreview() {
    val viewModel = TemperatureViewModel()
    val windowSizeClass = LocalConfiguration.current.windowSizeClass
    Surface {
        TemperatureOverlayView(viewModel, windowSizeClass)
    }
}
