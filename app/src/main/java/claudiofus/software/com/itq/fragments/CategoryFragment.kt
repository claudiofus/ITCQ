package claudiofus.software.com.itq.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import claudiofus.software.com.itq.R
import claudiofus.software.com.itq.adapters.CategoriesAdapter
import claudiofus.software.com.itq.adapters.DataItem
import claudiofus.software.com.itq.helper.DbStatement.Companion.selectCategoriesDB
import claudiofus.software.com.itq.utility.Strings
import claudiofus.software.com.itq.utility.Utils.startFragment
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import kotlinx.android.synthetic.main.activity_main.*

class CategoryFragment : Fragment()
{
	private var categoryList : ListView? = null
	private var mAdView : AdView? = null

	override fun onCreateView(inflater : LayoutInflater?, container : ViewGroup?,
	                          savedInstanceState : Bundle?) : View?
	{
		activity.nav_view.menu.findItem(R.id.nav_category).isChecked = true
		val view = inflater?.inflate(R.layout.fragment_category, container, false)
		mAdView = view?.findViewById(R.id.adViewCategory)
		categoryList = view?.findViewById(R.id.categoryList)
		val categories = selectCategoriesDB(activity)
		val args = Bundle()

		categoryList?.adapter = CategoriesAdapter(activity, categories)
		categoryList?.setOnItemClickListener { parent, view, position, id ->
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