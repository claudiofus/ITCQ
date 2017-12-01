package claudiofus.software.com.itq.utility

import android.content.Context
import android.content.Intent
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.TextView
import android.widget.ToggleButton
import claudiofus.software.com.itq.R
import java.util.*


object Utils
{

	/**
	 * Share the current app.
	 * @param ctx context of application
	 */
	fun shareApp(ctx : Context)
	{
		val sharingIntent = Intent(Intent.ACTION_SEND)
		sharingIntent.type = Strings.TEXT_TYPE
		sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT,
		                       ctx.getString(R.string.play_store))
		sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT,
		                       ctx.getString(R.string.play_store_link))
		ctx.startActivity(Intent.createChooser(sharingIntent, ctx.getString(R.string.share_via)))
	}

	fun animateFromBottom(textView : TextView?, context : Context)
	{
		val a = AnimationUtils.loadAnimation(context, R.anim.scale)
		a.reset()
		textView?.clearAnimation()
		textView?.startAnimation(a)
	}

	fun animateFromTop(toggleButton : ToggleButton?, context : Context)
	{
		val animation = AnimationUtils.loadAnimation(context, R.anim.scale_down)
		animation.reset()
		toggleButton?.clearAnimation()
		toggleButton?.startAnimation(animation)
	}

	fun startFragment(activity : FragmentActivity, fragment : Fragment)
	{
		activity.supportFragmentManager.beginTransaction().replace(R.id.content, fragment).commit()
	}

	fun writePrefs(activity : FragmentActivity, prefs : String, value : Int)
	{
		val editor = activity.getPreferences(Context.MODE_PRIVATE).edit()
		editor.putInt(prefs, value)
		editor.apply()
	}

	fun readPrefs(activity : FragmentActivity, prefs : String) : Int
	{
		val sharedPref = activity.getPreferences(Context.MODE_PRIVATE)
		return sharedPref.getInt(prefs, -1)
	}

	/**
	 * Valid only for 1 to 1 relation
	 */
	fun <T, E> getKeyByValue(map : Map<T, E>, value : E) : T?
	{
		for ((key, value1) in map)
		{
			if (Objects.equals(value, value1))
			{
				return key
			}
		}
		return null
	}

	fun makeInvisible(vararg components : TextView?)
	{
		for (comp in components) comp?.visibility = View.INVISIBLE
	}

	fun makeVisible(vararg components : TextView?)
	{
		for (comp in components) comp?.visibility = View.VISIBLE
	}
}
