package com.junjange.myapplication.adapter

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.model.GlideUrl
import com.junjange.myapplication.data.ItemComponent
import com.junjange.myapplication.data.ViewPolls
import com.junjange.myapplication.databinding.ImteRecyclerPhotoVoteBinding
import com.junjange.myapplication.network.PollsObject
import com.junjange.myapplication.utils.API

class PhotoVoteAdapter(val onClickListener: ItemClickListener) : RecyclerView.Adapter<PhotoVoteAdapter.ViewHolder>(){

    private var items: ViewPolls? = null


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
        holder.bind(items!!.viewPollsItem.items[position], position)
        holder.setItem(items!!.viewPollsItem.items[position])
    }

    // 뷰 홀더 설정
    inner class ViewHolder(private val binding: ImteRecyclerPhotoVoteBinding) : RecyclerView.ViewHolder(binding.root) {

        fun setItem(item: ItemComponent){
            binding.photoQuestionTxt.text =  item.contents
            val token = PollsObject.token
            val url =" ${API.BASE_URL1}images/images/3/3"
            val glideUrl = GlideUrl(url) { mapOf(Pair("Authorization", "$token"))}
            Glide.with(binding.pollImage).load(glideUrl).into(binding.pollImage)
        }

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
    override fun getItemCount(): Int{
        return if(items == null) 0 else items!!.viewPollsItem.items.size

    }

}