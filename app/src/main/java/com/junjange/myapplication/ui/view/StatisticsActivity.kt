package com.junjange.myapplication.ui.view

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.utils.ColorTemplate
import com.github.mikephil.charting.utils.ColorTemplate.COLORFUL_COLORS
import com.junjange.myapplication.databinding.ActivityStatisticsBinding

class StatisticsActivity : AppCompatActivity() {
    lateinit var binding: ActivityStatisticsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStatisticsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.piechart.setUsePercentValues(true)

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
}