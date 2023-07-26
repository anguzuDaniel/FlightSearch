@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class)

package com.example.flightsearch.ui.components

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.flightsearch.R
import com.example.flightsearch.data.Airport
import com.example.flightsearch.data.Favorite
import com.example.flightsearch.ui.AppViewModelProvider
import com.example.flightsearch.ui.components.favorite.FavoriteCard
import com.example.flightsearch.ui.components.favorite.FavoriteFlightList
import com.example.flightsearch.ui.components.navigation.FlightSearchTopAppBar
import com.example.flightsearch.ui.components.navigation.NavigationDestination
import com.example.flightsearch.ui.theme.AppTheme


object HomeDestination : NavigationDestination {
    override val route = "home"
    override val name: Int = R.string.app_name
}

@JvmOverloads
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navigateToAirportPage: (Int) -> Unit = {}, viewModel: HomePageViewModel = viewModel(
        factory = AppViewModelProvider.Factory
    )
) {
    Scaffold(
        topBar = {
            FlightSearchTopAppBar(
                title = stringResource(id = R.string.app_name),
                canNavigateBack = false,
                modifier = Modifier.fillMaxWidth()
            )
        }, modifier = Modifier.background(color = MaterialTheme.colorScheme.primary)
    ) { innerPadding ->
        HomePage(
            viewModel = viewModel,
            onSuggestionClicked = { navigateToAirportPage(it.id) },
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxWidth()
        )
    }
}

@Composable
fun HomePage(
    onSuggestionClicked: (Airport) -> Unit,
    viewModel: HomePageViewModel,
    modifier: Modifier = Modifier
) {
    val suggestions by viewModel.getSuggestions().collectAsState(initial = emptyList())
    val favorites by viewModel.getAllFavorites().collectAsState(initial = emptyList())
    val paddingHorizontal: Dp = 16.dp
    val paddingVertical: Dp = 6.dp

    Column(modifier = modifier) {
        FlightSearchContent(
            viewModel = viewModel,
            onValueChanged = viewModel::onInputSearchText,
            modifier = Modifier.padding(paddingHorizontal)
        )

        if (favorites.isNotEmpty()) {
            viewModel.uiState.isFavoriteDisplayed = true
        }

        // show is favorite is set to true and search text is empty
        if (viewModel.uiState.isFavoriteDisplayed && viewModel.uiState.searchText.isEmpty()) {
            FavoriteFlightList(
                favorites = favorites,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = paddingHorizontal, vertical = paddingVertical)
            )
        }


        // / show suggestions if search text is not empty
        // and favorite is not displayed
        // and suggestions is not empty
        if (suggestions.isNotEmpty() && viewModel.uiState.searchText.isNotEmpty()) {
            FlightSearchSuggestionList(
                airports = suggestions,
                onSuggestionItemClicked = onSuggestionClicked,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = paddingHorizontal, vertical = paddingVertical)
            )

            if (suggestions.isEmpty()) {
                Text(
                    text = stringResource(id = R.string.no_search_results),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = paddingHorizontal, vertical = paddingVertical)
                )
            }
        }
    }
}

// search bar
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBar(
    viewModel: HomePageViewModel,
    modifier: Modifier = Modifier,
    onValueChanged: (HomePageUiState) -> Unit
) {
    OutlinedTextField(
        value = viewModel.uiState.searchText,
        onValueChange = {
            onValueChanged(
                // when search text is empty, show favorite
                viewModel.uiState.copy(
                    searchText = it,

                    )
            )
        },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Text, imeAction = ImeAction.Search
        ),
        label = { Text(text = stringResource(id = R.string.search_placeholder)) },
        leadingIcon = {
            Icon(
                painter = painterResource(id = R.drawable.baseline_search_24),
                contentDescription = stringResource(id = R.string.search_placeholder)
            )
        },
        modifier = modifier
    )
}


@Composable
fun FlightSearchContent(
    viewModel: HomePageViewModel,
    modifier: Modifier = Modifier,
    onValueChanged: (HomePageUiState) -> Unit
) {
    LazyColumn(modifier = modifier) {
        item {
            SearchBar(
                viewModel = viewModel,
                onValueChanged = onValueChanged,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
            )
        }
    }
}

@Composable
fun FlightSearchSuggestionList(
    onSuggestionItemClicked: (Airport) -> Unit,
    airports: List<Airport>,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    LazyColumn(modifier = modifier) {
        items(airports, key = { it.id }) { airport ->
            SearchSuggestionListItem(code = airport.iataCode,
                name = airport.name,
                modifier = Modifier.clickable {
                    onSuggestionItemClicked(airport)
                    Toast.makeText(
                        context,
                        "Click on an item ${airport} to see more details",
                        Toast.LENGTH_SHORT
                    ).show()
                })
        }
    }
}

@Composable
fun SearchSuggestionListItem(
    code: String, name: String, modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.padding(bottom = 8.dp)
    ) {
        Text(
            text = code,
            fontWeight = MaterialTheme.typography.titleMedium.fontWeight,
            modifier = modifier
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = name,
        )
    }
}


@Composable
fun FlightCard(
    favorites: Airport, modifier: Modifier = Modifier
) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.secondaryContainer,
        ),
        shape = RoundedCornerShape(topEnd = 16.dp),
    ) {
        Row(
            modifier = modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Column {
                Text(
                    text = "Depart",
                    fontWeight = MaterialTheme.typography.titleMedium.fontWeight,
                )
                Spacer(modifier = Modifier.height(8.dp))
                SearchSuggestionListItem(
                    code = "SVO",
                    name = "Keflavik International Airport",
                )
                Spacer(modifier = Modifier.height(8.dp))
                SearchSuggestionListItem(
                    code = "SVO",
                    name = "Keflavik International Airport",
                )
            }
            Spacer(modifier = Modifier.width(16.dp))
            Icon(
                painter = painterResource(id = R.drawable.baseline_star_rate_24),
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.size(30.dp)
            )
        }

    }
}


@Preview(showBackground = true)
@Composable
fun PreviewSearchBar() {
    AppTheme {
        LazyColumn(modifier = Modifier.padding(16.dp)) {
            items(1) {
                SearchSuggestionListItem(
                    code = "SVO",
                    name = "Keflavik International Airport",
                )
            }

        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewSearchCard() {
    AppTheme {
        val fLI = Favorite(
            id = 1, departureCode = "HEL", destinationCode = "Heraklion Airport N. Kazantzakis"
        )
        FavoriteCard(favorite = fLI)
    }
}