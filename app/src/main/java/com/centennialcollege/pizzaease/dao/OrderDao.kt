package com.centennialcollege.pizzaease.dao
import com.centennialcollege.pizzaease.model.Order
import com.google.firebase.firestore.FirebaseFirestore

class OrderDao {

    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()
    private val ordersCollection = db.collection("orders")

    fun addOrder(order: Order) {
        ordersCollection.add(order)
    }

    fun updateOrder(orderId: String, order: Order) {
        ordersCollection.document(orderId).set(order)
    }

    fun removeOrder(orderId: String) {
        ordersCollection.document(orderId).delete()
    }

    fun getOrder(orderId: String, callback: (Order?) -> Unit) {
        ordersCollection.document(orderId).get()
            .addOnSuccessListener { documentSnapshot ->
                val order = documentSnapshot.toObject(Order::class.java)
                callback(order)
            }
            .addOnFailureListener {
                callback(null)
            }
    }

    fun getAllOrders(callback: (List<Order>) -> Unit) {
        ordersCollection.get()
            .addOnSuccessListener { querySnapshot ->
                val ordersList = mutableListOf<Order>()
                for (documentSnapshot in querySnapshot.documents) {
                    val order = documentSnapshot.toObject(Order::class.java)
                    if (order != null) {
                        ordersList.add(order)
                    }
                }
                callback(ordersList)
            }
            .addOnFailureListener {
                callback(emptyList())
            }
    }
}
