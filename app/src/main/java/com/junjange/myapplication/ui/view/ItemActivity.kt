package com.junjange.myapplication.ui.view

import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.content.ContextCompat
import androidx.core.view.children
import androidx.core.view.get
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.junjange.myapplication.R
import com.junjange.myapplication.adapter.ItemAdapter
import com.junjange.myapplication.data.Item
import com.junjange.myapplication.databinding.ActivityItemBinding

class ItemActivity : AppCompatActivity(), ItemAdapter.ItemClickListener {
    private lateinit var binding: ActivityItemBinding
    private var adapter = ItemAdapter(this, this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityItemBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupUi()
    }

    private fun setupUi() {
        setupRecycler()
    }

    private fun setupRecycler() {
        initRecyclerView(binding.recycler)
        binding.recycler.adapter = adapter
        adapter.addAll(getDummyList())
    }

    private fun initRecyclerView(recyclerView: RecyclerView) {
        recyclerView.layoutManager = LinearLayoutManager(
            this,
            LinearLayoutManager.VERTICAL,
            false
        )
    }

    override fun onItemClickListener(item: Item, position: Int) {

        Log.d("ttt", (binding.recycler[position].background).toString())


        val itemBackground: ColorDrawable =
            binding.recycler[position].background as ColorDrawable
        if (itemBackground.color == ContextCompat.getColor(this, R.color.white)) {
            binding.recycler.children.iterator().forEach { item ->
                item.setBackgroundColor(
                    ContextCompat.getColor(
                        this,
                        R.color.white
                    )
                )
            }
            binding.recycler[position].setBackgroundColor(
                ContextCompat.getColor(this, R.color.teal_200)
            )
        } else {
            binding.recycler.children.iterator().forEach { item ->
                item.setBackgroundColor(
                    ContextCompat.getColor(
                        this,
                        R.color.white
                    )
                )
            }
        }
    }

    private fun getDummyList(): ArrayList<Item> {
        val items = ArrayList<Item>()
        items.add(Item("Item one"))
        items.add(Item("Item two"))
        items.add(Item("Item three"))
        items.add(Item("Item four"))
        items.add(Item("Item five"))
        items.add(Item("Item six"))
        items.add(Item("Item seven"))
        items.add(Item("Item eight"))
        items.add(Item("Item nine"))
        items.add(Item("Item ten"))
        return items
    }


}