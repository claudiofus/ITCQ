package claudiofus.software.com.itq

import android.os.Bundle
import android.os.Handler
import android.support.design.widget.NavigationView
import android.support.v4.app.Fragment
import android.support.v4.view.GravityCompat
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.support.v7.app.AppCompatDelegate
import android.support.v7.content.res.AppCompatResources
import android.support.v7.widget.Toolbar
import android.view.MenuItem
import android.widget.Toast
import claudiofus.software.com.itq.fragments.*
import claudiofus.software.com.itq.utility.Strings.PREFS_COLOR
import claudiofus.software.com.itq.utility.ThemeColors.Companion.colorsMap
import claudiofus.software.com.itq.utility.ThemeColors.Companion.darkPrimaryMaps
import claudiofus.software.com.itq.utility.Utils
import claudiofus.software.com.itq.utility.Utils.getKeyByValue
import claudiofus.software.com.itq.utility.Utils.readPrefsInt
import claudiofus.software.com.itq.utility.Utils.startFragment

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener
{
	/**
	 * Flag used to close current Fragment.
	 */
	private var _toClose = false


	override fun onCreate(savedInstanceState : Bundle?)
	{
		val theme = readPrefsInt(this, PREFS_COLOR)
		val currTheme = if (theme == -1) R.style.AppTheme else theme
		val darkPrimary = darkPrimaryMaps[getKeyByValue(colorsMap, currTheme)]
		setTheme(currTheme)

		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_main)
		AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)

		val toolbar = findViewById<Toolbar>(R.id.toolbar)
		val mDrawer = findViewById<DrawerLayout>(R.id.drawer_layout)
		val mNavigationView = findViewById<NavigationView>(R.id.nav_view)
		mNavigationView.itemIconTintList = AppCompatResources.getColorStateList(this, darkPrimary!!)
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
			R.id.nav_graphs   -> fragment = GraphsFragment()
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
		if (_toClose)
		{
			finish() // finish activity
		}
		else
		{
			Toast.makeText(this, getString(R.string.back_button), Toast.LENGTH_SHORT).show()
			_toClose = true
			Handler().postDelayed({ _toClose = false }, 2 * 1000 /* 2 seconds */)
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