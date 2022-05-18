package com.junjange.myapplication.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.junjange.myapplication.R
import com.junjange.myapplication.data.ModelBoard
import com.junjange.myapplication.databinding.ItemRecyclerHotPollsBinding
import com.junjange.myapplication.databinding.ItemRecyclerPollsBinding
import com.junjange.myapplication.ui.view.VoteActivity

class HotPollsAdapter(val context: Context) : RecyclerView.Adapter<HotPollsAdapter.ViewHolder>()   {

    private var items: ModelBoard = ModelBoard(ArrayList())


    // 뷰 홀더 만들어서 반환
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemRecyclerHotPollsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    // 전달받은 위치의 아이템 연결
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.rankNumber(position)



    }

    // 뷰 홀더 설정
    inner class ViewHolder(private val binding: ItemRecyclerHotPollsBinding) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.hotPollCardView.setOnClickListener {
                val intent = Intent(context, VoteActivity::class.java) // 원하는 화면 연결
                // 데이터 전달
//                intent.putExtra("key", value)
                context.startActivity(intent) //액티비티 열기
            }

        }

        @SuppressLint("SetTextI18n")
        fun rankNumber(position: Int){
            binding.rank.text = "${position + 1}"
            when (position) {
                0 -> binding.rank.setBackgroundResource(R.drawable.layout_rank1)
                1 ->  binding.rank.setBackgroundResource(R.drawable.layout_rank2)
                2 -> binding.rank.setBackgroundResource(R.drawable.layout_rank3)
                else -> binding.rank.setBackgroundResource(R.drawable.layout_rank4)
            }
        }

    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    @SuppressLint("NotifyDataSetChanged")
    internal fun setData(newItems: ModelBoard) {

        this.items = newItems
        notifyDataSetChanged()

    }
    // 아이템 갯수
    override fun getItemCount() = items.board.size



}