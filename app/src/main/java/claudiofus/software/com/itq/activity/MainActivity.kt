package claudiofus.software.com.itq.activity

import android.os.Bundle
import android.support.design.widget.NavigationView
import claudiofus.software.com.itq.R
import claudiofus.software.com.itq.`interface`.GenericActivity
import claudiofus.software.com.itq.fragment.HomeFragment
import claudiofus.software.com.itq.utility.Utils.startFragment

class MainActivity : GenericActivity()
{
	override fun onCreate(savedInstanceState : Bundle?)
	{
		super.onCreate(savedInstanceState)
		val mNavigationView = findViewById<NavigationView>(R.id.nav_view)
		mNavigationView.setNavigationItemSelectedListener(this)
		mNavigationView.menu.findItem(R.id.nav_home).isChecked = true
		startFragment(this, HomeFragment())
	}
}