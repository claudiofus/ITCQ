package claudiofus.software.com.itq.helper

import android.support.v4.app.FragmentActivity
import claudiofus.software.com.itq.model.Answer
import claudiofus.software.com.itq.model.Answer.Companion.ANSWER_COLUMNS
import claudiofus.software.com.itq.model.Question
import claudiofus.software.com.itq.model.Score
import claudiofus.software.com.itq.model.Score.Companion.SCORE_COLUMNS
import claudiofus.software.com.itq.utility.Strings
import claudiofus.software.com.itq.utility.Utils.getDateMillis
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.insert
import org.jetbrains.anko.db.select
import org.jetbrains.anko.db.update
import java.util.*

class DbStatement
{
	companion object
	{
		// CategoryFragment
		fun selectCategoriesDB(activity : FragmentActivity?) : List<String>
		{
			var categories : List<String> = arrayListOf()
			activity?.database?.use {
				categories = select(Question.TABLE_NAME).distinct().column(
						Question.COLUMN_CATEGORY).parseList(classParser())
			}
			return categories
		}

		// QuizFragment
		fun selectDateDB(activity : FragmentActivity?) : List<Score>
		{
			var today : List<Score> = listOf()
			activity?.database?.use {
				today = select(Score.TABLE_NAME).columns(*SCORE_COLUMNS).whereSimple("date = ?",
				                                                                     getDateMillis().toString()).parseList(
						classParser())
			}
			return today
		}

		fun insertDateDB(activity : FragmentActivity?)
		{
			activity?.database?.use {
				insert(Score.TABLE_NAME, Score.COLUMN_DATE to getDateMillis().toString(),
				       Score.COLUMN_SCORE to 0, Score.COLUMN_CORRECT to 0,
				       Score.COLUMN_UNANSWERED to 0, Score.COLUMN_WRONG to 0)
			}
		}

		fun selectQuestionDB(activity : FragmentActivity?, category : String?) : ArrayList<Question>
		{
			val randomInt = Random().nextInt(Strings.QUESTION_NUM).toString()
			var whereCond = "(_id = ?)"
			var whereVal = randomInt
			if (!category.isNullOrEmpty())
			{
				whereCond = "(category = ?)"
				whereVal = category.toString()
			}
			val questionList = arrayListOf<Question>()
			activity?.database?.use {
				questionList.addAll(
						select(Question.TABLE_NAME).columns(*Question.QUESTION_COLUMNS).whereSimple(
								whereCond, whereVal).orderBy("RANDOM()").limit(1).parseList(
								classParser()))
			}

			return questionList
		}

		fun selectAnswersDB(activity : FragmentActivity?,
		                    questionList : ArrayList<Question>) : List<Answer>
		{
			var resultAns = listOf<Answer>()
			activity?.database?.use {
				resultAns = select(Answer.TABLE_NAME).columns(*ANSWER_COLUMNS).whereSimple(
						"question_id = ?", questionList.first().id.toString()).parseList(
						classParser())
			}

			return resultAns
		}

		fun updateScoreDB(activity : FragmentActivity?, score : Score)
		{
			activity?.database?.use {
				val formatted = getDateMillis().toString()
				update(Score.TABLE_NAME, Score.COLUMN_SCORE to score.weightedAv,
				       Score.COLUMN_CORRECT to score.correct,
				       Score.COLUMN_UNANSWERED to score.unanswered,
				       Score.COLUMN_WRONG to score.wrong).whereSimple("date = ?", formatted).exec()
			}
		}

		// GraphsFragment
		fun selectScoresDB(activity : FragmentActivity?) : List<Score>
		{
			var scoresList = listOf<Score>()
			activity?.database?.use {
				scoresList = select(Score.TABLE_NAME).columns(*Score.SCORE_COLUMNS).parseList(
						classParser())
			}
			return scoresList
		}
	}
}