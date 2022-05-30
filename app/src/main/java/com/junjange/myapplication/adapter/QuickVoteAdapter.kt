package com.junjange.myapplication.adapter

import android.annotation.SuppressLint
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.junjange.myapplication.R
import com.junjange.myapplication.data.QuickPolls
import com.junjange.myapplication.data.QuickPollsItem
import com.junjange.myapplication.databinding.ItemRecyclerQuickVoteBinding
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import java.util.*
import kotlin.collections.ArrayList
import kotlin.math.round


class QuickVoteAdapter(val onClickListener: ItemClickListener) : RecyclerView.Adapter<QuickVoteAdapter.ViewHolder>() {

    private var items: QuickPolls = QuickPolls(ArrayList())
    var expired = false

    interface ItemClickListener {
        fun onQuickVoteClickListener(
            item: QuickPollsItem,
            itemNum: ArrayList<Int>,
            voteState: Boolean
        )
    }

    // 뷰 홀더 만들어서 반환
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemRecyclerQuickVoteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    // 전달받은 위치의 아이템 연결
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.setItem(items.quickPollsItem[position])
        holder.bind(items.quickPollsItem[position], position)

    }

    // 뷰 홀더 설정
    inner class ViewHolder(private val binding: ItemRecyclerQuickVoteBinding) : RecyclerView.ViewHolder(binding.root) {


        @SuppressLint("SetTextI18n")
        fun bind(item: QuickPollsItem, position: Int) {
            binding.linearLayout.translationX = 0f
            binding.linearLayout.translationY = 0f


            if (item.myBallots == null){
                if (!expired){
                    // 빠른 투표 1번 항목 클릭시
                    binding.quickQuestion1Bg.setOnClickListener {
                        onClickListener.onQuickVoteClickListener(item, arrayListOf<Int>(item.items[0].itemNum), false)
                    }

                    // 빠른 투표 2번 항목 클릭시
                    binding.quickQuestion2Bg.setOnClickListener {
                        onClickListener.onQuickVoteClickListener(item, arrayListOf<Int>(item.items[1].itemNum), false)

                    }

                }else{
                    // 내가 투표하지는 않았지만 투표기간이 지나 투표율을 확인해야할 경우
                    // 코드 여기로!
                }

            }else{
                if (!expired){
                    binding.quickQuestion1Bg.setOnClickListener {
                        onClickListener.onQuickVoteClickListener(item, arrayListOf<Int>(item.items[0].itemNum), true)
                    }

                    // 빠른 투표 2번 항목 클릭시
                    binding.quickQuestion2Bg.setOnClickListener {
                        onClickListener.onQuickVoteClickListener(item, arrayListOf<Int>(item.items[1].itemNum), true)

                    }
                }


                 if (items.quickPollsItem[position].myBallots!![0] == 1){
                        binding.quickQuestion1Bg.setCardBackgroundColor(Color.parseColor("#e9efff"))
                        binding.quickQuestion1Bg.strokeColor = Color.parseColor("#abbced")
//                        binding.quickQuestion2Bg.setCardBackgroundColor(Color.parseColor("#e9ebff"))
                        binding.quickQuestion2Bg.strokeColor = Color.parseColor("#b3b6e8")



                    }else{
//                        binding.quickQuestion1Bg.setCardBackgroundColor(Color.parseColor("#e9ebff"))
                        binding.quickQuestion1Bg.strokeColor = Color.parseColor("#b3b6e8")
                        binding.quickQuestion2Bg.setCardBackgroundColor(Color.parseColor("#e9efff"))
                        binding.quickQuestion2Bg.strokeColor = Color.parseColor("#abbced")


                    }

                if (!items.quickPollsItem[position].stats!![0].isBest){
                    binding.quickQuestion1Txt.setTextColor(Color.BLACK)
                    binding.quickQuestion2Txt.setTextColor(Color.parseColor("#989898"))
                    binding.quickQuestion1Turnout.setTextColor(Color.BLACK)
                    binding.quickQuestion2Turnout.setTextColor(Color.parseColor("#989898"))


                }else{
                    binding.quickQuestion1Txt.setTextColor(Color.parseColor("#989898"))
                    binding.quickQuestion2Txt.setTextColor(Color.BLACK)
                    binding.quickQuestion1Turnout.setTextColor(Color.parseColor("#989898"))
                    binding.quickQuestion2Turnout.setTextColor(Color.BLACK)


                }

                binding.quickQuestion1Turnout.text = "${round(items.quickPollsItem[position].stats!![0].percent*100).toInt()}%"
                binding.quickQuestion2Turnout.text = "${round(items.quickPollsItem[position].stats!![1].percent*100).toInt()}%"

                binding.quickQuestion1Turnout.visibility = View.VISIBLE
                binding.quickQuestion2Turnout.visibility = View.VISIBLE




            }



        }

        @SuppressLint("SimpleDateFormat", "SetTextI18n")
        fun setItem(item: QuickPollsItem){

            when (item.tier) {
                1 -> binding.tier.setImageResource(R.drawable.layout_tier1)
                2 -> binding.tier.setImageResource(R.drawable.layout_tier2)
                3 -> binding.tier.setImageResource(R.drawable.layout_tier3)
                4 -> binding.tier.setImageResource(R.drawable.layout_tier4)
                5 -> binding.tier.setImageResource(R.drawable.layout_tier5)
            }


            binding.title.text =  item.contents
            binding.nick.text = item.nick
            binding.quickQuestion1Txt.text = item.items[0].contents
            binding.quickQuestion2Txt.text = item.items[1].contents

            val time = item.endTime// 변환할 문자열
            val now = LocalDateTime.now() // 현재 시간
            //문자열 LocalDateTime 으로 변관
            val convertTime = LocalDateTime.parse(time, DateTimeFormatter.ISO_LOCAL_DATE_TIME)
            val compareTime = ChronoUnit.DAYS.between(now, convertTime) //분단위 비교

            when {
                compareTime.equals(0) -> {
                    binding.dDay.text = "D-day"
                    expired = false
                }
                compareTime > 0 -> {
                    binding.dDay.text = "D-${compareTime}"
                    expired = false

                }
                else -> {
                    binding.dDay.text = "D+${-compareTime}"
                    expired = true


                }
            }


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