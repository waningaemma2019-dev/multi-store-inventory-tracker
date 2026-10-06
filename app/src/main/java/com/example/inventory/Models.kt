package com.example.inventory

data class Store(
    val id: String,
    val name: String
)

data class InventoryItem(
    val id: Long,
    val name: String,
    val category: String,
    val uom: String,
    val reorderLevel: Int
)

data class InventoryTransaction(
    val id: Long,
    val storeId: String,
    val itemId: Long,
    val date: String,
    val type: String,
    val qty: Int,
    val recipient: String,
    val ref: String,
    val remarks: String
)
