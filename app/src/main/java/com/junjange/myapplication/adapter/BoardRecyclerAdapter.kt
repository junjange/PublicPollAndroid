package com.junjange.myapplication.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.junjange.myapplication.databinding.ItemRecyclerBoardBinding
import com.junjange.myapplication.data.ModelBoard
import com.junjange.myapplication.data.ModelBoardComponent

/**
리사이클러 뷰를 데이터 바인딩으로 구현 : onCreateViewHolder()
ModelBoard에 있는 ModelBoardComponent를 리사이클러 뷰에 뿌려준다. : onBindViewHolder() --> setItem()
리사이클러 뷰 업데이트 => setData()

onBindViewHolder()에서 position이 늘어나지 않는? 이슈 해결 못함.. 해결!
getItemViewType() =>  RecyclerView 재사용 item 오류/ positon 오류 해결 방법[완벽한 해결]
각각의 viewType은 getItemViewType() 함수를 재정의하여 각 아이템 항목에 맞는 ViewType 값을 리턴하도록 재정의
이런식으로 하면 각각의 뷰들이 고유의 뷰 타입을 갖게 되어서 View 가 꼬이는 문제를 해결 할 수 있다.
실제로 구글에서 위 방법으로 문제를 해결하라고 하다고 한다..
 **/


class BoardRecyclerAdapter : RecyclerView.Adapter<BoardRecyclerAdapter.ViewHolder>() {
    private var items: ModelBoard = ModelBoard(ArrayList())


    // 뷰 홀더 만들어서 반환
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemRecyclerBoardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    // 전달받은 위치의 아이템 연결
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.setItem(items.board[position])

    }

    // 뷰 홀더 설정
    inner class ViewHolder(private val binding: ItemRecyclerBoardBinding) : RecyclerView.ViewHolder(binding.root) {

        fun setItem(item: ModelBoardComponent){
            binding.tvTitle.text =  item.title
            binding.tvContents.text =  item.contents
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