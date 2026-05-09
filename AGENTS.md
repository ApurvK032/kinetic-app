# Kinetic Fitness App

## Project Purpose
Kinetic is a personal fitness, diet, and recovery tracking app for the user's 3-4 month fat-loss and conditioning goal. The core idea is low-friction logging through a home-screen widget with two main actions: Exercise and Diet. The user taps one, speaks naturally, and the app turns that into structured logs and progress summaries.

## Tech Stack
Not chosen yet. Current direction:
- Android-first MVP, targeting Pixel 7a for early testing.
- App options: native Android/Kotlin with Jetpack Compose, or React Native with native Android modules.
- Storage: local-first database for MVP; sync/backend later if needed.
- AI: speech-to-text plus on-device Gemma for structured extraction.
- Candidate models: Gemma 3 1B int4 first; Gemma 3n E2B as stronger experiment.
- Integrations later: Strava, Health Connect/Google Fit, NoiseFit export/sync if practical.

## Commands
- No setup command yet.
- No run command yet.
- No test command yet.

## Important Files
- `README.md`: GitHub-facing project overview.
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
- Final app platform and database are not selected.
- Pixel 7a on-device Gemma performance needs a real spike before committing to the model.
- Strava/Health Connect/NoiseFit integration feasibility is not yet verified.
- Native home-screen widgets cannot always capture voice directly; MVP may need widget tap -> tiny voice capture screen -> save.
- Nutrition estimates should be approximate and editable, not treated as medical-grade tracking.

## Workflow
- Start by reading this file, `README.md`, and `PROJECT_CONTEXT.md`.
- Update `PROJECT_CONTEXT.md` when product direction or durable fitness assumptions change.
- Use `AGENT_LOG.md` for meaningful session summaries.
- Do not delete user context or overwrite project direction without confirmation.
