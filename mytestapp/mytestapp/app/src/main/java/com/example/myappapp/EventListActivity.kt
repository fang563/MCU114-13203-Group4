package com.example.myappapp

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myappapp.databinding.ActivityEventListBinding

// [關鍵] 我已經把所有跟 BottomSheet 相關的程式碼都刪掉了
class EventListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEventListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEventListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // *** ↓↓↓ [關鍵修正] 點擊右下角按鈕，直接跳轉到個人資料頁！ ↓↓↓ ***
        binding.fabProfile.setOnClickListener {
            val intent = Intent(this, ProfileActivity::class.java)
            startActivity(intent)
        }
        // *** ↑↑↑ 修正完成 ↑↑↑ ***

        // --- 以下是顯示畫展列表的程式碼 (這部分不變) ---
        val eventList = listOf(
            Event(imageResId = R.drawable.lake_light, name = "湖畔光影", startDate = "2026-02-20", endDate = "2026-02-28"),
            Event(imageResId = R.drawable.cherry_blossom, name = "盛開的櫻花樹", startDate = "2026-04-15", endDate = "2026-04-27"),
            Event(imageResId = R.drawable.city_scape, name = "城市地景", startDate = "2026-08-01", endDate = "2026-08-12"),
            Event(imageResId = R.drawable.sky, name = "天空", startDate = "2026-12-18", endDate = "2026-12-29")
        )

        val adapter = EventAdapter(eventList) { clickedEvent ->
            val intent = Intent(this, EventDetailActivity::class.java)
            intent.putExtra("EXTRA_EVENT_NAME", clickedEvent.name)
            intent.putExtra("EXTRA_START_DATE", clickedEvent.startDate)
            intent.putExtra("EXTRA_END_DATE", clickedEvent.endDate)
            startActivity(intent)
        }

        binding.recyclerViewEvents.adapter = adapter
        binding.recyclerViewEvents.layoutManager = LinearLayoutManager(this)
    }
}

