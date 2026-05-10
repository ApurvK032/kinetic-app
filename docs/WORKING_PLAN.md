# Working Plan

## Current Direction

Build Kinetic as an Android-first personal tracker. The first implementation should focus on Today, fast logging, fixed gym templates, meal logging, cooked batch planning, and approximate stats.

Do not start with a huge calorie database or complex integrations. Get the daily loop right first.

## Phase 0: Idea Package

Goal: prepare the repository for GitHub with clear docs.

Deliverables:

- README.
- MVP PRD.
- Folder structure.
- Data model.
- Gemma extraction prompt.
- Updated project context and agent log.

Status: complete.

## Phase 1: Platform Spike

Goal: choose the Android implementation path.

Decision:

- Use native Android with Kotlin and Jetpack Compose for the MVP.

Decision criteria:

- Voice capture quality.
- Local database support.
- On-device Gemma integration.
- Ease of building Today and checklist flows.
- Future widget support.

Current implementation:

- Native Android scaffold created.
- Milestone 1 static Compose UI created.
- Gradle debug build passes.

Deliverables:

- Native Android scaffold. Done.
- Today/Fitness/Diet/Plan/Stats UI. Done.
- Phone install smoke test. Next.
- Voice capture spike. Later.
- Gemma 3 1B or Gemma 3n E2B inference spike on Pixel 7a. Later.

## Phase 2: Local Data Foundation

Goal: create the storage model before making screens fancy.

Deliverables:

- Local database. Done with Room.
- Seed data:
  - Strength A. Done as templates.
  - Strength B. Done as templates.
  - common foods.
  - MET values. Done as simple estimator constants.
  - default meal sections. Done.
- Deterministic nutrition calculator. First rough version done.
- Deterministic exercise burn calculator. First rough version done.

## Phase 3: Today and Manual Logging

Goal: make the app useful without AI.

Deliverables:

- Today screen. Done.
- Manual cardio log. Done.
- Gym checklist for Strength A/B. Done.
- Meal sections for Diet > Eat. Done.
- Cooked batch and current week plan. First version done.
- Daily summary. First version done.

This phase proves the product shape even if Gemma is not ready.

Current remaining work:

- Better edit flows instead of delete/re-add.
- Improve defaults and food estimate calibration.
- Add persistent user profile defaults.
- Add empty-state onboarding hints.
- Add basic automated tests.

## Phase 4: Voice and Gemma Extraction

Goal: add the low-friction logging layer.

Deliverables:

- Voice capture screen.
- Speech-to-text integration.
- Gemma structured extraction service.
- Editable confirmation screen.
- JSON validation and fallback handling.
- Prompt test set with real meal and gym examples.

Rules:

- Gemma extracts fields.
- App code calculates nutrition and burn.
- User confirms before save.

## Phase 5: Stats and Weekly Review

Goal: make the user understand trends.

Deliverables:

- Protein trend.
- Calorie estimate trend.
- Cardio/gym consistency.
- Weight and waist trend.
- Weekly summary.
- Recovery warning if too many hard days stack up.

## Phase 6: Integrations

Goal: reduce manual cardio entry.

Possible integrations:

- Strava.
- Google Fit / Health Connect.
- NoiseFit export or accessible sync path.

Priority:

1. Health Connect if it captures watch/cardio data.
2. Strava if cycling becomes the main source of truth.
3. NoiseFit only if there is a practical export/sync route.

## First Build Milestone

The first usable version should support:

- Today screen.
- Manual cardio.
- Strength A/B checklist.
- Meal logs.
- Cooked batch planning.
- Daily protein and calorie estimates.

No external integrations required.

## Open Decisions

- Native Android vs React Native.
- Exact local database.
- Speech-to-text provider.
- Gemma 3 1B vs Gemma 3n E2B for Pixel 7a.
- Whether the first GitHub repo should include only docs or also an empty app scaffold.

## Recommended Next Step

Install the current debug build on the Pixel 7a and smoke test the navigation/UI:

- enable Developer options and USB debugging.
- connect the phone.
- run `adb devices`.
- run `.\gradlew.bat installDebug`.

After the manual logging smoke test, continue with edit flows, better estimates, and voice capture.
