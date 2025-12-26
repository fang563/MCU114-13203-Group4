package com.example.myappapp

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * Event 資料類別 (Data Class)
 *
 * 這個類別是用來描述一個「活動」所需要的所有資訊。
 * - @Parcelize 和 : Parcelable 是為了讓這個物件可以在不同頁面 (Activity) 之間傳遞。
 */
@Parcelize
data class Event(
    // 圖片的資源 ID (例如 R.drawable.lake_light)
    val imageResId: Int,

    // 活動的名稱 (例如 "湖畔光影")
    val name: String,

    // 活動的開始日期 (格式："YYYY-MM-DD")
    val startDate: String,

    // 活動的結束日期 (格式："YYYY-MM-DD")
    val endDate: String,

) : Parcelable
