package com.example.yourtravels.add_screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.runtime.rememberCoroutineScope
import com.example.yourtravels.R
import com.example.yourtravels.YTTopAppBar
import com.example.yourtravels.navigation.NavigationDestination
import com.example.yourtravels.AppViewModelProvider
import kotlinx.coroutines.launch


object AddTravel : NavigationDestination {
    override val route = "add_travel_to_list"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewTravelScreen(
    navigateBack: () -> Unit,
    onNavigateUp: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: NewTravelViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    // val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
   val coroutineScope = rememberCoroutineScope()
    Scaffold(
        //modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            YTTopAppBar(
                name = stringResource(R.string.app_name),
                navigateBack = true,
                //scrollBehavior = scrollBehavior,
                color = MaterialTheme.colorScheme.primaryContainer,
                navigateUp = navigateBack
            )
        },
    ) {
        innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                //.verticalScroll((rememberScrollState()))
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(45.dp)
        ) {
            InputForNewTravel(
                travelUiState = viewModel.travelUiState,
                onTravelValueChange = viewModel::updateUiState,
                //modifier.verticalScroll(rememberScrollState())
            )
            Button(
               onClick = {
                         coroutineScope.launch {
                             viewModel.saveTravel()
                             navigateBack()
                         }
               },
                enabled = viewModel.travelUiState.validEntry,        ///isENtryVAlid
                modifier = Modifier
                    .fillMaxWidth(),
                    //.padding(horizontal = 8.dp),
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


//problem s travelUiState

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
        /*InputField(
            name = stringResource(R.string.currency),
            input = "",
            valueToSet = travelInfo.curr,
            onValueChange = onTravelValueChange)*/
        InputField(
            name = stringResource(R.string.budget),
            valueToSet = travelInfo.budget,
            onValueChange = {onTravelValueChange(travelInfo.copy(budget = it))})
        InputField(
            name = stringResource(R.string.start),
            valueToSet = travelInfo.startDate,
            onValueChange = { onTravelValueChange(travelInfo.copy(startDate = it)) })
        InputField(
            name = stringResource(R.string.end),
            valueToSet = travelInfo.endDate,
            onValueChange = { onTravelValueChange(travelInfo.copy(endDate = it)) })
       /* InputField(
            name = stringResource(R.string.notes),
            valueToSet = "",
            onValueChange = { onTravelValueChange(travelInfo.copy(notes = it)) })*/
    }
}



@Composable
fun InputField (
    modifier: Modifier = Modifier,
    valueToSet: String,
    onValueChange: (String) -> Unit = {},
    name : String
) {
    OutlinedTextField(
        value = valueToSet,
        onValueChange = onValueChange,
        label = {Text(name, color = MaterialTheme.colorScheme.outline)},
        colors = OutlinedTextFieldDefaults.colors(
            focusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
            unfocusedContainerColor = MaterialTheme.colorScheme.surfaceVariant,
            disabledContainerColor = MaterialTheme.colorScheme.secondaryContainer,
            focusedBorderColor = Color.Transparent,
            unfocusedBorderColor = Color.Transparent

        ),
        shape = RoundedCornerShape(14.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp),
        singleLine = false
    )
}


/*@Preview
@Composable
fun ItemInputPreview() {
    YourTravelsTheme {
        InputField(
            name = "input",
            input = "")
    }
}*/












