package com.centennialcollege.pizzaease.dao

import com.centennialcollege.pizzaease.model.OrderItem
import com.google.firebase.firestore.FirebaseFirestore


class OrderItemDao {

    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()
    private val orderItemsCollection = db.collection("orderItems")

    fun addOrderItem(orderItem: OrderItem) {
        orderItemsCollection.document(orderItem.id).set(orderItem)
    }

    fun updateOrderItem(orderItemId: String, orderItem: OrderItem) {
        orderItemsCollection.document(orderItemId).set(orderItem)
    }

    fun removeOrderItem(orderItemId: String) {
        orderItemsCollection.document(orderItemId).delete()
    }

    fun getOrderItem(orderItemId: String, callback: (OrderItem?) -> Unit) {
        orderItemsCollection.document(orderItemId).get()
            .addOnSuccessListener { documentSnapshot ->
                val orderItem = documentSnapshot.toObject(OrderItem::class.java)
                callback(orderItem)
            }
            .addOnFailureListener {
                callback(null)
            }
    }

    fun getAllOrderItems(callback: (List<OrderItem>) -> Unit) {
        orderItemsCollection.get()
            .addOnSuccessListener { querySnapshot ->
                val orderItemsList = mutableListOf<OrderItem>()
                for (documentSnapshot in querySnapshot.documents) {
                    val orderItem = documentSnapshot.toObject(OrderItem::class.java)
                    if (orderItem != null) {
                        orderItemsList.add(orderItem)
                    }
                }
                callback(orderItemsList)
            }
            .addOnFailureListener {
                callback(emptyList())
            }
    }
}