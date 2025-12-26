package com.example.myappapp

import android.os.Bundle
import android.view.MenuItem // 1. 匯入 MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.example.myappapp.databinding.ActivityPurchaseHistoryBinding

class PurchaseHistoryActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPurchaseHistoryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPurchaseHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 設定 Toolbar
        setSupportActionBar(binding.toolbar)
        // 啟用返回箭頭
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // TODO: 未來在這裡加入判斷邏輯
        // 範例：假設使用者有買過票
        // val historyText = "當代藝術展 - 2025-12-20\n攝影大師回顧展 - 2025-12-25"
        // binding.textViewHistory.text = historyText
        // binding.textViewHistory.gravity = android.view.Gravity.START
    }

    // --- ↓↓↓ 我們修改了這裡的邏輯 ↓↓↓ ---
    // 2. 覆寫 onOptionsItemSelected 方法來處理所有選單項目的點擊
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // 判斷被點擊的是不是「返回」按鈕 (它的 ID 是 android.R.id.home)
        if (item.itemId == android.R.id.home) {
            finish() // 執行 finish() 來關閉當前頁面，這會自然地返回到上一頁 (ProfileActivity)
            return true // 表示我們已經處理了這個點擊事件
        }
        return super.onOptionsItemSelected(item) // 如果是其他按鈕，交給系統預設處理
    }
    // --- ↑↑↑ 修改完成 ↑↑↑ ---
}

    