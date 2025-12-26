package com.example.myappapp

import android.content.Context // 1. 匯入 Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.myappapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 設定「登入」按鈕的點擊事件
        binding.buttonLogin.setOnClickListener {
            val account = binding.editTextAccount.text.toString()
            val password = binding.editTextPassword.text.toString()

            if (account.isNotEmpty() && password.isNotEmpty()) {
                // --- ↓↓↓ 我們在這裡修改了驗證邏輯 ↓↓↓ ---

                // 2. 打開同一本 "UserData" 筆記本
                val sharedPref = getSharedPreferences("UserData", Context.MODE_PRIVATE)

                // 3. 嘗試從筆記本中讀取 "key" 為使用者輸入帳號的資料
                //    如果找不到，就返回 "null"
                val storedPassword = sharedPref.getString(account, null)

                // 4. 檢查讀出來的密碼是否和使用者輸入的密碼一致
                if (storedPassword != null && storedPassword == password) {
                    // 登入成功
                    Toast.makeText(this, "登入成功！", Toast.LENGTH_SHORT).show()

                    // TODO: 準備跳轉到下一個頁面 (P2)
                     val intent = Intent(this, EventListActivity::class.java)
                     startActivity(intent)

                } else {
                    // 登入失敗 (帳號不存在或密碼錯誤)
                    Toast.makeText(this, "帳號或密碼錯誤！", Toast.LENGTH_SHORT).show()
                }
                // --- ↑↑↑ 驗證邏輯修改完成 ↑↑↑ ---

            } else {
                // 欄位不得為空
                Toast.makeText(this, "帳號和密碼不能為空！", Toast.LENGTH_SHORT).show()
            }
        }

        // 設定「註冊」按鈕的點擊事件
        binding.buttonRegister.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
    }
}

