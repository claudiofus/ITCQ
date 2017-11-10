package claudiofus.software.com.itq.utility

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import claudiofus.software.com.itq.R


class ImageAdapter(var mContext : Context) : BaseAdapter()
{
    val itemsId : Array<String> = arrayOf("Help", "Category", "Graphs", "Share")

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

            if (currItem == "Help") imageView.setImageResource(R.drawable.ic_help)
            else if (currItem == "Category") imageView.setImageResource(R.drawable.ic_action_database)
            else if (currItem == "Graphs") imageView.setImageResource(R.drawable.ic_action_bargraph)
            else imageView.setImageResource(R.drawable.ic_share)
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
}