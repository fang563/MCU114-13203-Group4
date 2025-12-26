package com.example.myappapp

import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.myappapp.databinding.ActivityRegisterBinding

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 設定「完成註冊」按鈕的點擊事件
        binding.buttonCreateAccount.setOnClickListener {
            val newAccount = binding.editTextNewAccount.text.toString()
            val newPassword = binding.editTextNewPassword.text.toString()

            if (newAccount.isNotEmpty() && newPassword.isNotEmpty()) {
                // --- ↓↓↓ 我們在這裡加入了儲存邏輯 ↓↓↓ ---

                // 2. 取得 SharedPreferences 實體，就像打開一本名為 "UserData" 的筆記本
                val sharedPref = getSharedPreferences("UserData", Context.MODE_PRIVATE)

                // 3. 告訴筆記本我們要開始編輯了
                val editor = sharedPref.edit()

                // 4. 將帳號和密碼寫入筆記本
                //    用帳號作為 "key"，密碼作為 "value"
                editor.putString(newAccount, newPassword)

                // 5. 儲存變更，非常重要！
                editor.apply()

                // --- ↑↑↑ 儲存邏輯結束 ↑↑↑ ---

                Toast.makeText(this, "註冊成功！", Toast.LENGTH_SHORT).show()
                // 註冊成功後，結束此頁面，返回登入頁
                finish()
            } else {
                Toast.makeText(this, "帳號和密碼不能為空！", Toast.LENGTH_SHORT).show()
            }
        }

        // *** ↓↓↓ 我在這裡新增了「取消」按鈕的點擊事件 ↓↓↓ ***
        binding.buttonCancel.setOnClickListener {
            // 不做任何儲存或檢查，直接關閉當前的註冊頁面
            finish()
        }
        // *** ↑↑↑ 新增完成！ ↑↑↑ ***
    }
}


    