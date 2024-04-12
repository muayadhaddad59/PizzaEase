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
// Inside OrderScreen composable
@Composable
fun OrderScreen(navController: NavController, foodName: String, foodPrice: String, foodSize: String) {
    // Declare a variable to track whether the order has been successfully placed
    var orderPlaced by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Customize", fontWeight = FontWeight.Bold)
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
                text = "Customize Your Order",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 16.dp)
            )
            Text(
                text = "Food Name: $foodName",
                fontSize = 16.sp,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            Text(
                text = "Size: $foodSize",
                fontSize = 16.sp,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            Text(
                text = "Price: $foodPrice",
                fontSize = 16.sp,
                modifier = Modifier.padding(bottom = 16.dp)
            )
            Button(
                onClick = {
                    // Call function to send order to Firestore
                    sendOrderToFirestore(foodName, foodPrice, foodSize)
                    // Set orderPlaced to true to indicate that the order has been successfully placed
                    orderPlaced = true
                },
                modifier = Modifier
                    .widthIn(max = 200.dp)
                    .height(50.dp),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text(text = "Place Order", fontWeight = FontWeight.Bold)
            }
            // Display a message when the order has been successfully placed
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


fun sendOrderToFirestore(foodName: String, foodPrice: String, foodSize: String) {
    val db = FirebaseFirestore.getInstance()
    val orderDetails = hashMapOf(
        "foodName" to foodName,
        "foodPrice" to foodPrice,
        "foodSize" to foodSize
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