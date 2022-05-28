package com.junjange.myapplication.adapter

import android.annotation.SuppressLint
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.junjange.myapplication.data.QuickPolls
import com.junjange.myapplication.data.QuickPollsItem
import com.junjange.myapplication.databinding.ItemRecyclerQuickVoteBinding
import kotlin.math.round


class QuickVoteAdapter(val onClickListener: ItemClickListener) : RecyclerView.Adapter<QuickVoteAdapter.ViewHolder>() {

    private var items: QuickPolls = QuickPolls(ArrayList())
    var voteState = false

    interface ItemClickListener {
        fun onQuickVoteClickListener(
            item: QuickPollsItem,
            itemNum: ArrayList<Int>,
            voteState: Boolean
        )
    }

    // 뷰 홀더 만들어서 반환
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemRecyclerQuickVoteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    // 전달받은 위치의 아이템 연결
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.setItem(items.quickPollsItem[position])
//        holder.checkItem(items.quickPollsItem[position])
        holder.bind(items.quickPollsItem[position], position)
        Log.d("ttt", position.toString())




    }

    // 뷰 홀더 설정
    inner class ViewHolder(private val binding: ItemRecyclerQuickVoteBinding) : RecyclerView.ViewHolder(binding.root) {


        @SuppressLint("SetTextI18n")
        fun bind(item: QuickPollsItem, position: Int) {
            Log.d("aaaa", item.id.toString())
            Log.d("aaaa", item.myBallots.toString())


            if (item.myBallots == null){
                voteState = false
                // 빠른 투표 1번 항목 클릭시
                binding.quickQuestion1Bg.setOnClickListener {
                    onClickListener.onQuickVoteClickListener(item, arrayListOf<Int>(item.items[0].itemNum), voteState)
                    notifyData()


//                    binding.quickQuestion1Bg.setCardBackgroundColor(Color.parseColor("#e9efff"))
//                    binding.quickQuestion1Bg.strokeColor = Color.parseColor("#abbced")
//                    binding.quickQuestion2Bg.setCardBackgroundColor(Color.parseColor("#e9ebff"))
//                    binding.quickQuestion2Bg.strokeColor = Color.parseColor("#b3b6e8")
//                    binding.quickQuestion1Txt.setTextColor(Color.BLACK)
//                    binding.quickQuestion2Txt.setTextColor(Color.parseColor("#989898"))
//                    binding.quickQuestion1Turnout.setTextColor(Color.BLACK)
//                    binding.quickQuestion2Turnout.setTextColor(Color.parseColor("#989898"))
//                    binding.quickQuestion1Turnout.visibility = View.VISIBLE
//                    binding.quickQuestion2Turnout.visibility = View.VISIBLE
                }

                // 빠른 투표 2번 항목 클릭시
                binding.quickQuestion2Bg.setOnClickListener {
                    onClickListener.onQuickVoteClickListener(item, arrayListOf<Int>(item.items[1].itemNum), voteState)
                    notifyData()

//                    binding.quickQuestion1Bg.setCardBackgroundColor(Color.parseColor("#e9ebff"))
//                    binding.quickQuestion1Bg.strokeColor = Color.parseColor("#b3b6e8")
//                    binding.quickQuestion2Bg.setCardBackgroundColor(Color.parseColor("#e9efff"))
//                    binding.quickQuestion2Bg.strokeColor = Color.parseColor("#abbced")
//                    binding.quickQuestion1Txt.setTextColor(Color.parseColor("#989898"))
//                    binding.quickQuestion2Txt.setTextColor(Color.BLACK)
//                    binding.quickQuestion1Turnout.setTextColor(Color.parseColor("#989898"))
//                    binding.quickQuestion2Turnout.setTextColor(Color.BLACK)
//                    binding.quickQuestion1Turnout.visibility = View.VISIBLE
//                    binding.quickQuestion2Turnout.visibility = View.VISIBLE

                }
            }else{

                voteState = true

                binding.quickQuestion1Bg.setOnClickListener {
                    onClickListener.onQuickVoteClickListener(item, arrayListOf<Int>(item.items[0].itemNum), voteState)
                    notifyItemChanged(position)

                }

                // 빠른 투표 2번 항목 클릭시
                binding.quickQuestion2Bg.setOnClickListener {
                    onClickListener.onQuickVoteClickListener(item, arrayListOf<Int>(item.items[1].itemNum), voteState)
                    notifyItemChanged(position)


                }


                 if (items.quickPollsItem[position].myBallots!![0] == 1){
                        binding.quickQuestion1Bg.setCardBackgroundColor(Color.parseColor("#e9efff"))
                        binding.quickQuestion1Bg.strokeColor = Color.parseColor("#abbced")
                        binding.quickQuestion2Bg.setCardBackgroundColor(Color.parseColor("#e9ebff"))
                        binding.quickQuestion2Bg.strokeColor = Color.parseColor("#b3b6e8")



                    }else{
                        binding.quickQuestion1Bg.setCardBackgroundColor(Color.parseColor("#e9ebff"))
                        binding.quickQuestion1Bg.strokeColor = Color.parseColor("#b3b6e8")
                        binding.quickQuestion2Bg.setCardBackgroundColor(Color.parseColor("#e9efff"))
                        binding.quickQuestion2Bg.strokeColor = Color.parseColor("#abbced")


                    }

                if (!items.quickPollsItem[position].stats!![0].isBest){
                    binding.quickQuestion1Txt.setTextColor(Color.parseColor("#989898"))
                    binding.quickQuestion2Txt.setTextColor(Color.BLACK)
                    binding.quickQuestion1Turnout.setTextColor(Color.parseColor("#989898"))
                    binding.quickQuestion2Turnout.setTextColor(Color.BLACK)


                }else{
                    binding.quickQuestion1Txt.setTextColor(Color.BLACK)
                    binding.quickQuestion2Txt.setTextColor(Color.parseColor("#989898"))
                    binding.quickQuestion1Turnout.setTextColor(Color.BLACK)
                    binding.quickQuestion2Turnout.setTextColor(Color.parseColor("#989898"))

                }

                binding.quickQuestion1Turnout.text = "${round(items.quickPollsItem[position].stats!![0].percent*100)/100}%"
                binding.quickQuestion2Turnout.text = "${round(items.quickPollsItem[position].stats!![1].percent*100)/100}%"

                binding.quickQuestion1Turnout.visibility = View.VISIBLE
                binding.quickQuestion2Turnout.visibility = View.VISIBLE




            }



        }

        fun setItem(item: QuickPollsItem){

            binding.title.text =  item.contents
            binding.nick.text = item.nick
            binding.quickQuestion1Txt.text = item.items[0].contents
            binding.quickQuestion2Txt.text = item.items[1].contents

        }


    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    @SuppressLint("NotifyDataSetChanged")
    fun notifyData() {
        notifyDataSetChanged()

    }




    @SuppressLint("NotifyDataSetChanged")
    internal fun setData(newItems: QuickPolls) {

        this.items = newItems
        notifyDataSetChanged()

    }

    // 아이템 갯수
    override fun getItemCount() = items.quickPollsItem.size


}