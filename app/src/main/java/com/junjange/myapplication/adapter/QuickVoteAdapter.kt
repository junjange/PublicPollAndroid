package com.junjange.myapplication.adapter

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.junjange.myapplication.data.ModelBoardComponent
import com.junjange.myapplication.data.QuickPolls
import com.junjange.myapplication.data.QuickPollsItem
import com.junjange.myapplication.databinding.ItemRecyclerQuickVoteBinding


class QuickVoteAdapter : RecyclerView.Adapter<QuickVoteAdapter.ViewHolder>() {

    private var items: QuickPolls = QuickPolls(ArrayList())


    // 뷰 홀더 만들어서 반환
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemRecyclerQuickVoteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    // 전달받은 위치의 아이템 연결
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.setItem(items.quickPollsItem[position])
        holder.checkItem(items.quickPollsItem[position])


    }

    // 뷰 홀더 설정
    inner class ViewHolder(private val binding: ItemRecyclerQuickVoteBinding) : RecyclerView.ViewHolder(binding.root) {

        init {


            // 빠른 투표 1번 항목 클릭시
            binding.quickQuestion1Bg.setOnClickListener {
                binding.quickQuestion1Bg.setCardBackgroundColor(Color.parseColor("#e9efff"))
                binding.quickQuestion1Bg.strokeColor = Color.parseColor("#abbced")
                binding.quickQuestion2Bg.setCardBackgroundColor(Color.parseColor("#e9ebff"))
                binding.quickQuestion2Bg.strokeColor = Color.parseColor("#b3b6e8")
                binding.quickQuestion1Txt.setTextColor(Color.BLACK)
                binding.quickQuestion2Txt.setTextColor(Color.parseColor("#989898"))
                binding.quickQuestion1Turnout.setTextColor(Color.BLACK)
                binding.quickQuestion2Turnout.setTextColor(Color.parseColor("#989898"))
                binding.quickQuestion1Turnout.visibility = View.VISIBLE
                binding.quickQuestion2Turnout.visibility = View.VISIBLE
            }

            // 빠른 투표 2번 항목 클릭시
            binding.quickQuestion2Bg.setOnClickListener {
                binding.quickQuestion1Bg.setCardBackgroundColor(Color.parseColor("#e9ebff"))
                binding.quickQuestion1Bg.strokeColor = Color.parseColor("#b3b6e8")
                binding.quickQuestion2Bg.setCardBackgroundColor(Color.parseColor("#e9efff"))
                binding.quickQuestion2Bg.strokeColor = Color.parseColor("#abbced")
                binding.quickQuestion1Txt.setTextColor(Color.parseColor("#989898"))
                binding.quickQuestion2Txt.setTextColor(Color.BLACK)
                binding.quickQuestion1Turnout.setTextColor(Color.parseColor("#989898"))
                binding.quickQuestion2Turnout.setTextColor(Color.BLACK)
                binding.quickQuestion1Turnout.visibility = View.VISIBLE
                binding.quickQuestion2Turnout.visibility = View.VISIBLE
            }

        }

        fun setItem(item: QuickPollsItem){
            binding.title.text =  item.contents
            binding.nick.text = item.user.nick
            binding.quickQuestion1Txt.text = item.items[0].contents
            binding.quickQuestion2Txt.text = item.items[0].contents
        }

        fun checkItem(item: QuickPollsItem){
//            if (item.items[0].poll.user.email == "test@test.com"){
//
//            }
            binding.title.text =  item.contents
            binding.nick.text = item.user.nick
            binding.quickQuestion1Txt.text = item.items[0].contents
            binding.quickQuestion2Txt.text = item.items[0].contents
        }



    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    @SuppressLint("NotifyDataSetChanged")
    internal fun setData(newItems: QuickPolls) {

        this.items = newItems
        notifyDataSetChanged()

    }

    // 아이템 갯수
    override fun getItemCount() = items.quickPollsItem.size


}