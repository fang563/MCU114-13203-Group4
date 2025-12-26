package com.example.myappapp

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.myappapp.databinding.ActivityConfirmationBinding

class ConfirmationActivity : AppCompatActivity() {

    private lateinit var binding: ActivityConfirmationBinding

    // 假設票價 (您可以根據實際需求修改)
    private val priceFull = 280
    private val priceConcession = 180
    private val priceStudent = 120

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityConfirmationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 1. 接收上一頁 (Page 4) 傳來的資料
        val eventName = intent.getStringExtra("EXTRA_EVENT_NAME") ?: "未知活動"
        val selectedDate = intent.getStringExtra("EXTRA_SELECTED_DATE") ?: "未知日期"
        val qtyFull = intent.getIntExtra("EXTRA_QTY_FULL", 0)
        val qtyConcession = intent.getIntExtra("EXTRA_QTY_CONCESSION", 0)
        val qtyStudent = intent.getIntExtra("EXTRA_QTY_STUDENT", 0)

        // 2. 計算總金額
        val totalAmount = (qtyFull * priceFull) + (qtyConcession * priceConcession) + (qtyStudent * priceStudent)

        // 3. 將資料顯示在畫面上 (對應 XML 的 ID)
        binding.textViewEventName.text = eventName
        binding.textViewDate.text = selectedDate
        binding.textViewFullTicketInfo.text = "全票 x $qtyFull 張"
        binding.textViewConcessionTicketInfo.text = "優待票 x $qtyConcession 張"
        binding.textViewStudentTicketInfo.text = "學生票 x $qtyStudent 張"
        binding.textViewTotalAmount.text = "總價：$$totalAmount 元"

        // 返回按鈕
        binding.buttonBack.setOnClickListener { finish() }

        // 取消按鈕 (回到上一頁)
        binding.buttonCancel.setOnClickListener { finish() }

        // 4. 立即提交按鈕 -> 彈出確認視窗
        binding.buttonSubmit.setOnClickListener {
            showConfirmDialog(eventName, selectedDate, qtyFull, qtyConcession, qtyStudent, totalAmount)
        }
    }

    // 顯示「確認購買」的小視窗
    // 顯示「確認購買」的小視窗
    // 顯示「確認購買」的小視窗
    private fun showConfirmDialog(
        eventName: String,
        date: String,
        qFull: Int,
        qConc: Int,
        qStud: Int,
        total: Int
    ) {
        AlertDialog.Builder(this)
            .setTitle("確認購買")
            .setMessage("是否已經確認好購買張數？")
            .setNegativeButton("否") { dialog, _ ->
                // 按否，關閉視窗，留在原地
                dialog.dismiss()
            }
            // [關鍵修正] 按 "是" 跳到您指定的 FinalPageActivity
            .setPositiveButton("是") { _, _ ->
                val intent = Intent(this, FinalPageActivity::class.java)

                // 為了讓 FinalPageActivity 也能儲存或使用，我們把資料一樣傳過去
                intent.putExtra("EXTRA_EVENT_NAME", eventName)
                intent.putExtra("EXTRA_SELECTED_DATE", date)
                intent.putExtra("EXTRA_QTY_FULL", qFull)
                intent.putExtra("EXTRA_QTY_CONCESSION", qConc)
                intent.putExtra("EXTRA_QTY_STUDENT", qStud)
                intent.putExtra("EXTRA_TOTAL_AMOUNT", total)

                startActivity(intent)
            }
            .show()
    }


}




