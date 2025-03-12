# Album Viewer
An Android application that displays album data from a remote API with offline support.

## Project Structure
This project follows Git best practices with a structured branching strategy:
- **`master`**: The main branch containing the stable, production-ready code. **This is the branch that should be reviewed**.
- Feature branches: Individual branches were used during development for specific features and components.
The project was developed incrementally with commit messages to demonstrate the development process.

## Technology Stack
- Kotlin
- MVVM Architecture with Clean Architecture principles
- Jetpack Compose
- Kotlin Coroutines & Flow
- Hilt for dependency injection
- Retrofit for API communication
- Room for local data persistence
- Coil for image loading

## Setup
1. Clone this repository
2. Open the project in Android Studio
3. Build and run the application on a device or emulator

## Documentation
For detailed information about the architecture, design patterns, and technology choices, please see the [Documentation](DOCUMENTATION.md).

## Acknowledgments
- API provided by [LeBonCoin](https://leboncoin.fr)
