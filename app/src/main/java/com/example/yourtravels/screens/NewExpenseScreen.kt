package com.example.yourtravels.screens


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.yourtravels.application.AppViewModelProvider
import com.example.yourtravels.components.DatePickerField
import com.example.yourtravels.components.DropdownField
import com.example.yourtravels.components.InputField
import com.example.yourtravels.R
import com.example.yourtravels.components.YTTopAppBar
import com.example.yourtravels.data.ExpenseCategory
import com.example.yourtravels.data.PaymentMethod
import com.example.yourtravels.navigation.NavigationDestination
import kotlinx.coroutines.launch

object AddExpense : NavigationDestination {
    override val route = "add_expense_to_list"
    const val travelId = "travelId"
    val routeWithParam = "$route/{$travelId}"
}

/**
 * predstavuje obrazovku
 * robene podla codelabu Inventory
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewExpenseScreen(
    travelId: Int,
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: NewExpenseViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {

    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            YTTopAppBar(
                name = stringResource(R.string.add_new_expense),
                navigateBack = true,
                scrollBehavior = scrollBehavior,
                color = MaterialTheme.colorScheme.primaryContainer,
                navigateUp = navigateBack
            )
        }
    ) {
        innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .verticalScroll((rememberScrollState()))
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(45.dp)
        ) {
            InputForNewExpense(
                expenseUiState = viewModel.expenseUiState,
                onExpenseValueChange = viewModel::updateUiState,
                paymentMethods = PaymentMethod.entries.map { it.name.lowercase() },
            )

            Button(
                onClick = {
                    coroutineScope.launch {
                        if (travelId != -1) {
                            val updatedExpenseInfo = viewModel.expenseUiState.expenseInfo.copy(travelId = travelId.toString())
                            viewModel.updateUiState(updatedExpenseInfo)
                        }
                        viewModel.saveExpense()
                        navigateBack()
                    }
                },
                enabled = viewModel.expenseUiState.validEntry,
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
 * predstavuje vstupne polia pre novy vydavok
 */
@Composable
fun InputForNewExpense(
    expenseUiState: ExpenseUiState,
    paymentMethods: List<String>,
    expenseInfo: ExpenseInfo = expenseUiState.expenseInfo,
    onExpenseValueChange: (ExpenseInfo) -> Unit = {},
    modifier: Modifier = Modifier
) {
    val itemList: List<String> = ExpenseCategory.entries.map { it.name.lowercase() }
    var selectedIndex by rememberSaveable { mutableIntStateOf(-1) }

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(15.dp)
    ) {

        InputField(
            name = stringResource(R.string.name),
            valueToSet = expenseInfo.name,
            onValueChange = { onExpenseValueChange(expenseInfo.copy(name = it)) }
        )
        InputField(
            name = stringResource(R.string.expense_price),
            valueToSet = expenseInfo.price,
            onValueChange = { onExpenseValueChange(expenseInfo.copy(price = it)) },
            keyboard = KeyboardOptions(keyboardType = KeyboardType.Decimal)
        )
        DatePickerField(
            label = stringResource(R.string.date),
            selectedDate = expenseInfo.date,
            onDateSelected ={onExpenseValueChange(expenseInfo.copy(date = it))}
        )
        DropdownField(
            itemList = itemList,
            selectedIndex = selectedIndex,
            onItemClick = { selectedIndex = it;
                onExpenseValueChange(expenseInfo.copy(category = itemList[it])) }
        )
        InputField(
            name = stringResource(R.string.notes),
            valueToSet = expenseInfo.notes.orEmpty(),
            onValueChange = {onExpenseValueChange(expenseInfo.copy(notes = it))}
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
        Text(
            stringResource(R.string.select_payment_method),
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Start
        )
          MyRadioButtons(
              options = paymentMethods,
              expenseInfo = expenseInfo,
              onExpenseValueChange = onExpenseValueChange
          )
        }
    }
}

/**
 * predstavuje radio buttony pre vyber z moznosti
 * robene podla codelabu Cupcake
 */
@Composable
fun MyRadioButtons(
    options: List<String>,
    onExpenseValueChange: (ExpenseInfo) -> Unit = {},
    expenseInfo: ExpenseInfo
    ) {

    var selectedValue by rememberSaveable { mutableStateOf("") }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp),
        horizontalArrangement = Arrangement.Center
    ) {
        options.forEach { item ->
            Row(
                modifier = Modifier
                    .selectable(
                        selected = selectedValue == item,
                        onClick = {
                            selectedValue = item
                        }
                    ),
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(
                    selected = selectedValue == item,
                    onClick = {
                        selectedValue = item
                        onExpenseValueChange(expenseInfo.copy(paymentMethod = item))
                    }
                )
                Text(item)
            }
        }
    }
}







