# Public Hospital Patient Navigation & Queue Guide

A lightweight Java Swing desktop/kiosk application built for low-bandwidth environments in public hospitals. It helps patients navigate between key stops, track queue status, and follow simple landmark directions with audio prompt cues.

## Problem
Crowded public hospitals often have confusing layouts, long queues, and high foot traffic. First-time or low-income visitors can easily get lost between registration, consultation, diagnostics, and pharmacy stops, resulting in confusion and missed turns.

## Solution
A low-bandwidth step-by-step navigation and queue guidance interface built in Java:
- Guides patients through sequential stops (`Stop 1 of 4` to `Stop 4 of 4`), including OPD Registration, Doctor Consultation, Diagnostic/Blood Lab, and Pharmacy.
- Provides clear floor, corridor, counter, and signboard cues.
- Displays token details (such as `#18`), currently serving token, people ahead, and estimated wait time.
- Includes voice prompt playback and simple navigation controls (`Previous Stop`, `Play Voice Prompt`, `Next Stop` / `Finish Visit`, and `New Visit`).

## Tech Stack
- **Language:** Java
- **UI:** Java Swing (`JFrame`, `JPanel`, `JLabel`, `JButton`, `JProgressBar`)
- **Audio:** `javax.sound.sampled` with background thread execution and guidance updates
- **Data & Bandwidth:** Lightweight local state designed for low-data operation

## Project Structure
- `src/Project.java` (or `Project` in your `src` folder): Main application source code.

## Future Scope
- Integrate with live hospital queue management systems and databases.
- Add multi-language text and spoken voice synthesis for regional accessibility.
- Deploy on hospital entrance kiosks or mobile web interfaces.
-
