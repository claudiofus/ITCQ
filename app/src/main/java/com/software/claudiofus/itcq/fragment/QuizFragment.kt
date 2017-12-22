package com.software.claudiofus.itcq.fragment

import android.app.AlertDialog
import android.os.Build
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat.getColor
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.ToggleButton
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.software.claudiofus.itcq.R
import com.software.claudiofus.itcq.helper.DbStatement.Companion.insertDateDB
import com.software.claudiofus.itcq.helper.DbStatement.Companion.selectAnswersDB
import com.software.claudiofus.itcq.helper.DbStatement.Companion.selectDateDB
import com.software.claudiofus.itcq.helper.DbStatement.Companion.selectQuestionDB
import com.software.claudiofus.itcq.helper.DbStatement.Companion.updateScoreDB
import com.software.claudiofus.itcq.model.Answer
import com.software.claudiofus.itcq.model.Question
import com.software.claudiofus.itcq.model.Score
import com.software.claudiofus.itcq.utility.Strings
import com.software.claudiofus.itcq.utility.Strings.UNANSWERED_WEIGHT
import com.software.claudiofus.itcq.utility.Utils.animateFromBottom
import com.software.claudiofus.itcq.utility.Utils.animateFromTop
import com.software.claudiofus.itcq.utility.Utils.getDateMillis
import com.software.claudiofus.itcq.utility.Utils.makeInvisible
import com.software.claudiofus.itcq.utility.Utils.makeVisible
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.collections.component1
import kotlin.collections.component2


class QuizFragment : Fragment()
{
	private var mQuestionText : TextView? = null
	private var mAnswerToggle1 : ToggleButton? = null
	private var mAnswerToggle2 : ToggleButton? = null
	private var mAnswerToggle3 : ToggleButton? = null
	private var mAnswerToggle4 : ToggleButton? = null
	private var mNextButton : Button? = null
	private var mWhyButton : Button? = null
	private var mWhyText : TextView? = null
	private var mAverageText : TextView? = null
	private var mAdView : AdView? = null
	private var scoreUpdated = false
	private var changeQuestion = false

	override fun onCreateView(inflater : LayoutInflater, container : ViewGroup?,
	                          savedInstanceState : Bundle?) : View?
	{
		activity?.nav_view?.menu?.findItem(R.id.nav_quiz)?.isChecked = true
		val view = inflater.inflate(R.layout.fragment_quiz, container, false)
		mQuestionText = view?.findViewById(R.id.questionText)
		mAnswerToggle1 = view?.findViewById(R.id.answer1)
		mAnswerToggle2 = view?.findViewById(R.id.answer2)
		mAnswerToggle3 = view?.findViewById(R.id.answer3)
		mAnswerToggle4 = view?.findViewById(R.id.answer4)
		mNextButton = view?.findViewById(R.id.nextButton)
		mWhyButton = view?.findViewById(R.id.explanationButton)
		mWhyText = view?.findViewById(R.id.whyText)
		mAverageText = view?.findViewById(R.id.averageText)
		mAdView = view?.findViewById(R.id.adViewQuiz)
		val answerArr = arrayOf(mAnswerToggle1, mAnswerToggle2, mAnswerToggle3, mAnswerToggle4)
		val answerMap = HashMap<Answer, ToggleButton?>()
		val questionList = arrayListOf<Question>()
		val category = if (arguments != null) arguments!!.getString(Strings.CATEGORY_KEY) else null
		val today = selectDateDB(activity)

		val score = if (today.isEmpty())
		{
			insertDateDB(activity)
			Score(getDateMillis(), 0, 0, 0, 0)
		}
		else
		{
			Score(getDateMillis(), today.first().weightedAv, today.first().correct,
			      today.first().unanswered, today.first().wrong)
		}

		mAverageText?.text = String.format("%d%%", score.weightedAv)

		for (toggleBtn in answerArr) animateFromTop(toggleBtn, context)
		makeInvisible(mWhyButton)

		refreshQuestion(questionList, answerMap, answerArr, category)
		mQuestionText?.text = questionList.first().text

		for (toggleBtn in answerMap.values)
		{
			toggleBtn?.setOnClickListener({
				                              clearAnsBackground(answerArr)
				                              toggleBtn.isChecked = true
				                              toggleBtn.setBackgroundColor(getColor(context!!,
				                                                                    R.color.secondaryLightColor))
			                              })
		}

		mNextButton?.setOnClickListener({
			                                when
			                                {
				                                changeQuestion       ->
				                                {
					                                cleanReloadQuiz(answerMap, answerArr, questionList,
					                                                category)
					                                changeQuestion = false
				                                }
				                                isChecked(answerMap) ->
				                                {
					                                updateScore(answerMap, score, true)
					                                changeQuestion = true
				                                }
				                                else                 ->
				                                {
					                                val dialog = createDialog()
					                                dialog.setPositiveButton(android.R.string.yes, { _, _ ->
						                                updateScore(answerMap, score, false)
						                                cleanReloadQuiz(answerMap, answerArr,
						                                                questionList, category)
					                                }).show()
					                                changeQuestion = false
				                                }
			                                }
		                                })

		mWhyButton?.setOnClickListener({
			                               makeVisible(mWhyText)
			                               mWhyText?.text = questionList.first().explanation
			                               animateFromBottom(mWhyText, context)
			                               makeInvisible(mWhyButton)
		                               })

		mAdView?.loadAd(AdRequest.Builder().build())

		return view
	}

	private fun refreshQuestion(questionList : ArrayList<Question>,
	                            answerMap : HashMap<Answer, ToggleButton?>,
	                            answerArr : Array<ToggleButton?>, category : String?)
	{
		scoreUpdated = false

		answerMap.clear()
		questionList.clear()
		questionList.addAll(selectQuestionDB(activity, category))
		val resultAns = selectAnswersDB(activity, questionList)

		var i = 0
		while (i < resultAns.size)
		{
			answerArr[i]?.text = resultAns[i].text
			answerArr[i]?.textOn = resultAns[i].text
			answerArr[i]?.textOff = resultAns[i].text
			answerMap.put(resultAns[i], answerArr[i])
			i++
		}
	}

	private fun clearAnsBackground(answerArr : Array<ToggleButton?>)
	{
		for (toggleBtn in answerArr)
		{
			toggleBtn?.isChecked = false
			toggleBtn?.setBackgroundColor((getColor(context!!, R.color.white)))
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
					toggleBtn.setBackgroundColor(getColor(context!!, R.color.green))
				}
				else
				{
					toggleBtn.setBackgroundColor(getColor(context!!, R.color.red))
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
			updateScoreDB(activity, score)
			scoreUpdated = true
		}
	}

	private fun createDialog() : AlertDialog.Builder
	{
		val builder : AlertDialog.Builder = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
		{
			AlertDialog.Builder(context, android.R.style.Theme_Material_Light_Dialog_Alert)
		}
		else
		{
			AlertDialog.Builder(context)
		}
		builder.setMessage(R.string.no_selected_dialog).setNegativeButton(android.R.string.no,
		                                                                  { _, _ -> }).setIcon(
				android.R.drawable.ic_dialog_alert)

		return builder
	}

	private fun cleanReloadQuiz(answerMap : HashMap<Answer, ToggleButton?>,
	                            answerArr : Array<ToggleButton?>,
	                            questionList : ArrayList<Question>, category : String?)
	{
		clearAnsBackground(answerArr)
		makeInvisible(mWhyButton, mWhyText)
		mWhyText?.clearAnimation()
		refreshQuestion(questionList, answerMap, answerArr, category)
		mQuestionText?.text = questionList.first().text
	}
}