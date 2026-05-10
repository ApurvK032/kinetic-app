package com.apurvk.kinetic.ui

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.apurvk.kinetic.data.local.CardioSessionEntity
import com.apurvk.kinetic.data.local.CookedBatchEntity
import com.apurvk.kinetic.data.local.GymExerciseLogEntity
import com.apurvk.kinetic.data.local.KineticDatabase
import com.apurvk.kinetic.data.local.MealLogEntity
import com.apurvk.kinetic.data.repository.KineticRepository
import com.apurvk.kinetic.domain.fitness.FitnessEstimator
import com.apurvk.kinetic.domain.nutrition.NutritionEstimator
import com.apurvk.kinetic.domain.workout.WorkoutTemplates
import com.apurvk.kinetic.util.todayKey
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class GymExerciseDraft(
    val name: String,
    val target: String,
    val completed: Boolean = false,
    val sets: String = "",
    val reps: String = "",
    val weight: String = ""
)

data class KineticUiState(
    val dateKey: String,
    val meals: List<MealLogEntity> = emptyList(),
    val gymLogs: List<GymExerciseLogEntity> = emptyList(),
    val cardio: List<CardioSessionEntity> = emptyList(),
    val batches: List<CookedBatchEntity> = emptyList(),
    val selectedTemplate: String = "Strength A",
    val gymDrafts: List<GymExerciseDraft> = emptyList()
) {
    val caloriesIn: Int = meals.sumOf { it.calories }
    val proteinGrams: Int = meals.sumOf { it.proteinGrams }
    val exerciseCalories: Int = cardio.sumOf { it.caloriesBurned }
    val loggedMealSections: Int = meals.map { it.section }.distinct().size
    val gymExercisesCompleted: Int = gymLogs.count { it.completed }
    val gymCompleted: Boolean = gymExercisesCompleted >= 4
}

class KineticViewModel(private val repository: KineticRepository) : ViewModel() {
    private val dateKey = todayKey()
    private val selectedTemplate = MutableStateFlow("Strength A")
    private val gymDrafts = MutableStateFlow(WorkoutTemplates.strengthA.toDrafts())
    private val workoutDraftState = combine(selectedTemplate, gymDrafts) { template, drafts ->
        template to drafts
    }

    val uiState = combine(
        repository.observeMeals(dateKey),
        repository.observeGymLogs(dateKey),
        repository.observeCardio(dateKey),
        repository.observeBatches(),
        workoutDraftState
    ) { meals, gymLogs, cardio, batches, workoutDraft ->
        KineticUiState(
            dateKey = dateKey,
            meals = meals,
            gymLogs = gymLogs,
            cardio = cardio,
            batches = batches,
            selectedTemplate = workoutDraft.first,
            gymDrafts = workoutDraft.second
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = KineticUiState(
            dateKey = dateKey,
            selectedTemplate = "Strength A",
            gymDrafts = WorkoutTemplates.strengthA.toDrafts()
        )
    )

    fun addMeal(section: String, description: String) {
        val trimmed = description.trim()
        if (trimmed.isBlank()) return

        val estimate = NutritionEstimator.estimate(trimmed)
        viewModelScope.launch {
            repository.addMeal(
                MealLogEntity(
                    dateKey = dateKey,
                    section = section,
                    description = trimmed,
                    calories = estimate.calories,
                    proteinGrams = estimate.proteinGrams,
                    createdAt = System.currentTimeMillis()
                )
            )
        }
    }

    fun deleteMeal(meal: MealLogEntity) {
        viewModelScope.launch {
            repository.deleteMeal(meal)
        }
    }

    fun selectTemplate(template: String) {
        selectedTemplate.value = template
        gymDrafts.value = when (template) {
            "Strength B" -> WorkoutTemplates.strengthB.toDrafts()
            else -> WorkoutTemplates.strengthA.toDrafts()
        }
    }

    fun updateGymDraft(
        name: String,
        completed: Boolean? = null,
        sets: String? = null,
        reps: String? = null,
        weight: String? = null
    ) {
        gymDrafts.update { drafts ->
            drafts.map { draft ->
                if (draft.name == name) {
                    draft.copy(
                        completed = completed ?: draft.completed,
                        sets = sets ?: draft.sets,
                        reps = reps ?: draft.reps,
                        weight = weight ?: draft.weight
                    )
                } else {
                    draft
                }
            }
        }
    }

    fun saveGymDraft() {
        val template = selectedTemplate.value
        val completed = gymDrafts.value.filter { it.completed }
        if (completed.isEmpty()) return

        val now = System.currentTimeMillis()
        val logs = completed.map { draft ->
            GymExerciseLogEntity(
                dateKey = dateKey,
                templateName = template,
                exerciseName = draft.name,
                target = draft.target,
                completed = true,
                sets = draft.sets.ifBlank { "-" },
                reps = draft.reps.ifBlank { "-" },
                weight = draft.weight.ifBlank { "-" },
                createdAt = now
            )
        }

        viewModelScope.launch {
            repository.addGymLogs(logs)
            gymDrafts.value = when (template) {
                "Strength B" -> WorkoutTemplates.strengthB.toDrafts()
                else -> WorkoutTemplates.strengthA.toDrafts()
            }
        }
    }

    fun deleteGymLog(log: GymExerciseLogEntity) {
        viewModelScope.launch {
            repository.deleteGymLog(log)
        }
    }

    fun addCardio(type: String, durationMinutes: Int, distance: String, intensity: String, notes: String) {
        if (durationMinutes <= 0) return

        val calories = FitnessEstimator.estimateCalories(type, intensity, durationMinutes)
        viewModelScope.launch {
            repository.addCardio(
                CardioSessionEntity(
                    dateKey = dateKey,
                    type = type,
                    durationMinutes = durationMinutes,
                    distance = distance.trim(),
                    intensity = intensity,
                    caloriesBurned = calories,
                    notes = notes.trim(),
                    createdAt = System.currentTimeMillis()
                )
            )
        }
    }

    fun deleteCardio(cardio: CardioSessionEntity) {
        viewModelScope.launch {
            repository.deleteCardio(cardio)
        }
    }

    fun addBatch(dishName: String, servingsLeft: Int, plannedFor: String) {
        val trimmedDish = dishName.trim()
        if (trimmedDish.isBlank() || servingsLeft <= 0) return

        val estimate = NutritionEstimator.estimate(trimmedDish)
        viewModelScope.launch {
            repository.addBatch(
                CookedBatchEntity(
                    dishName = trimmedDish,
                    servingsLeft = servingsLeft,
                    plannedFor = plannedFor.trim(),
                    caloriesPerServing = estimate.calories,
                    proteinPerServing = estimate.proteinGrams,
                    createdAt = System.currentTimeMillis()
                )
            )
        }
    }

    fun deleteBatch(batch: CookedBatchEntity) {
        viewModelScope.launch {
            repository.deleteBatch(batch)
        }
    }

    private fun List<com.apurvk.kinetic.domain.workout.ExerciseTemplate>.toDrafts(): List<GymExerciseDraft> {
        return map { template ->
            GymExerciseDraft(
                name = template.name,
                target = template.target
            )
        }
    }
}

class KineticViewModelFactory(context: Context) : ViewModelProvider.Factory {
    private val appContext = context.applicationContext

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val database = KineticDatabase.getInstance(appContext)
        val repository = KineticRepository(database.kineticDao())
        return KineticViewModel(repository) as T
    }
}
