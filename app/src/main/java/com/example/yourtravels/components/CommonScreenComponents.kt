package com.example.yourtravels.components

import android.app.DatePickerDialog
import android.icu.text.SimpleDateFormat
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupProperties
import java.util.Calendar
import java.util.Locale


/**
 * Táto funkcia vytvára pole s rozbaľovacím zoznamom.
 * Robila som ho s využitím tohto zdroja:
 *  https://medium.com/@itsuki.enjoy/android-kotlin-jetpack-compose-dropdown-selectable-list-menu-b7ad86ba6a5a
 */
@Composable
fun DropdownField(
    itemList: List<String>,
    selectedIndex: Int,
    modifier: Modifier = Modifier,
    defaultText: String = "Select category",
    onItemClick: (Int) -> Unit) {

    var showDropdown by rememberSaveable { mutableStateOf(false) }
    val scrollState = rememberScrollState()

    Column(
        modifier = modifier.padding(horizontal = 8.dp),

        horizontalAlignment = Alignment.CenterHorizontally,
        ) {

        // button
        Box(
            modifier = modifier
                .background(MaterialTheme.colorScheme.surfaceVariant, RoundedCornerShape(14.dp))
                .fillMaxWidth()
                .height(55.dp)
                .padding(horizontal = 8.dp)
                .clickable { showDropdown = true },
            contentAlignment = Alignment.CenterStart
        ) {
            Text(text = if (selectedIndex != -1) itemList[selectedIndex] else defaultText,
                modifier = Modifier.padding(8.dp),
                color = MaterialTheme.colorScheme.outline,
            )
        }

        // dropdown list
        Box() {
            if (showDropdown) {
                Popup(
                    alignment = Alignment.TopCenter,
                    properties = PopupProperties(
                        excludeFromSystemGesture = true,
                    ),
                    // to dismiss on click outside
                    onDismissRequest = { showDropdown = false }
                ) {

                    Column(
                        modifier = modifier
                            .heightIn(10.dp)
                            .verticalScroll(state = scrollState)
                            .background(Color.White),

                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {

                        itemList.onEachIndexed { index, item ->

                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable {
                                        onItemClick(index)
                                        showDropdown = !showDropdown
                                    },
                                contentAlignment = Alignment.Center
                            ) {
                                Text(text = item,
                                    color = MaterialTheme.colorScheme.outline,
                                    modifier = Modifier.padding(8.dp),
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}


/**
 * Táto funkcia vytvára OutlineTextField upravený pre moju aplikáciu.
 */
@Composable
fun InputField (
    valueToSet: String,
    onValueChange: (String) -> Unit = {},
    name : String,
    keyboard: KeyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
) {
    OutlinedTextField(
        value = valueToSet,
        onValueChange = onValueChange,
        label = { Text(name, color = MaterialTheme.colorScheme.outline) },
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


/**
 * Táto funkcia vytvára Date Picker pre výber dátumu.
 * Túto funkciu som robila s ChatomGPT.
 */
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
        onValueChange = {},
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
                    DatePickerDialog(
                        context,
                        { _, year, month, dayOfMonth ->
                            calendar.set(year, month, dayOfMonth)
                            val formattedDate = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault()).format(calendar.time)
                            onDateSelected(formattedDate)
                        },
                        calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH)
                    ).show()
                    keyboardController?.hide()
                }
            },
        singleLine = true
    )
}


