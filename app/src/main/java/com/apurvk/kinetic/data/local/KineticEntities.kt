package com.apurvk.kinetic.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "meal_logs")
data class MealLogEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val dateKey: String,
    val section: String,
    val description: String,
    val calories: Int,
    val proteinGrams: Int,
    val createdAt: Long
)

@Entity(tableName = "gym_exercise_logs")
data class GymExerciseLogEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val dateKey: String,
    val templateName: String,
    val exerciseName: String,
    val target: String,
    val completed: Boolean,
    val sets: String,
    val reps: String,
    val weight: String,
    val createdAt: Long
)

@Entity(tableName = "cardio_sessions")
data class CardioSessionEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val dateKey: String,
    val type: String,
    val durationMinutes: Int,
    val distance: String,
    val intensity: String,
    val caloriesBurned: Int,
    val notes: String,
    val createdAt: Long
)

@Entity(tableName = "cooked_batches")
data class CookedBatchEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val dishName: String,
    val servingsLeft: Int,
    val plannedFor: String,
    val caloriesPerServing: Int,
    val proteinPerServing: Int,
    val createdAt: Long
)
