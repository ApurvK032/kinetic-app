# Kinetic MVP PRD

## Product Summary

Kinetic is an Android-first fitness and diet tracker for fast, low-friction logging. The app helps the user track cycling, gym workouts, home-cooked meals, planned food, protein intake, approximate calories, and weekly consistency.

The MVP should feel like a personal daily companion, not a generic calorie tracker.

## Target User

- 24-year-old male.
- Around 5 ft 7.5 in and 82 kg at planning start.
- Lives near University of Minnesota Twin Cities.
- Recently bought a Giant Escape bike.
- Goal: lose waist, thigh, and belly fat over 3-4 months while improving cycling fitness and preserving/gaining muscle.
- Eats mostly home-cooked Indian vegetarian food.
- Wants voice-first logging with minimal typing.

## Problem

Most fitness and diet apps are too high-friction. The user needs a way to log real life quickly:

- "Easy ride, 42 minutes, around 6 miles."
- "Strength A done, chest press 3 sets, lat pulldown 3 sets."
- "Lunch was 2 rotis, chhole, dahi, and salad."

The app should turn this into structured logs, estimates, and progress signals.

## MVP Goals

- Make daily logging fast enough to use consistently.
- Support the actual fitness routine: cycling plus fixed gym workouts.
- Support the actual diet pattern: Indian vegetarian meals and cooked batches.
- Estimate protein, calories, activity, and weekly consistency.
- Keep calculations approximate, editable, and transparent.
- Run structured extraction on Android with Gemma where practical.

## Non-Goals

- Medical advice or diagnosis.
- Perfect calorie precision.
- Full food database search at launch.
- Native widget recording at launch.
- Social features.
- Advanced coaching.
- Direct Strava/NoiseFit integration in the first build unless easy.

## Main Navigation

The app should open to Today.

```text
Today
  Fitness
    Cardio
    Gym
  Diet
    Eat
    Plan
  Stats
```

## Core Screens

### Today

Purpose: show the user's day at a glance.

Must show:

- Today's planned fitness.
- Fitness completed or pending.
- Meal sections logged or pending.
- Current protein estimate.
- Current calorie intake estimate.
- Exercise calories estimate.
- Suggested next action.

### Fitness

Fitness has two areas: Cardio and Gym.

Cardio includes:

- Cycling.
- Running.
- Walking.

MVP cardio logging:

- Manual duration.
- Optional distance.
- Optional intensity.
- Optional notes.

Future cardio import:

- Strava.
- Google Fit.
- NoiseFit or exported watch data.

Gym includes:

- Strength A template.
- Strength B template.
- Checklist for exercises completed.
- Sets and reps entry.
- Optional weight used.
- Voice input for quick logging.

The user has a fixed workout pool, so the MVP should not require searching a huge exercise database.

### Diet

Diet has two areas: Eat and Plan.

Eat sections:

- Pre cycling/gym fruit.
- Tea/drinks.
- Breakfast.
- Lunch.
- Dinner.
- Extras/snacks.

Each section supports:

- Voice input.
- Text input.
- Quick food defaults.
- Confirmation before save.

Plan sections:

- Current week.
- Cooked food batches.
- Available ingredients.
- Planned meals for the next 2-3 days.

The Plan screen should answer:

- What have I already cooked?
- How many servings are left?
- What meals did I plan from that food?
- Does the plan roughly meet protein needs?

### Stats

MVP stats:

- Daily protein estimate.
- Daily calorie intake estimate.
- Exercise calories estimate.
- Weekly cycling consistency.
- Weekly gym consistency.
- Meal logging consistency.
- Weight trend.
- Waist trend.

Stats should be signals, not exact truth.

## AI Behavior

The on-device model should parse logs and produce structured JSON.

Recommended Android model path:

- First test: Gemma 3 1B int4.
- Second test: Gemma 3n E2B.
- Device target: Pixel 7a.

Gemma responsibilities:

- Classify log type.
- Extract meal name, foods, quantities, and units.
- Extract workout type, exercises, sets, reps, duration, distance, and intensity.
- Mark assumptions.
- Ask for clarification only when the log is too ambiguous.

Gemma must not be treated as the nutrition source of truth. App code should calculate protein, calories, and burn.

## Calculation Rules

### Food Intake

Use food defaults and user overrides.

```text
total calories = sum(quantity * calories per serving)
total protein = sum(quantity * protein per serving)
```

Food estimates should include confidence:

- High: exact user quantity and known default.
- Medium: known food with assumed serving.
- Low: vague food or unclear portion.

### Exercise Burn

Use this priority order:

1. Imported calorie burn from a trusted app/device.
2. Duration + distance + body weight.
3. Duration + intensity + MET value.
4. User-entered estimate.

Fallback formula:

```text
calories burned = MET * 3.5 * body weight kg / 200 * minutes
```

### Daily Energy

Estimate baseline with Mifflin-St Jeor:

```text
BMR = 10 * weight_kg + 6.25 * height_cm - 5 * age + 5
```

Then:

```text
estimated daily burn = baseline activity burn + exercise calories
estimated deficit = estimated daily burn - calorie intake
```

The app should compare estimates against 2-4 week weight and waist trends and adjust assumptions.

## Core Data Objects

- UserProfile.
- WorkoutTemplate.
- WorkoutSession.
- ExerciseSet.
- CardioSession.
- MealLog.
- FoodItem.
- CookedBatch.
- MealPlanEntry.
- DailySummary.
- WeeklySummary.

See [Data Model](DATA_MODEL.md) for the working schema.

## MVP User Stories

- As a user, I can open Today and see what remains to log.
- As a user, I can log a ride by voice or quick manual entry.
- As a user, I can complete Strength A/B with checkboxes and sets/reps.
- As a user, I can speak what I ate and see a structured confirmation.
- As a user, I can save cooked food batches and plan meals from them.
- As a user, I can see rough protein and calorie totals for the day.
- As a user, I can correct bad AI assumptions before saving.

## Acceptance Criteria

- The user can log a full day of meals in under 2 minutes.
- The user can log a fixed gym workout without typing exercise names.
- The user can voice-log common Indian meals and receive editable structured output.
- The user can create a cooked batch and assign it to meals for the current week.
- The app can produce daily protein, calories, and exercise estimates.
- All estimates are editable and marked as approximate.

## Risks

- On-device model latency on Pixel 7a may be too slow for a polished feel.
- Speech-to-text may struggle with Indian food names.
- Nutrition estimates can drift if portions are not personalized.
- Watch/Strava/NoiseFit integration may require separate OAuth/export work.
- Widget voice capture may be limited by Android/iOS restrictions.

## MVP Success Metrics

- User logs at least 5 days per week.
- User logs most meals with less effort than a calorie app.
- User can see whether protein target is being met.
- User can identify missed workouts or low-protein days.
- The app helps maintain the 5 cycling + 2 strength weekly rhythm.
