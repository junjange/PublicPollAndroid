package com.junjange.myapplication.adapter

import android.content.Context
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.junjange.myapplication.data.Item
import com.junjange.myapplication.databinding.ItemRecyclerBinding

class ItemAdapter(context: Context, val onClickListener: ItemClickListener) :
    RecyclerView.Adapter<ItemAdapter.ViewHolder>() {

    private val items: ArrayList<Item> = ArrayList()

    interface ItemClickListener {
        fun onItemClickListener(item: Item, position: Int)
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemRecyclerBinding.inflate(
                LayoutInflater.from(viewGroup.context),
                viewGroup,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun addAll(itemList: List<Item>) {
        if (itemList.isNotEmpty()) {
            items.clear()
            items.addAll(itemList)
            notifyDataSetChanged()
        }
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.bind(items[position], position)
    }

    inner class ViewHolder(private val binding: ItemRecyclerBinding) :
        RecyclerView.ViewHolder(binding.root) {

        private lateinit var item: Item
        private var itemPosition: Int = 0
        private val itemName = binding.itemName
        private val container = binding.root

        fun bind(item: Item, position: Int) {
            this.item = item
            itemPosition = position

            if (!TextUtils.isEmpty(item.itemName)) {
                itemName.text = item.itemName
            }

            container.setOnClickListener {
                onClickListener.onItemClickListener(item, position)
            }
        }
    }
}