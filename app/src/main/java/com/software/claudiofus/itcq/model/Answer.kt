package com.software.claudiofus.itcq.model

data class Answer(val id : Int, val question_id : Int, val text : String, val is_correct : Int,
                  val is_active : Int, val created_dt : String, val updated_dt : String)
{
	companion object
	{
		val TABLE_NAME = "quiz_answer"
		private val COLUMN_ID = "_id"
		private val COLUMN_QUESTION_ID = "question_id"
		private val COLUMN_TEXT = "text"
		private val COLUMN_IS_CORRECT = "is_correct"
		private val COLUMN_IS_ACTIVE = "is_active"
		private val COLUMN_CREATED_DT = "created_dt"
		private val COLUMN_UPDATED_DT = "updated_dt"

		val ANSWER_COLUMNS = arrayOf(COLUMN_ID, COLUMN_QUESTION_ID, COLUMN_TEXT, COLUMN_IS_CORRECT,
		                             COLUMN_IS_ACTIVE, COLUMN_CREATED_DT, COLUMN_UPDATED_DT)
	}
}