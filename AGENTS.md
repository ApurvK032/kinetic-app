# Kinetic Fitness App

## Project Purpose
Kinetic is a personal fitness, diet, and recovery tracking app for the user's 3-4 month fat-loss and conditioning goal. The core idea is low-friction logging through a home-screen widget with two main actions: Exercise and Diet. The user taps one, speaks naturally, and the app turns that into structured logs and progress summaries.

## Tech Stack
Current stack:
- Native Android app.
- Kotlin.
- Jetpack Compose and Material 3.
- Gradle wrapper `8.10.2`.
- Android Gradle Plugin `8.7.3`.
- Compile/target SDK 35; min SDK 26.
- Local-first storage planned for MVP; sync/backend later if needed.
- Room local database is implemented for meals, gym logs, cardio sessions, and cooked batches.
- AI planned: speech-to-text plus on-device Gemma for structured extraction.
- Candidate models: Gemma 3 1B int4 first; Gemma 3n E2B as stronger experiment.
- Integrations later: Strava, Health Connect/Google Fit, NoiseFit export/sync if practical.

## Commands
- Build debug APK: `.\gradlew.bat assembleDebug`
- Check connected devices: `adb devices`
- Install debug APK on connected phone: `.\gradlew.bat installDebug`
- Debug APK output: `app/build/outputs/apk/debug/app-debug.apk`
- No automated test suite yet.

## Important Files
- `README.md`: GitHub-facing project overview.
- `settings.gradle.kts`, `build.gradle.kts`, `app/build.gradle.kts`: Android Gradle project.
- `app/src/main/java/com/apurvk/kinetic/MainActivity.kt`: Android entry point.
- `app/src/main/java/com/apurvk/kinetic/ui/KineticApp.kt`: Milestone 1 Compose UI.
- `app/src/main/java/com/apurvk/kinetic/ui/KineticViewModel.kt`: local UI state and save/delete actions.
- `app/src/main/java/com/apurvk/kinetic/data/local/`: Room entities, DAO, and database.
- `app/src/main/java/com/apurvk/kinetic/data/repository/`: local repository wrapper.
- `app/src/main/java/com/apurvk/kinetic/domain/nutrition/NutritionEstimator.kt`: rough known-food estimates.
- `app/src/main/java/com/apurvk/kinetic/domain/fitness/FitnessEstimator.kt`: rough cardio burn estimates.
- `app/src/main/java/com/apurvk/kinetic/domain/workout/WorkoutTemplates.kt`: Strength A/B templates.
- `app/src/main/java/com/apurvk/kinetic/data/sample/SampleKineticData.kt`: static sample data for UI prototype.
- `app/src/main/java/com/apurvk/kinetic/domain/model/KineticModels.kt`: first UI/domain models.
- `PROJECT_CONTEXT.md`: full product, fitness, cycling, diet, and logging context.
- `AGENT_LOG.md`: dated project work log.
- `AGENTS.md`: durable project instructions for future Codex sessions.
- `docs/MVP_PRD.md`: MVP product requirements.
- `docs/WORKING_PLAN.md`: phased build plan.
- `docs/FOLDER_STRUCTURE.md`: current and future repo structure.
- `docs/DATA_MODEL.md`: working domain/data schema.
- `prompts/GEMMA_LOG_EXTRACTION.md`: structured extraction prompt examples.

## Conventions
- Build around logging friction first; the dashboard is secondary.
- Prefer a Today-first app flow: Fitness, Diet, Plan, and Stats should all serve today's decisions.
- Treat Gemma as the parser/interpreter; deterministic app code should calculate nutrition and exercise estimates.
- Diet planning should center on cooked batches, available ingredients, and the current week's 2-3 day plan.
- Prefer small, focused app increments.
- Keep health guidance practical and non-medical; avoid extreme dieting advice.
- The user's preferred schedule is banana before workout, tea after, protein shake plus food at 10-11 AM, lunch at 2-3 PM, dinner at 7-8 PM.
- The weekly activity plan is 5 cycling days plus 2 strength days.

## Known Issues
- UI is now backed by local Room data for meals, gym, cardio, and batches.
- Meal logging uses quick checkbox options plus an "Other / notes" field at the bottom; speech buttons are visible placeholders only.
- Nutrition and burn estimates are simple known-food/MET approximations and need editing/calibration later.
- Voice logging and Gemma extraction are not implemented yet.
- Pixel 7a on-device Gemma performance needs a real spike before committing to the model.
- Strava/Health Connect/NoiseFit integration feasibility is not yet verified.
- Native home-screen widgets cannot always capture voice directly; MVP may need widget tap -> tiny voice capture screen -> save.
- Nutrition estimates should be approximate and editable, not treated as medical-grade tracking.

## Workflow
- Start by reading this file, `README.md`, and `PROJECT_CONTEXT.md`.
- Update `PROJECT_CONTEXT.md` when product direction or durable fitness assumptions change.
- Use `AGENT_LOG.md` for meaningful session summaries.
- Do not delete user context or overwrite project direction without confirmation.
