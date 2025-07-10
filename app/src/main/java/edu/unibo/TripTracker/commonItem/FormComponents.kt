package edu.unibo.tracker.commonItem

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun DropdownField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    options: List<String>,
    modifier: Modifier = Modifier.fillMaxWidth(0.8f)
) {
    var expanded by remember { mutableStateOf(false) }
    
    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded },
        modifier = modifier
    ) {
        OutlinedTextField(
            value = value,
            onValueChange = { },
            readOnly = true,
            label = { Text(label) },
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
            colors = ExposedDropdownMenuDefaults.outlinedTextFieldColors(),
            modifier = modifier
        )
        
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            options.forEach { option ->
                DropdownMenuItem(
                    onClick = {
                        onValueChange(option)
                        expanded = false
                    }
                ) {
                    Text(option)
                }
            }
        }
    }
}

@Composable
fun NumericField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    placeholder: String = label,
    allowDecimals: Boolean = false,
    maxValue: Int? = null,
    modifier: Modifier = Modifier.fillMaxWidth(0.8f)
) {
    OutlinedTextField(
        value = value,
        onValueChange = { newValue ->
            val filteredText = if (allowDecimals) {
                newValue.filter { it.isDigit() || it == '.' }
                    .let { filtered ->
                        // Allow only one decimal point
                        if (filtered.count { it == '.' } <= 1) filtered else value
                    }
            } else {
                newValue.filter { it.isDigit() }
            }
            
            // Check max value if specified
            val finalText = if (maxValue != null && filteredText.isNotEmpty()) {
                try {
                    val numValue = if (allowDecimals) filteredText.toDouble() else filteredText.toInt().toDouble()
                    if (numValue <= maxValue.toDouble()) filteredText else value
                } catch (e: NumberFormatException) {
                    filteredText // Keep the filtered text if parsing fails
                }
            } else {
                filteredText
            }
            
            onValueChange(finalText)
        },
        label = { Text(label) },
        placeholder = { Text(placeholder) },
        keyboardOptions = KeyboardOptions(
            keyboardType = if (allowDecimals) KeyboardType.Number else KeyboardType.Number
        ),
        modifier = modifier,
        maxLines = 1
    )
}