package com.apurvk.kinetic.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [
        MealLogEntity::class,
        GymExerciseLogEntity::class,
        CardioSessionEntity::class,
        CookedBatchEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class KineticDatabase : RoomDatabase() {
    abstract fun kineticDao(): KineticDao

    companion object {
        @Volatile
        private var instance: KineticDatabase? = null

        fun getInstance(context: Context): KineticDatabase {
            return instance ?: synchronized(this) {
                instance ?: Room.databaseBuilder(
                    context.applicationContext,
                    KineticDatabase::class.java,
                    "kinetic.db"
                ).build().also { instance = it }
            }
        }
    }
}
