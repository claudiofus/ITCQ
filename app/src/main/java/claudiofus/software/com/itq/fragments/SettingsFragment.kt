package claudiofus.software.com.itq.fragments

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import claudiofus.software.com.itq.MainActivity
import claudiofus.software.com.itq.R
import claudiofus.software.com.itq.utility.Strings.PREFS_COLOR
import claudiofus.software.com.itq.utility.Strings.SELECTED_COLOR
import claudiofus.software.com.itq.utility.ThemeColors.Companion.colorsArray
import claudiofus.software.com.itq.utility.ThemeColors.Companion.colorsMap
import claudiofus.software.com.itq.utility.Utils.writePrefsInt
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import kotlinx.android.synthetic.main.activity_main.*


class SettingsFragment : Fragment()
{
	private var colorSpinner : Spinner? = null
	private var mAdView : AdView? = null

	override fun onCreateView(inflater : LayoutInflater?, container : ViewGroup?,
	                          savedInstanceState : Bundle?) : View?
	{
		activity.nav_view.menu.findItem(R.id.nav_settings).isChecked = true
		var check = false
		val view = inflater?.inflate(R.layout.fragment_settings, container, false)
		mAdView = view?.findViewById(R.id.adViewSettings)
		val adapter = ArrayAdapter<String>(view?.context, R.layout.spinner_item,
		                                   colorsArray)

		colorSpinner = view?.findViewById(R.id.colorsSpinner)
		adapter.setDropDownViewResource(R.layout.spinner_dropdown_item)
		colorSpinner?.adapter = adapter
		colorSpinner?.setSelection(activity.intent.getIntExtra(SELECTED_COLOR, -1))

		colorSpinner?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener
		{
			override fun onItemSelected(parent : AdapterView<*>, view : View, position : Int,
			                            id : Long)
			{
				if (check)
				{
					val selected = parent.getItemAtPosition(position) as String
					writePrefsInt(activity, PREFS_COLOR, colorsMap[selected]!!)
					val intent = Intent(activity, MainActivity::class.java).putExtra(SELECTED_COLOR,
					                                                                 position)
					startActivity(intent)
					check = false
				}
				check = true
			}

			override fun onNothingSelected(parent : AdapterView<*>)
			{
			}
		}

		mAdView?.loadAd(AdRequest.Builder().build())

		return view
	}
}