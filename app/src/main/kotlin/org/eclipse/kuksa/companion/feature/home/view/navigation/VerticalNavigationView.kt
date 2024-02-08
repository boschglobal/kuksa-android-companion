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

package org.eclipse.kuksa.companion.feature.home.view.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun VerticalNavigationView(
    viewModel: NavigationViewModel,
    modifier: Modifier = Modifier,
    onPageSelected: (NavigationPage) -> Unit = {},
) {
    var selectedItemIndex by remember {
        mutableIntStateOf(viewModel.selectedNavigationIndex)
    }

    val pages = NavigationPage.entries.toTypedArray()

    Box(contentAlignment = Alignment.Center, modifier = modifier) {
        NavigationRail(modifier) {
            pages.forEachIndexed { index, page ->
                NavigationRailItem(
                    label = { Text(page.title) },
                    icon = {
                        Icon(
                            painterResource(id = page.iconRes),
                            contentDescription = page.title,
                            modifier = Modifier.size(30.dp),
                        )
                    },
                    selected = selectedItemIndex == index,
                    onClick = {
                        selectedItemIndex = index
                        viewModel.selectedNavigationIndex = index
                        viewModel.selectedNavigationPage = page
                        onPageSelected(page)
                    },
                )
            }
        }
    }
}

@Preview
@Composable
private fun VerticalNavigationViewPreview() {
    val viewModel = NavigationViewModel()
    VerticalNavigationView(viewModel)
}
