package com.example.yourtravels.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.runtime.rememberCoroutineScope
import com.example.yourtravels.R
import com.example.yourtravels.components.YTTopAppBar
import com.example.yourtravels.navigation.NavigationDestination
import com.example.yourtravels.application.AppViewModelProvider
import kotlinx.coroutines.launch
import androidx.compose.ui.text.input.KeyboardType
import com.example.yourtravels.components.DatePickerField
import com.example.yourtravels.components.InputField



object AddTravel : NavigationDestination {
    override val route = "add_travel_to_list"

}

/**
 * predstavuje obrazovku
 * robene podla codelabu Inventory
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewTravelScreen(
    navigateBack: () -> Unit,
    onNavigateUp: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: NewTravelViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
   val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
   val coroutineScope = rememberCoroutineScope()
    Scaffold(
        topBar = {
            YTTopAppBar(
                name = stringResource(R.string.add_new_travel),
                navigateBack = true,
                scrollBehavior = scrollBehavior,
                color = MaterialTheme.colorScheme.primaryContainer,
                navigateUp = navigateBack
            )
        },
    ) {
        innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .verticalScroll((rememberScrollState()))
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(45.dp)
        ) {
            InputForNewTravel(
                travelUiState = viewModel.travelUiState,
                onTravelValueChange = viewModel::updateUiState,
            )
            Button(
               onClick = {
                         coroutineScope.launch {
                             viewModel.saveTravel()
                             navigateBack()
                         }
               },
                enabled = viewModel.travelUiState.validEntry,
                modifier = Modifier
                    .fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    contentColor = Color.Black
                )
            ) {
                Text(
                    text = stringResource(R.string.save_button),
                    modifier = Modifier.padding(8.dp)
                )
            }
        }
    }
}

/**
 * predstavuje moznosti zadania vstupov pre novu cestu
 */
@Composable
fun InputForNewTravel(
    travelUiState: TravelUiState,
    travelInfo: TravelInfo = travelUiState.travelInfo,
    onTravelValueChange: (TravelInfo) -> Unit = {},
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(15.dp)
    ) {
        InputField(
            name = stringResource(R.string.travel_name),
            valueToSet = travelInfo.name,
            onValueChange = { onTravelValueChange(travelInfo.copy(name = it)) })
        InputField(
            name = stringResource(R.string.budget),
            valueToSet = travelInfo.budget,
            onValueChange = {onTravelValueChange(travelInfo.copy(budget = it))},
            keyboard = KeyboardOptions(keyboardType = KeyboardType.Decimal))
        DatePickerField(
            label = stringResource(R.string.start),
            selectedDate = travelInfo.startDate,
            onDateSelected = { onTravelValueChange(travelInfo.copy(startDate = it)) })
        DatePickerField(
            label = stringResource(R.string.end),
            selectedDate = travelInfo.endDate,
            onDateSelected = { onTravelValueChange(travelInfo.copy(endDate = it)) })
       InputField(
            name = stringResource(R.string.notes),
            valueToSet = travelInfo.notes.orEmpty(),
            onValueChange = { onTravelValueChange(travelInfo.copy(notes = it)) })
    }
}


















