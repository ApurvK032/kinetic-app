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

Status: in progress.

## Phase 1: Platform Spike

Goal: choose the Android implementation path.

Options to compare:

- Native Android with Kotlin/Jetpack Compose.
- React Native with Expo development build.
- PWA for early prototype, then Android later.

Decision criteria:

- Voice capture quality.
- Local database support.
- On-device Gemma integration.
- Ease of building Today and checklist flows.
- Future widget support.

Recommended likely path:

- Native Android or React Native with a native Android module for on-device model inference.
- Use Google AI Edge / MediaPipe LLM Inference for Gemma tests.

Deliverables:

- One-page decision note.
- Hello-world app.
- Voice capture spike.
- Gemma 3 1B or Gemma 3n E2B inference spike on Pixel 7a.

## Phase 2: Local Data Foundation

Goal: create the storage model before making screens fancy.

Deliverables:

- Local database.
- Seed data:
  - Strength A.
  - Strength B.
  - common foods.
  - MET values.
  - default meal sections.
- Deterministic nutrition calculator.
- Deterministic exercise burn calculator.

## Phase 3: Today and Manual Logging

Goal: make the app useful without AI.

Deliverables:

- Today screen.
- Manual cardio log.
- Gym checklist for Strength A/B.
- Meal sections for Diet > Eat.
- Cooked batch and current week plan.
- Daily summary.

This phase proves the product shape even if Gemma is not ready.

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

Push the documentation-first idea repo to GitHub, then do a small Android spike to test:

- microphone capture.
- local storage.
- Gemma inference latency on Pixel 7a.
