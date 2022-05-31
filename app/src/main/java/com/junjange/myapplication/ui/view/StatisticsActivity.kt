package com.junjange.myapplication.ui.view

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.utils.ColorTemplate
import com.github.mikephil.charting.utils.ColorTemplate.COLORFUL_COLORS
import com.junjange.myapplication.databinding.ActivityStatisticsBinding
import com.junjange.myapplication.ui.viewmodel.StatisticsViewModel

class StatisticsActivity : AppCompatActivity() {
    private val binding by lazy { ActivityStatisticsBinding.inflate(layoutInflater) }
    private val viewModel by lazy { ViewModelProvider(this, StatisticsViewModel.Factory(application))[StatisticsViewModel::class.java] }
    private var isChecking = true
    private var mCheckedId = 0
    private var ageSelect = 0
    private var genderSelect = 0
    private var tierSelect = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        // 데이터 바인딩
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        binding.piechart.setUsePercentValues(true)

        // 여기서 api 호출!
        viewModel.getStatRetrofit(84, 4, 0, 0)

        // api를 호출하면서 바뀐 값들은 여기서 확인
        // 계속해서 관찰함!
        statSetObserver()

        drawGraph()

        binding.ageAll.isChecked = true
        binding.genderAll.isChecked = true
        binding.tierAll.isChecked = true

        binding.ageFirstLine.setOnCheckedChangeListener { group, checkedId ->
            if (checkedId != -1 && isChecking) {
                isChecking = false
                binding.ageSecondLine.clearCheck()
                mCheckedId = checkedId
            }

            if (checkedId == binding.ageAll.id) {
                ageSelect = 0
            } else if (checkedId == binding.age10s.id) {
                ageSelect = 1
            } else if (checkedId == binding.age20s.id) {
                ageSelect = 2
            }

            isChecking = true
        }
        binding.ageSecondLine.setOnCheckedChangeListener { group, checkedId ->
            if (checkedId != -1 && isChecking) {
                isChecking = false
                binding.ageFirstLine.clearCheck()
                mCheckedId = checkedId
            }

            if (checkedId == binding.age30s.id) {
                ageSelect = 3
            } else if (checkedId == binding.age40s.id) {
                ageSelect = 4
            } else if (checkedId == binding.age50s.id) {
                ageSelect = 5
            }

            isChecking = true
        }

        binding.gender.setOnCheckedChangeListener { group, checkedId ->

        }

        binding.tier1.setOnCheckedChangeListener { group, checkedId ->
            if (checkedId != -1 && isChecking) {
                isChecking = false
                binding.tier2.clearCheck()
                mCheckedId = checkedId
            }
            isChecking = true
        }
        binding.tier2.setOnCheckedChangeListener { group, checkedId ->
            if (checkedId != -1 && isChecking) {
                isChecking = false
                binding.tier1.clearCheck()
                mCheckedId = checkedId
            }
            isChecking = true
        }

    }

    private fun drawGraph() {
        val entries = ArrayList<PieEntry>()
        entries.add(PieEntry(508f,"Apple"))
        entries.add(PieEntry(600f,"Orange"))
        entries.add(PieEntry(750f,"Mango"))
        entries.add(PieEntry(508f,"RedOrange"))
        entries.add(PieEntry(670f,"Other"))

        val colorsItems = ArrayList<Int>()
        for (c in ColorTemplate.VORDIPLOM_COLORS) colorsItems.add(c)
        for (c in ColorTemplate.JOYFUL_COLORS) colorsItems.add(c)
        for (c in COLORFUL_COLORS) colorsItems.add(c)
        for (c in ColorTemplate.LIBERTY_COLORS) colorsItems.add(c)
        for (c in ColorTemplate.PASTEL_COLORS) colorsItems.add(c)

        colorsItems.add(ColorTemplate.getHoloBlue())

        val pieDataSet = PieDataSet(entries, "")
        pieDataSet.apply {
            colors = colorsItems
            valueTextColor = Color.BLACK
            valueTextSize = 16f
        }

        val pieData = PieData(pieDataSet)
        binding.piechart.apply {
            data = pieData
            description.isEnabled = false
            isRotationEnabled = true
            centerText = "this is center"
            setEntryLabelColor(Color.BLACK)
            animateY(1400, Easing.EaseInOutQuad)
            animate()
        }
    }

    private fun statSetObserver() {
        viewModel.retrofitStat.observe(this, {
            viewModel.retrofitStat.value?.let {
                Log.d("ttt", it.toString())
            }
        })
    }
}