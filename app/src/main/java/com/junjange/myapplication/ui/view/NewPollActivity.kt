package com.junjange.myapplication.ui.view

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.KeyEvent
import android.view.KeyEvent.KEYCODE_SPACE
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.junjange.myapplication.R
import com.junjange.myapplication.databinding.ActivityNewPollBinding

class NewPollActivity : AppCompatActivity() {
    lateinit var binding: ActivityNewPollBinding
    var layoutPollBoard : LinearLayout? = null
    var layoutHashTagBoard : com.google.android.flexbox.FlexboxLayout? = null
    var itemCount : Int = 3
    var itemText = Array(6,{""})
    var hashTagText = ArrayList<String>();

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewPollBinding.inflate(layoutInflater)
        setContentView(binding.root)
        layoutPollBoard = binding.pollBoard
        layoutHashTagBoard = binding.HashTagBoard

        binding.addItemBt.setOnClickListener {
            if(itemCount == 7) {

            } else {
                addItem(itemCount)
                itemCount++
            }
        }

        binding.item1Text.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable) {
                itemText[0] = binding.item1Text.text.toString()
            }
        })

        binding.item2Text.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable) {
                itemText[1] = binding.item2Text.text.toString()
            }
        })

        binding.hashtagEdit.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun afterTextChanged(p0: Editable?) {
                hashTagText.add(binding.hashtagEdit.text.toString())
            }
        })
        
        binding.hashtagEdit.setOnKeyListener { view, i, keyEvent ->
            if (i == KeyEvent.KEYCODE_SPACE && i == KeyEvent.KEYCODE_ENTER) {
                addHashTag(hashTagText[hashTagText.size-1])
            }
            return@setOnKeyListener false
        }

        binding.doneBt.setOnClickListener {
            for (i in 0..itemCount-2) {
                Log.d("아이템", itemText[i])
            }
        }
    }

    private fun addItem(num : Int) {
        val view = layoutInflater.inflate(R.layout.poll_item, null)

        val numText = view.findViewById<TextView>(R.id.number)
        val image = view.findViewById<androidx.appcompat.widget.AppCompatButton>(R.id.image)

        view.findViewById<EditText>(R.id.poll_text).addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable) {
                itemText[num-1] = view.findViewById<EditText>(R.id.poll_text).text.toString()
            }
        })
        numText.setText(num.toString())



        image.setOnClickListener {
            layoutPollBoard?.removeView(view)
            itemCount--
        }

        layoutPollBoard?.addView(view)
    }

    private fun addHashTag(text : String) {
        binding.hashtagEdit.text.clear()
        val view = layoutInflater.inflate(R.layout.hashtag_item, null)
        val deleteBt = view.findViewById<ImageView>(R.id.deleteBt)

        view.findViewById<TextView>(R.id.hashTagText).setText(text)

        deleteBt.setOnClickListener {
            layoutHashTagBoard?.removeView(view)
        }

        layoutHashTagBoard?.addView(view)

    }
}