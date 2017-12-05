package claudiofus.software.com.itq.model

import claudiofus.software.com.itq.utility.Strings.CORRECT_WEIGHT
import claudiofus.software.com.itq.utility.Strings.UNANSWERED_WEIGHT
import claudiofus.software.com.itq.utility.Strings.WRONG_WEIGHT

data class Score(val dateInMillis : Long, val score : Int)
{
	private var correct = CORRECT_WEIGHT
	private var unanswered = UNANSWERED_WEIGHT
	private var wrong = WRONG_WEIGHT
	var weightedAv = 0
	var map = mutableMapOf(correct to 0, unanswered to 0, wrong to 0)

	fun addScore(score : Double)
	{
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

		val SCORE_COLUMNS = arrayOf(COLUMN_DATE, COLUMN_SCORE)
	}
}