package com.centennialcollege.pizzaease.ui.screens
import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.twotone.ArrowBack
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.google.firebase.firestore.FirebaseFirestore

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OrderScreen(navController: NavController, foodName: String, foodPrice: String, foodSize: String) {
    var customerName by remember { mutableStateOf("") }
    var phoneNumber by remember { mutableStateOf("") }
    var address by remember { mutableStateOf("") }

    var isCustomerNameValid by remember { mutableStateOf(true) }
    var isPhoneNumberValid by remember { mutableStateOf(true) }
    var isAddressValid by remember { mutableStateOf(true) }

    var orderPlaced by remember { mutableStateOf(false) }
    var showDialog by remember { mutableStateOf(false) }
    var showEmptyFieldsAlert by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Order Details", fontWeight = FontWeight.Bold)
                },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            navController.popBackStack()
                        }
                    ) {
                        Icon(
                            imageVector = Icons.TwoTone.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                }
            )
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Pizza Name: $foodName",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            Text(
                text = "Size: $foodSize",
                fontSize = 16.sp,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            Text(
                text = "Price: $$foodPrice",
                fontSize = 16.sp,
                modifier = Modifier.padding(bottom = 12.dp)
            )

            OutlinedTextField(
                value = customerName,
                onValueChange = {
                    customerName = it
                    isCustomerNameValid = it.isNotBlank()
                },
                label = { Text("Customer Name") },
                isError = !isCustomerNameValid,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp)
            )
            OutlinedTextField(
                value = phoneNumber,
                onValueChange = {
                    phoneNumber = it
                    isPhoneNumberValid = it.isNotBlank()
                },
                label = { Text("Phone Number") },
                isError = !isPhoneNumberValid,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp)
            )
            OutlinedTextField(
                value = address,
                onValueChange = {
                    address = it
                    isAddressValid = it.isNotBlank()
                },
                label = { Text("Address") },
                isError = !isAddressValid,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp)
            )

            Button(
                onClick = {
                    if (customerName.isNotBlank() && phoneNumber.isNotBlank() && address.isNotBlank()) {
                        showDialog = true
                    } else {
                        // Show alert if any of the fields are empty
                        showDialog = false // Close any existing dialogs
                        showEmptyFieldsAlert = true
                    }
                },
                modifier = Modifier
                    .widthIn(max = 200.dp)
                    .height(50.dp)
                    .padding(top = 16.dp),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text(text = "Place Order", fontWeight = FontWeight.Bold)
            }

            if (orderPlaced) {
                Column(
                    modifier = Modifier.padding(top = 8.dp)
                ) {
                    Text(
                        text = "Your order has been successfully placed!",
                        fontSize = 16.sp,
                        color = Color.Green,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                    Text(
                        text = "Pizza Ordered: $foodName",
                        fontSize = 16.sp,
                        modifier = Modifier.padding(bottom = 4.dp)
                    )
                    Text(
                        text = "Size: $foodSize",
                        fontSize = 16.sp,
                        modifier = Modifier.padding(bottom = 4.dp)
                    )
                    Text(
                        text = "Price: $$foodPrice",
                        fontSize = 16.sp,
                        modifier = Modifier.padding(bottom = 4.dp)
                    )
                    Text(
                        text = "Customer Name: $customerName",
                        fontSize = 16.sp,
                        modifier = Modifier.padding(bottom = 4.dp)
                    )
                    Text(
                        text = "Phone Number: $phoneNumber",
                        fontSize = 16.sp,
                        modifier = Modifier.padding(bottom = 4.dp)
                    )
                    Text(
                        text = "Address: $address",
                        fontSize = 16.sp,
                        modifier = Modifier.padding(bottom = 4.dp)
                    )
                }
            }

        }
    }

    // Alert dialog for empty fields
    if (showEmptyFieldsAlert) {
        AlertDialog(
            onDismissRequest = { showEmptyFieldsAlert = false },
            title = { Text("Error") },
            text = { Text("Please fill in all required fields.") },
            confirmButton = {
                Button(
                    onClick = { showEmptyFieldsAlert = false }
                ) {
                    Text("OK")
                }
            }
        )
    }

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text("Confirmation") },
            text = { Text("Are you sure you want to place the order?") },
            confirmButton = {
                Button(
                    onClick = {
                        //store in firestore db
                        sendOrderToFirestore(
                            foodName = foodName,
                            foodPrice = foodPrice,
                            foodSize = foodSize,
                            customerName = customerName,
                            phoneNumber = phoneNumber,
                            address = address
                        )
                        orderPlaced = true
                        showDialog = false
                        // Clear text fields after confirming order
                        customerName = ""
                        phoneNumber = ""
                        address = ""
                    }
                ) {
                    Text("Confirm")
                }
            },
            dismissButton = {
                Button(
                    onClick = { showDialog = false }
                ) {
                    Text("Cancel")
                }
            }
        )
    }
}

fun sendOrderToFirestore(
    foodName: String,
    foodPrice: String,
    foodSize: String,
    customerName: String,
    phoneNumber: String,
    address: String
) {
    val db = FirebaseFirestore.getInstance()
    val orderDetails = hashMapOf(
        "foodName" to foodName,
        "foodPrice" to foodPrice,
        "foodSize" to foodSize,
        "customerName" to customerName,
        "phoneNumber" to phoneNumber,
        "address" to address
    )

    db.collection("orders")
        .add(orderDetails)
        .addOnSuccessListener { documentReference ->
            println("DocumentSnapshot added with ID: ${documentReference.id}")
        }
        .addOnFailureListener { e ->
            println("Error adding document: $e")
        }
}
