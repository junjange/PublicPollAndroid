package com.junjange.myapplication.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.junjange.myapplication.data.ModelBoard
import com.junjange.myapplication.data.Polls
import com.junjange.myapplication.databinding.ItemRecyclerPollsBinding
import com.junjange.myapplication.ui.view.VoteActivity

class PollsAdapter(val context: Context) : RecyclerView.Adapter<PollsAdapter.ViewHolder>()  {

    private var items: Polls = Polls(ArrayList())


    // 뷰 홀더 만들어서 반환
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemRecyclerPollsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    // 전달받은 위치의 아이템 연결
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

    }

    // 뷰 홀더 설정
    inner class ViewHolder(private val binding: ItemRecyclerPollsBinding) : RecyclerView.ViewHolder(binding.root) {

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
    internal fun setData(newItems: Polls) {

        this.items = newItems
        notifyDataSetChanged()

    }
    // 아이템 갯수
    override fun getItemCount() = items.pollsItem.size

}