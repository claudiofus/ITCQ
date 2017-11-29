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
import claudiofus.software.com.itq.model.Question
import claudiofus.software.com.itq.utility.Strings
import claudiofus.software.com.itq.utility.Strings.QUESTION_NUM
import claudiofus.software.com.itq.utility.Utils.animateFromBottom
import claudiofus.software.com.itq.utility.Utils.animateFromTop
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.select
import java.util.Random
import kotlin.collections.HashMap
import kotlin.collections.component1
import kotlin.collections.component2


class QuizFragment : Fragment()
{
	override fun onCreateView(inflater : LayoutInflater?, container : ViewGroup?,
	                          savedInstanceState : Bundle?) : View?
	{
		val view = inflater?.inflate(R.layout.fragment_quiz, container, false)
		val mQuestionText = view?.findViewById<TextView>(R.id.questionText)
		val mAnswerToggle1 = view?.findViewById<ToggleButton>(R.id.answer1)
		val mAnswerToggle2 = view?.findViewById<ToggleButton>(R.id.answer2)
		val mAnswerToggle3 = view?.findViewById<ToggleButton>(R.id.answer3)
		val mAnswerToggle4 = view?.findViewById<ToggleButton>(R.id.answer4)
		val mVerifyButton = view?.findViewById<Button>(R.id.verifyButton)
		val mNextButton = view?.findViewById<Button>(R.id.nextButton)
		val mWhyButton = view?.findViewById<Button>(R.id.explanationButton)
		val mWhyText = view?.findViewById<TextView>(R.id.whyText)
		val answerArr = arrayOf(mAnswerToggle1, mAnswerToggle2, mAnswerToggle3, mAnswerToggle4)
		val answerMap = HashMap<Answer, ToggleButton?>()
		val questionList = arrayListOf<Question>()
		val category = if (arguments != null) arguments.getString(Strings.CATEGORY_KEY) else null

		//TODO REMOVE COMMENT
		//		val mAdView = view?.findViewById<AdView>(R.id.adView)
		//		mAdView?.loadAd(AdRequest.Builder().build())

		activity.nav_view.menu.findItem(R.id.nav_quiz).isChecked = true

		for (toggleBtn in answerArr) animateFromTop(toggleBtn, context)
		mWhyButton?.visibility = View.INVISIBLE

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
						if (mWhyButton?.visibility == View.VISIBLE || mWhyText?.visibility == View.INVISIBLE) mWhyButton?.visibility = View.VISIBLE
					}
					break
				}
			}
		})

		mNextButton?.setOnClickListener(View.OnClickListener {
			clearAnsBackground(answerArr)
			mWhyButton?.visibility = View.INVISIBLE
			mWhyText?.clearAnimation()
			mWhyText?.visibility = View.INVISIBLE
			refreshQuestion(questionList, answerMap, answerArr, category)
			mQuestionText?.text = questionList.first().text
		})

		mWhyButton?.setOnClickListener(View.OnClickListener {
			mWhyText?.visibility = View.VISIBLE
			mWhyText?.text = questionList.first().explanation
			animateFromBottom(mWhyText, context)
			mWhyButton.visibility = View.INVISIBLE
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
		if (!category.isNullOrEmpty())
		{
			whereCond = "(category = ?)"
			whereVal = category.toString()
		}

		answerMap.clear()
		questionList.clear()
		activity.database.use {
			questionList.addAll(
					select(Question.TABLE_NAME).columns(Question.COLUMN_ID, Question.COLUMN_TEXT,
					                                    Question.COLUMN_CATEGORY,
					                                    Question.COLUMN_LEVEL_ID,
					                                    Question.COLUMN_HINT,
					                                    Question.COLUMN_EXPLANATION,
					                                    Question.COLUMN_IS_ACTIVE,
					                                    Question.COLUMN_CREATED_DT,
					                                    Question.COLUMN_UPDATED_DT).whereSimple(
							whereCond, whereVal).orderBy("RANDOM()").limit(1).parseList(
							classParser()))
			resultAns = select(Answer.TABLE_NAME).columns(Answer.COLUMN_ID,
			                                              Answer.COLUMN_QUESTION_ID,
			                                              Answer.COLUMN_TEXT,
			                                              Answer.COLUMN_IS_CORRECT,
			                                              Answer.COLUMN_IS_ACTIVE,
			                                              Answer.COLUMN_CREATED_DT,
			                                              Answer.COLUMN_UPDATED_DT).whereSimple(
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
}