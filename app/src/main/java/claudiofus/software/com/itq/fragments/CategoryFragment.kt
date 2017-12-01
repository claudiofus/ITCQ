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
import claudiofus.software.com.itq.helper.database
import claudiofus.software.com.itq.model.Question
import claudiofus.software.com.itq.utility.Strings
import claudiofus.software.com.itq.utility.Utils.startFragment
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.select

class CategoryFragment : Fragment()
{
	override fun onCreateView(inflater : LayoutInflater?, container : ViewGroup?,
	                          savedInstanceState : Bundle?) : View?
	{
		activity.nav_view.menu.findItem(R.id.nav_category).isChecked = true
		val view = inflater?.inflate(R.layout.fragment_category, container, false)
		val categoryList = view?.findViewById<ListView>(R.id.categoryList)
		var categories : List<String> = arrayListOf()
		val args = Bundle()

		activity.database.use {
			categories = select(Question.TABLE_NAME).distinct().column(
					Question.COLUMN_CATEGORY).parseList(classParser())
		}

		categoryList?.adapter = CategoriesAdapter(activity, categories)
		categoryList?.setOnItemClickListener { parent, view, position, id ->
			val dataItem = parent.getItemAtPosition(position) as DataItem
			val category = dataItem.label
			args.putString(Strings.CATEGORY_KEY, category)
			val fragment = QuizFragment()
			fragment.arguments = args
			startFragment(activity, fragment)
		}

		//TODO REMOVE COMMENT
		//val mAdView = view?.findViewById<AdView>(R.id.adView)
		//mAdView?.loadAd(AdRequest.Builder().build())
		return view
	}
}