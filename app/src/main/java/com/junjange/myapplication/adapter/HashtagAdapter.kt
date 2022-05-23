package com.junjange.myapplication.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.Dimension
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.model.GlideUrl
import com.junjange.myapplication.data.Hashtag
import com.junjange.myapplication.data.PollsComponent
import com.junjange.myapplication.databinding.ItemRecyclerHashtagBinding
import com.junjange.myapplication.network.PollsObject
import com.junjange.myapplication.ui.view.VoteActivity

class HashtagAdapter (val context: Context) : RecyclerView.Adapter<HashtagAdapter.ViewHolder>()  {

    private var items: Hashtag? = null

    // 뷰 홀더 만들어서 반환
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemRecyclerHashtagBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    // 전달받은 위치의 아이템 연결
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.setItem(items!!.hashtagItem.polls[position])
        holder.clickItem(items!!.hashtagItem.polls[position])

    }

    // 뷰 홀더 설정
    inner class ViewHolder(private val binding: ItemRecyclerHashtagBinding) : RecyclerView.ViewHolder(binding.root) {

        fun clickItem(item: PollsComponent){
            binding.pollCardView.setOnClickListener  {

                // 원하는 화면 연결
                Intent(context, VoteActivity::class.java).apply {
                    // 데이터 전달
                    putExtra("id", item.id)
                    putExtra("presentImagePath", item.presentImagePath)
                    addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                }.run {
                    //액티비티 열기
                    context.startActivity(this)
                }
            }

        }

        fun setItem(item: PollsComponent){
            binding.title.text =  item.contents



            if(item.presentImagePath != null){

                val token = PollsObject.token
                val url = item.presentImagePath
                val glideUrl = GlideUrl(url) { mapOf(Pair("Authorization", "Bearer $token"))}
                Glide.with(binding.pollImage).load(glideUrl).into(binding.pollImage)
                binding.pollImage.visibility = View.VISIBLE
                binding.title.setTextSize(Dimension.SP, 20F)


            }else{
                binding.pollImage.visibility = View.GONE
                binding.title.setTextSize(Dimension.SP, 16F)

            }

            // 이미지!
        }

    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    @SuppressLint("NotifyDataSetChanged")
    internal fun setData(newItems: Hashtag) {

        this.items = newItems
        notifyDataSetChanged()

    }

    // 아이템 갯수
    override fun getItemCount(): Int{
        return if(items == null) 0 else items!!.hashtagItem.polls.size

    }

}