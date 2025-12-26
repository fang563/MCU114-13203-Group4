package com.example.myappapp

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.myappapp.databinding.ActivityOrderCompleteBinding

class OrderCompleteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityOrderCompleteBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOrderCompleteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 您可以在這裡呼叫儲存資料的函式 (saveOrderData)

        binding.buttonFinish.setOnClickListener {
            // 回到活動列表頁 (Page 2)，並清除中間的頁面
            val intent = Intent(this, EventListActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
            finish()
        }
    }
}

