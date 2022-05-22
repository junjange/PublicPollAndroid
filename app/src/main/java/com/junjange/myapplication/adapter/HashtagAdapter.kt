package com.junjange.myapplication.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.junjange.myapplication.data.Hashtag
import com.junjange.myapplication.data.Polls
import com.junjange.myapplication.databinding.ItemRecyclerHashtagBinding
import com.junjange.myapplication.ui.view.VoteActivity

class HashtagAdapter (val context: Context) : RecyclerView.Adapter<HashtagAdapter.ViewHolder>()  {

    private var items: Hashtag? = null

    // 뷰 홀더 만들어서 반환
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemRecyclerHashtagBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    // 전달받은 위치의 아이템 연결
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

    }

    // 뷰 홀더 설정
    inner class ViewHolder(private val binding: ItemRecyclerHashtagBinding) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.pollCardView.setOnClickListener {
                val intent = Intent(context, VoteActivity::class.java) // 원하는 화면 연결
                // 데이터 전달
//                intent.putExtra("key", value)
                context.startActivity(intent) //액티비티 열기

            }
        }

    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    @SuppressLint("NotifyDataSetChanged")
    internal fun setData(newItems: Hashtag) {

        this.items = newItems
        notifyDataSetChanged()

    }

    // 아이템 갯수
    override fun getItemCount(): Int{
        return if(items == null) 0 else items!!.hashtagItem.polls.size

    }

}