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
    private var ageChecking = false
    private var ageText = "All"
    private var genderText = "All"
    private var tierText = "All"

    val colorsItems = ArrayList<Int>()
    var entries = ArrayList<PieEntry>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        // 데이터 바인딩
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        binding.piechart.setUsePercentValues(true)

        val id = intent.getSerializableExtra("id") as Int

        for (c in ColorTemplate.VORDIPLOM_COLORS) colorsItems.add(c)
        for (c in ColorTemplate.JOYFUL_COLORS) colorsItems.add(c)
        for (c in COLORFUL_COLORS) colorsItems.add(c)
        for (c in ColorTemplate.LIBERTY_COLORS) colorsItems.add(c)
        for (c in ColorTemplate.PASTEL_COLORS) colorsItems.add(c)

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

                when (checkedId) {
                    binding.ageAll.id -> {
                        ageSelect = 0
                        ageText = "All"
                    }
                    binding.age10s.id -> {
                        ageSelect = 1
                        ageText = "~10s"
                    }
                    binding.age20s.id -> {
                        ageSelect = 2
                        ageText = "20s"
                    }

                }

                viewModel.getStatRetrofit(id, ageSelect, genderSelect, tierSelect)
                statSetObserver()
                setText()
            }
            isChecking = true
        }
        binding.ageSecondLine.setOnCheckedChangeListener { group, checkedId ->
            if (checkedId != -1 && isChecking) {
                isChecking = false
                binding.ageFirstLine.clearCheck()
                mCheckedId = checkedId

                when (checkedId) {
                    binding.age30s.id -> {
                        ageSelect = 3
                        ageText = "30s"
                    }
                    binding.age40s.id -> {
                        ageSelect = 4
                        ageText = "40s"
                    }
                    binding.age50s.id -> {
                        ageSelect = 5
                        ageText = "50s~"
                    }
                }

                viewModel.getStatRetrofit(id, ageSelect, genderSelect, tierSelect)
                statSetObserver()
                setText()
            }


            isChecking = true
        }

        binding.gender.setOnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                binding.genderAll.id -> {
                    genderSelect = 0
                    genderText = "All"
                }
                binding.genderMan.id -> {
                    genderSelect = 1
                    genderText = "Man"
                }
                binding.genderWoman.id -> {
                    genderSelect = 2
                    genderText = "Woman"
                }
            }
            viewModel.getStatRetrofit(id, ageSelect, genderSelect, tierSelect)
            Log.d("gender 전송", "$ageSelect, $genderSelect, $tierSelect")
            statSetObserver()
            setText()
        }

        binding.tier1.setOnCheckedChangeListener { group, checkedId ->
            if (checkedId != -1 && isChecking) {
                isChecking = false
                binding.tier2.clearCheck()
                mCheckedId = checkedId

                when (checkedId) {
                    binding.tierAll.id -> {
                        tierSelect = 0
                        tierText = "All"
                    }
                    binding.tierBronze.id -> {
                        tierSelect = 1
                        tierText = "Bronze"
                    }
                    binding.tierSilver.id -> {
                        tierSelect = 2
                        tierText = "Silver"
                    }
                }
                viewModel.getStatRetrofit(id, ageSelect, genderSelect, tierSelect)
                statSetObserver()
                setText()

            }



            isChecking = true
        }
        binding.tier2.setOnCheckedChangeListener { group, checkedId ->
            if (checkedId != -1 && isChecking) {
                isChecking = false
                binding.tier1.clearCheck()
                mCheckedId = checkedId

                when (checkedId) {
                    binding.tierGold.id -> {
                        tierSelect = 3
                        tierText = "Gold"
                    }
                    binding.tierPlatinum.id -> {
                        tierSelect = 4
                        tierText = "Platinum"
                    }
                    binding.tierDiamond.id -> {
                        tierSelect = 5
                        tierText = "Diamond"
                    }
                }

                viewModel.getStatRetrofit(id, ageSelect, genderSelect, tierSelect)
                statSetObserver()
                setText()
            }
            isChecking = true
        }

    }

    private fun drawGraph(entries : ArrayList<PieEntry>) {
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

    private fun setText() {
        if (ageText == "All" && genderText == "All" && tierText == "All") {
            binding.statisticsText.text = "Statistics of All ..."
        } else {
            if (ageText != "All") {                 //age
                binding.statisticsText.text = "Statistics of " + ageText + " ..."
                if (tierText != "All") {            //age, tier
                    binding.statisticsText.text =
                        "Statistics of " + ageText + " " + tierText + " ..."
                    if (genderText != "All") {      //age, tier, gender
                        binding.statisticsText.text =
                            "Statistics of " + ageText + " " + tierText + " " + genderText + " ..."
                    }
                } else {                            //age, gender
                    if (genderText != "All") {
                        binding.statisticsText.text =
                            "Statistics of " + ageText + " " + genderText + " ..."
                    }
                }
            } else {
                if (tierText != "All") {            //tier
                    binding.statisticsText.text =
                        "Statistics of " + tierText + " ..."
                    if (genderText != "All") {      //tier, gender
                        binding.statisticsText.text =
                            "Statistics of " + tierText + " " + genderText + " ..."
                    }
                } else {                            //gender
                    if (genderText != "All") {
                        binding.statisticsText.text =
                            "Statistics of " + genderText + " ..."
                    }
                }
            }
        }
    }

    private fun statSetObserver() {
        viewModel.retrofitStat.observe(this, {
            viewModel.retrofitStat.value?.let {
                entries.clear()
                for (i in 0..it.voteItem.size-1) {
                    var persent = (it.voteItem[i].percent*100).toFloat()
                    var msg = it.voteItem[i].contents
                    entries.add(PieEntry(persent,msg))
                }
                drawGraph(entries)
            }
        })
    }
}