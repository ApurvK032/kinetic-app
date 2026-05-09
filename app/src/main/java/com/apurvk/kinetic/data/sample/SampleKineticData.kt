package com.apurvk.kinetic.data.sample

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.DirectionsBike
import androidx.compose.material.icons.outlined.CalendarMonth
import androidx.compose.material.icons.outlined.FitnessCenter
import androidx.compose.material.icons.outlined.Mic
import androidx.compose.material.icons.outlined.Restaurant
import androidx.compose.ui.graphics.Color
import com.apurvk.kinetic.domain.model.CookedBatch
import com.apurvk.kinetic.domain.model.DashboardStat
import com.apurvk.kinetic.domain.model.MealSection
import com.apurvk.kinetic.domain.model.QuickAction
import com.apurvk.kinetic.domain.model.TodayTask
import com.apurvk.kinetic.domain.model.WeeklySignal
import com.apurvk.kinetic.domain.model.WorkoutItem

object KineticColors {
    val Moss = Color(0xFF4F6F52)
    val Copper = Color(0xFFC56A3A)
    val Blue = Color(0xFF33658A)
    val Gold = Color(0xFFB58B2A)
    val Ink = Color(0xFF17211E)
    val Muted = Color(0xFF65736D)
    val Paper = Color(0xFFF7F8F3)
    val Card = Color(0xFFFFFFFF)
    val Line = Color(0xFFE1E6DD)
}

object SampleKineticData {
    val stats = listOf(
        DashboardStat("Protein", "82g", "58g left", KineticColors.Moss),
        DashboardStat("Intake", "1,480", "kcal logged", KineticColors.Copper),
        DashboardStat("Burn", "360", "kcal ride est.", KineticColors.Blue),
        DashboardStat("Week", "5/7", "days active", KineticColors.Gold)
    )

    val todayTasks = listOf(
        TodayTask("Morning ride", "Easy 42 min cycling logged", true, KineticColors.Blue),
        TodayTask("Strength B", "Optional if legs feel fresh", false, KineticColors.Copper),
        TodayTask("Lunch plan", "Chhole, 2 rotis, dahi, salad", false, KineticColors.Moss),
        TodayTask("Dinner check", "Rajma batch or paneer sandwich", false, KineticColors.Gold)
    )

    val quickActions = listOf(
        QuickAction("Log voice", "Speak meal or workout", Icons.Outlined.Mic, KineticColors.Moss),
        QuickAction("Cardio", "Ride, run, or walk", Icons.AutoMirrored.Outlined.DirectionsBike, KineticColors.Blue),
        QuickAction("Gym", "Strength A/B checklist", Icons.Outlined.FitnessCenter, KineticColors.Copper),
        QuickAction("Meal", "Add food to today", Icons.Outlined.Restaurant, KineticColors.Gold),
        QuickAction("Plan", "Cooked batches", Icons.Outlined.CalendarMonth, KineticColors.Moss)
    )

    val meals = listOf(
        MealSection("Pre-workout fruit", "Banana plus water", true, "1g"),
        MealSection("Tea / drinks", "Tea after ride", true, "2g"),
        MealSection("Breakfast", "Protein shake plus sandwich", true, "35g"),
        MealSection("Lunch", "Chhole, roti, dahi, salad", false, "32g"),
        MealSection("Dinner", "Rajma or paneer meal", false, "40g"),
        MealSection("Extras", "Only if needed", false, "0g")
    )

    val strengthA = listOf(
        WorkoutItem("Goblet squat", "3 x 10-12", true),
        WorkoutItem("Chest press", "3 x 8-12", true),
        WorkoutItem("Lat pulldown", "3 x 8-12", true),
        WorkoutItem("Dumbbell RDL", "3 x 10", false),
        WorkoutItem("Cable row", "3 x 10-12", false),
        WorkoutItem("Plank", "3 x 30-60s", false)
    )

    val cookedBatches = listOf(
        CookedBatch("Chhole", "3 servings left", "Mon lunch, Tue dinner", "15g / serving"),
        CookedBatch("Rajma", "2 servings left", "Tonight or Wed lunch", "16g / serving"),
        CookedBatch("Paneer filling", "1 serving left", "Backup sandwich", "22g / serving")
    )

    val weeklySignals = listOf(
        WeeklySignal("Cycling", "3 of 5 days", 0.6f, KineticColors.Blue),
        WeeklySignal("Gym", "1 of 2 days", 0.5f, KineticColors.Copper),
        WeeklySignal("Protein", "4 strong days", 0.57f, KineticColors.Moss),
        WeeklySignal("Meal logs", "5 of 7 days", 0.71f, KineticColors.Gold)
    )
}
