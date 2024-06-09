package com.example.yourtravels.add_screens

import android.app.DatePickerDialog
import android.icu.text.SimpleDateFormat
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import com.example.yourtravels.R
import com.example.yourtravels.YTTopAppBar
import com.example.yourtravels.navigation.NavigationDestination
import com.example.yourtravels.AppViewModelProvider
import kotlinx.coroutines.launch
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.KeyboardType
import java.util.*


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
    //horná aplikácia je vždy viditeľná, keď sa používateľ začne posúvať hore (scroll up),
    // a začne sa skrývať, keď používateľ začne posúvať dole (scroll down).
     val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
   val coroutineScope = rememberCoroutineScope()
    Scaffold(
        //modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
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

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun DatePickerField(
    label: String,
    selectedDate: String,
    onDateSelected: (String) -> Unit
) {
    val context = LocalContext.current
    val calendar = Calendar.getInstance()

    val focusRequester = remember { FocusRequester() }
    val keyboardController = LocalSoftwareKeyboardController.current

    OutlinedTextField(
        value = selectedDate,
        onValueChange = { /* Do nothing here to disable manual text input */ },
        label = { Text(label, color = MaterialTheme.colorScheme.outline) },
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
            .padding(horizontal = 8.dp)
            .focusRequester(focusRequester)
            .onFocusChanged { focusState ->
                if (focusState.isFocused) {
                    // Show DatePickerDialog when the field gains focus
                    DatePickerDialog(
                        context,
                        { _, year, month, dayOfMonth ->
                            calendar.set(year, month, dayOfMonth)
                            //treba MM, lebo mm su minuty
                            val formattedDate = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault()).format(calendar.time)
                            onDateSelected(formattedDate)
                        },
                        calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH)
                    ).show()
                    // Hide the keyboard
                    keyboardController?.hide()
                }
            },
        singleLine = true
    )
}

@Composable
fun InputField (
    modifier: Modifier = Modifier,
    valueToSet: String,
    onValueChange: (String) -> Unit = {},
    name : String,
    keyboard: KeyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
) {
    OutlinedTextField(
        value = valueToSet,
        onValueChange = onValueChange,
        label = {Text(name, color = MaterialTheme.colorScheme.outline)},
        keyboardOptions = keyboard,
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















