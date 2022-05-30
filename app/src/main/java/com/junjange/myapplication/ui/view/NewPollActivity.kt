package com.junjange.myapplication.ui.view

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.KeyEvent
import android.view.MotionEvent
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.JsonObject
import com.junjange.myapplication.R
import com.junjange.myapplication.data.Item
import com.junjange.myapplication.data.NewPoll
import com.junjange.myapplication.databinding.ActivityNewPollBinding
import com.junjange.myapplication.network.PollsObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.collections.ArrayList

class NewPollActivity : AppCompatActivity() {
    lateinit var binding: ActivityNewPollBinding
    var layoutPollBoard : LinearLayout? = null
    var layoutHashTagBoard : com.google.android.flexbox.FlexboxLayout? = null
    var itemCount : Int = 3
    var itemText = Array(6,{""})
    var hashTagText = ArrayList<String>();
    var text = ""
    var contentsText = ""
    var isPublic = false
    var showNick = false
    var canRevote = false
    var canComment = false
    var isSingleVote = false
    var date = ""
    var time = ""

    @SuppressLint("ClickableViewAccessibility")
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

        binding.contentText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable) {
                contentsText = binding.contentText.text.toString()
            }
        })

        binding.item1Text.addTextChangedListener(object : TextWatcher {
            var text = ""
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
                text = binding.hashtagEdit.text.toString()
            }
        })
        
        binding.hashtagEdit.setOnKeyListener { view, i, keyEvent ->
            if (i == KeyEvent.KEYCODE_SPACE || i == KeyEvent.KEYCODE_ENTER) {
                hashTagText.add(text)
                addHashTag(hashTagText[hashTagText.size-1])
            }
            return@setOnKeyListener false
        }

        binding.yearEdit.setOnTouchListener { view, motionEvent ->
            if (motionEvent.action == MotionEvent.ACTION_DOWN) {
                val calendar = Calendar.getInstance()
                val year = calendar.get(Calendar.YEAR)
                val month = calendar.get(Calendar.MONTH)
                val day = calendar.get(Calendar.DAY_OF_MONTH)

                var listener = DatePickerDialog.OnDateSetListener{_, i, i2, i3 ->
                    binding.yearEdit.setText(i.toString())
                    if (i2 < 10) {
                        binding.monthEdit.setText("0${i2+1}")
                    } else {
                        binding.monthEdit.setText((i2+1).toString())
                    }

                    if (i3 < 10) {
                        binding.dayEdit.setText("0${i3}")
                    } else {
                        binding.dayEdit.setText(i3.toString())
                    }

                    date = "$i-${binding.monthEdit.text}-${binding.dayEdit.text}"
                }

                var picker = DatePickerDialog(this, listener, year, month, day)
                picker.show()

            }
            return@setOnTouchListener false
        }
        binding.hourEdit.setOnTouchListener{ view, motionEvent ->
            if (motionEvent.action == MotionEvent.ACTION_DOWN) {
                val calendar = Calendar.getInstance()
                var hour = calendar.get(Calendar.HOUR)
                var minute = calendar.get(Calendar.MINUTE)

                var listener = TimePickerDialog.OnTimeSetListener {_, i, i2 ->

                    if (i < 10) {
                        binding.hourEdit.setText("0$i")
                    } else {
                        binding.hourEdit.setText(i.toString())
                    }

                    if (i2 < 10) {
                        binding.minEdit.setText("0$i2")
                    } else {
                        binding.minEdit.setText(i2.toString())
                    }

                    time = "T${binding.hourEdit.text}:${binding.minEdit.text}:00"
                }

                var picker = TimePickerDialog(this, listener, hour, minute, false)

                picker.show()
            }
            return@setOnTouchListener false
        }

        binding.isPublic.setOnCheckedChangeListener { group, i ->
            when(i) {
                binding.Public.id -> isPublic = true

                binding.Private.id -> isPublic = false
            }
        }

        binding.showNick.setOnCheckedChangeListener { group, i ->
            when(i) {
                binding.show.id -> showNick = true

                binding.hide.id -> showNick = false
            }
        }

        binding.canRevote.setOnCheckedChangeListener { group, i ->
            when(i) {
                binding.revoteAvailable.id -> canRevote = true

                binding.revoteUnAvailable.id -> canRevote = false
            }
        }

        binding.canComment.setOnCheckedChangeListener { group, i ->
            when(i) {
                binding.commentAvailable.id -> canComment = true

                binding.commentUnAvailable.id -> canComment = false
            }
        }

        binding.isSingleVote.setOnCheckedChangeListener { group, i ->
            when(i) {
                binding.single.id -> isSingleVote = true

                binding.multiple.id -> isSingleVote = false
            }
        }

        binding.doneBt.setOnClickListener {
            val service = PollsObject.getRetrofitService
            val items = ArrayList<Item>();

            for (i in 0..itemCount-2) {

                items.add(Item(i+1, itemText[i].toString(), false))
                Log.d("아이템", items.toString())
            }


            /**
             * 앱에서 날짜랑 시간을 넣으면
             * 2022-7-29T7:33:00 <= 이런식으로 들어가서 안되는 것 같애
             * 2022-07-29T07:33:00 <= 이런식으로 7앞에 0이 들어가야하는뎁
             *
             * 그리고 달력에서 날짜를 설정할 때
             * 이전달로 선택되는 이슈가 있어!
             * 5월을 선택했으면 4월로 되는..
             * **/

            //val endTime = "2022-07-29T07:33:00"


            val call = service.postAddPoll(NewPoll(contentsText, hashTagText, date+time, false, isPublic, showNick, canRevote, canComment, isSingleVote, items))

            Log.d("보낼 결과", "$contentsText, $hashTagText, ${date+time}, $isPublic, $showNick, $canRevote, $canComment, $isSingleVote, ${items.toString()}")

            call.enqueue(object : Callback<JsonObject> {
                override fun onResponse(
                    call: Call<JsonObject>,
                    response: Response<JsonObject>
                ) {
                    Log.d("ttt", "통신 성공: ${response.isSuccessful}")
                    Log.d("성공", response.body().toString())
                }

                override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                    Log.d("실패", "크크루삥뽕")
                }
            })
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
        Log.d("해시태그", hashTagText.toString())
        val view = layoutInflater.inflate(R.layout.hashtag_item, null)
        val deleteBt = view.findViewById<ImageView>(R.id.deleteBt)

        view.findViewById<TextView>(R.id.hashTagText).setText(text)

        deleteBt.setOnClickListener {
            hashTagText.remove(view.findViewById<TextView>(R.id.hashTagText).text)
            Log.d("해시태그", hashTagText.toString())
            layoutHashTagBoard?.removeView(view)
        }

        layoutHashTagBoard?.addView(view)

    }
}