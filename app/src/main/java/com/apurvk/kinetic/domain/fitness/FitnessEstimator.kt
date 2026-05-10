package com.apurvk.kinetic.domain.fitness

import kotlin.math.roundToInt

object FitnessEstimator {
    private const val USER_WEIGHT_KG = 82.0

    fun estimateCalories(activityType: String, intensity: String, durationMinutes: Int): Int {
        val met = when (activityType.lowercase()) {
            "running" -> when (intensity.lowercase()) {
                "hard" -> 10.0
                "moderate" -> 8.0
                else -> 6.0
            }
            "walking" -> when (intensity.lowercase()) {
                "hard" -> 5.0
                "moderate" -> 4.0
                else -> 3.0
            }
            else -> when (intensity.lowercase()) {
                "hard" -> 8.0
                "moderate" -> 6.0
                "recovery" -> 3.5
                else -> 4.5
            }
        }

        return (met * 3.5 * USER_WEIGHT_KG / 200.0 * durationMinutes).roundToInt()
    }
}
