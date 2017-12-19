package claudiofus.software.com.itq.`interface`

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.support.v7.app.AppCompatDelegate
import android.support.v7.content.res.AppCompatResources
import android.support.v7.widget.Toolbar
import android.view.MenuItem
import android.widget.Toast
import claudiofus.software.com.itq.R
import claudiofus.software.com.itq.activity.*
import claudiofus.software.com.itq.fragment.HomeFragment
import claudiofus.software.com.itq.utility.Strings
import claudiofus.software.com.itq.utility.ThemeColors
import claudiofus.software.com.itq.utility.Utils

abstract class GenericActivity : AppCompatActivity(),
                                 NavigationView.OnNavigationItemSelectedListener
{
	/**
	 * Flag used to close current Fragment.
	 */
	private var _toClose = false
	private lateinit var toolbar : Toolbar
	private lateinit var mDrawer : DrawerLayout
	private lateinit var mNavigationView : NavigationView

	override fun onCreate(savedInstanceState : Bundle?)
	{
		super.onCreate(savedInstanceState)
		val theme = Utils.readPrefsInt(this, Strings.PREFS_COLOR)
		val currTheme = if (theme == -1) R.style.AppTheme else theme
		val darkPrimary = ThemeColors.darkPrimaryMaps[Utils.getKeyByValue(ThemeColors.colorsMap,
		                                                                  currTheme)]
		setTheme(currTheme)

		AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)
		setContentView(R.layout.activity_main)

		toolbar = findViewById(R.id.toolbar)
		mDrawer = findViewById(R.id.drawer_layout)
		mNavigationView = findViewById(R.id.nav_view)
		mNavigationView.itemIconTintList = AppCompatResources.getColorStateList(this, darkPrimary!!)
		setSupportActionBar(toolbar)

		val toggle = ActionBarDrawerToggle(this, mDrawer, toolbar, R.string.navigation_drawer_open,
		                                   R.string.navigation_drawer_close)

		mDrawer.addDrawerListener(toggle)
		toggle.syncState()
	}

	override fun onNavigationItemSelected(item : MenuItem) : Boolean
	{
		// Assume fragment is never null
		var intent = Intent(this, MainActivity::class.java)
		val id = item.itemId
		mDrawer = findViewById(R.id.drawer_layout)
		mNavigationView = findViewById(R.id.nav_view)
		mNavigationView.setNavigationItemSelectedListener(this)
		mNavigationView.menu.findItem(R.id.nav_home).isChecked = true

		when (id)
		{
			R.id.nav_quiz     -> intent = Intent(this, QuizActivity::class.java)
			R.id.nav_category -> intent = Intent(this, CategoryActivity::class.java)
			R.id.nav_graphs   -> intent = Intent(this, GraphActivity::class.java)
			R.id.nav_settings -> intent = Intent(this, SettingsActivity::class.java)
			R.id.nav_share    ->
			{
				Utils.shareApp(mNavigationView.context)
				return true
			}
		}

		startActivity(intent)
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
			finishAffinity() // finish activity
		}
		else
		{
			Toast.makeText(this, getString(R.string.back_button), Toast.LENGTH_SHORT).show()
			_toClose = true
			Handler().postDelayed({ _toClose = false }, 2 * 1000 /* 2 seconds */)
			mNavigationView = findViewById(R.id.nav_view)
			mNavigationView.setNavigationItemSelectedListener(this)
			mNavigationView.menu.findItem(R.id.nav_home).isChecked = true
			Utils.startFragment(this, HomeFragment())
		}

		mDrawer = findViewById(R.id.drawer_layout)
		if (mDrawer.isDrawerOpen(GravityCompat.START))
		{
			mDrawer.closeDrawer(GravityCompat.START)
		}
	}
}