# Kinetic Fitness App - Project Context

## One-Line Product Idea
Kinetic is a personal voice-first fitness and diet logger for cycling, strength training, weight loss, recovery, and habit consistency.

## Current Product Shape
Kinetic should be Android-first and open to a Today screen.

Main areas:
- Today: current daily plan, logs, and next best action.
- Fitness:
  - Cardio: cycling, running, and walking, initially manual and later imported from Strava, Health Connect/Google Fit, or NoiseFit if practical.
  - Gym: fixed Strength A/B templates with checkboxes, sets, reps, optional weight, and voice input.
- Diet:
  - Eat: pre cycling/gym fruit, tea/drinks, breakfast, lunch, dinner, and extras/snacks.
  - Plan: current week plan based on cooked batches, available ingredients, and meals planned for the next 2-3 days.
- Stats: approximate protein, calorie intake, exercise calories, workout consistency, meal logging consistency, weight trend, and waist trend.

Core product rule:
- Gemma should parse messy voice/text into structured JSON.
- App code should calculate calories, protein, burn estimates, and summaries.
- Estimates should remain editable and approximate.

## User Context
- User is a 24-year-old male.
- Height: about 5 ft 7.5 in.
- Weight: about 82 kg at the start of planning.
- Lives near the University of Minnesota Twin Cities.
- Recently bought a used Giant Escape bike.
- Goal: lose waist, thigh, and belly fat over 3-4 months while improving fitness and eventually preserving/gaining muscle.
- Current situation: diet has become stricter over the last couple of weeks, but physical activity has been low.
- Important health note: spot reduction is not realistic; the goal should be overall fat loss with cycling, strength training, sleep, and diet consistency.

## Activity Plan
The agreed weekly plan:

| Day | Activity | Intensity |
|---|---|---|
| Sunday | Solo ride | Easy |
| Monday | Solo ride | Very easy / recovery |
| Tuesday | Team ride | Moderate |
| Wednesday | Strength A | Moderate |
| Thursday | Team ride | Moderate to hard |
| Friday | Strength B | Moderate |
| Saturday | Team ride | Longer easy/steady or moderate |

This gives:
- 5 cycling days per week.
- 2 strength days per week.
- No fixed full rest day, but Sunday and Monday are easy/recovery-style rides.
- If fatigue builds up, Sunday should occasionally become a full rest day.

## Cycling Context
- Team rides happen Tuesday, Thursday, and Saturday.
- The user's friend's app, Gopher Summer Rides, already supports team ride planning.
- Kinetic is for personal tracking outside the team app.
- Solo rides should be easy and sustainable, especially Sunday and Monday.
- Main ride goals:
  - Build consistency.
  - Improve cardiovascular fitness.
  - Support calorie deficit.
  - Avoid making every ride hard.

## Strength Training Context
The user's apartment gym has:
- Dumbbells.
- Chest press machine.
- Lat pulldown/back machine.
- Cable/multi-machine with ropes and handles.
- Cardio machines.

This is enough for the first 3-4 months.

### Strength A
| Exercise | Equipment | Sets x Reps |
|---|---|---:|
| Goblet squat | Dumbbell | 3 x 10-12 |
| Chest press | Machine | 3 x 8-12 |
| Lat pulldown | Machine | 3 x 8-12 |
| Dumbbell Romanian deadlift | Dumbbells | 3 x 10 |
| Cable row | Cable machine | 3 x 10-12 |
| Plank | Bodyweight | 3 x 30-60 sec |

### Strength B
| Exercise | Equipment | Sets x Reps |
|---|---|---:|
| Dumbbell split squat | Dumbbells | 3 x 8 each leg |
| Dumbbell shoulder press | Dumbbells | 3 x 8-12 |
| Cable face pull | Cable rope | 3 x 12-15 |
| Chest press or pushups | Machine/bodyweight | 2-3 x 8-12 |
| Lat pulldown | Machine | 2-3 x 8-12 |
| Cable woodchop | Cable machine | 3 x 10 each side |
| Calf raises | Dumbbells/bodyweight | 3 x 12-20 |

Training rule:
- Use a weight where the last 2 reps are hard but form is clean.
- When the top of the rep range is achieved for all sets, increase weight slightly next time.

## Diet Context
The user is Indian and cooks most food at home.

Common foods:
- Tea.
- Banana.
- Sweet corn.
- Multigrain or brown bread sandwich, often paneer sandwich.
- Poha.
- Boiled sprouts: chana, moong, peanuts with vegetables.
- Pasta with red sauce.
- Protein shake with banana or berries, honey, nuts, and whole milk.
- Indian meals: dal, chhole/chana, rajma, aloo matar plus soybeans, paneer.
- Dahi/raita.
- Frozen uncooked roti heated before meals.
- Sometimes sewai or Maggi when short on time.
- Cooking fats: ghee and olive oil.
- Rice has been stopped, though rice is not inherently bad if portion-controlled.

Nutrition concern:
- The food choices are mostly fine.
- The main risks are irregular meals, skipped meals, liquid calories, high oil/ghee, too many nuts, honey, whole milk, paneer, and backup meals with low protein.

## Preferred Meal Timing
The user does not want to eat 4 full meals.

Preferred schedule:

| Time | Plan |
|---|---|
| Before ride/gym | Banana plus water |
| After ride/gym | Tea only |
| 10-11 AM | Protein shake plus small food |
| 2-3 PM | Lunch |
| 7-8 PM | Dinner |

This is acceptable. The 10-11 AM meal should function as the real breakfast/protein meal.

## Protein Targets
Target range:
- Rough goal: 105-140 g protein per day.
- Earlier guidance: about 115-140 g/day is a useful practical range for this user.

Suggested distribution:

| Meal | Protein Target |
|---|---:|
| 10-11 AM meal | 25-35 g |
| Lunch | 40-50 g |
| Dinner | 40-50 g |

Good vegetarian Indian protein anchors:
- Dal/lentils.
- Chhole/chana.
- Rajma.
- Soy chunks.
- Paneer, but portion-controlled because it is calorie dense.
- Tofu.
- Dahi/Greek yogurt.
- Milk.
- Whey/protein powder if used.

Approximate protein examples:
- 1 cup cooked dal/lentils: 12-18 g.
- 1 cup chhole/rajma: 14-16 g.
- 50 g dry soy chunks: 25+ g.
- 100 g paneer: about 18 g, calorie dense.
- 250 ml milk: about 8 g.
- 1 scoop whey: about 20-25 g.

## Protein Shake Guidance
The protein shake should be taken around 10-11 AM as part of breakfast, not as an extra random drink.

Better fat-loss version:
- 250-350 ml low-fat/toned milk or curd.
- 1 scoop whey/protein if available.
- Banana or berries, not always both.
- No honey, or very little.
- Very small nuts portion, or skip nuts.

Avoid making the shake a 750 ml whole milk shake with banana, honey, and large nuts every day, because it can become a 700-1000 calorie drink.

## Meal Templates
### 10-11 AM Meal
Protein shake plus one small food item:
- Paneer/tofu sandwich.
- Moong/chana sprouts with onion, tomato, lemon.
- Smaller poha plus curd.
- 1-2 besan chillas.
- Thick dahi/Greek yogurt with fruit.

### Lunch
Strong balanced meal:
- Dal/chhole/rajma/soy/paneer/tofu.
- 1-2 rotis, or measured rice if desired.
- Big sabzi/salad.
- Dahi/raita if useful.
- Oil/ghee portion controlled.

### Dinner
Similar to lunch, slightly lighter:
- Protein source.
- Lots of vegetables.
- 1-2 rotis.
- Dahi if protein is low that day.

## Plate Rule
Each lunch/dinner should answer:
1. Where is the protein?
2. Where are the vegetables/fiber?
3. How much oil/ghee was used?
4. Is the carb portion controlled?

Recommended plate:
- 1/2 vegetables/salad.
- 1/4 protein.
- 1/4 carbs.

## Sleep Context
Preferred sleep:
- Usually 11 PM to 6 AM.
- Target: 7 hours generally, up to 8 hours on harder days.

Guidance:
- 7 hours is fine as the baseline.
- Try for 7.5-8 hours if recovery is poor, after hard rides/gym days, or during high stress.
- Avoid falling below 7 hours as a pattern.
- If 11 PM to 6 AM includes too much scrolling before sleep, wind down earlier around 10:30-10:45 PM.

## App Interaction Model
The app should not depend on the user opening a full app repeatedly.

Preferred interaction:
- Home-screen widget with two main buttons:
  - Exercise
  - Diet
- User taps one button and speaks naturally.
- App transcribes the speech, extracts structured data, shows a quick confirmation, then saves.

Important implementation caveat:
- Native home-screen widgets may not be able to directly record audio due to iOS/Android restrictions.
- Practical MVP: widget tap -> opens a tiny voice capture screen -> user speaks -> app saves -> returns to home screen.

## On-Device AI Direction
The current Android target is Pixel 7a.

Likely model path:
- First candidate: Gemma 3 1B int4 for structured extraction.
- Second candidate: Gemma 3n E2B for stronger Android-first parsing if latency is acceptable.
- Avoid using the model as a nutrition database or calculator.

Preferred pipeline:
1. Capture voice or typed text.
2. Transcribe speech if needed.
3. Use Gemma to extract structured JSON.
4. Validate output against app schema.
5. Show quick confirmation.
6. Save logs.
7. Run deterministic nutrition, exercise, and summary calculations.

## Example Voice Logs
Exercise:
- "Easy ride, 42 minutes, around 6 miles, felt good."
- "Strength A completed. Chest press 3 sets, lat pulldown 3 sets, goblet squat 3 sets."
- "Skipped ride today because legs were sore."

Diet:
- "Breakfast was protein shake with banana and paneer sandwich."
- "Lunch was 2 rotis, chhole, dahi, and salad."
- "Dinner was rajma, 2 rotis, salad, and raita."

Sleep/body:
- "Weight today 81.7 kg, waist 37 inches."
- "Slept 11 to 6, energy was okay."

## Data To Track
Core fields:
- Date.
- Exercise type: ride, Strength A, Strength B, recovery, rest.
- Exercise duration.
- Cycling distance if available.
- Exercise intensity: easy, recovery, moderate, hard.
- Meal type: pre-workout, breakfast, lunch, dinner.
- Foods.
- Protein estimate.
- Weight.
- Waist measurement.
- Optional thigh measurement.
- Sleep start and end.
- Perceived energy/recovery.
- Hunger/mood notes.

MVP data objects:
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

## Dashboard Ideas
Keep the dashboard simple:
- Today's plan.
- Exercise completed?
- Meals logged.
- Estimated protein.
- Weight trend.
- Waist trend.
- Weekly consistency score.
- Sleep consistency.
- Recovery warning if too many hard days happen.

## Product Priorities
1. Lowest-friction logging.
2. Accurate enough structured extraction from natural language.
3. Easy manual correction.
4. Weekly trend summaries.
5. No calorie obsession in MVP.
6. Make consistency visible.

## Possible MVP Stack
Option A: Android-first native
- Kotlin / Jetpack Compose.
- Tiny voice capture screen.
- Local-first database.
- Google AI Edge / MediaPipe LLM Inference for Gemma.

Option B: React Native Android-first
- React Native / Expo development build.
- Native Android module for on-device Gemma if needed.
- Local-first database.
- Later backend/sync if needed.

Option C: Chat-first prototype
- Telegram bot for text/voice.
- Web dashboard.
- Useful for validating logging behavior before native widgets.

Current preferred direction:
- Build toward an Android-first app with a Today screen and fast voice/manual logging.
- Run a Pixel 7a spike for speech capture and Gemma 3 1B/Gemma 3n E2B latency.
- Use Telegram only if it helps validate logging behavior quickly.

## Possible App Name
Chosen working name: Kinetic.

Why it fits:
- Motion, energy, cycling, activity.
- Broader than diet-only or gym-only.
- Works as a personal rhythm and progress tracker.

Other names considered:
- Pulse.
- Fuel.
- Stride.
- Pace.

## Safety And Scope Notes
- The app should not provide medical diagnosis.
- It should flag extreme fatigue, dizziness, or crash-diet signs as reasons to reduce intensity and consider professional advice.
- Nutrition values are estimates unless integrated with a food database.
- The app should encourage sustainable fat loss, not aggressive restriction.

## Current Build Status
- Native Android/Kotlin/Jetpack Compose scaffold exists.
- Milestone 2 local manual logging exists for Today, Fitness, Diet, Plan, and Stats.
- Room stores meal logs, gym exercise logs, cardio sessions, and cooked batches locally.
- Diet logs can be added by meal section and deleted.
- Diet logging now opens a quick checkbox-based dialog with common foods for that meal section, an "Other / notes" text field at the bottom, and a nonfunctional speech placeholder.
- Cardio logs can be added manually with estimated burn and deleted.
- Cardio and cooked batch dialogs also show a nonfunctional speech placeholder for the future voice path.
- Gym Strength A/B draft checklists can be saved and deleted.
- Cooked batches can be added and deleted.
- Simple estimators calculate rough calories/protein from known food keywords and cardio burn from MET-style estimates.
- Local build tooling was installed on the PC: JDK 17, Android Studio, Android SDK command-line tools, platform-tools/ADB, Android 35 platform, and build tools.
- GitHub-facing README, MVP PRD, working plan, folder structure, data model, and Gemma prompt docs have been added.
- Debug build succeeds with `.\gradlew.bat assembleDebug`.
- Debug APK output: `app/build/outputs/apk/debug/app-debug.apk`.
- Debug APK has been installed and launched on the Pixel 7a.
- Next step is testing the manual logging UX on phone, then improving edit flows and adding voice capture.
