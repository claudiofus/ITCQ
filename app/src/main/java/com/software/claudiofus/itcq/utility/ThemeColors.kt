package com.software.claudiofus.itcq.utility

import com.software.claudiofus.itcq.R
import java.util.*

class ThemeColors
{
	companion object
	{
		private val ORANGE = if (Locale.getDefault().language == Locale.ITALIAN.language) "arancio" else "orange"
		private val BLUE   = if (Locale.getDefault().language == Locale.ITALIAN.language) "blu" else "blue"
		private val PURPLE = if (Locale.getDefault().language == Locale.ITALIAN.language) "viola" else "purple"
		private val GREEN  = if (Locale.getDefault().language == Locale.ITALIAN.language) "verde" else "green"

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