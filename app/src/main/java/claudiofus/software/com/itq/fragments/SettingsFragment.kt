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
import claudiofus.software.com.itq.utility.ThemeColors.Companion.colorsArray
import claudiofus.software.com.itq.utility.ThemeColors.Companion.colorsMap
import claudiofus.software.com.itq.utility.Utils
import claudiofus.software.com.itq.utility.Utils.writePrefsInt
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import kotlinx.android.synthetic.main.activity_main.*


class SettingsFragment : Fragment()
{
	private var colorSpinner : Spinner? = null
	private var mAdView : AdView? = null

	override fun onCreateView(inflater : LayoutInflater? , container : ViewGroup? ,
	                          savedInstanceState : Bundle?) : View?
	{
		activity.nav_view.menu.findItem(R.id.nav_settings).isChecked = true
		var check = false
		val view = inflater?.inflate(R.layout.fragment_settings , container , false)
		mAdView = view?.findViewById(R.id.adViewSettings)
		val adapter = ArrayAdapter<String>(
				view?.context , R.layout.spinner_item ,
				colorsArray
		                                  )

		colorSpinner = view?.findViewById(R.id.colorsSpinner)
		adapter.setDropDownViewResource(R.layout.spinner_dropdown_item)
		colorSpinner?.adapter = adapter
		val theme = Utils.readPrefsInt(activity , PREFS_COLOR)
		val themeName = Utils.getKeyByValue(colorsMap , if (theme == -1) R.style.AppTheme else theme)
		var index = 0
		when
		{
			"orange".equals(themeName) -> index = 0
			"blue".equals(themeName)   -> index = 1
			"purple".equals(themeName) -> index = 2
			"green".equals(themeName)  -> index = 3
		}

		colorSpinner?.setSelection(index)

		colorSpinner?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener
		{
			override fun onItemSelected(parent : AdapterView<*> , view : View , position : Int ,
			                            id : Long)
			{
				if (check)
				{
					val selected = parent.getItemAtPosition(position) as String
					writePrefsInt(activity , PREFS_COLOR , colorsMap[selected]!!)
					val intent = Intent(activity , MainActivity::class.java)
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