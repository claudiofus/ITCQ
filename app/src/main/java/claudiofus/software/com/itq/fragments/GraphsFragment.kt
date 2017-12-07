package claudiofus.software.com.itq.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat.getColor
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import claudiofus.software.com.itq.R
import claudiofus.software.com.itq.helper.DbStatement.Companion.selectScoresDB
import claudiofus.software.com.itq.utility.Utils.millisToString
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
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class GraphsFragment : Fragment()
{
	private var mChart : LineChart? = null
	private var mAdView : AdView? = null
	override fun onCreateView(inflater : LayoutInflater?, container : ViewGroup?,
	                          savedInstanceState : Bundle?) : View?
	{
		activity.nav_view.menu.findItem(R.id.nav_graphs).isChecked = true
		val view = inflater?.inflate(R.layout.fragment_graphs, container, false)
		val values = arrayListOf<Entry>()
		val fancyTime = arrayListOf<String>()
		val scoresList = selectScoresDB(activity)

		mAdView = view?.findViewById(R.id.adViewGraphs)
		mChart = view?.findViewById(R.id.mChart)
		mChart?.axisLeft?.setDrawAxisLine(false)
		mChart?.axisRight?.isEnabled = false
		mChart?.axisRight?.setDrawAxisLine(false)
		mChart?.description = null
		mChart?.setNoDataText(context.getString(R.string.no_score_av))
		mChart?.setNoDataTextColor(getColor(context, R.color.white))
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
		val lineDataSet = LineDataSet(values, context.getString(R.string.score))
		lineDataSet.valueTextSize = 10f
		lineDataSet.valueFormatter = IValueFormatter { value, _, _, _ -> value.toInt().toString() }
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