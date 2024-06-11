package com.example.yourtravels.screens


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.yourtravels.application.AppViewModelProvider
import com.example.yourtravels.R
import com.example.yourtravels.components.YTTopAppBar
import com.example.yourtravels.data.Expense
import com.example.yourtravels.data.Travel


import com.example.yourtravels.navigation.NavigationDestination


object InfoAboutTravel : NavigationDestination {
    override val route = "info_about_travel"
    const val travelId = "travelId"
    val routeWithParam = "$route/{$travelId}"
}

/**
 * predstavuje obrazovku
 * robene podla codelabu Inventory
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InfoTravelScreen(
    navigateBack: () -> Unit,
    navigateToAddExpense: (Int) -> Unit,
    viewModel: InfoTravelViewModel = viewModel(factory = AppViewModelProvider.Factory)

) {

    val infoUiState by viewModel.infoUiState.collectAsState()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold(
        topBar = {
            YTTopAppBar(
                name = (infoUiState.travelInfo.name),
                navigateBack = true,
                scrollBehavior = scrollBehavior,
                color = MaterialTheme.colorScheme.primaryContainer,
                navigateUp = navigateBack
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {navigateToAddExpense(infoUiState.travelInfo.id)},
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
            BodyOfInfoScreen(
                infoUiState,
                expenseList = infoUiState.expenseList
            )
        }
    }
}

/**
 * predstavuje telo obrazovky
 */
@Composable
private fun BodyOfInfoScreen(
    infoTravelUiState: InfoTravelUiState,
    expenseList: List<Expense>,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp)
) {

    if (expenseList.isEmpty()) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = modifier
        ) {
            InfoCards(
                totalExpenses = infoTravelUiState.totalExpenses,
                remainingBudget = infoTravelUiState.remainingBudget
            )
            InfoTravelScreenBody(infoTravelUiState = infoTravelUiState)
            Text(
                text = stringResource(R.string.empty_expense_list),
                style = MaterialTheme.typography.titleMedium,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(25.dp)
            )
        }
    } else {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = modifier
        ) {
            InfoCards(
                totalExpenses = infoTravelUiState.totalExpenses,
                remainingBudget = infoTravelUiState.remainingBudget
            )
            InfoTravelScreenBody(
                infoTravelUiState = infoTravelUiState
            )
            Text(
                text = stringResource(R.string.expenses_title),
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier
                    .padding(horizontal = 8.dp)
                    .align(alignment = Alignment.Start)
            )
            ListOfExpenses(
                expenseList = expenseList,
                contentPadding = contentPadding,
                modifier = Modifier.padding(horizontal = 8.dp)
            )
        }
    }
}


/**
 * zoznam výdavkov
 * zdroj: codelab Inventory
 */
@Composable
private fun ListOfExpenses(
    expenseList: List<Expense>,
    contentPadding: PaddingValues,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = contentPadding
    ) {
         items(items = expenseList, key = {it.id}) { item->
            SimpleExpense (expense = item)
        }
    }
}


/**
 * usporiadanie kariet s informaciami
 */
@Composable
fun InfoCards(
    totalExpenses: Double,
    remainingBudget: Double
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        SimpleInfoCard(
            title = stringResource(R.string.total_spent),
            sum = totalExpenses,
            modifier = Modifier.weight(1f)
        )
        SimpleInfoCard(
            title = stringResource(R.string.total_left),
            sum = remainingBudget,
            modifier = Modifier.weight(1f)
        )
    }
}

/**
 * predstavuje jednu kartu s informaciami
 */
@Composable
private fun SimpleInfoCard (
    title: String,
    sum: Double,
    modifier: Modifier
) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        modifier = modifier
            .padding(8.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
        ) {
            Text(
                text = (title),
                style = MaterialTheme.typography.titleLarge
            )
            Text(
                text = String.format("%.2f€", sum),
                style = MaterialTheme.typography.titleLarge
            )
        }
    }
}

/**
 * predsatvuje jeden výdavok
 */
@Composable
private fun SimpleExpense (
    expense : Expense,
    modifier : Modifier = Modifier
) {
    Card(
        modifier = modifier.padding(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(12.dp)
                .fillMaxWidth()
        ) {
            Text(
                text = expense.name,
                textAlign = TextAlign.Left,
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.weight(1f)
            )
            Text(
                text = String.format("%.2f", expense.price) + "€",
                textAlign = TextAlign.Right,
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.weight(1f)
            )
        }
    }
}

/**
 * predstavuje usporiadane info o ceste
 */
@Composable
private fun InfoTravelScreenBody(
    infoTravelUiState: InfoTravelUiState,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.padding(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        TravelDetails(
            travel = infoTravelUiState.travelInfo.toTravel(),
            modifier = Modifier.fillMaxWidth()
        )
    }
}

/**
 * predstavuje kompletné info o ceste
 */
@Composable
fun TravelDetails(
    travel: Travel,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        TravelDetailRow(
            travelDescription = stringResource(R.string.travel_description_name),
            travelDescriptionValue = travel.name
        )
        TravelDetailRow(
            travelDescription = stringResource(R.string.travel_description_budget),
            travelDescriptionValue = String.format("%.2f", travel.budget) + stringResource(R.string.euro)
        )
        TravelDetailRow(
            travelDescription = stringResource(R.string.travel_description_start_day),
            travelDescriptionValue = travel.startDate
        )
        TravelDetailRow(
            travelDescription = stringResource(R.string.travel_description_end_day),
            travelDescriptionValue = travel.endDate
        )
        TravelDetailRow(
            travelDescription = stringResource(R.string.travel_description_notes),
            travelDescriptionValue = travel.notes.orEmpty()
        )
    }
}

/**
 * predsatvuje riadok s informaciami o ceste
 */
@Composable
fun TravelDetailRow(
    travelDescription: String,
    travelDescriptionValue: String
) {
    Row() {
        Text(travelDescription, style = MaterialTheme.typography.titleMedium)
        Spacer(modifier = Modifier.weight(1f))
        Text(travelDescriptionValue, style = MaterialTheme.typography.titleMedium)
    }
}


