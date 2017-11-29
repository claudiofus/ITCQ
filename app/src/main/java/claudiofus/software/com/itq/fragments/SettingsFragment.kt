package claudiofus.software.com.itq.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import claudiofus.software.com.itq.R


class SettingsFragment : Fragment()
{
	override fun onCreateView(inflater : LayoutInflater?, container : ViewGroup?,
	                          savedInstanceState : Bundle?) : View?
	{
		val view = inflater?.inflate(R.layout.fragment_settings, container, false)
		val colorSpinner = view?.findViewById<Spinner>(R.id.colorsSpinner)
		val adapter = ArrayAdapter<String>(view?.context, android.R.layout.simple_spinner_item,
		                                   arrayOf("yellow", "orange", "blue", "purple", "lime"))
		colorSpinner?.adapter = adapter
		colorSpinner?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
			override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
				val selected = parent.getItemAtPosition(position)
				println(selected)
			}

			override fun onNothingSelected(parent: AdapterView<*>) {}
		}

		return view
	}
}