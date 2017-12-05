package claudiofus.software.com.itq.adapters

import android.content.Context
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import claudiofus.software.com.itq.R
import claudiofus.software.com.itq.fragments.CategoryFragment
import claudiofus.software.com.itq.fragments.GraphsFragment
import claudiofus.software.com.itq.fragments.QuizFragment
import claudiofus.software.com.itq.utility.Utils

class ImageAdapter(private var mContext : Context) : BaseAdapter()
{
	private val itemsId : Array<String> = arrayOf(mContext.getString(R.string.quiz),
	                                              mContext.getString(R.string.category),
	                                              mContext.getString(R.string.graphs),
	                                              mContext.getString(R.string.share))

	override fun getView(position : Int, convertView : View?, parent : ViewGroup?) : View
	{
		val inflater = mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
		val gridView : View

		if (convertView != null)
		{
			gridView = convertView
		}
		else
		{
			// get layout from mobile.xml
			gridView = inflater.inflate(R.layout.home_grid_item, parent, false)
			val currItem = itemsId[position]

			// set value into textview
			val textView = gridView.findViewById<TextView>(R.id.grid_item_text)
			textView.text = currItem

			// set image based on selected text
			val imageView = gridView.findViewById<ImageView>(R.id.grid_item_image)

			when (currItem)
			{
				itemsId[0] -> imageView.setImageResource(R.drawable.ic_help)
				itemsId[1] -> imageView.setImageResource(R.drawable.ic_action_database)
				itemsId[2] -> imageView.setImageResource(R.drawable.ic_action_bargraph)
				itemsId[3] -> imageView.setImageResource(R.drawable.ic_share)
			}

			gridView.setOnClickListener({ openFragment(currItem)})
		}

		return gridView
	}

	override fun getItem(position : Int) : Any
	{
		return itemsId[position]
	}

	override fun getItemId(position : Int) : Long
	{
		return 0
	}

	override fun getCount() : Int
	{
		return itemsId.size
	}

	private fun openFragment(currItem : String)
	{
		val fragment : Fragment = when (currItem)
		{
			itemsId[0] -> QuizFragment()
			itemsId[1] -> CategoryFragment()
			itemsId[2] -> GraphsFragment()
			else       ->
			{
				Utils.shareApp(mContext)
				return
			}
		}
		val activity = mContext as FragmentActivity
		activity.supportFragmentManager.beginTransaction().replace(R.id.content, fragment).commit()
	}
}