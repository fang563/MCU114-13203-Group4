package com.example.myappapp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myappapp.databinding.ListItemEventBinding

// *** ↓↓↓ 1. 修改 class 的宣告，讓它可以接收一個「點擊後要執行的動作」 ↓↓↓ ***
// 這個動作被定義為一個函式 (Function)，它會接收一個 Event 物件，並且不返回任何東西 (Unit)
class EventAdapter(
    private val events: List<Event>,
    private val onItemClicked: (Event) -> Unit
) : RecyclerView.Adapter<EventAdapter.EventViewHolder>() {
// *** ↑↑↑ 修改完成 ↑↑↑ ***

    class EventViewHolder(val binding: ListItemEventBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {
        val binding = ListItemEventBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return EventViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return events.size
    }

    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        val event = events[position]

        // --- 這部分的資料綁定程式碼保持不變 ---
        holder.binding.imageViewEvent.setImageResource(event.imageResId)
        holder.binding.textViewEventName.text = event.name
        holder.binding.textViewEventDate.text = "${event.startDate} ~ ${event.endDate}"

        // *** ↓↓↓ 2. 修改點擊事件的處理方式 ↓↓↓ ***
        // 當整個項目被點擊時
        holder.itemView.setOnClickListener {
            // 不再自己建立 Intent，而是直接呼叫從外部傳進來的 onItemClicked 函式，
            // 並把「被點擊的」這個 event 物件當作參數傳進去。
            onItemClicked(event)
        }
        // *** ↑↑↑ 修改完成 ↑↑↑ ***
    }
}
