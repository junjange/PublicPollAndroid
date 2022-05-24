package com.junjange.myapplication.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.junjange.myapplication.data.*
import com.junjange.myapplication.databinding.ItemRecyclerSearchBinding
import com.junjange.myapplication.ui.view.HashtagActivity
import com.junjange.myapplication.ui.view.VoteActivity

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


class SearchAdapter(val context: Context) : RecyclerView.Adapter<SearchAdapter.ViewHolder>() {
    private var items: HashtagName = HashtagName(ArrayList())


    // 뷰 홀더 만들어서 반환
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemRecyclerSearchBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    // 전달받은 위치의 아이템 연결
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.setItem(items.hashtagNameItem[position])
        holder.clickItem(items.hashtagNameItem[position])



    }

    // 뷰 홀더 설정
    inner class ViewHolder(private val binding: ItemRecyclerSearchBinding) : RecyclerView.ViewHolder(binding.root) {

        fun setItem(item: HashtagNameItem){
            binding.tvTitle.text =  "#"+item.name
        }

        fun clickItem(item: HashtagNameItem){
            binding.linearLayout.setOnClickListener {

                // 원하는 화면 연결
                Intent(context, HashtagActivity::class.java).apply {
                    // 데이터 전달
                    putExtra("id", item.id)
                    putExtra("name", item.name)

                    addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                }.run {
                    //액티비티 열기
                    context.startActivity(this)
                }
            }

        }



    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    @SuppressLint("NotifyDataSetChanged")
    internal fun setData(newItems: HashtagName) {
        this.items = newItems
        notifyDataSetChanged()

    }

    // 아이템 갯수
    override fun getItemCount() = items.hashtagNameItem.size


}