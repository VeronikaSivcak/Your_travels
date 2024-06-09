package com.example.yourtravels.home


import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.yourtravels.AppViewModelProvider
import com.example.yourtravels.R
import com.example.yourtravels.YTTopAppBar
import com.example.yourtravels.data.Travel
import com.example.yourtravels.navigation.NavigationDestination
import com.example.yourtravels.ui.theme.YourTravelsTheme


object Home : NavigationDestination {
    override val route = "app_name"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navigateToItemEntry: () -> Unit,
    navigateToTravelDetails: (Int) -> Unit,
    viewModel: HomeViewModel = viewModel(factory = AppViewModelProvider.Factory),
    modifier: Modifier = Modifier
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    val homeUiState by viewModel.homeUiState.collectAsState()
    Scaffold(
       // modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
                 YTTopAppBar(
                     name = stringResource(R.string.app_name),
                     navigateBack = false,
                     scrollBehavior = scrollBehavior,
                     color = MaterialTheme.colorScheme.primaryContainer
                     )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = navigateToItemEntry,
                modifier = Modifier.padding(20.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = stringResource(R.string.add_travel_to_list))
            }
        }
    )
    { innerPadding ->

        BodyOfHomeScreen(
            travelList = homeUiState.travelList,
            onItemClick = navigateToTravelDetails,
            modifier = modifier
                .fillMaxSize()
                .padding(innerPadding),
            //contentPadding = innerPadding
        )
    }
}


@Composable
private fun BodyOfHomeScreen(
    travelList: List<Travel>,
    onItemClick: (Int) -> Unit,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp)
) {
    if (travelList.isEmpty()) {
        Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = modifier
        ) {
            Text(
                text = stringResource(R.string.empty_travel_list),
                style = typography.titleLarge,
                textAlign = TextAlign.Center
            )
        }
    } else {
        Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = modifier
        ) {
            Card(
                modifier = Modifier.padding(18.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                ),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(8.dp)
                ) {
                    Text(
                        text = stringResource(R.string.spent_today),
                        style = typography.titleLarge
                    )
                    Text(
                        text = "150€",
                        style = typography.titleLarge
                    )
                }
            }
            Text(
                text = stringResource(R.string.travels_title),
                style = typography.titleMedium,
                modifier = Modifier
                    .padding(horizontal = 8.dp)
                    .align(alignment = Alignment.Start)
            )

            ListOfTravels(
                travelList = travelList,
                onItemClick = { onItemClick(it.id) },
                contentPadding = contentPadding,
                modifier = Modifier.padding(horizontal = 8.dp)
            )
        }
    }
}


@Composable
private fun SimpleTravel(
    travel : Travel,
    modifier : Modifier = Modifier)
    {
        Card(
            modifier = modifier.padding(15.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant
            ),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                //verticalArrangement = Arrangement.spacedBy(3.dp),
                modifier = Modifier
                    .padding(10.dp)
                    .fillMaxWidth()

            ) {
                Text(
                    text = travel.name,
                    style = typography.titleLarge
                )
                //resource!!!!!!!!!!!!!
                Text(
                    text = travel.startDate + " - " + travel.endDate,
                    style = typography.titleMedium
                )
                Text(
                    text = String.format("%.2f", travel.budget) + "€",
                    style = typography.titleMedium
                )

            }
        }
}

@Composable
private fun ListOfTravels(
    travelList: List<Travel>,
    onItemClick: (Travel) -> Unit,
    contentPadding: PaddingValues,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = contentPadding
    ) {
        items(items = travelList, key = {it.id}) { item->
            SimpleTravel(travel = item,
                modifier = Modifier
                    .clickable { onItemClick(item)} )
        }

    }
}

@Preview(showBackground = true)
@Composable
fun HomeBodyPreview() {
    YourTravelsTheme {
        BodyOfHomeScreen(
            listOf(

            ), onItemClick ={} )
    }
}

@Preview
@Composable
fun SimpleItemPreview() {
    //SimpleTravel
}