package com.apurvk.kinetic.domain.model

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector

data class DashboardStat(
    val label: String,
    val value: String,
    val helper: String,
    val accent: Color
)

data class TodayTask(
    val title: String,
    val detail: String,
    val completed: Boolean,
    val accent: Color
)

data class QuickAction(
    val title: String,
    val detail: String,
    val icon: ImageVector,
    val accent: Color
)

data class MealSection(
    val name: String,
    val plan: String,
    val logged: Boolean,
    val protein: String
)

data class WorkoutItem(
    val name: String,
    val target: String,
    val completed: Boolean
)

data class CookedBatch(
    val dish: String,
    val servingsLeft: String,
    val plannedFor: String,
    val protein: String
)

data class WeeklySignal(
    val label: String,
    val value: String,
    val progress: Float,
    val accent: Color
)

enum class KineticTab(val label: String) {
    Today("Today"),
    Fitness("Fitness"),
    Diet("Diet"),
    Plan("Plan"),
    Stats("Stats")
}
