package com.software.claudiofus.itcq.fragment

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.software.claudiofus.itcq.R
import com.software.claudiofus.itcq.activity.SettingsActivity
import com.software.claudiofus.itcq.utility.Strings.PREFS_COLOR
import com.software.claudiofus.itcq.utility.ThemeColors.Companion.colorsArray
import com.software.claudiofus.itcq.utility.ThemeColors.Companion.colorsMap
import com.software.claudiofus.itcq.utility.Utils
import com.software.claudiofus.itcq.utility.Utils.writePrefsInt
import kotlinx.android.synthetic.main.activity_main.*


class SettingsFragment : Fragment()
{
	private var colorSpinner : Spinner? = null
	private var mAdView : AdView? = null

	override fun onCreateView(inflater : LayoutInflater, container : ViewGroup?,
	                          savedInstanceState : Bundle?) : View?
	{
		activity?.nav_view?.menu?.findItem(R.id.nav_settings)?.isChecked = true
		var check = false
		val view = inflater.inflate(R.layout.fragment_settings, container, false)
		mAdView = view?.findViewById(R.id.adViewSettings)
		val adapter = ArrayAdapter<String>(view?.context, R.layout.spinner_item, colorsArray)

		colorSpinner = view?.findViewById(R.id.colorsSpinner)
		adapter.setDropDownViewResource(R.layout.spinner_dropdown_item)
		colorSpinner?.adapter = adapter
		val theme = Utils.readPrefsInt(activity, PREFS_COLOR)
		val themeName = Utils.getKeyByValue(colorsMap, if (theme == -1) R.style.AppTheme else theme)
		var index = 0
		when (themeName)
		{
			"orange", "arancio" -> index = 0
			"blue",   "blu"     -> index = 1
			"purple", "viola"   -> index = 2
			"green",  "verde"   -> index = 3
		}

		colorSpinner?.setSelection(index)

		colorSpinner?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener
		{
			override fun onItemSelected(parent : AdapterView<*>, view : View, position : Int,
			                            id : Long)
			{
				if (check)
				{
					val selected = parent.getItemAtPosition(position) as String
					writePrefsInt(activity, PREFS_COLOR, colorsMap[selected]!!)
					val intent = Intent(activity, SettingsActivity::class.java)
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