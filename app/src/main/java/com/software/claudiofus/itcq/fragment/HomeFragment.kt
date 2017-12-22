package com.software.claudiofus.itcq.fragment

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.software.claudiofus.itcq.R
import com.software.claudiofus.itcq.activity.CategoryActivity
import com.software.claudiofus.itcq.activity.GraphActivity
import com.software.claudiofus.itcq.activity.QuizActivity
import com.software.claudiofus.itcq.activity.SettingsActivity
import kotlinx.android.synthetic.main.activity_main.*

class HomeFragment : Fragment()
{
	private var mQuiz : TextView? = null
	private var mCategory : TextView? = null
	private var mGraphs : TextView? = null
	private var mHelp : TextView? = null
	private var mAdView : AdView? = null

	override fun onCreateView(inflater : LayoutInflater, container : ViewGroup?,
	                          savedInstanceState : Bundle?) : View?
	{
		activity?.nav_view?.menu?.findItem(R.id.nav_home)?.isChecked = true
		val view = inflater.inflate(R.layout.fragment_home, container, false)
		mQuiz = view?.findViewById(R.id.quizHome)
		mCategory = view?.findViewById(R.id.categoryHome)
		mGraphs = view?.findViewById(R.id.graphsHome)
		mHelp = view?.findViewById(R.id.helpHome)

		mQuiz?.setOnClickListener {
			context?.startActivity(Intent(context, QuizActivity::class.java))
		}
		mCategory?.setOnClickListener {
			context?.startActivity(Intent(context, CategoryActivity::class.java))
		}
		mGraphs?.setOnClickListener {
			context?.startActivity(Intent(context, GraphActivity::class.java))
		}
		mHelp?.setOnClickListener {
			context?.startActivity(Intent(context, SettingsActivity::class.java))
		}

		mAdView = view?.findViewById(R.id.adViewHome)
		mAdView?.loadAd(AdRequest.Builder().build())

		return view
	}
}