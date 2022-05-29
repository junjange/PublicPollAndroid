package com.junjange.myapplication.adapter

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.junjange.myapplication.R
import com.junjange.myapplication.data.*
import com.junjange.myapplication.databinding.ItemRecyclerNormalVoteBinding
import kotlin.math.round

class NormalVoteAdapter(val onClickListener: ItemClickListener, var voteState : Boolean)  : RecyclerView.Adapter<NormalVoteAdapter.ViewHolder>() {


    private var items: ViewPolls? = null

    interface ItemClickListener {
        fun onNormalVoteClickListener(item: ItemComponent, position: Int, isSingleVote: Boolean, myBallots: ArrayList<Int>?)
    }

    // 뷰 홀더 만들어서 반환
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemRecyclerNormalVoteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)

    }

    // 전달받은 위치의 아이템 연결
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.setItem(items!!.viewPollsItem.items[position], items!!.viewPollsItem, position )

        holder.bind(items!!.viewPollsItem.items[position], position, items!!.viewPollsItem.isSingleVote, items!!.viewPollsItem.myBallots)

    }

    // 뷰 홀더 설정
    inner class ViewHolder(private val binding: ItemRecyclerNormalVoteBinding) : RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("SetTextI18n")
        fun setItem(item: ItemComponent, viewPollsItem: ViewPollsItem, position: Int){
            binding.normalQuestionTxt.text =  item.contents

            // 투표를 했고 재투표를 안한다면
            if (viewPollsItem.myBallots != null && !voteState){

                // 1등 투표 확인
                if(items!!.viewPollsItem.stats!![position].isBest)binding.normalQuestionTxt.setTextColor(Color.parseColor("#f5dc00"))
                else binding.normalQuestionTxt.setTextColor(Color.parseColor("#929292"))

                binding.normalQuestionTurnout.setTextColor(Color.parseColor("#929292"))
                binding.normalQuestionTurnout.visibility = View.VISIBLE
                binding.normalQuestionTurnout.text = "${round(viewPollsItem.stats!![position].percent*100).toInt()}%"

                // 나의 투표 확인
                if (items!!.viewPollsItem.myBallots!!.find { it == item.itemNum } != null){
                    binding.normalQuestionCardView.setBackgroundResource(R.drawable.layout_select_normal_poll_background)
                    binding.normalQuestionTxt.setTextColor(Color.parseColor("#ffffff"))
                    binding.normalQuestionTurnout.setTextColor(Color.parseColor("#ffffff"))

                }

            }

        }

        fun bind(item: ItemComponent, position: Int, isSingleVote: Boolean, myBallots: ArrayList<Int>?) {

            binding.normalQuestionCardView.setOnClickListener {
                onClickListener.onNormalVoteClickListener(item, position, isSingleVote, myBallots)
            }

        }

    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    @SuppressLint("NotifyDataSetChanged")
    internal fun setData(newItems: ViewPolls) {
        this.items = newItems
        notifyDataSetChanged()

    }

    // 아이템 갯수
    override fun getItemCount(): Int{
        return if(items == null) 0 else items!!.viewPollsItem.items.size

    }
}