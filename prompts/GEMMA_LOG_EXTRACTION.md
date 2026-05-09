# Gemma Log Extraction Prompt

Use this prompt pattern for on-device structured extraction. The model should return JSON only.

## System Instruction

You convert fitness and diet logs into structured JSON for a personal tracking app. Extract only what the user said or what is a reasonable default assumption. Mark assumptions clearly. Do not calculate nutrition or calories. Do not give advice. Return valid JSON only.

## Diet Extraction Prompt

```text
Extract this diet log as JSON only.

Text: "{{transcript}}"

Allowed meal sections:
- pre_workout_fruit
- tea_drinks
- breakfast
- lunch
- dinner
- extras

Schema:
{
  "type": "diet_log",
  "section": string,
  "items": [
    {
      "food": string,
      "quantity": number|null,
      "unit": string|null,
      "assumption": string|null,
      "confidence": "high"|"medium"|"low"
    }
  ],
  "clarification_needed": boolean,
  "clarification_question": string|null
}
```

## Fitness Extraction Prompt

```text
Extract this fitness log as JSON only.

Text: "{{transcript}}"

Schema:
{
  "type": "fitness_log",
  "activity_type": "cycling"|"running"|"walking"|"gym"|"rest"|"recovery"|null,
  "workout_template": "Strength A"|"Strength B"|null,
  "duration_minutes": number|null,
  "distance": number|null,
  "distance_unit": "miles"|"km"|null,
  "intensity": "recovery"|"easy"|"moderate"|"hard"|null,
  "exercises": [
    {
      "name": string,
      "sets": number|null,
      "reps": number|null,
      "weight": number|null,
      "weight_unit": "lb"|"kg"|null,
      "duration_seconds": number|null,
      "assumption": string|null,
      "confidence": "high"|"medium"|"low"
    }
  ],
  "notes": string|null,
  "clarification_needed": boolean,
  "clarification_question": string|null
}
```

## Example Diet Input

```text
Lunch was 2 rotis, chhole, dahi, and salad.
```

## Example Diet Output

```json
{
  "type": "diet_log",
  "section": "lunch",
  "items": [
    {
      "food": "roti",
      "quantity": 2,
      "unit": "piece",
      "assumption": "medium roti",
      "confidence": "medium"
    },
    {
      "food": "chhole",
      "quantity": 1,
      "unit": "bowl",
      "assumption": "default bowl because quantity was not specified",
      "confidence": "medium"
    },
    {
      "food": "dahi",
      "quantity": 1,
      "unit": "bowl",
      "assumption": "default bowl because quantity was not specified",
      "confidence": "medium"
    },
    {
      "food": "salad",
      "quantity": 1,
      "unit": "serving",
      "assumption": "default serving because quantity was not specified",
      "confidence": "medium"
    }
  ],
  "clarification_needed": false,
  "clarification_question": null
}
```

## Example Gym Input

```text
Strength A completed. Chest press 3 sets of 10, lat pulldown 3 sets of 12, goblet squat 3 sets of 10, plank 45 seconds.
```

## Example Gym Output

```json
{
  "type": "fitness_log",
  "activity_type": "gym",
  "workout_template": "Strength A",
  "duration_minutes": null,
  "distance": null,
  "distance_unit": null,
  "intensity": null,
  "exercises": [
    {
      "name": "chest_press",
      "sets": 3,
      "reps": 10,
      "weight": null,
      "weight_unit": null,
      "duration_seconds": null,
      "assumption": null,
      "confidence": "high"
    },
    {
      "name": "lat_pulldown",
      "sets": 3,
      "reps": 12,
      "weight": null,
      "weight_unit": null,
      "duration_seconds": null,
      "assumption": null,
      "confidence": "high"
    },
    {
      "name": "goblet_squat",
      "sets": 3,
      "reps": 10,
      "weight": null,
      "weight_unit": null,
      "duration_seconds": null,
      "assumption": null,
      "confidence": "high"
    },
    {
      "name": "plank",
      "sets": null,
      "reps": null,
      "weight": null,
      "weight_unit": null,
      "duration_seconds": 45,
      "assumption": null,
      "confidence": "high"
    }
  ],
  "notes": null,
  "clarification_needed": false,
  "clarification_question": null
}
```
