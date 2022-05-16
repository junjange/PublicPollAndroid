package com.junjange.myapplication.adapter

import android.annotation.SuppressLint
import android.graphics.Color
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.junjange.myapplication.data.Item
import com.junjange.myapplication.data.ModelBoard
import com.junjange.myapplication.data.ModelBoardComponent
import com.junjange.myapplication.databinding.ItemRecyclerNormalVoteBinding

class NormalVoteAdapter(val onClickListener: ItemClickListener1)  : RecyclerView.Adapter<NormalVoteAdapter.ViewHolder>() {


    private var items: ModelBoard = ModelBoard(ArrayList())

    interface ItemClickListener1 {
        fun onItemClickListener1(item: ModelBoardComponent, position: Int)
    }

    // 뷰 홀더 만들어서 반환
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemRecyclerNormalVoteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)

    }

    // 전달받은 위치의 아이템 연결
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.bind(items.board[position], position)

    }

    // 뷰 홀더 설정
    inner class ViewHolder(private val binding: ItemRecyclerNormalVoteBinding) : RecyclerView.ViewHolder(binding.root) {


        init {
//            binding.normalQuestionCardView.setOnClickListener {

//                binding.normalQuestionCardView.setCardBackgroundColor(Color.parseColor("#ffffff"))
//                binding.normalQuestionTxt.setTextColor(Color.parseColor("#6d6d6d"))
//                binding.normalQuestionCardView.setCardBackgroundColor(Color.parseColor("#c4baef"))
//                binding.normalQuestionTxt.setTextColor(Color.WHITE)
//                binding.normalQuestionTurnout.setTextColor(Color.WHITE)
//                binding.normalQuestionTurnout.visibility = View.VISIBLE
//            }
        }

        fun bind(item: ModelBoardComponent, position: Int) {


            binding.root.setOnClickListener {
                onClickListener.onItemClickListener1(item, position)
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