package claudiofus.software.com.itq.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
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
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class GraphsFragment : Fragment()
{
	private var mChart : LineChart? = null
	override fun onCreateView(inflater : LayoutInflater?, container : ViewGroup?,
	                          savedInstanceState : Bundle?) : View?
	{
		activity.nav_view.menu.findItem(R.id.nav_graphs).isChecked = true
		val view = inflater?.inflate(R.layout.fragment_graphs, container, false)
		val values = arrayListOf<Entry>()
		val fancyTime = arrayListOf<String>()
		val scoresList = selectScoresDB(activity)

		mChart = view?.findViewById(R.id.mChart)
		mChart?.axisLeft?.setDrawAxisLine(false)
		mChart?.axisRight?.isEnabled = false
		mChart?.axisRight?.setDrawAxisLine(false)
		mChart?.description = null
		mChart?.setNoDataText(context.getString(R.string.no_score_av))
		mChart?.setNoDataTextColor(ContextCompat.getColor(context, R.color.white))
		mChart?.animateXY(500, 500)
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
			values.add(Entry(i.toFloat(), scoresList[i].score.toFloat()))
			fancyTime.add(millisToString(scoresList[i++].dateInMillis.toFloat()))
		}

		val dataSets = ArrayList<ILineDataSet>()
		dataSets.add(LineDataSet(values, context.getString(R.string.score)))
		val data = LineData(dataSets)

		xAxis?.valueFormatter = IAxisValueFormatter { value, axis -> fancyTime[value.toInt()] }
		if (values.isEmpty() || values.size == 1) mChart?.clear() else mChart?.data = data
		mChart?.invalidate()

		//TODO REMOVE COMMENT
		//val mAdView = view?.findViewById<AdView>(R.id.adView)
		//mAdView?.loadAd(AdRequest.Builder().build())
		return view
	}
}