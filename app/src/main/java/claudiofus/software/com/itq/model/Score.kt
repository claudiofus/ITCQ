package claudiofus.software.com.itq.model

import claudiofus.software.com.itq.utility.Strings.CORRECT_WEIGHT
import claudiofus.software.com.itq.utility.Strings.UNANSWERED_WEIGHT
import claudiofus.software.com.itq.utility.Strings.WRONG_WEIGHT

data class Score(val dateInMillis : Long, var weightedAv : Int, var correct : Int, var unanswered : Int,
                 var wrong : Int)
{
	private var corWeight = CORRECT_WEIGHT
	private var unansWeight = UNANSWERED_WEIGHT
	private var wrWeight = WRONG_WEIGHT
	private var map = mutableMapOf(corWeight to correct, unansWeight to unanswered, wrWeight to wrong)

	fun addScore(score : Double)
	{
		when (score)
		{
			0.0 -> wrong++
			0.5 -> unanswered++
			1.0 -> correct++
		}
		map.put(score, map[score]?.plus(1)!!)
		weightedAv = calculateWeightedAverage(map)
	}

	private fun calculateWeightedAverage(map : Map<Double, Int>) : Int
	{
		var num = 0.0
		var denom = 0.0
		for ((key, value) in map)
		{
			num += key * value
			denom += value
		}

		return ((num / denom) * 100).toInt()
	}

	companion object
	{
		val TABLE_NAME = "quiz_score"
		val COLUMN_DATE = "date"
		val COLUMN_SCORE = "score"
		val COLUMN_CORRECT = "correct"
		val COLUMN_UNANSWERED = "unanswered"
		val COLUMN_WRONG = "wrong"

		val SCORE_COLUMNS = arrayOf(COLUMN_DATE, COLUMN_SCORE, COLUMN_CORRECT, COLUMN_UNANSWERED,
		                            COLUMN_WRONG)
	}
}