package com.example.myappapp

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.myappapp.databinding.ActivityFinalPageBinding

// P6 現在只負責顯示成功畫面和處理UI互動
class FinalPageActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFinalPageBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFinalPageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 1. [已修改] 直接設定文字和顏色，不再呼叫 setupWebLink()
        binding.textViewEmailInfo.text = "已回傳email給您請查收"
        binding.textViewEmailInfo.setTextColor(Color.BLUE) // 顏色保留為藍色
        // 確保沒有底線
        binding.textViewEmailInfo.paint.isUnderlineText = false


        // 2. 設定「結束」按鈕的點擊事件
        binding.buttonFinish.setOnClickListener {
            // 建立跳轉到 P2 (EventListActivity) 的意圖
            val intent = Intent(this, EventListActivity::class.java)
            // 設定 flag，清除 P2 之上所有頁面(P3, P4, P5, P6)，讓 P2 成為頂層
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP)
            startActivity(intent)
            finish() // 關閉自己
        }
    }

    // [已刪除] setupWebLink() 整個函式已被移除
}




