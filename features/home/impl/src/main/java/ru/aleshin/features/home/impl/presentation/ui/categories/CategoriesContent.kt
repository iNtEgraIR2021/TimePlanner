/*
 * Copyright 2023 Stanislav Aleshin
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * imitations under the License.
 */
package ru.aleshin.features.home.impl.presentation.ui.categories

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.aleshin.core.ui.theme.TimePlannerTheme
import ru.aleshin.core.ui.theme.material.ThemeColorsUiType
import ru.aleshin.core.ui.theme.tokens.LanguageUiType
import ru.aleshin.features.home.api.domains.entities.categories.MainCategory
import ru.aleshin.features.home.api.domains.entities.categories.SubCategory
import ru.aleshin.features.home.impl.presentation.theme.HomeTheme
import ru.aleshin.features.home.impl.presentation.theme.HomeThemeRes
import ru.aleshin.features.home.impl.presentation.ui.categories.contract.CategoriesViewState
import ru.aleshin.features.home.impl.presentation.ui.categories.views.MainCategoriesHorizontalList
import ru.aleshin.features.home.impl.presentation.ui.categories.views.MainCategoryCreatorDialog
import ru.aleshin.features.home.impl.presentation.ui.categories.views.SubCategoriesList

/**
 * @author Stanislav Aleshin on 05.04.2023.
 */
@Composable
internal fun CategoriesContent(
    state: CategoriesViewState,
    modifier: Modifier = Modifier,
    onAddMainCategory: (name: String) -> Unit,
    onChangeMainCategory: (MainCategory) -> Unit,
    onSubCategoryUpdate: (SubCategory) -> Unit,
    onMainCategoryUpdate: (MainCategory) -> Unit,
    onMainCategoryDelete: (MainCategory) -> Unit,
    onSubCategoryDelete: (SubCategory) -> Unit,
) {
    var isMainCategoryCreatorOpen by rememberSaveable { mutableStateOf(false) }
    val scrollableState = rememberScrollState()

    Column(modifier = modifier.fillMaxSize().verticalScroll(scrollableState)) {
        val categories = state.categories.find { it.mainCategory == state.selectedMainCategory }
        val subCategories = categories?.subCategories ?: emptyList()

        MainCategoriesHeader(
            modifier = Modifier.padding(top = 8.dp).fillMaxWidth(),
            onAddMainCategory = { isMainCategoryCreatorOpen = true },
        )
        MainCategoriesHorizontalList(
            modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
            mainCategories = state.categories.map { it.mainCategory },
            selectedCategory = state.selectedMainCategory,
            onSelectCategory = onChangeMainCategory,
            onUpdateCategory = onMainCategoryUpdate,
            onDeleteCategory = onMainCategoryDelete,
        )
        SubCategoriesHeader(modifier = Modifier.padding(vertical = 4.dp).fillMaxWidth())
        SubCategoriesList(
            modifier = Modifier.height(200.dp),
            mainCategory = state.selectedMainCategory,
            subCategories = subCategories,
            onCategoryUpdate = onSubCategoryUpdate,
            onCategoryDelete = onSubCategoryDelete,
        )
    }
    if (isMainCategoryCreatorOpen) {
        MainCategoryCreatorDialog(
            onDismiss = { isMainCategoryCreatorOpen = false },
            onConfirm = { name ->
                onAddMainCategory(name)
                isMainCategoryCreatorOpen = false
            },
        )
    }
}

@Composable
internal fun MainCategoriesHeader(
    modifier: Modifier = Modifier,
    onAddMainCategory: () -> Unit,
) {
    Row(
        modifier = modifier.padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = HomeThemeRes.strings.mainCategoryTitle,
            color = MaterialTheme.colorScheme.onBackground,
            style = MaterialTheme.typography.titleMedium,
        )
        Spacer(Modifier.weight(1f))
        IconButton(modifier = Modifier.size(36.dp), onClick = onAddMainCategory) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onBackground,
            )
        }
    }
}

@Composable
internal fun SubCategoriesHeader(modifier: Modifier = Modifier) {
    Row(
        modifier = modifier.padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = HomeThemeRes.strings.subCategoryTitle,
            color = MaterialTheme.colorScheme.onBackground,
            style = MaterialTheme.typography.titleMedium,
        )
        Divider(modifier.weight(1f))
    }
}

@Composable
@Preview(showBackground = true, showSystemUi = true)
private fun CategoriesContent_Preview() {
    TimePlannerTheme(
        dynamicColor = false,
        themeColorsType = ThemeColorsUiType.DARK,
        language = LanguageUiType.RU,
    ) {
        HomeTheme {
            CategoriesContent(
                state = CategoriesViewState(),
                onAddMainCategory = {},
                onChangeMainCategory = {},
                onMainCategoryUpdate = {},
                onMainCategoryDelete = {},
                onSubCategoryUpdate = {},
                onSubCategoryDelete = {},
            )
        }
    }
}
