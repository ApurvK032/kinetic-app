# Kinetic Fitness App

Kinetic is a voice-first Android fitness, diet, and recovery tracker built around fast daily logging. The app is designed for a personal fat-loss and conditioning plan where the user cycles, does fixed gym workouts, eats mostly home-cooked Indian vegetarian meals, and wants useful stats without turning the app into a calorie obsession.

## Core Idea

The app has two main areas:

- Fitness: cycling/running/walking imports or manual logs, plus gym workout logging.
- Diet: quick meal logging and a weekly food plan based on what is cooked and available.

The first screen should be a Today view that answers:

- What did I do today?
- What did I eat today?
- What is planned from the food I already cooked?
- Am I roughly on track for protein, activity, and consistency?

## AI Role

Kinetic should use an on-device model as an interpreter, not as the source of truth.

```text
voice or text input
-> speech-to-text
-> Gemma extracts structured JSON
-> app calculator estimates calories, protein, activity, and trends
```

Target device for early Android planning: Pixel 7a.

Likely on-device model path:

- Start with Gemma 3 1B int4 for structured log extraction.
- Test Gemma 3n E2B as a stronger Android-first option.
- Keep deterministic nutrition and exercise formulas in app code.

## MVP Scope

The MVP should include:

- Today dashboard.
- Fitness logging:
  - cardio manual entry first, Strava/watch import later.
  - fixed gym templates with checkbox, sets, reps, and voice input.
- Diet logging:
  - pre-workout fruit.
  - tea/drinks.
  - breakfast.
  - lunch.
  - dinner.
  - extras/snacks.
- Diet planning:
  - current week plan.
  - cooked food batches.
  - available ingredients.
  - planned meals for the next 2-3 days.
- Stats:
  - protein estimate.
  - calorie intake estimate.
  - exercise calories estimate.
  - weekly consistency.
  - weight and waist trend once measurements exist.

## Current Status

This repository now includes a native Android/Kotlin/Jetpack Compose scaffold and Milestone 1 UI. The current app is a static prototype for the Today-first flow:

- Today.
- Fitness.
- Diet.
- Plan.
- Stats.

Milestone 2 adds local-first manual tracking:

- Room database persistence.
- Manual meal logs by section.
- Checkbox-first meal logging with an "Other / notes" field at the bottom.
- Visible speech input placeholders for future voice logging.
- Rough food protein/calorie estimates for common foods.
- Manual cardio logs with estimated burn.
- Strength A/B checklist drafts saved as gym logs.
- Cooked batch planning.
- Delete actions for saved logs.

The debug APK builds, installs, and launches on Pixel 7a.

## Build Commands

Requirements already used for local setup:

- JDK 17.
- Android Studio.
- Android SDK command-line tools.
- Android SDK Platform 35.
- Android SDK Build Tools 35.

Build:

```powershell
.\gradlew.bat assembleDebug
```

Install on a connected Android phone with USB debugging enabled:

```powershell
adb devices
.\gradlew.bat installDebug
```

Start here:

- [Project Context](PROJECT_CONTEXT.md)
- [MVP PRD](docs/MVP_PRD.md)
- [Working Plan](docs/WORKING_PLAN.md)
- [Folder Structure](docs/FOLDER_STRUCTURE.md)
- [Data Model](docs/DATA_MODEL.md)
- [Gemma Extraction Prompt](prompts/GEMMA_LOG_EXTRACTION.md)
