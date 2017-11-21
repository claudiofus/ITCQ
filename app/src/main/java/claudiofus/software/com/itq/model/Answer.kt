package claudiofus.software.com.itq.model

data class Answer(val text : String)
{
    companion object
    {
        val TABLE_NAME = "quiz_answer"
        val COLUMN_ID = "_id"
        val COLUMN_QUESTION_ID = "question_id"
        val COLUMN_TEXT = "text"
        val COLUMN_IS_CORRECT = "is_correct"
        val COLUMN_IS_ACTIVE = "is_active"
        val COLUMN_CREATED_DT = "created_dt"
        val COLUMN_UPDATED_DT = "updated_dt"
    }
}

/*data class Answer(val id : Int, val question_id : Int, val text : String, val is_correct : Boolean,
                  val is_active : Boolean, val created_dt : String, val updated_dt : String)
{
	constructor() : this(0, 0, "", false, false, "", "")

	companion object
	{
		val TABLE_NAME = "quiz_answer"
		val COLUMN_ID = "_id"
		val COLUMN_QUESTION_ID = "question_id"
		val COLUMN_TEXT = "text"
		val COLUMN_IS_CORRECT = "is_correct"
		val COLUMN_IS_ACTIVE = "is_active"
		val COLUMN_CREATED_DT = "created_dt"
		val COLUMN_UPDATED_DT = "updated_dt"
	}
}*/