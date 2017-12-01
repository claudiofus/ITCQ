package claudiofus.software.com.itq.model

data class Question(val id : Int, val text : String, val category : String, val level_id : Int,
                    val hint : String, val explanation : String, val is_active : Int,
                    val created_dt : String, val updated_dt : String)
{

	companion object
	{
		val TABLE_NAME = "quiz_question"
		val COLUMN_ID = "_id"
		val COLUMN_TEXT = "text"
		val COLUMN_CATEGORY = "category"
		val COLUMN_LEVEL_ID = "level_id"
		val COLUMN_HINT = "hint"
		val COLUMN_EXPLANATION = "explanation"
		val COLUMN_IS_ACTIVE = "is_active"
		val COLUMN_CREATED_DT = "created_dt"
		val COLUMN_UPDATED_DT = "updated_dt"

		val QUESTION_COLUMNS = arrayOf(COLUMN_ID, COLUMN_TEXT, COLUMN_CATEGORY, COLUMN_LEVEL_ID,
		                               COLUMN_HINT, COLUMN_EXPLANATION, COLUMN_IS_ACTIVE,
		                               COLUMN_CREATED_DT, COLUMN_UPDATED_DT)
	}
}