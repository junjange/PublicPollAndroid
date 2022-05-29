package com.junjange.myapplication.adapter

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.model.GlideUrl
import com.junjange.myapplication.R
import com.junjange.myapplication.data.ItemComponent
import com.junjange.myapplication.data.ViewPolls
import com.junjange.myapplication.data.ViewPollsItem
import com.junjange.myapplication.databinding.ImteRecyclerPhotoVoteBinding
import com.junjange.myapplication.network.PollsObject
import com.junjange.myapplication.utils.API
import kotlin.math.round

class PhotoVoteAdapter(val onClickListener: ItemClickListener, var voteState : Boolean) : RecyclerView.Adapter<PhotoVoteAdapter.ViewHolder>(){

    private var items: ViewPolls? = null


    interface ItemClickListener {
        fun onPhotoVoteClickListener(
            item: ItemComponent,
            position: Int,
            isSingleVote: Boolean,
            myBallots: ArrayList<Int>?
        )
    }

    // 뷰 홀더 만들어서 반환
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ImteRecyclerPhotoVoteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)

    }

    // 전달받은 위치의 아이템 연결
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items!!.viewPollsItem.items[position], position, items!!.viewPollsItem.isSingleVote, items!!.viewPollsItem.myBallots)
        holder.setItem(items!!.viewPollsItem.items[position],items!!.viewPollsItem, position  )
    }

    // 뷰 홀더 설정
    inner class ViewHolder(private val binding: ImteRecyclerPhotoVoteBinding) : RecyclerView.ViewHolder(binding.root) {

        // 사진 호출
        @SuppressLint("SetTextI18n")
        fun setItem(item: ItemComponent, viewPollsItem: ViewPollsItem, position: Int){
            binding.photoQuestionTxt.text =  item.contents

            val token = PollsObject.token
            val url ="${API.BASE_URL}/images/images/${item.pollId}/${item.itemNum}"
            val glideUrl = GlideUrl(url) { mapOf(Pair("Authorization", token))}
            Glide.with(binding.pollImage.context).load(glideUrl).error(R.drawable.image_default).into(binding.pollImage)

            // 투표한 것이라면
            if (viewPollsItem.myBallots != null && !voteState){

                binding.pollImage.alpha = 0.5F

                // 1등 투표 확인
                if(items!!.viewPollsItem.stats!![position].isBest) binding.photoQuestionTurnout.setTextColor(Color.parseColor("#f5dc00"))

                else binding.photoQuestionTurnout.setTextColor(Color.parseColor("#ffffff"))


                binding.photoQuestionTxt.setTextColor(Color.parseColor("#929292"))
                binding.photoQuestionTurnout.visibility = View.VISIBLE
                binding.photoQuestionTurnout.text = "${round(viewPollsItem.stats!![position].percent*100).toInt()}%"

                // 나의 투표 확인
                if (items!!.viewPollsItem.myBallots!!.find { it == item.itemNum } != null){
                    binding.linearLayout.setBackgroundResource(R.drawable.layout_select_normal_poll_background)
                    binding.photoQuestionTxt.setTextColor(Color.parseColor("#ffffff"))
                    binding.photoQuestionTurnout.setTextColor(Color.parseColor("#6f61aa"))

                }

            }

        }

        // 투표 클릭 이벤트
        fun bind(
            item: ItemComponent,
            position: Int,
            isSingleVote: Boolean,
            myBallots: ArrayList<Int>?
        ) {
            binding.root.setOnClickListener {
                onClickListener.onPhotoVoteClickListener(item, position, isSingleVote, myBallots)
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

    // 아이템 개수
    override fun getItemCount(): Int{
        return if(items == null) 0 else items!!.viewPollsItem.items.size

    }

}