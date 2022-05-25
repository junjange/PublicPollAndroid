package com.junjange.myapplication.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.junjange.myapplication.R
import com.junjange.myapplication.data.*
import com.junjange.myapplication.databinding.ItemRecyclerHotPollsBinding
import com.junjange.myapplication.ui.view.VoteActivity

class HotPollsAdapter(val context: Context) : RecyclerView.Adapter<HotPollsAdapter.ViewHolder>()   {

    private var items: HotPolls = HotPolls(ArrayList())


    // 뷰 홀더 만들어서 반환
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemRecyclerHotPollsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    // 전달받은 위치의 아이템 연결
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.rankNumber(position)

        holder.clickItem(items.hotPollsItem[0].polls[position])
        holder.setItem(items.hotPollsItem[0].polls[position])
    }

    // 뷰 홀더 설정
    inner class ViewHolder(private val binding: ItemRecyclerHotPollsBinding) : RecyclerView.ViewHolder(binding.root) {


        fun clickItem(item: HotPollsComponent){
            binding.hotPollCardView.setOnClickListener {

                // 원하는 화면 연결
                Intent(context, VoteActivity::class.java).apply {
                    // 데이터 전달
                    putExtra("id", item.id)
                    putExtra("presentImagePath", item.presentImagePath)
                    addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                }.run {
                    //액티비티 열기
                    context.startActivity(this)
                }
            }

        }

        fun setItem(item: HotPollsComponent){
            binding.title.text =  item.contents
        }


        // 랭킹에 따라 이미지 변경
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
    internal fun setData(newItems: HotPolls) {

        this.items = newItems
        notifyDataSetChanged()

    }
    // 아이템 개수
    override fun getItemCount(): Int {
        return if (items.hotPollsItem.isEmpty()) 0 else items.hotPollsItem[0].polls.size
    }


}