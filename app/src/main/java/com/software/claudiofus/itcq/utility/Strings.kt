package com.software.claudiofus.itcq.utility

import java.util.*

object Strings
{
	val TEXT_TYPE = "text/plain"
	val SQL_FILENAME = if (Locale.getDefault().language == Locale.ITALIAN.language) "db_ita.sql" else "db.sql"
	val DB_NAME = "DB_ITQ"
	val DB_VERSION = 1
	val QUESTION_NUM = 504
	val CATEGORY_KEY = "category"

	// Answers weights
	val CORRECT_WEIGHT = 1.0
	val UNANSWERED_WEIGHT = 0.5
	val WRONG_WEIGHT = 0.0

	// Prefs key
	val PREFS_FILE = "app_prefs"
	val PREFS_COLOR = "theme"
}