package com.junjange.myapplication.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.junjange.myapplication.data.ItemComponent
import com.junjange.myapplication.data.ModelBoardComponent
import com.junjange.myapplication.data.ViewPolls
import com.junjange.myapplication.databinding.ImteRecyclerPhotoVoteBinding

class PhotoVoteAdapter(val onClickListener: ItemClickListener) : RecyclerView.Adapter<PhotoVoteAdapter.ViewHolder>(){

    private lateinit var items: ViewPolls

    interface ItemClickListener {
        fun onPhotoVoteClickListener(item: ItemComponent, position: Int)
    }

    // 뷰 홀더 만들어서 반환
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ImteRecyclerPhotoVoteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)

    }

    // 전달받은 위치의 아이템 연결
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.bind(items.viewPollsItem.items[position], position)
    }

    // 뷰 홀더 설정
    inner class ViewHolder(private val binding: ImteRecyclerPhotoVoteBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: ItemComponent, position: Int) {


            binding.root.setOnClickListener {

                onClickListener.onPhotoVoteClickListener(item, position)


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
    override fun getItemCount() = items.viewPollsItem.items.size

}