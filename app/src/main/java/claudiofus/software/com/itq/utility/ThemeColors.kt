package claudiofus.software.com.itq.utility

import claudiofus.software.com.itq.R

class ThemeColors
{
	companion object
	{
		private const val ORANGE = "orange"
		private const val BLUE   = "blue"
		private const val PURPLE = "purple"
		private const val GREEN  = "green"

		val colorsArray = arrayOf(ORANGE, BLUE, PURPLE, GREEN)
		val colorsMap = hashMapOf(ORANGE to R.style.AppTheme,
		                          BLUE to R.style.AppTheme_Blue,
		                          PURPLE to R.style.AppTheme_Purple,
		                          GREEN to R.style.AppTheme_Green)

		val darkPrimaryMaps = hashMapOf(ORANGE to R.color.primaryDarkColor,
		                                BLUE to R.color.primaryDarkColorBlue,
		                                PURPLE to R.color.primaryDarkColorPurple,
		                                GREEN to R.color.primaryDarkColorGreen)
	}
}
