package com.apurvk.kinetic.domain.workout

data class ExerciseTemplate(
    val name: String,
    val target: String
)

object WorkoutTemplates {
    val strengthA = listOf(
        ExerciseTemplate("Goblet squat", "3 x 10-12"),
        ExerciseTemplate("Chest press", "3 x 8-12"),
        ExerciseTemplate("Lat pulldown", "3 x 8-12"),
        ExerciseTemplate("Dumbbell RDL", "3 x 10"),
        ExerciseTemplate("Cable row", "3 x 10-12"),
        ExerciseTemplate("Plank", "3 x 30-60s")
    )

    val strengthB = listOf(
        ExerciseTemplate("Dumbbell split squat", "3 x 8 each"),
        ExerciseTemplate("Dumbbell shoulder press", "3 x 8-12"),
        ExerciseTemplate("Cable face pull", "3 x 12-15"),
        ExerciseTemplate("Chest press or pushups", "2-3 x 8-12"),
        ExerciseTemplate("Lat pulldown", "2-3 x 8-12"),
        ExerciseTemplate("Cable woodchop", "3 x 10 each"),
        ExerciseTemplate("Calf raises", "3 x 12-20")
    )
}
