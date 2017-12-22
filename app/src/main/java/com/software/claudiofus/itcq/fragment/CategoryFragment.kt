package com.software.claudiofus.itcq.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.software.claudiofus.itcq.R
import com.software.claudiofus.itcq.adapter.CategoriesAdapter
import com.software.claudiofus.itcq.adapter.DataItem
import com.software.claudiofus.itcq.helper.DbStatement.Companion.selectCategoriesDB
import com.software.claudiofus.itcq.utility.Strings
import com.software.claudiofus.itcq.utility.Utils.startFragment
import kotlinx.android.synthetic.main.activity_main.*

class CategoryFragment : Fragment()
{
	private var categoryList : ListView? = null
	private var mAdView : AdView? = null

	override fun onCreateView(inflater : LayoutInflater, container : ViewGroup?,
	                          savedInstanceState : Bundle?) : View?
	{
		activity?.nav_view?.menu?.findItem(R.id.nav_category)?.isChecked = true
		val view = inflater.inflate(R.layout.fragment_category, container, false)
		mAdView = view?.findViewById(R.id.adViewCategory)
		categoryList = view?.findViewById(R.id.categoryList)
		val categories = selectCategoriesDB(activity)
		val args = Bundle()

		categoryList?.adapter = CategoriesAdapter(activity, categories)
		categoryList?.setOnItemClickListener { parent, _, position, _ ->
			val dataItem = parent.getItemAtPosition(position) as DataItem
			val category = dataItem.label
			args.putString(Strings.CATEGORY_KEY, category)
			val fragment = QuizFragment()
			fragment.arguments = args
			startFragment(activity, fragment)
		}

		mAdView?.loadAd(AdRequest.Builder().build())

		return view
	}
}