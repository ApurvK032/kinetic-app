package com.apurvk.kinetic.domain.nutrition

import kotlin.math.roundToInt

data class NutritionEstimate(
    val calories: Int,
    val proteinGrams: Int
)

private data class FoodDefault(
    val aliases: List<String>,
    val calories: Int,
    val proteinGrams: Int
)

object NutritionEstimator {
    private val foods = listOf(
        FoodDefault(listOf("roti", "rotis", "chapati", "chapatis"), 110, 4),
        FoodDefault(listOf("chhole", "chole", "chana"), 300, 15),
        FoodDefault(listOf("rajma"), 300, 16),
        FoodDefault(listOf("dal", "lentil", "lentils"), 240, 16),
        FoodDefault(listOf("dahi", "curd", "raita", "yogurt"), 100, 6),
        FoodDefault(listOf("salad"), 30, 1),
        FoodDefault(listOf("banana"), 105, 1),
        FoodDefault(listOf("tea", "chai"), 70, 2),
        FoodDefault(listOf("protein shake", "shake", "whey"), 350, 30),
        FoodDefault(listOf("paneer"), 300, 18),
        FoodDefault(listOf("sandwich"), 350, 20),
        FoodDefault(listOf("sprouts", "moong", "mung"), 220, 14),
        FoodDefault(listOf("poha"), 300, 8),
        FoodDefault(listOf("soy", "soya", "soy chunks"), 200, 25),
        FoodDefault(listOf("milk"), 120, 8),
        FoodDefault(listOf("maggi"), 350, 8),
        FoodDefault(listOf("sewai", "sevai"), 250, 6),
        FoodDefault(listOf("pasta"), 450, 14),
        FoodDefault(listOf("corn", "sweet corn"), 140, 5),
        FoodDefault(listOf("nuts", "peanuts"), 170, 5),
        FoodDefault(listOf("honey"), 60, 0)
    )

    fun estimate(text: String): NutritionEstimate {
        val normalized = text.lowercase()
        var calories = 0.0
        var protein = 0.0

        foods.forEach { food ->
            val matchedAlias = food.aliases.firstOrNull { alias ->
                normalized.containsWordOrPhrase(alias)
            }

            if (matchedAlias != null) {
                val quantity = extractQuantity(normalized, matchedAlias)
                calories += food.calories * quantity
                protein += food.proteinGrams * quantity
            }
        }

        return NutritionEstimate(
            calories = calories.roundToInt(),
            proteinGrams = protein.roundToInt()
        )
    }

    private fun extractQuantity(text: String, alias: String): Double {
        val words = alias.split(" ").joinToString("\\s+")
        val beforeAlias = Regex("""(\d+(?:\.\d+)?)\s+(?:small|medium|large|big)?\s*(?:cups?|bowls?|servings?|pieces?)?\s*$words\b""")
            .find(text)
            ?.groupValues
            ?.getOrNull(1)
            ?.toDoubleOrNull()

        if (beforeAlias != null) return beforeAlias

        if (Regex("""\bhalf\s+(?:a\s+)?(?:small|medium|large|big)?\s*$words\b""").containsMatchIn(text)) {
            return 0.5
        }

        return 1.0
    }

    private fun String.containsWordOrPhrase(value: String): Boolean {
        val escaped = Regex.escape(value).replace("\\ ", "\\s+")
        return Regex("""\b$escaped\b""").containsMatchIn(this)
    }
}
