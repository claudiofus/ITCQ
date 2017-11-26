package claudiofus.software.com.itq.model

//data class Answer(val text : String)
//{
//    companion object
//    {
//        val TABLE_NAME = "quiz_answer"
//        val COLUMN_ID = "_id"
//        val COLUMN_QUESTION_ID = "question_id"
//        val COLUMN_TEXT = "text"
//        val COLUMN_IS_CORRECT = "is_correct"
//        val COLUMN_IS_ACTIVE = "is_active"
//        val COLUMN_CREATED_DT = "created_dt"
//        val COLUMN_UPDATED_DT = "updated_dt"
//    }
//}

data class Answer(val id: Int, val question_id: Int, val text: String, val is_correct: Long,
                  val is_active: Boolean, val created_dt: String, val updated_dt: String) {
    constructor(id: Int) : this(id, 0, "", 0, false, "", "")
    constructor(id: Int, is_correct: Long) : this(id, 0, "", is_correct, false, "", "")
    constructor(id: Int, text: String) : this(id, 0, text, 0, false, "", "")

    companion object {
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