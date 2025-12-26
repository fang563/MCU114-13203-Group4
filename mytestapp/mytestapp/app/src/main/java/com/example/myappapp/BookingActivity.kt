package com.example.myappapp

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.myappapp.databinding.ActivityBookingBinding

class BookingActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBookingBinding

    // 票價
    private val priceFull = 280
    private val priceConcession = 180
    private val priceStudent = 120

    // 票數
    private var quantityFull = 0
    private var quantityConcession = 0
    private var quantityStudent = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBookingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 1. 接收並顯示 P3 傳來的資料
        val eventName = intent.getStringExtra("EXTRA_EVENT_NAME")
        val selectedDate = intent.getStringExtra("EXTRA_SELECTED_DATE")
        binding.textViewBookingEventName.text = eventName
        binding.textViewBookingDate.text = selectedDate

        // 2. 設定所有按鈕的點擊事件
        setupClickListeners()

        // 3. 初始更新一次票數顯示
        updateTicketQuantities()
    }

    private fun setupClickListeners() {
        // 返回按鈕
        binding.buttonBack.setOnClickListener {
            finish() // 關閉當前頁面，返回 P3
        }

        // 全票
        binding.buttonPlusFull.setOnClickListener {
            quantityFull++
            updateTicketQuantities()
        }
        binding.buttonMinusFull.setOnClickListener {
            if (quantityFull > 0) {
                quantityFull--
                updateTicketQuantities()
            }
        }

        // 優待票
        binding.buttonPlusConcession.setOnClickListener {
            quantityConcession++
            updateTicketQuantities()
        }
        binding.buttonMinusConcession.setOnClickListener {
            if (quantityConcession > 0) {
                quantityConcession--
                updateTicketQuantities()
            }
        }

        // 學生票
        binding.buttonPlusStudent.setOnClickListener {
            quantityStudent++
            updateTicketQuantities()
        }
        binding.buttonMinusStudent.setOnClickListener {
            if (quantityStudent > 0) {
                quantityStudent--
                updateTicketQuantities()
            }
        }

        // 確認按鈕
        // In BookingActivity.kt, inside setupClickListeners()

// 確認按鈕
        binding.buttonConfirm.setOnClickListener {    // 在點擊確認時，才在內部計算總金額
            val totalAmount = (quantityFull * priceFull) + (quantityConcession * priceConcession) + (quantityStudent * priceStudent)

            if (totalAmount > 0) {
                // 如果總金額大於0，就跳轉到 P5
                val intent = Intent(this, ConfirmationActivity::class.java)

                // --- ↓↓↓ 這是升級的重點：把所有需要的資料都打包進去 ↓↓↓ ---
                // 傳遞活動名稱和日期
                intent.putExtra("EXTRA_EVENT_NAME", binding.textViewBookingEventName.text.toString())
                intent.putExtra("EXTRA_SELECTED_DATE", binding.textViewBookingDate.text.toString())
                // 傳遞三種票的數量
                intent.putExtra("EXTRA_QTY_FULL", quantityFull)
                intent.putExtra("EXTRA_QTY_CONCESSION", quantityConcession)
                intent.putExtra("EXTRA_QTY_STUDENT", quantityStudent)
                // 傳遞總金額
                intent.putExtra("EXTRA_TOTAL_AMOUNT", totalAmount)
                // --- ↑↑↑ 打包完成 ↑↑↑ ---

                startActivity(intent)
            } else {
                // 如果總金額為0，提示使用者，不跳轉
                Toast.makeText(this, "請至少選擇一張票", Toast.LENGTH_SHORT).show()
            }
        }

    }

    // 這個函式只負責更新票數的數字顯示
    private fun updateTicketQuantities() {
        binding.textQuantityFull.text = quantityFull.toString()
        // --- ↓↓↓ 這裡是修改重點之二 ↓↓↓ ---
        binding.textQuantityConcession.text = quantityConcession.toString()
        binding.textQuantityStudent.text = quantityStudent.toString()
    }
}
