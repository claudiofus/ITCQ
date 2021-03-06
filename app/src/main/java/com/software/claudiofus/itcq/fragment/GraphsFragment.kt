package com.software.claudiofus.itcq.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat.getColor
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.IAxisValueFormatter
import com.github.mikephil.charting.formatter.IValueFormatter
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.software.claudiofus.itcq.R
import com.software.claudiofus.itcq.helper.DbStatement.Companion.selectScoresDB
import com.software.claudiofus.itcq.utility.Strings
import com.software.claudiofus.itcq.utility.Utils
import com.software.claudiofus.itcq.utility.Utils.millisToString
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class GraphsFragment : Fragment()
{
	private var mCorrect : TextView? = null
	private var mUnans : TextView? = null
	private var mWrong : TextView? = null
	private var mChart : LineChart? = null
	private var mAdView : AdView? = null
	private var correct = 0
	private var unanswered = 0
	private var wrong = 0

	override fun onCreateView(inflater : LayoutInflater, container : ViewGroup?,
	                          savedInstanceState : Bundle?) : View?
	{
		activity?.nav_view?.menu?.findItem(R.id.nav_graphs)?.isChecked = true
		val view = inflater.inflate(R.layout.fragment_graphs, container, false)
		val values = arrayListOf<Entry>()
		val fancyTime = arrayListOf<String>()
		val scoresList = selectScoresDB(activity)
		val whiteColor = getColor(context!!, R.color.white)

		for (score in scoresList)
		{
			correct += score.correct
			unanswered += score.unanswered
			wrong += score.wrong
		}

		mCorrect = view?.findViewById(R.id.correct)
		mUnans = view?.findViewById(R.id.unanswered)
		mWrong = view?.findViewById(R.id.wrong)
		mAdView = view?.findViewById(R.id.adViewGraphs)
		mChart = view?.findViewById(R.id.mChart)

		mCorrect?.text = correct.toString()
		mUnans?.text = unanswered.toString()
		mWrong?.text = wrong.toString()
		mChart?.axisLeft?.setDrawAxisLine(false)
		mChart?.axisRight?.isEnabled = false
		mChart?.axisRight?.setDrawAxisLine(false)
		mChart?.description = null
		mChart?.setNoDataText(context!!.getString(R.string.no_score_av))
		mChart?.setNoDataTextColor(whiteColor)
		mChart?.description?.textColor = whiteColor
		mChart?.animateXY(800, 800)
		val xAxis = mChart?.xAxis
		xAxis?.granularity = 1f // minimum axis-step (interval) is 1
		xAxis?.position = XAxis.XAxisPosition.BOTTOM
		xAxis?.setDrawGridLines(false)
		xAxis?.setAvoidFirstLastClipping(true)
		mChart?.setExtraOffsets(0f, 0f, 30f, 0f)

		Collections.sort(scoresList) { o1, o2 -> o1.dateInMillis.compareTo(o2.dateInMillis) }

		var i = 0
		while (i < scoresList.size)
		{
			values.add(Entry(i.toFloat(), scoresList[i].weightedAv.toFloat()))
			fancyTime.add(millisToString(scoresList[i++].dateInMillis.toFloat()))
		}

		val dataSets = ArrayList<ILineDataSet>()
		val lineDataSet = LineDataSet(values, context!!.getString(R.string.score))
		lineDataSet.valueTextSize = 10f
		lineDataSet.valueFormatter = IValueFormatter { value, _, _, _ -> value.toInt().toString() }
		if (Utils.readPrefsInt(activity, Strings.PREFS_COLOR) == R.style.AppTheme_Blue
				|| Utils.readPrefsInt(activity, Strings.PREFS_COLOR) == R.style.AppTheme_Purple)
		{
			mChart?.axisLeft?.textColor = whiteColor
			lineDataSet.valueTextColor = whiteColor
			xAxis?.textColor = whiteColor
		}

		dataSets.add(lineDataSet)
		val data = LineData(dataSets)

		xAxis?.valueFormatter = IAxisValueFormatter { value, _ ->
			if (value > -1 && value <= fancyTime.size - 1) fancyTime[value.toInt()]
			else ""
		}
		if (values.isEmpty()) mChart?.clear() else mChart?.data = data

		mChart?.invalidate()
		mAdView?.loadAd(AdRequest.Builder().build())

		return view
	}
}