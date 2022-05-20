package com.junjange.myapplication.adapter

import android.annotation.SuppressLint
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.junjange.myapplication.R
import com.junjange.myapplication.data.ModelBoard
import com.junjange.myapplication.data.ModelBoardComponent
import com.junjange.myapplication.databinding.ItemRecyclerNormalVoteBinding

class NormalVoteAdapter(val onClickListener: ItemClickListener)  : RecyclerView.Adapter<NormalVoteAdapter.ViewHolder>() {


    private var items: ModelBoard = ModelBoard(ArrayList())
    private var normalCheckBox = -1

    interface ItemClickListener {
        fun onNormalVoteClickListener(item: ModelBoardComponent, position: Int)
    }

    // 뷰 홀더 만들어서 반환
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemRecyclerNormalVoteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)

    }

    // 전달받은 위치의 아이템 연결
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.bind(items.board[position], position, holder.layoutPosition)
        Log.d("ttt", "tttt")



    }

    // 뷰 홀더 설정
    inner class ViewHolder(private val binding: ItemRecyclerNormalVoteBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ModelBoardComponent, position: Int, cnt : Int) {

            Log.d("ttt", binding.normalQuestionTxt.toString())
            Log.d("ttt", cnt.toString())

            binding.normalQuestionCardView.setOnClickListener {



                onClickListener.onNormalVoteClickListener(item, position)

                Log.d("ttt", binding.normalQuestionTxt.toString())

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
    internal fun setData(newItems: ModelBoard) {

        this.items = newItems
        notifyDataSetChanged()

    }

    // 아이템 갯수
    override fun getItemCount() = items.board.size
}