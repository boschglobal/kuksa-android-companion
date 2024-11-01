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

package org.eclipse.kuksa.companion.feature.navigation.view

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.eclipse.kuksa.companion.feature.navigation.NavigationPage
import org.eclipse.kuksa.companion.feature.navigation.viewmodel.NavigationViewModel

@Composable
fun HorizontalNavigationView(
    viewModel: NavigationViewModel,
    modifier: Modifier = Modifier,
    onPageSelected: (NavigationPage) -> Unit = {},
) {
    var selectedItemIndex by remember {
        mutableIntStateOf(viewModel.selectedNavigationIndex)
    }

    NavigationBar(
        containerColor = MaterialTheme.colorScheme.surfaceColorAtElevation(3.dp),
        modifier = modifier.fillMaxWidth(),
    ) {
        NavigationPage.entries.forEachIndexed { index, page ->
            NavigationBarItem(
                selected = index == selectedItemIndex,
                onClick = {
                    selectedItemIndex = index
                    viewModel.selectedNavigationIndex = index
                    viewModel.selectedNavigationPage = page
                    onPageSelected(page)
                },
                icon = {
                    Icon(
                        painter = painterResource(id = page.iconRes),
                        contentDescription = page.description,
                        modifier = Modifier.size(30.dp),
                    )
                },
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun HorizontalNavigationViewPreview() {
    val viewModel = NavigationViewModel()
    HorizontalNavigationView(viewModel)
}
