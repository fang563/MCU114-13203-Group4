package com.example.myappapp

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.myappapp.databinding.ActivityProfileBinding

class ProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 1. 設定「購票紀錄」按鈕的點擊事件
        // 對應 XML 裡的 android:id="@+id/buttonPurchaseHistory"
        binding.buttonPurchaseHistory.setOnClickListener {
            // 跳轉到購票紀錄頁面 (PurchaseHistoryActivity)
            val intent = Intent(this, PurchaseHistoryActivity::class.java)
            startActivity(intent)
        }

        // 2. 設定左下角「房子(Home)」按鈕的點擊事件
        // 對應 XML 裡的 android:id="@+id/fabHome"
        binding.fabHome.setOnClickListener {
            // 結束目前的 Profile 頁面，這樣就會自動回到上一頁 (通常是活動列表頁)
            finish()
        }

        // 3. 設定右下角「個人資料(Profile)」按鈕的點擊事件
        // 對應 XML 裡的 android:id="@+id/fabProfile"
        binding.fabProfile.setOnClickListener {
            // 因為使用者已經在個人資料頁了，所以點這個按鈕不需要做任何事
            // 或者您可以顯示一個提示訊息
            // Toast.makeText(this, "您已經在個人資料頁面了", Toast.LENGTH_SHORT).show()
        }
    }
}



