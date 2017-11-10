package claudiofus.software.com.itq

import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.MenuItem

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener
{
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
        supportFragmentManager.beginTransaction().replace(R.id.content, HomeFragment()).commit()
    }

    override fun onNavigationItemSelected(item : MenuItem) : Boolean
    {
        //val id = item.itemId
        val fragment = HomeFragment()
        val mDrawer = findViewById<DrawerLayout>(R.id.drawer_layout)
        val mNavigationView = findViewById<NavigationView>(R.id.nav_view)
        mNavigationView.setNavigationItemSelectedListener(this)

        supportFragmentManager.beginTransaction().replace(R.id.content, fragment).commit()
        mDrawer.closeDrawer(GravityCompat.START)
        return true
    }
}