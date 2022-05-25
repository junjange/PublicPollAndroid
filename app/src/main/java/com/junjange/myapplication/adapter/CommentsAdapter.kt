package com.junjange.myapplication.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.junjange.myapplication.data.Comment
import com.junjange.myapplication.data.CommentItem
import com.junjange.myapplication.databinding.ItemRecyclerCommentBinding

class CommentsAdapter : RecyclerView.Adapter<CommentsAdapter.ViewHolder>()    {
    private var items: Comment = Comment(ArrayList())


    // 뷰 홀더 만들어서 반환
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemRecyclerCommentBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    // 전달받은 위치의 아이템 연결
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.setItem(items.commentItem[position])
    }

    // 뷰 홀더 설정
    inner class ViewHolder(private val binding: ItemRecyclerCommentBinding) : RecyclerView.ViewHolder(binding.root) {

        fun setItem(item: CommentItem){
            binding.comment.text =  item.contents
            binding.nick.text = item.user.nick
        }

    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    @SuppressLint("NotifyDataSetChanged")
    internal fun setData(newItems: Comment) {

        this.items = newItems
        notifyDataSetChanged()

    }

    // 아이템 개수
    override fun getItemCount(): Int = items.commentItem.size



}