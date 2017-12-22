package com.software.claudiofus.itcq.activity

import android.os.Bundle
import android.support.design.widget.NavigationView
import com.software.claudiofus.itcq.R
import com.software.claudiofus.itcq.`interface`.GenericActivity
import com.software.claudiofus.itcq.fragment.QuizFragment
import com.software.claudiofus.itcq.utility.Utils.startFragment

class QuizActivity : GenericActivity()
{
	override fun onCreate(savedInstanceState : Bundle?)
	{
		super.onCreate(savedInstanceState)
		val mNavigationView = findViewById<NavigationView>(R.id.nav_view)
		mNavigationView.setNavigationItemSelectedListener(this)
		mNavigationView.menu.findItem(R.id.nav_quiz).isChecked = true
		startFragment(this, QuizFragment())
	}
}