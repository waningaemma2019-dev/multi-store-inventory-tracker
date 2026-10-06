package com.example.inventory

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class InventoryDbHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        const val DATABASE_NAME = "inventory_tracker.db"
        const val DATABASE_VERSION = 1

        const val TABLE_ITEMS = "items"
        const val TABLE_TRANSACTIONS = "transactions"

        const val COL_ID = "id"
        const val COL_NAME = "name"
        const val COL_CATEGORY = "category"
        const val COL_UOM = "uom"
        const val COL_REORDER_LEVEL = "reorder_level"
        const val COL_STORE_ID = "store_id"
        const val COL_ITEM_ID = "item_id"
        const val COL_DATE = "date"
        const val COL_TYPE = "type"
        const val COL_QTY = "qty"
        const val COL_RECIPIENT = "recipient"
        const val COL_REF = "ref"
        const val COL_REMARKS = "remarks"
    }

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(
            "CREATE TABLE $TABLE_ITEMS (" +
                "$COL_ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "$COL_NAME TEXT, " +
                "$COL_CATEGORY TEXT, " +
                "$COL_UOM TEXT, " +
                "$COL_REORDER_LEVEL INTEGER)"
        )

        db.execSQL(
            "CREATE TABLE $TABLE_TRANSACTIONS (" +
                "$COL_ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "$COL_STORE_ID TEXT, " +
                "$COL_ITEM_ID INTEGER, " +
                "$COL_DATE TEXT, " +
                "$COL_TYPE TEXT, " +
                "$COL_QTY INTEGER, " +
                "$COL_RECIPIENT TEXT, " +
                "$COL_REF TEXT, " +
                "$COL_REMARKS TEXT)"
        )

        seedData(db)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_TRANSACTIONS")
        db.execSQL("DROP TABLE IF EXISTS $TABLE_ITEMS")
        onCreate(db)
    }

    fun getItems(): List<InventoryItem> {
        val items = mutableListOf<InventoryItem>()
        val db = readableDatabase
        val cursor = db.query(TABLE_ITEMS, null, null, null, null, null, "$COL_NAME ASC")

        while (cursor.moveToNext()) {
            items.add(
                InventoryItem(
                    id = cursor.getLong(cursor.getColumnIndexOrThrow(COL_ID)),
                    name = cursor.getString(cursor.getColumnIndexOrThrow(COL_NAME)),
                    category = cursor.getString(cursor.getColumnIndexOrThrow(COL_CATEGORY)),
                    uom = cursor.getString(cursor.getColumnIndexOrThrow(COL_UOM)),
                    reorderLevel = cursor.getInt(cursor.getColumnIndexOrThrow(COL_REORDER_LEVEL))
                )
            )
        }
        cursor.close()
        return items
    }

    fun getTransactions(): List<InventoryTransaction> {
        val transactions = mutableListOf<InventoryTransaction>()
        val db = readableDatabase
        val cursor = db.query(TABLE_TRANSACTIONS, null, null, null, null, null, "$COL_DATE ASC")

        while (cursor.moveToNext()) {
            transactions.add(
                InventoryTransaction(
                    id = cursor.getLong(cursor.getColumnIndexOrThrow(COL_ID)),
                    storeId = cursor.getString(cursor.getColumnIndexOrThrow(COL_STORE_ID)),
                    itemId = cursor.getLong(cursor.getColumnIndexOrThrow(COL_ITEM_ID)),
                    date = cursor.getString(cursor.getColumnIndexOrThrow(COL_DATE)),
                    type = cursor.getString(cursor.getColumnIndexOrThrow(COL_TYPE)),
                    qty = cursor.getInt(cursor.getColumnIndexOrThrow(COL_QTY)),
                    recipient = cursor.getString(cursor.getColumnIndexOrThrow(COL_RECIPIENT)),
                    ref = cursor.getString(cursor.getColumnIndexOrThrow(COL_REF)),
                    remarks = cursor.getString(cursor.getColumnIndexOrThrow(COL_REMARKS))
                )
            )
        }
        cursor.close()
        return transactions
    }

    fun addItem(item: InventoryItem): Long {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COL_NAME, item.name)
            put(COL_CATEGORY, item.category)
            put(COL_UOM, item.uom)
            put(COL_REORDER_LEVEL, item.reorderLevel)
        }
        return db.insert(TABLE_ITEMS, null, values)
    }

    fun addTransaction(transaction: InventoryTransaction): Long {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COL_STORE_ID, transaction.storeId)
            put(COL_ITEM_ID, transaction.itemId)
            put(COL_DATE, transaction.date)
            put(COL_TYPE, transaction.type)
            put(COL_QTY, transaction.qty)
            put(COL_RECIPIENT, transaction.recipient)
            put(COL_REF, transaction.ref)
            put(COL_REMARKS, transaction.remarks)
        }
        return db.insert(TABLE_TRANSACTIONS, null, values)
    }

    private fun seedData(db: SQLiteDatabase) {
        val items = listOf(
            InventoryItem(1, "BEB", "Alcoholic", "Curton", 10),
            InventoryItem(2, "Ballpoint Pens (Blue - Box)", "Stationery", "Box", 5),
            InventoryItem(3, "Hardcover Register Book (2 Quire)", "Stationery", "Piece", 3),
            InventoryItem(4, "Liquid Hand Wash Soap (5L)", "Cleaning Supplies", "Jerrican", 2),
            InventoryItem(5, "Fluorescent Tubes (4ft 40W)", "Electrical & Maintenance", "Piece", 5),
            InventoryItem(6, "A4 Printing Paper (70gsm)", "Stationery", "Ream", 10)
        )

        items.forEach { item ->
            val values = ContentValues().apply {
                put(COL_ID, item.id)
                put(COL_NAME, item.name)
                put(COL_CATEGORY, item.category)
                put(COL_UOM, item.uom)
                put(COL_REORDER_LEVEL, item.reorderLevel)
            }
            db.insertWithOnConflict(TABLE_ITEMS, null, values, SQLiteDatabase.CONFLICT_REPLACE)
        }

        val calendar = Calendar.getInstance()
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.US)

        val txns = listOf(
            InventoryTransaction(1, "nabulya", 6, dateFormat.format(calendar.time), "RECEIVED", 50, "Central Stores", "GRN-001", "Initial Stock In"),
            InventoryTransaction(2, "nabulya", 2, dateFormat.format(calendar.time), "RECEIVED", 20, "Central Stores", "GRN-002", "Initial Stock In"),
            InventoryTransaction(3, "wesonga", 6, dateFormat.format(calendar.time), "RECEIVED", 20, "Transferred from Nabulya", "TRF-01", "Store Transfer"),
            InventoryTransaction(4, "nabulya", 6, dateFormat.format(calendar.time), "ISSUED", 15, "Administration", "SRN-101", "Issued for Examinations"),
            InventoryTransaction(5, "wesonga", 6, dateFormat.format(calendar.time), "ISSUED", 8, "Library Office", "SRN-201", "Issued for Printing"),
            InventoryTransaction(6, "wesonga", 5, dateFormat.format(calendar.time), "RECEIVED", 12, "Electrical Store", "GRN-005", "Maintenance Stock"),
            InventoryTransaction(7, "nabulya", 3, dateFormat.format(calendar.time), "RECEIVED", 10, "Supplier Delivery", "GRN-003", "Purchased Stock"),
            InventoryTransaction(8, "nabulya", 4, dateFormat.format(calendar.time), "RECEIVED", 8, "Cleaning Staff", "GRN-004", "Monthly Supply")
        )

        txns.forEach { txn ->
            val values = ContentValues().apply {
                put(COL_ID, txn.id)
                put(COL_STORE_ID, txn.storeId)
                put(COL_ITEM_ID, txn.itemId)
                put(COL_DATE, txn.date)
                put(COL_TYPE, txn.type)
                put(COL_QTY, txn.qty)
                put(COL_RECIPIENT, txn.recipient)
                put(COL_REF, txn.ref)
                put(COL_REMARKS, txn.remarks)
            }
            db.insertWithOnConflict(TABLE_TRANSACTIONS, null, values, SQLiteDatabase.CONFLICT_REPLACE)
        }
    }
}
