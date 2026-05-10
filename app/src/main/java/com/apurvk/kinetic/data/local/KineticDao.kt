package com.apurvk.kinetic.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface KineticDao {
    @Query("SELECT * FROM meal_logs WHERE dateKey = :dateKey ORDER BY createdAt DESC")
    fun observeMeals(dateKey: String): Flow<List<MealLogEntity>>

    @Query("SELECT * FROM gym_exercise_logs WHERE dateKey = :dateKey ORDER BY createdAt DESC")
    fun observeGymLogs(dateKey: String): Flow<List<GymExerciseLogEntity>>

    @Query("SELECT * FROM cardio_sessions WHERE dateKey = :dateKey ORDER BY createdAt DESC")
    fun observeCardio(dateKey: String): Flow<List<CardioSessionEntity>>

    @Query("SELECT * FROM cooked_batches ORDER BY createdAt DESC")
    fun observeBatches(): Flow<List<CookedBatchEntity>>

    @Insert
    suspend fun insertMeal(meal: MealLogEntity)

    @Insert
    suspend fun insertGymLogs(logs: List<GymExerciseLogEntity>)

    @Insert
    suspend fun insertCardio(cardio: CardioSessionEntity)

    @Insert
    suspend fun insertBatch(batch: CookedBatchEntity)

    @Delete
    suspend fun deleteMeal(meal: MealLogEntity)

    @Delete
    suspend fun deleteGymLog(log: GymExerciseLogEntity)

    @Delete
    suspend fun deleteCardio(cardio: CardioSessionEntity)

    @Delete
    suspend fun deleteBatch(batch: CookedBatchEntity)
}
