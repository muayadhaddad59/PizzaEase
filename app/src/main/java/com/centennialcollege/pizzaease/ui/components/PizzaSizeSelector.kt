package com.centennialcollege.pizzaease.ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.centennialcollege.pizzaease.model.PizzaSize

//horizontal row of buttons for selecting pizza size in the foodscreen
@Composable
fun PizzaSizeSelector(
    sizes: List<PizzaSize>, // List of available pizza sizes
    onSizeSelected: (PizzaSize) -> Unit, // Callback function when a size is selected
    selectedSize: PizzaSize // Currently selected pizza size
) {
    Row {
        sizes.forEach { size ->
            val isSelected = size == selectedSize // Check if the size is currently selected
            val backgroundColor = if (isSelected) MaterialTheme.colorScheme.primary else Color.White // Background color based on selection
            val contentColor = if (isSelected) Color.White else MaterialTheme.colorScheme.primary // Text color based on selection
            // Click listener to select the size
            Button(
                onClick = { onSizeSelected(size) },
                colors = ButtonDefaults.buttonColors(
                    containerColor = backgroundColor,
                    contentColor = contentColor
                )
            ) {
                Text(text = "${size.name} (${size.size})", style = MaterialTheme.typography.bodySmall)
            }
            Spacer(modifier = Modifier.width(4.dp))
        }
    }
}

@Preview
@Composable
fun PreviewPizzaSizeSelector() {
    var selectedSize by remember { mutableStateOf(PizzaSize.Medium) }
    PizzaSizeSelector(
        sizes = PizzaSize.values().toList(),
        onSizeSelected = { size ->
            selectedSize = size
        },
        selectedSize = selectedSize
    )
}