package com.centennialcollege.pizzaease.ui.screens
import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

import com.google.firebase.firestore.FirebaseFirestore
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OrderScreen(
    navController: NavController,
    foodName: String,
    foodPrice: String,
    foodSize: String
) {
    var customerName by remember { mutableStateOf("") }
    var phoneNumber by remember { mutableStateOf("") }
    var address by remember { mutableStateOf("") }

    var orderPlaced by remember { mutableStateOf(false) }

    var isCustomerNameValid by remember { mutableStateOf(true) }
    var isPhoneNumberValid by remember { mutableStateOf(true) }
    var isAddressValid by remember { mutableStateOf(true) }

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
                            imageVector = Icons.Default.ArrowBack,
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
                text = "Food Name: $foodName",
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
                modifier = Modifier.padding(bottom = 16.dp)
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

            // Button to place order
            Button(
                onClick = {
                    if (isCustomerNameValid && isPhoneNumberValid && isAddressValid) {
                        sendOrderToFirestore(
                            foodName = foodName,
                            foodPrice = foodPrice,
                            foodSize = foodSize,
                            customerName = customerName,
                            phoneNumber = phoneNumber,
                            address = address
                        )
                        orderPlaced = true
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
                Text(
                    text = "Your order has been successfully placed!",
                    fontSize = 16.sp,
                    color = Color.Green,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }
        }
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
