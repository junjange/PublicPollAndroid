package com.junjange.myapplication.adapter

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.junjange.myapplication.data.ModelBoard
import com.junjange.myapplication.databinding.ImteRecyclerPhotoVoteBinding
import com.junjange.myapplication.databinding.ItemRecyclerQuickVoteBinding

class PhotoVoteAdapter : RecyclerView.Adapter<PhotoVoteAdapter.ViewHolder>(){

    private var items: ModelBoard = ModelBoard(ArrayList())


    // 뷰 홀더 만들어서 반환
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ImteRecyclerPhotoVoteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)

    }

    // 전달받은 위치의 아이템 연결
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {


    }

    // 뷰 홀더 설정
    inner class ViewHolder(private val binding: ImteRecyclerPhotoVoteBinding) : RecyclerView.ViewHolder(binding.root) {

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