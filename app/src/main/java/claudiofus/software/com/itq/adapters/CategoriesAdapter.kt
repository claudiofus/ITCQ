package claudiofus.software.com.itq.adapters

import android.app.Activity
import android.content.Context
import android.graphics.drawable.AnimationDrawable
import android.graphics.drawable.Drawable
import android.support.v4.content.ContextCompat.getColor
import android.support.v4.content.ContextCompat.getDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import claudiofus.software.com.itq.R
import claudiofus.software.com.itq.utility.TextDrawable
import claudiofus.software.com.itq.utility.Utils.changeDrawableColor

class CategoriesAdapter(private val activity : Activity, categories : List<String>) : BaseAdapter()
{
	private val mDataSource = DataSource(categories, activity.applicationContext)
	override fun getCount() : Int
	{
		return mDataSource.count
	}

	override fun getItem(position : Int) : DataItem
	{
		return mDataSource.getItem(position)
	}

	override fun getItemId(position : Int) : Long
	{
		return position.toLong()
	}

	override fun getView(position : Int, convertView : View?, parent : ViewGroup) : View
	{
		var convertView = convertView
		val holder : ViewHolder
		if (convertView == null)
		{
			val inflater = activity.applicationContext.getSystemService(
					Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
			convertView = inflater.inflate(R.layout.list_item_layout, parent, false)
			holder = ViewHolder(convertView)
			convertView!!.tag = holder
		}
		else
		{
			holder = convertView.tag as ViewHolder
		}

		val item = getItem(position)
		val drawable = item.drawable
		holder.imageView.setImageDrawable(drawable)
		holder.textView.text = item.label

		// fix for animation not playing for some below 4.4 devices
		if (drawable is AnimationDrawable)
		{
			holder.imageView.post({
				                      (drawable).stop()
				                      (drawable).start()
			                      })
		}

		return convertView
	}
}

private class ViewHolder constructor(view : View)
{
	val imageView : ImageView = view.findViewById(R.id.imageView)
	val textView : TextView = view.findViewById(R.id.textView)
}

class DataItem(val label : String, val drawable : Drawable)

class DataSource(categories : List<String>, val context : Context)
{
	private val mDataSource : ArrayList<DataItem> = ArrayList()
	private val mProvider : DrawableProvider = DrawableProvider()

	val count : Int
		get() = mDataSource.size

	init
	{
		categories.mapTo(mDataSource) { DataItem(it, drawableForLabel(it)) }
	}

	fun getItem(position : Int) : DataItem
	{
		return mDataSource[position]
	}

	private fun drawableForLabel(category : String) : Drawable
	{
		var drawable : Drawable? = null
		val color = getColor(context, R.color.darkestColor)
		when (category)
		{
			"network"          -> drawable = getDrawable(context, R.drawable.ic_network)
			"os"               -> drawable = getDrawable(context, R.drawable.ic_settings)
			"data-structures"  -> drawable = getDrawable(context, R.drawable.ic_data_structure)
			"server-software"  -> drawable = getDrawable(context, R.drawable.ic_server)
			"hardware"         -> drawable = getDrawable(context, R.drawable.ic_hardware)
			"tools"            -> drawable = getDrawable(context, R.drawable.ic_tools)
			"security"         -> drawable = getDrawable(context, R.drawable.ic_security)
			"programming"      -> drawable = getDrawable(context, R.drawable.ic_programming)
			"history"          -> drawable = getDrawable(context, R.drawable.ic_history)
			"virtualization"   -> drawable = getDrawable(context, R.drawable.ic_virtualization)
			"version-control"  -> drawable = getDrawable(context, R.drawable.ic_merge)
			"build-automation" -> drawable = getDrawable(context, R.drawable.ic_tools)
			"windows"          -> drawable = getDrawable(context, R.drawable.ic_windows)
			"linux"            -> drawable = getDrawable(context, R.drawable.ic_linux)
		}

		return if (drawable != null) changeDrawableColor(drawable, color)
		else mProvider.getRound(category[0].toUpperCase().toString())
	}

	class DrawableProvider
	{
		fun getRound(text : String) : TextDrawable
		{
			return TextDrawable.builder().buildRound(text, R.color.red)
		}
	}
}