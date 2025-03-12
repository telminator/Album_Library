This document summarizes the choices of architecture, patterns, and libraries applied to this project. 

## Architecture
The application follows the MVVM (Model-View-ViewModel) pattern with Clean Architecture. I chose this architecture as it provides better separation of concerns and logic, it makes the codebase more maintainable and adaptable to future changes.
Hence, the codebase is organized into three main layers:
### PRESENTATION: 
    - contains UI components (using Jetpack Compose) and View Models. Displays data to the user and handles user interaction.
### DOMAIN: 
    - contains business logic and domain models. This layer defines the core functionality of the application.
### DATA: 
    - handles data operations and provides it to the domain layer. It Handles API communication and manages local database operations. 


## Design Patterns
These are the identifiable design patterns used in this project
### REPOSITORY PATTERN:
    - The app uses the Repository pattern to abstract the data sources and provide data to the domain layer. I chose this because it centralizes all data access logic and decouples it from the rest of the app
### OBSERVER PATTERN:
    - I implemented this through Kotlin Flow. It allows for reactive programming where UI components observe data changes and update accordingly.
### MVVM PATTERN: 
    - As mentioned above, it separates UI from business logic, where the View Model manages UI state and business logic and  provides the data to the view


## Libraries
These are the main libraries used in this project
### UI - Jetpack Compose
    - Altouhg I haven't had any experience using Jetpack Compose, I chose it as it is recommended by Google for new Android projects, and also a plus in the job offer.  I value its reduced boilerplate code and better state handling compared to XML layouts. Also, the code is more intuitive and readable. 
### NAVIGATION - Navigation Compose
    - I used this as is the recommended by Google. Used the latest version, with type-safe route declaration.
### DEPENDENCY INJECTION - Hilt
    - I've only had experience working with Dagger in previous projects, but this is a simpler implementation of Dagger, and also recommended by Google.
### NETWORKING - Retrofit
    - I chose this as it's the industry standard for API communication and also what I've used in previous projects.
### LOCAL DATABASE - Room
    - Also used this in previous projects.
### IMAGE LOADING - Coil
    _ In my previous projects I've used Glide, but I decided to try this, as its known to be more lightweight and simpler to use.


Apart from the requested for the documentation, I've added these sections below that justify some of my actions and approaches to this project.

## Challenges and Solutions
This list the main challenges I identified in this project. I've listed them below and also the solution I applied to solve them.
### Large Dataset Handling
    **Challenge**: The API returns over 5,000 items, which could cause performance issues if loaded all at once.
    **Solution**: Implemented pagination to load data in smaller chunks, with automatic loading of the next page when the user approaches the end of the current data.
### Offline Support
    **Challenge**: Ensuring data availability when offline.
    **Solution**: Implemented Room database for local caching, with an offline-first approach that shows cached data immediately while refreshing in the background.
### Configuration Changes
    **Challenge**: Maintaining UI state and scroll position during configuration changes like rotation.
    **Solution**: Used ViewModel to preserve state across configuration changes.
### Image Loading with Headers
    **Challenge**: Adding a User-Agent header specifically for image requests.
    **Solution**: Implemented a custom OkHttp interceptor that adds the required header only to image requests.

## Testing Strategy
This lists the unit tests implemented for this project. 
### UNIT TESTS
    **Repository Tests**: Verify data fetching, mapping, and storage logic
    **ViewModel Tests**: Ensure correct state management and business logic