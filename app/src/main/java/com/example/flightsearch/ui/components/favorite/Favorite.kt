package com.example.flightsearch.ui.components.favorite

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
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.flightsearch.R
import com.example.flightsearch.data.Favorite
import com.example.flightsearch.ui.components.SearchSuggestionListItem

@Composable
fun FavoriteCard(
    favorite: Favorite,
    modifier: Modifier = Modifier
) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.secondaryContainer,
        ),
        shape = RoundedCornerShape(topEnd = 16.dp),
        modifier = modifier
            .fillMaxWidth()
    ) {
        Row(
            modifier = modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier
                    .weight(1f)
            ) {
                Text(
                    text = favorite.departureCode,
                    fontWeight = MaterialTheme.typography.titleMedium.fontWeight,
                )
                Spacer(modifier = Modifier.height(8.dp))
                SearchSuggestionListItem(
                    code = favorite.departureCode,
                    name = favorite.destinationCode,
                )
                Spacer(modifier = Modifier.height(8.dp))
                SearchSuggestionListItem(
                    code = favorite.departureCode,
                    name = favorite.destinationCode,
                )
            }
            Spacer(modifier = Modifier.width(16.dp))
            Icon(
                painter = painterResource(id = R.drawable.baseline_star_rate_24),
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier
            )
        }

    }
}

@Composable
fun FavoriteFlightList(
    favorites: List<Favorite>,
    modifier: Modifier = Modifier
) {
    LazyColumn(modifier = modifier) {
        item {
            Text(
                text = stringResource(R.string.favorite_routes),
                fontWeight = MaterialTheme.typography.titleMedium.fontWeight,
                fontStyle = MaterialTheme.typography.headlineLarge.fontStyle,
            )
            Spacer(modifier = Modifier.height(16.dp))
        }
        items(favorites, key = { it.id }) { favorite ->
            FavoriteCard(
                favorite = favorite,
            )
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}