package claudiofus.software.com.itq.utility

import android.content.Context
import android.content.Intent

import claudiofus.software.com.itq.R

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
}
