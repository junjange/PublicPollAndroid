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
    var entries = ArrayList<PieEntry>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        // 데이터 바인딩
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        binding.piechart.setUsePercentValues(true)

        val id = intent.getSerializableExtra("id") as Int

        // 여기서 api 호출!
        viewModel.getStatRetrofit(id, ageSelect, genderSelect, tierSelect)

        // api를 호출하면서 바뀐 값들은 여기서 확인
        // 계속해서 관찰함!

        statSetObserver()

        binding.ageAll.isChecked = true
        binding.genderAll.isChecked = true
        binding.tierAll.isChecked = true

        binding.ageFirstLine.setOnCheckedChangeListener { group, checkedId ->
            if (checkedId != -1 && isChecking) {
                isChecking = false
                binding.ageSecondLine.clearCheck()
                mCheckedId = checkedId
            }

            when (checkedId) {
                binding.ageAll.id -> {
                    ageSelect = 0
                }
                binding.age10s.id -> {
                    ageSelect = 1
                }
                binding.age20s.id -> {
                    ageSelect = 2
                }
            }
            viewModel.getStatRetrofit(84, ageSelect, genderSelect, tierSelect)
            statSetObserver()
            isChecking = true
        }
        binding.ageSecondLine.setOnCheckedChangeListener { group, checkedId ->
            if (checkedId != -1 && isChecking) {
                isChecking = false
                binding.ageFirstLine.clearCheck()
                mCheckedId = checkedId
            }

            when (checkedId) {
                binding.age30s.id -> {
                    ageSelect = 3
                }
                binding.age40s.id -> {
                    ageSelect = 4
                }
                binding.age50s.id -> {
                    ageSelect = 5
                }
            }
            viewModel.getStatRetrofit(84, ageSelect, genderSelect, tierSelect)
            statSetObserver()
            isChecking = true
        }

        binding.gender.setOnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                binding.genderAll.id -> {
                    genderSelect = 0
                }
                binding.genderMan.id -> {
                    genderSelect = 1
                }
                binding.genderWoman.id -> {
                    genderSelect = 2
                }
            }
            viewModel.getStatRetrofit(84, ageSelect, genderSelect, tierSelect)
            statSetObserver()
        }

        binding.tier1.setOnCheckedChangeListener { group, checkedId ->
            if (checkedId != -1 && isChecking) {
                isChecking = false
                binding.tier2.clearCheck()
                mCheckedId = checkedId
            }

            when (checkedId) {
                binding.tierAll.id -> {
                    tierSelect = 0
                }
                binding.tierBronze.id -> {
                    tierSelect = 1
                }
                binding.tierSilver.id -> {
                    tierSelect = 2
                }
            }
            viewModel.getStatRetrofit(84, ageSelect, genderSelect, tierSelect)
            statSetObserver()
            isChecking = true
        }
        binding.tier2.setOnCheckedChangeListener { group, checkedId ->
            if (checkedId != -1 && isChecking) {
                isChecking = false
                binding.tier1.clearCheck()
                mCheckedId = checkedId
            }

            when (checkedId) {
                binding.tierGold.id -> {
                    tierSelect = 3
                }
                binding.tierPlatinum.id -> {
                    tierSelect = 4
                }
                binding.tierDiamond.id -> {
                    tierSelect = 5
                }
            }
            viewModel.getStatRetrofit(84, ageSelect, genderSelect, tierSelect)
            statSetObserver()
            isChecking = true
        }

    }

    private fun drawGraph(entries : ArrayList<PieEntry>) {
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
            setEntryLabelColor(Color.BLACK)
            animateY(1400, Easing.EaseInOutQuad)
            animate()
        }
    }

    private fun statSetObserver() {
        viewModel.retrofitStat.observe(this, {
            viewModel.retrofitStat.value?.let {
                entries.clear()
                for (i in 0..it.voteItem.size-1) {
                    if (it.voteItem[i].percent != 0.0) {
                        var persent = (it.voteItem[i].percent*100).toFloat()
                        var msg = it.voteItem[i].contents
                        Log.d("hi", "${persent}, ${msg}")
                        entries.add(PieEntry(persent,msg))
                        drawGraph(entries)
                    }
                }
                Log.d("ttt", entries.toString())
            }
        })
    }
}