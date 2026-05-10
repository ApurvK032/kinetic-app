package com.apurvk.kinetic.data.repository

import com.apurvk.kinetic.data.local.CardioSessionEntity
import com.apurvk.kinetic.data.local.CookedBatchEntity
import com.apurvk.kinetic.data.local.GymExerciseLogEntity
import com.apurvk.kinetic.data.local.KineticDao
import com.apurvk.kinetic.data.local.MealLogEntity
import kotlinx.coroutines.flow.Flow

class KineticRepository(private val dao: KineticDao) {
    fun observeMeals(dateKey: String): Flow<List<MealLogEntity>> = dao.observeMeals(dateKey)

    fun observeGymLogs(dateKey: String): Flow<List<GymExerciseLogEntity>> = dao.observeGymLogs(dateKey)

    fun observeCardio(dateKey: String): Flow<List<CardioSessionEntity>> = dao.observeCardio(dateKey)

    fun observeBatches(): Flow<List<CookedBatchEntity>> = dao.observeBatches()

    suspend fun addMeal(meal: MealLogEntity) = dao.insertMeal(meal)

    suspend fun addGymLogs(logs: List<GymExerciseLogEntity>) = dao.insertGymLogs(logs)

    suspend fun addCardio(cardio: CardioSessionEntity) = dao.insertCardio(cardio)

    suspend fun addBatch(batch: CookedBatchEntity) = dao.insertBatch(batch)

    suspend fun deleteMeal(meal: MealLogEntity) = dao.deleteMeal(meal)

    suspend fun deleteGymLog(log: GymExerciseLogEntity) = dao.deleteGymLog(log)

    suspend fun deleteCardio(cardio: CardioSessionEntity) = dao.deleteCardio(cardio)

    suspend fun deleteBatch(batch: CookedBatchEntity) = dao.deleteBatch(batch)
}
