package claudiofus.software.com.itq.model

import claudiofus.software.com.itq.utility.Strings.UNANSWERED_WEIGHT
import java.util.*

class Score
{
	var day : Date? = null
	private var correct = 1.0
	private var unanswered = UNANSWERED_WEIGHT
	private var wrong = 0.0
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
}