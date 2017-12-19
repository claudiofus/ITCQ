package claudiofus.software.com.itq.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import claudiofus.software.com.itq.R
import claudiofus.software.com.itq.activity.CategoryActivity
import claudiofus.software.com.itq.activity.GraphActivity
import claudiofus.software.com.itq.activity.QuizActivity
import claudiofus.software.com.itq.activity.SettingsActivity

class ImageAdapter(private var mContext : Context?) : BaseAdapter()
{
	private val itemsId : Array<String> = arrayOf(mContext!!.getString(R.string.quiz),
	                                              mContext!!.getString(R.string.category),
	                                              mContext!!.getString(R.string.graphs),
	                                              mContext!!.getString(R.string.help))

	override fun getView(position : Int, convertView : View?, parent : ViewGroup?) : View
	{
		val inflater = mContext?.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
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
				itemsId[0] -> imageView.setImageResource(R.drawable.ic_developer_mode)
				itemsId[1] -> imageView.setImageResource(R.drawable.ic_action_database)
				itemsId[2] -> imageView.setImageResource(R.drawable.ic_action_bargraph)
				itemsId[3] -> imageView.setImageResource(R.drawable.ic_help_outline)
			}

			gridView.setOnClickListener({ openFragment(currItem) })
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
		val intent : Intent = when (currItem)
		{
			itemsId[0] -> Intent(mContext, QuizActivity::class.java)
			itemsId[1] -> Intent(mContext, CategoryActivity::class.java)
			itemsId[2] -> Intent(mContext, GraphActivity::class.java)
			else       -> Intent(mContext, SettingsActivity::class.java)
		}

		mContext?.startActivity(intent)
	}
}