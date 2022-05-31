package com.junjange.myapplication.ui.view

import android.annotation.SuppressLint
import android.app.Activity
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
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
import com.junjange.myapplication.data.ItemImage
import com.junjange.myapplication.data.NewPoll
import com.junjange.myapplication.data.NewPollResponse
import com.junjange.myapplication.databinding.ActivityNewPollBinding
import com.junjange.myapplication.network.PollsObject
import okhttp3.MediaType
import okhttp3.MediaType.Companion.parse
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import okio.BufferedSink
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import java.lang.Exception
import java.util.*
import kotlin.collections.ArrayList

class NewPollActivity : AppCompatActivity(), IdCallback {
    lateinit var binding: ActivityNewPollBinding
    var layoutPollBoard : LinearLayout? = null
    var layoutHashTagBoard : com.google.android.flexbox.FlexboxLayout? = null
    var itemCount : Int = 3
    var itemText = Array(6,{""})
    var hashTagText = ArrayList<String>();
    var text = ""
    var contentsText = ""
    var hasImage = false
    var isPublic = false
    var showNick = false
    var canRevote = false
    var canComment = false
    var isSingleVote = false
    var date = ""
    var time = ""
    var bitmap : Bitmap? = null
    var itemImage = ArrayList<ItemImage>();
    var itemhasImage = Array(6, {false})
    var currentItemNum = 0

    private val OPEN_GALLERY = 1

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

        binding.item1Image.setOnClickListener {
            currentItemNum = 1
            openGallery()
            binding.item1Image.isClickable = false
            itemhasImage[0] = true
        }

        binding.item2Image.setOnClickListener {
            currentItemNum = 2
            openGallery()
            binding.item2Image.isClickable = false
            itemhasImage[1] = true
        }

        binding.doneBt.setOnClickListener {
            if (!itemImage.isEmpty()) {
                hasImage = true
            }

            val service = PollsObject.getRetrofitService
            val items = ArrayList<Item>();
            //여기서 리스트 객체 생성

            for (i in 0..itemCount-2) {
            //아이템 수 만큼 리스트 객체 안에 저장
                items.add(Item(i+1, itemText[i].toString(), itemhasImage[i]))
                Log.d("아이템", items.toString())
            }
            //items 리스트 객체를 통채로 넣음
            val call = service.postAddPoll(NewPoll(contentsText, hashTagText, date+time, hasImage, isPublic, showNick, canRevote, canComment, isSingleVote, items))

            Log.d("보낼 결과", "$contentsText, $hashTagText, ${date+time}, $isPublic, $showNick, $canRevote, $canComment, $isSingleVote, ${items.toString()}")

            call.enqueue(object : Callback<NewPollResponse> {
                override fun onResponse(
                    call: Call<NewPollResponse>,
                    response: Response<NewPollResponse>
                ) {
                    Log.d("ttt", "통신 성공: ${response.isSuccessful}")
                    Log.d("성공", response.body().toString())
                    val pollID = response.body()!!.data.id
                    onGetId(pollID)
                }

                override fun onFailure(call: Call<NewPollResponse>, t: Throwable) {
                    t.message?.let { it1 -> Log.d("실패", it1) }
                }
            })
            finish()
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
            currentItemNum = num
            openGallery()
            binding.item2Image.isClickable = false
            itemhasImage[num-1] = true
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

    @Override
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == OPEN_GALLERY) {
                var currentImageUrl : Uri? = data?.data
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(contentResolver, currentImageUrl)
                    itemImage.add(ItemImage(bitmap!!, currentItemNum))
                    Log.d("??", itemImage.toString())

                //여기서 이미지 변경
                } catch (e:Exception) {
                    e.printStackTrace()
                }
            }
        }
    }

    private fun String.toPlainRequestBody() = requireNotNull(this).toRequestBody("text/plain".toMediaTypeOrNull())

    private fun ImageUpload(bitmap: Bitmap,pollID: Int, itemNum : Int) {
        val bitmapRequestBody = bitmap.let { BitmapRequestBody(bitmap) }
        val bitmapMultipartBody : MultipartBody.Part? =
            MultipartBody.Part.createFormData("file", "file", bitmapRequestBody)

        val service = PollsObject.getRetrofitService
        val call = service.postImage(bitmapMultipartBody, pollID.toString().toPlainRequestBody(), itemNum.toString().toPlainRequestBody())

        call.enqueue(object : Callback<JsonObject> {
            override fun onResponse(
                call: Call<JsonObject>,
                response: Response<JsonObject>
            ) {
                Log.d("ttt", "통신 성공: ${response.isSuccessful}")
                Log.d("성공", response.body().toString())
            }

            override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                Log.d("실패", t.toString())
            }
        })
    }

    class BitmapRequestBody(private val bitmap: Bitmap) : RequestBody() {
        override fun contentType(): MediaType = "image/*".toMediaType()
        override fun writeTo(sink: BufferedSink) {
            bitmap.compress(Bitmap.CompressFormat.JPEG, 99, sink.outputStream())
        }
    }
    private fun openGallery() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.setType("image/*")
        startActivityForResult(intent, OPEN_GALLERY)
    }

    override fun onGetId(id: Int) {
        Log.d("ongetId", "$id")
        for (i in 0 until itemImage.size) {
            ImageUpload(itemImage[i].file,id, itemImage[i].item_num)
        }
    }

}

interface IdCallback{
    fun onGetId(id: Int)
}