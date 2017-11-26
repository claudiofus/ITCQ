package claudiofus.software.com.itq.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
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
import claudiofus.software.com.itq.utility.Strings.QUESTION_NUM
import org.jetbrains.anko.db.rowParser
import org.jetbrains.anko.db.select
import java.util.*
import kotlin.collections.HashMap

class QuizFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater?.inflate(R.layout.fragment_quiz, container, false)
        val mQuestionView = view?.findViewById<TextView>(R.id.questionView)
        val mAnswerToggle1 = view?.findViewById<ToggleButton>(R.id.answer1)
        val mAnswerToggle2 = view?.findViewById<ToggleButton>(R.id.answer2)
        val mAnswerToggle3 = view?.findViewById<ToggleButton>(R.id.answer3)
        val mAnswerToggle4 = view?.findViewById<ToggleButton>(R.id.answer4)
        val mVerifyButton = view?.findViewById<Button>(R.id.verifyButton)
        val mNextButton = view?.findViewById<Button>(R.id.nextButton)
        val answerArr = arrayOf(mAnswerToggle1, mAnswerToggle2, mAnswerToggle3, mAnswerToggle4)
        val answerMap = HashMap<Answer, ToggleButton?>()
        var randomInt = Random().nextInt(QUESTION_NUM).toString()
        //TODO REMOVE COMMENT
        //val mAdView = view?.findViewById<AdView>(R.id.adView)
        //mAdView?.loadAd(AdRequest.Builder().build())

        refreshQuestion(mQuestionView, answerMap, answerArr, randomInt)

        for (toggleBtn in answerMap.values) {
            toggleBtn?.setOnClickListener(View.OnClickListener {
                clearAnsBackground(answerArr)
                toggleBtn.setBackgroundColor(resources.getColor(R.color.secondaryLightColor))
            })
        }

        mVerifyButton?.setOnClickListener(View.OnClickListener {
            activity.database.use {
                val parserVerify = rowParser { id: Int, isCorrect: Long -> Answer(id, isCorrect) }
                val resultVerify = select(Answer.TABLE_NAME).columns(
                        Answer.COLUMN_ID,
                        Answer.COLUMN_IS_CORRECT).whereSimple("question_id = ?",
                        randomInt).parseList(
                        parserVerify)
                println(resultVerify)
            }
        })

        mNextButton?.setOnClickListener(View.OnClickListener {
            clearAnsBackground(answerArr)
            randomInt = Random().nextInt(QUESTION_NUM).toString()
            refreshQuestion(mQuestionView, answerMap, answerArr, randomInt)
        })

        return view
    }

    private fun refreshQuestion(mQuestionView: TextView?, answerMap: HashMap<Answer, ToggleButton?>, answerArr: Array<ToggleButton?>,
                                randomInt: String) {
        activity.database.use {
            val parserQue = rowParser { id: Int, text: String -> Question(id, text) }
            val parserAns = rowParser { id: Int, text: String -> Answer(id, text) }
            val resultQue = select(Question.TABLE_NAME).columns(Question.COLUMN_ID,
                    Question.COLUMN_TEXT).whereSimple(
                    "_id = ?", randomInt).parseList(parserQue)
            val resultAns = select(Answer.TABLE_NAME).columns(Answer.COLUMN_ID, Answer.COLUMN_TEXT).whereSimple(
                    "question_id = ?", resultQue.first().id.toString()).parseList(parserAns)

            // Show results in view
            mQuestionView?.text = resultQue.first().text
            for (i in resultAns.indices) {
                answerArr[i]?.text = resultAns[i].text
                answerArr[i]?.textOn = resultAns[i].text
                answerArr[i]?.textOff = resultAns[i].text
                answerMap.put(resultAns[i], answerArr[i])
            }
        }
    }

    private fun clearAnsBackground(answerArr: Array<ToggleButton?>) {
        for (toggleBtn in answerArr) {
            toggleBtn?.setBackgroundColor((resources.getColor(R.color.white)))
        }
    }
}