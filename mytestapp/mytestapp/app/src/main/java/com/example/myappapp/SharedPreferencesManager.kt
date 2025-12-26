package com.example.myappapp

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

object SharedPreferencesManager {

    private const val PREFS_NAME = "MyAppPrefs"
    private const val KEY_PURCHASE_HISTORY = "purchase_history"
    private val gson = Gson()

    // 儲存一筆新的訂單紀錄
    fun addPurchaseRecord(context: Context, record: PurchaseRecord) {
        val allRecords = getPurchaseHistory(context).toMutableList()
        allRecords.add(0, record) // 將最新的紀錄加到最前面
        savePurchaseHistory(context, allRecords)
    }

    // 讀取所有的訂單紀錄
    fun getPurchaseHistory(context: Context): List<PurchaseRecord> {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val json = prefs.getString(KEY_PURCHASE_HISTORY, null)
        if (json != null) {
            val type = object : TypeToken<List<PurchaseRecord>>() {}.type
            return gson.fromJson(json, type)
        }
        return emptyList() // 如果沒有紀錄，返回空列表
    }

    // 將整個紀錄列表儲存起來
    private fun savePurchaseHistory(context: Context, records: List<PurchaseRecord>) {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val json = gson.toJson(records)
        prefs.edit().putString(KEY_PURCHASE_HISTORY, json).apply()
    }
}
