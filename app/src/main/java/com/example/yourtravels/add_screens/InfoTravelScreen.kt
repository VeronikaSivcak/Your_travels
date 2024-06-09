package com.example.yourtravels.add_screens


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.*
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


import com.example.yourtravels.navigation.NavigationDestination


object InfoAboutTravel : NavigationDestination {
    //dat ako resource
    override val route = "info_about_travel"
    const val travelId = "travelId"
    val routeWithParam = "$route/{$travelId}"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InfoTravelScreen(
    viewModel: InfoTravelViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    //definuje správanie AppBaru v kontajneri s posuvným obsahom, ako je napríklad ScrollView.
    //Kedykoľvek sa obsah posúva, správanie "enterAlways" znamená,
    // že AppBar sa vždy zobrazuje, keď sa obsah posúva v akejkoľvek smerom dole
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    Scaffold(
        // modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            YTTopAppBar(
                name = stringResource(R.string.app_name),
                navigateBack = true,
                scrollBehavior = scrollBehavior,
                color = MaterialTheme.colorScheme.primaryContainer
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {},
                modifier = Modifier.padding(20.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = stringResource(R.string.add_travel_to_list))
            }
        }
    )
    { innerPadding ->

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(3.dp),
            modifier = Modifier
                .padding(10.dp)
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            InfoCards()
            SimpleExpense()

        }
    }
}




@Preview
@Composable
fun InfoCards() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Card(
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer
            ),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
            modifier = Modifier
                .weight(1f)
                .padding(8.dp)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
            ) {
                Text(
                    text = stringResource(R.string.spent_today),
                    style = MaterialTheme.typography.titleLarge
                )
            }
        }
        Card(
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer
            ),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
            modifier = Modifier
                .weight(1f)
                .padding(8.dp)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
                 ) {
                Text(
                    text = stringResource(R.string.spent_today),
                    style = MaterialTheme.typography.titleLarge
                )
            }
        }
    }
}

@Preview
@Composable
private fun SimpleExpense(
    //expense : Expense,
    modifier : Modifier = Modifier) {
    Card(
        modifier = modifier.padding(15.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically,
            //verticalArrangement = Arrangement.spacedBy(3.dp),
            modifier = Modifier
                .padding(12.dp)
                .fillMaxWidth()

        ) {
            Text(
                text = "hello",
                textAlign = TextAlign.Left,
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.weight(1f)
            )
            Text(
                text = "hi",
                textAlign = TextAlign.Right,
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.weight(1f)
            )
        }
    }
}




