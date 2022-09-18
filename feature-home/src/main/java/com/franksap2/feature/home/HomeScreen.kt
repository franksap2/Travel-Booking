package com.franksap2.feature.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.franksap2.feature.home.components.Places
import com.franksap2.feature.home.components.TopPlaces

@Composable
fun HomeScreen(
    onClickPlace: (String) -> Unit,
    viewModel: HomeScreenViewModel = hiltViewModel()
) {

    val homeUiState by viewModel.homeUiState.collectAsState()

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .systemBarsPadding(),
        verticalArrangement = Arrangement.spacedBy(20.dp),
        contentPadding = PaddingValues(vertical = 32.dp)
    ) {
        itemTitle(title = R.string.top_places)
        item { TopPlaces(topPlaces = homeUiState.topPlaces) }
        itemTitle(title = R.string.discover)
        item { Places(item = homeUiState.places, onClick = onClickPlace) }
    }
}

private fun LazyListScope.itemTitle(title: Int) {
    item {
        Text(
            modifier = Modifier.padding(start = 16.dp),
            text = stringResource(id = title),
            color = MaterialTheme.colors.onBackground,
            style = MaterialTheme.typography.h4
        )
    }
}