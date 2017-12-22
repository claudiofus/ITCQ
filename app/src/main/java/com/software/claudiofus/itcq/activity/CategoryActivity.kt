package com.software.claudiofus.itcq.activity

import android.os.Bundle
import android.support.design.widget.NavigationView
import com.software.claudiofus.itcq.R
import com.software.claudiofus.itcq.`interface`.GenericActivity
import com.software.claudiofus.itcq.fragment.CategoryFragment
import com.software.claudiofus.itcq.utility.Utils.startFragment

class CategoryActivity : GenericActivity()
{
	override fun onCreate(savedInstanceState : Bundle?)
	{
		super.onCreate(savedInstanceState)
		val mNavigationView = findViewById<NavigationView>(R.id.nav_view)
		mNavigationView.setNavigationItemSelectedListener(this)
		mNavigationView.menu.findItem(R.id.nav_category).isChecked = true
		startFragment(this, CategoryFragment())
	}
}