package com.example.flightsearch.ui.components.Airport

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.flightsearch.R
import com.example.flightsearch.ui.AppViewModelProvider
import com.example.flightsearch.ui.components.navigation.FlightSearchTopAppBar
import com.example.flightsearch.ui.components.navigation.NavigationDestination
import com.example.flightsearch.ui.theme.AppTheme

object AirportDestination : NavigationDestination {
    override val route = "flight"
    override val name: Int = R.string.app_name
    const val itemIdArg = "itemId"
    val routeWithArgs = "$route/{$itemIdArg}"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AirportScreen(
    onStarClicked: () -> Unit = {},
    onAddFavoriteBtnClicked: () -> Unit = {},
    viewModel: AirportViewModel = viewModel(
        factory = AppViewModelProvider.Factory
    ),
) {
    val context = LocalContext.current
    Scaffold(
        topBar = {
            FlightSearchTopAppBar(
                title = AirportDestination.route,
                canNavigateBack = true,
                navigateUp = onAddFavoriteBtnClicked,
                modifier = Modifier
                    .fillMaxWidth()
            )
        },
        modifier = Modifier
            .background(color = MaterialTheme.colorScheme.primary)
    ) { innerPadding ->
        AirportInformation(
            viewModel = viewModel,
            onAddFavoriteBtnClicked = {
                viewModel.onAddFavoriteClicked()
                Toast.makeText(
                    context,
                    "Added to favorites",
                    Toast.LENGTH_SHORT
                ).show()
            },
            modifier = Modifier.padding(innerPadding)
        )
    }
}

@Composable
fun AirportInformation(
    onAddFavoriteBtnClicked: () -> Unit,
    viewModel: AirportViewModel,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        item {
            AirportPage(
                onAddFavoriteBtnClicked = onAddFavoriteBtnClicked,
                viewModel = viewModel,
                modifier = modifier
            )
        }
    }
}

@Composable
fun AirportPage(
    onAddFavoriteBtnClicked: () -> Unit,
    isFavorite: Boolean = false,
    viewModel: AirportViewModel,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.padding(20.dp)) {
        Row(
            verticalAlignment = Alignment.CenterVertically,

            ) {
            Text(
                text = viewModel.uiState.name,
                fontWeight = MaterialTheme.typography.titleMedium.fontWeight,
                fontStyle = MaterialTheme.typography.headlineLarge.fontStyle,
                modifier = Modifier.weight(1f)
            )

            IconButton(
                onClick = onAddFavoriteBtnClicked,
            ) {
                Icon(
                    painter = painterResource(
                        id = R.drawable.baseline_star_rate_24
                    ),
                    contentDescription = null
                )
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        AirportRow(
            label = "Name:",
            value = viewModel.uiState.name
        )
        Spacer(modifier = Modifier.height(8.dp))
        AirportRow(
            label = "Iata Code:",
            value = viewModel.uiState.iataCode
        )
        Spacer(modifier = Modifier.height(8.dp))
        AirportRow(
            label = "Passengers:",
            value = viewModel.uiState.passengers.toString()
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = onAddFavoriteBtnClicked,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Add to favorites")
        }
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedButton(
            onClick = { /*TODO*/ },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Remove from favorites")
        }
    }
}

@Composable
fun AirportRow(
    label: String,
    value: String,
) {
    Row {
        Text(
            text = label,
            fontWeight = MaterialTheme.typography.titleMedium.fontWeight,
            fontStyle = MaterialTheme.typography.headlineLarge.fontStyle,
            modifier = Modifier.weight(1f)
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.weight(1f)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun AirportRowPreview() {
    AppTheme() {
        AirportScreen()
    }
}