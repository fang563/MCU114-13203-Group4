package com.example.myappapp

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.myappapp.databinding.ActivityEventDetailBinding
// [關鍵] 引入傳統的日期處理工具
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class EventDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEventDetailBinding
    private var selectedDate: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEventDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 1. 接收資料 (這部分不變)
        val eventName = intent.getStringExtra("EXTRA_EVENT_NAME")
        val startDateStr = intent.getStringExtra("EXTRA_START_DATE")
        val endDateStr = intent.getStringExtra("EXTRA_END_DATE")

        // 2. 顯示畫展名稱和圖片 (這部分不變)
        binding.textViewEventName.text = eventName ?: "活動名稱載入失敗"
        val imageResId = when (eventName) {
            "湖畔光影" -> R.drawable.lake_light
            "盛開的櫻花樹" -> R.drawable.cherry_blossom
            "城市地景" -> R.drawable.city_scape
            "天空" -> R.drawable.sky
            else -> R.drawable.ic_launcher_background
        }
        binding.imageViewEvent.setImageResource(imageResId)

        // 使用新的 getDatesBetween 函式
        if (startDateStr != null && endDateStr != null) {
            val dates = getDatesBetween(startDateStr, endDateStr)
            val dateAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, dates)
            binding.gridViewDates.adapter = dateAdapter

            binding.gridViewDates.setOnItemClickListener { parent, view, position, id ->
                selectedDate = parent.getItemAtPosition(position) as String
                Toast.makeText(this, "您已選擇日期: $selectedDate", Toast.LENGTH_SHORT).show()
            }
        }

        // 3. 設定「立即訂票」按鈕的點擊事件 (這部分不變)
        binding.buttonBookNow.setOnClickListener {
            if (selectedDate == null) {
                Toast.makeText(this, "請先選擇一個日期！", Toast.LENGTH_SHORT).show()
            } else {
                val intent = Intent(this, BookingActivity::class.java)
                intent.putExtra("EXTRA_EVENT_NAME", eventName)
                intent.putExtra("EXTRA_SELECTED_DATE", selectedDate)
                startActivity(intent)
            }
        }

        // 4. 設定「上一頁」按鈕的點擊事件 (這部分不變)
        binding.buttonGoBack.setOnClickListener {
            finish()
        }
    }

    // *** ↓↓↓ [關鍵修正] 使用最穩定的 Calendar 來重寫日期產生函式 ↓↓↓ ***
    private fun getDatesBetween(startDateStr: String, endDateStr: String): List<String> {
        val dates = mutableListOf<String>()
        // 定義日期格式
        val formatter = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

        try {
            // 將字串解析為 Date 物件
            val startDate = formatter.parse(startDateStr)
            val endDate = formatter.parse(endDateStr)

            // 建立一個 Calendar 實體，並設定為開始日期
            val calendar = Calendar.getInstance()
            if (startDate != null) {
                calendar.time = startDate
            }

            // 當 calendar 的日期還沒超過結束日期時，持續迴圈
            while (calendar.time.before(endDate) || calendar.time.equals(endDate)) {
                // 從 Calendar 中取得日期的 "日"
                val dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH)
                dates.add(dayOfMonth.toString())

                // 將 calendar 的日期加一天
                calendar.add(Calendar.DAY_OF_MONTH, 1)
            }
        } catch (e: Exception) {
            // 如果日期格式錯誤，印出錯誤訊息，避免閃退
            e.printStackTrace()
            dates.add("日期錯誤")
        }
        return dates
    }
    // *** ↑↑↑ 修正完成 ↑↑↑ ***
}

