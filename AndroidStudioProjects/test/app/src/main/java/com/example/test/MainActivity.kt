package com.example.test

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
// import androidx.compose.ui.semantics.text
import com.example.test.databinding.ActivityMainBinding // 1. 匯入 View Binding 類別

class MainActivity : AppCompatActivity() {

    // 2. 宣告一個 binding 變數，它會持有所有畫面的元件
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // 3. 初始化 binding，並設定畫面內容
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 這一步就取代了所有 txtShow = findViewById(...)、btnZero = findViewById(...) 的程式碼
        // 現在你可以直接用 binding.txtShow、binding.btnZero 來存取元件

        // 4. 定義一個共用的點擊監聽器 (OnClickListener)
        // 這對應了舊程式碼中的 private Button.OnClickListener myListner
        val numberClickListener = View.OnClickListener { view ->
            val button = view as? Button
            val currentText = binding.txtShow.text.toString() // 對應 String s = txtShow.getText().toString();
            // 這一段取代了舊程式碼中冗長的 switch-case 判斷
            // 它會直接拿被點擊按鈕上的文字來用，更簡潔
            binding.txtShow.text = currentText + button?.text.toString()
        }

        // 5. 將監聽器設定給所有數字按鈕
        // 這對應了舊程式碼中的 btnZero.setOnClickListener(myListner); 等等
        binding.btnZero.setOnClickListener(numberClickListener)
        binding.btnOne.setOnClickListener(numberClickListener)
        binding.btnTwo.setOnClickListener(numberClickListener)
        binding.btnThree.setOnClickListener(numberClickListener)
        binding.btnFour.setOnClickListener(numberClickListener)
        binding.btnFive.setOnClickListener(numberClickListener)
        binding.btnSix.setOnClickListener(numberClickListener)
        binding.btnSeven.setOnClickListener(numberClickListener)
        binding.btnEight.setOnClickListener(numberClickListener)
        binding.btnNine.setOnClickListener(numberClickListener)

        // 6. 為清除按鈕設定獨立的點擊事件
        // 這對應了 switch-case 中的 case R.id.btnClear
        binding.btnClear.setOnClickListener {
            binding.txtShow.text = "電話號碼：" // 對應 txtShow.setText("電話號碼：");
        }
    }
}
