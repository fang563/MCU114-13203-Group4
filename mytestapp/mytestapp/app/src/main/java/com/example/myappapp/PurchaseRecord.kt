package com.example.myappapp

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class PurchaseRecord(
    val eventName: String,
    val selectedDate: String,
    val qtyFull: Int,
    val qtyConcession: Int,
    val qtyStudent: Int,
    val totalAmount: Int,
    val purchaseTimestamp: Long // 購買當下的時間戳，用於排序
) : Parcelable
