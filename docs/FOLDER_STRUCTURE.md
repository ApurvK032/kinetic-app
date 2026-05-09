# Folder Structure

This repository now contains a native Android/Kotlin/Jetpack Compose scaffold plus planning docs.

## Current Repository Structure

```text
Kinetic-Fitness-App/
  README.md
  AGENTS.md
  PROJECT_CONTEXT.md
  AGENT_LOG.md
  settings.gradle.kts
  build.gradle.kts
  gradle.properties
  gradlew
  gradlew.bat
  app/
    build.gradle.kts
    src/main/
      AndroidManifest.xml
      java/com/apurvk/kinetic/
        MainActivity.kt
        data/sample/
          SampleKineticData.kt
        domain/model/
          KineticModels.kt
        ui/
          KineticApp.kt
          theme/KineticTheme.kt
      res/
        drawable/ic_launcher.xml
        values/
        xml/
  docs/
    MVP_PRD.md
    WORKING_PLAN.md
    FOLDER_STRUCTURE.md
    DATA_MODEL.md
  prompts/
    GEMMA_LOG_EXTRACTION.md
```

## Planned App Structure

As implementation grows, keep product logic separate from screens.

```text
Kinetic-Fitness-App/
  app/
    src/
      features/
        today/
        fitness/
          cardio/
          gym/
        diet/
          eat/
          plan/
        stats/
      domain/
        fitness/
        nutrition/
        planning/
        summaries/
      services/
        ai/
        integrations/
        storage/
        speech/
      data/
        seed/
        repositories/
      ui/
        components/
        theme/
      navigation/
    tests/
  docs/
  prompts/
```

## Folder Responsibilities

- `features/today`: the main home screen and daily status cards.
- `features/fitness/cardio`: cycling, running, and walking logs, plus future imports.
- `features/fitness/gym`: Strength A/B templates, exercise checklist, sets, reps, and voice logs.
- `features/diet/eat`: meal logging for pre-workout fruit, tea, breakfast, lunch, dinner, and extras.
- `features/diet/plan`: cooked batches, available foods, current week plan, and 2-3 day meal planning.
- `features/stats`: daily and weekly summaries.
- `domain`: pure app rules and calculations.
- `services/ai`: Gemma prompts, structured extraction, validation, and fallback handling.
- `services/integrations`: Strava, Google Fit, NoiseFit export/import, or watch integrations.
- `services/storage`: local database and sync later.
- `services/speech`: voice capture and speech-to-text.
- `data/seed`: default foods, workout templates, MET values, and user-specific defaults.
- `ui`: shared design components.

## Structure Principles

- Keep Gemma integration behind a service boundary.
- Keep calculations deterministic and testable in `domain`.
- Keep food and workout defaults editable.
- Avoid tying MVP storage to a remote backend until the logging flow feels right.
- Start with manual cardio logs; add imports after core logging works.
