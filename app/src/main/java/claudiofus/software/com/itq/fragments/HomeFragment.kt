package claudiofus.software.com.itq.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridView
import claudiofus.software.com.itq.R
import claudiofus.software.com.itq.utility.ImageAdapter
import com.google.android.gms.ads.AdView


class HomeFragment : Fragment()
{
    override fun onCreateView(inflater : LayoutInflater?, container : ViewGroup?,
                              savedInstanceState : Bundle?) : View?
    {
        val view = inflater?.inflate(R.layout.fragment_home, container, false)
        val mAdView = view?.findViewById<AdView>(R.id.adView)
        val mGridView = view?.findViewById<GridView>(R.id.gridview)
        mGridView?.adapter = ImageAdapter(this.context)


        //TODO REMOVE COMMENT
        //mAdView?.loadAd(AdRequest.Builder().build())

        return view
    }
}