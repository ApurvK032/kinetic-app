# Data Model

This is the working data model for the MVP. It is intentionally simple and should be refined once the app stack is chosen.

## UserProfile

Stores personal defaults used for estimates.

```json
{
  "id": "user",
  "age": 24,
  "sex": "male",
  "height_cm": 171,
  "weight_kg": 82,
  "protein_target_min_g": 105,
  "protein_target_max_g": 140,
  "timezone": "America/Chicago"
}
```

## WorkoutTemplate

Fixed gym workout template.

```json
{
  "id": "strength_a",
  "name": "Strength A",
  "exercises": [
    {
      "exercise_id": "goblet_squat",
      "target_sets": 3,
      "target_reps": "10-12"
    }
  ]
}
```

## WorkoutSession

One completed or partial gym session.

```json
{
  "id": "session_001",
  "date": "2026-05-09",
  "template_id": "strength_a",
  "status": "completed",
  "notes": "Felt good"
}
```

## ExerciseSet

One set within a gym session.

```json
{
  "id": "set_001",
  "session_id": "session_001",
  "exercise_id": "chest_press",
  "set_number": 1,
  "reps": 10,
  "weight": 60,
  "weight_unit": "lb"
}
```

## CardioSession

Cycling, running, or walking.

```json
{
  "id": "cardio_001",
  "date": "2026-05-09",
  "type": "cycling",
  "source": "manual",
  "duration_minutes": 42,
  "distance_miles": 6,
  "intensity": "easy",
  "calories_burned": 360,
  "calorie_source": "estimated",
  "notes": "Felt good"
}
```

## FoodItem

Reusable food or ingredient estimate.

```json
{
  "id": "roti_medium",
  "name": "Roti",
  "serving_label": "1 medium",
  "calories_per_serving": 110,
  "protein_g_per_serving": 3.5,
  "carbs_g_per_serving": null,
  "fat_g_per_serving": null,
  "confidence": "medium",
  "user_default": true
}
```

## MealLog

One meal section for one day.

```json
{
  "id": "meal_001",
  "date": "2026-05-09",
  "section": "lunch",
  "raw_text": "Lunch was 2 rotis, chhole, dahi, and salad.",
  "items": [
    {
      "food_item_id": "roti_medium",
      "quantity": 2,
      "unit": "piece",
      "assumption": "medium roti"
    }
  ],
  "calories_estimate": 650,
  "protein_estimate_g": 29,
  "confidence": "medium"
}
```

Meal sections:

- pre_workout_fruit.
- tea_drinks.
- breakfast.
- lunch.
- dinner.
- extras.

## CookedBatch

Food cooked for multiple meals.

```json
{
  "id": "batch_001",
  "dish_name": "Chhole",
  "cooked_on": "2026-05-09",
  "servings_total": 4,
  "servings_left": 3,
  "calories_per_serving": 300,
  "protein_g_per_serving": 15,
  "notes": "Made with moderate oil"
}
```

## MealPlanEntry

Planned meal using cooked food or available ingredients.

```json
{
  "id": "plan_001",
  "date": "2026-05-10",
  "section": "lunch",
  "batch_id": "batch_001",
  "planned_servings": 1,
  "additions": ["2 rotis", "dahi", "salad"]
}
```

## DailySummary

Generated from logs.

```json
{
  "date": "2026-05-09",
  "protein_g": 118,
  "calories_in": 2050,
  "exercise_calories": 420,
  "estimated_deficit": 450,
  "gym_completed": true,
  "cardio_completed": true,
  "meal_sections_logged": 5
}
```

## WeeklySummary

Generated from daily summaries.

```json
{
  "week_start": "2026-05-04",
  "cycling_days": 5,
  "gym_days": 2,
  "protein_target_days": 4,
  "meal_logging_days": 6,
  "notes": "Good consistency; protein low on two days."
}
```

## Calculation Pipeline

```text
raw voice/text
-> transcript
-> Gemma structured JSON
-> validation
-> user confirmation
-> save log
-> deterministic calculations
-> daily/weekly summaries
```

## Calculation Ownership

Gemma:

- extracts foods, quantities, exercises, sets, reps, duration, distance, and assumptions.

App code:

- maps foods to defaults.
- calculates calories and protein.
- calculates exercise burn.
- computes daily and weekly summaries.
- stores confidence levels.
