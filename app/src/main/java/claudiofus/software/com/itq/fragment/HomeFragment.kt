package claudiofus.software.com.itq.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridView
import claudiofus.software.com.itq.R
import claudiofus.software.com.itq.adapter.ImageAdapter
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import kotlinx.android.synthetic.main.activity_main.*


class HomeFragment : Fragment()
{
	private var mGridView : GridView? = null
	private var mAdView : AdView? = null

	override fun onCreateView(inflater : LayoutInflater, container : ViewGroup?,
	                          savedInstanceState : Bundle?) : View?
	{
		activity?.nav_view?.menu?.findItem(R.id.nav_home)?.isChecked = true
		val view = inflater.inflate(R.layout.fragment_home, container, false)
		mGridView = view?.findViewById(R.id.gridview)
		mAdView = view?.findViewById(R.id.adViewHome)
		mGridView?.adapter = ImageAdapter(this.context)
		mAdView?.loadAd(AdRequest.Builder().build())

		return view
	}
}