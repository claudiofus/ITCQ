package claudiofus.software.com.itq

import android.os.Bundle
import android.os.Handler
import android.support.design.widget.NavigationView
import android.support.v4.app.Fragment
import android.support.v4.view.GravityCompat
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.MenuItem
import android.widget.Toast
import claudiofus.software.com.itq.fragments.CategoryFragment
import claudiofus.software.com.itq.fragments.HomeFragment
import claudiofus.software.com.itq.fragments.QuizFragment
import claudiofus.software.com.itq.fragments.SettingsFragment
import claudiofus.software.com.itq.utility.Utils
import claudiofus.software.com.itq.utility.Utils.startFragment

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener
{
	/**
	 * Flag used to close current Fragment.
	 */
	private var TO_CLOSE = false

	override fun onCreate(savedInstanceState : Bundle?)
	{
		super.onCreate(savedInstanceState)

		setContentView(R.layout.activity_main)

		val toolbar = findViewById<Toolbar>(R.id.toolbar)
		val mDrawer = findViewById<DrawerLayout>(R.id.drawer_layout)
		val mNavigationView = findViewById<NavigationView>(R.id.nav_view)
		setSupportActionBar(toolbar)

		val toggle = ActionBarDrawerToggle(this, mDrawer, toolbar, R.string.navigation_drawer_open,
		                                   R.string.navigation_drawer_close)

		mDrawer?.addDrawerListener(toggle)

		toggle.syncState()

		mNavigationView.setNavigationItemSelectedListener(this)
		mNavigationView.menu.findItem(R.id.nav_home).isChecked = true
		startFragment(this, HomeFragment())
	}

	override fun onNavigationItemSelected(item : MenuItem) : Boolean
	{
		// Assume fragment is never null
		var fragment : Fragment
		fragment = HomeFragment()
		val id = item.itemId
		val mDrawer = findViewById<DrawerLayout>(R.id.drawer_layout)
		val mNavigationView = findViewById<NavigationView>(R.id.nav_view)
		mNavigationView.setNavigationItemSelectedListener(this)
		mNavigationView.menu.findItem(R.id.nav_home).isChecked = true

		when (id)
		{
			R.id.nav_quiz     -> fragment = QuizFragment()
			R.id.nav_category -> fragment = CategoryFragment()
		//        else if (id == R.id.nav_graphs)
		//            fragment = GraphsFragment()
			R.id.nav_settings -> fragment = SettingsFragment()
			R.id.nav_share    ->
			{
				Utils.shareApp(mNavigationView.context)
				return true
			}
		}

		startFragment(this, fragment)
		mDrawer.closeDrawer(GravityCompat.START)
		return true
	}

	/**
	 * Override AppCompatActivity onBackPressed method.
	 */
	override fun onBackPressed()
	{
		if (TO_CLOSE)
		{
			finish() // finish activity
		}
		else
		{
			Toast.makeText(this, getString(R.string.back_button), Toast.LENGTH_SHORT).show()
			TO_CLOSE = true
			Handler().postDelayed({ TO_CLOSE = false }, 3 * 1000 /* 3 seconds */)
		}

		if (supportFragmentManager.backStackEntryCount == 0)
		{
			val navigationView = findViewById<NavigationView>(R.id.nav_view)
			navigationView.setNavigationItemSelectedListener(this)
			navigationView.menu.findItem(R.id.nav_home).isChecked = true
			startFragment(this, HomeFragment())
		}
		else
		{
			supportFragmentManager.popBackStack()
		}

		val drawer = findViewById<DrawerLayout>(R.id.drawer_layout)
		if (drawer.isDrawerOpen(GravityCompat.START))
		{
			drawer.closeDrawer(GravityCompat.START)
		}
	}
}