package com.junjange.myapplication.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.junjange.myapplication.data.*
import com.junjange.myapplication.databinding.ItemRecyclerNormalVoteBinding

class NormalVoteAdapter(val onClickListener: ItemClickListener)  : RecyclerView.Adapter<NormalVoteAdapter.ViewHolder>() {


    private var items: ViewPolls? = null
    private var normalCheckBox = -1

    interface ItemClickListener {
        fun onNormalVoteClickListener(item: ItemComponent, position: Int)
    }

    // 뷰 홀더 만들어서 반환
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemRecyclerNormalVoteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)

    }

    // 전달받은 위치의 아이템 연결
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items!!.viewPollsItem.items[position], position)
        holder.setItem(items!!.viewPollsItem.items[position])
    }

    // 뷰 홀더 설정
    inner class ViewHolder(private val binding: ItemRecyclerNormalVoteBinding) : RecyclerView.ViewHolder(binding.root) {

        fun setItem(item: ItemComponent){
            binding.normalQuestionTxt.text =  item.contents

        }

        fun bind(item: ItemComponent, position: Int) {

            binding.normalQuestionCardView.setOnClickListener {
                onClickListener.onNormalVoteClickListener(item, position)

//                if (normalCheckBox != position){
//
//                    binding.normalQuestionCardView.setBackgroundResource(R.drawable.layout_select_normal_poll_background)
//                    binding.normalQuestionTxt.setTextColor(Color.WHITE)
//                    normalCheckBox = position
//
//                }else{
//                    binding.normalQuestionCardView.setBackgroundResource(R.drawable.layout_unselect_normal_poll_background)
//                    binding.normalQuestionTxt.setTextColor(Color.parseColor("#dcdcdc"))
//                    normalCheckBox = -1
//
//                }

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