package claudiofus.software.com.itq.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.ToggleButton
import claudiofus.software.com.itq.R
import claudiofus.software.com.itq.helper.database
import claudiofus.software.com.itq.model.Answer
import claudiofus.software.com.itq.model.Answer.Companion.ANSWER_COLUMNS
import claudiofus.software.com.itq.model.Question
import claudiofus.software.com.itq.model.Question.Companion.QUESTION_COLUMNS
import claudiofus.software.com.itq.model.Score
import claudiofus.software.com.itq.utility.Strings
import claudiofus.software.com.itq.utility.Strings.QUESTION_NUM
import claudiofus.software.com.itq.utility.Strings.UNANSWERED_WEIGHT
import claudiofus.software.com.itq.utility.Utils.animateFromBottom
import claudiofus.software.com.itq.utility.Utils.animateFromTop
import claudiofus.software.com.itq.utility.Utils.makeInvisible
import claudiofus.software.com.itq.utility.Utils.makeVisible
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.select
import java.util.Random
import kotlin.collections.HashMap
import kotlin.collections.component1
import kotlin.collections.component2


class QuizFragment : Fragment()
{
	private var mQuestionText : TextView? = null
	private var mAnswerToggle1 : ToggleButton? = null
	private var mAnswerToggle2 : ToggleButton? = null
	private var mAnswerToggle3 : ToggleButton? = null
	private var mAnswerToggle4 : ToggleButton? = null
	private var mVerifyButton : Button? = null
	private var mNextButton : Button? = null
	private var mWhyButton : Button? = null
	private var mWhyText : TextView? = null
	private var mAverageText : TextView? = null
	private var scoreUpdated = false

	override fun onCreateView(inflater : LayoutInflater?, container : ViewGroup?,
	                          savedInstanceState : Bundle?) : View?
	{
		activity.nav_view.menu.findItem(R.id.nav_quiz).isChecked = true
		val view = inflater?.inflate(R.layout.fragment_quiz, container, false)
		mQuestionText = view?.findViewById(R.id.questionText)
		mAnswerToggle1 = view?.findViewById(R.id.answer1)
		mAnswerToggle2 = view?.findViewById(R.id.answer2)
		mAnswerToggle3 = view?.findViewById(R.id.answer3)
		mAnswerToggle4 = view?.findViewById(R.id.answer4)
		mVerifyButton = view?.findViewById(R.id.verifyButton)
		mNextButton = view?.findViewById(R.id.nextButton)
		mWhyButton = view?.findViewById(R.id.explanationButton)
		mWhyText = view?.findViewById(R.id.whyText)
		mAverageText = view?.findViewById(R.id.averageText)
		val answerArr = arrayOf(mAnswerToggle1, mAnswerToggle2, mAnswerToggle3, mAnswerToggle4)
		val answerMap = HashMap<Answer, ToggleButton?>()
		val questionList = arrayListOf<Question>()
		val category = if (arguments != null) arguments.getString(Strings.CATEGORY_KEY) else null
		val score = Score()
		mAverageText?.text = String.format("%d%%", score.weightedAv)

		//TODO REMOVE COMMENT
		//		val mAdView = view?.findViewById<AdView>(R.id.adView)
		//		mAdView?.loadAd(AdRequest.Builder().build())

		for (toggleBtn in answerArr) animateFromTop(toggleBtn, context)
		makeInvisible(mWhyButton)

		refreshQuestion(questionList, answerMap, answerArr, category)
		mQuestionText?.text = questionList.first().text

		for (toggleBtn in answerMap.values)
		{
			toggleBtn?.setOnClickListener(View.OnClickListener {
				clearAnsBackground(answerArr)
				toggleBtn.isChecked = true
				toggleBtn.setBackgroundColor(
						ContextCompat.getColor(context, R.color.secondaryLightColor))
			})
		}

		mVerifyButton?.setOnClickListener(View.OnClickListener {
			if (isChecked(answerMap)) updateScore(answerMap, score, true)
		})

		mNextButton?.setOnClickListener(View.OnClickListener {
			updateScore(answerMap, score, false)
			clearAnsBackground(answerArr)
			makeInvisible(mWhyButton, mWhyText)
			mWhyText?.clearAnimation()
			refreshQuestion(questionList, answerMap, answerArr, category)
			mQuestionText?.text = questionList.first().text
		})

		mWhyButton?.setOnClickListener(View.OnClickListener {
			makeVisible(mWhyText)
			mWhyText?.text = questionList.first().explanation
			animateFromBottom(mWhyText, context)
			makeInvisible(mWhyButton)
		})

		return view
	}

	private fun refreshQuestion(questionList : ArrayList<Question>,
	                            answerMap : HashMap<Answer, ToggleButton?>,
	                            answerArr : Array<ToggleButton?>, category : String?)
	{
		val randomInt = Random().nextInt(QUESTION_NUM).toString()
		var resultAns = listOf<Answer>()
		var whereCond = "(_id = ?)"
		var whereVal = randomInt
		scoreUpdated = false
		if (!category.isNullOrEmpty())
		{
			whereCond = "(category = ?)"
			whereVal = category.toString()
		}

		answerMap.clear()
		questionList.clear()
		activity.database.use {
			questionList.addAll(
					select(Question.TABLE_NAME).columns(*QUESTION_COLUMNS).whereSimple(whereCond,
					                                                                   whereVal).orderBy(
							"RANDOM()").limit(1).parseList(classParser()))
			resultAns = select(Answer.TABLE_NAME).columns(*ANSWER_COLUMNS).whereSimple(
					"question_id = ?", questionList.first().id.toString()).parseList(classParser())
		}

		var i = 0
		do
		{
			answerArr[i]?.text = resultAns[i].text
			answerArr[i]?.textOn = resultAns[i].text
			answerArr[i]?.textOff = resultAns[i].text
			answerMap.put(resultAns[i], answerArr[i])
			i++
		} while (i < resultAns.size)
	}

	private fun clearAnsBackground(answerArr : Array<ToggleButton?>)
	{
		for (toggleBtn in answerArr)
		{
			toggleBtn?.isChecked = false
			toggleBtn?.setBackgroundColor((ContextCompat.getColor(context, R.color.white)))
		}
	}

	private fun isCorrectAns(answerMap : HashMap<Answer, ToggleButton?>) : Double
	{
		for ((answer, toggleBtn) in answerMap)
		{
			if (toggleBtn != null && toggleBtn.isChecked)
			{
				if (answer.is_correct == 1)
				{
					toggleBtn.setBackgroundColor(ContextCompat.getColor(context, R.color.green))
				}
				else
				{
					toggleBtn.setBackgroundColor(ContextCompat.getColor(context, R.color.red))
					if (mWhyButton?.visibility == View.VISIBLE || mWhyText?.visibility == View.INVISIBLE)
					{
						makeVisible(mWhyButton)
					}
				}
				return answer.is_correct.toDouble()
			}
		}
		return UNANSWERED_WEIGHT
	}

	private fun isChecked(answerMap : HashMap<Answer, ToggleButton?>) : Boolean
	{
		return answerMap.values.any { it != null && it.isChecked }
	}

	private fun updateScore(answerMap : HashMap<Answer, ToggleButton?>, score : Score,
	                        validateAnswer : Boolean)
	{
		val scorePoint = if (validateAnswer) isCorrectAns(answerMap) else UNANSWERED_WEIGHT
		if (!scoreUpdated)
		{
			score.addScore(scorePoint)
			mAverageText?.text = String.format("%d%%", score.weightedAv)
			scoreUpdated = true
		}
	}
}