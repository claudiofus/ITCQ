package claudiofus.software.com.itq.utility

import claudiofus.software.com.itq.R

class ThemeColors
{
	companion object
	{
		const val ORANGE = "orange"
		const val BLUE   = "blue"
		const val PURPLE = "purple"
		const val GREEN  = "green"

		val colorsArray = arrayOf(ORANGE, BLUE, PURPLE, GREEN)
		val colorsMap = hashMapOf<String, Int>(ORANGE to R.style.AppTheme,
		                                       BLUE to R.style.AppTheme_Blue,
		                                       PURPLE to R.style.AppTheme_Purple,
		                                       GREEN to R.style.AppTheme_Green)

		val darkPrimaryMaps = hashMapOf<String, Int>(ORANGE to R.color.primaryDarkColor,
		                                             BLUE to R.color.primaryDarkColorBlue,
		                                             PURPLE to R.color.primaryDarkColorPurple,
		                                             GREEN to R.color.primaryDarkColorGreen)
	}
}
