# SmartInventory

## Overview

This project is an Inventory Application developed in Kotlin using Jetpack Compose for the UI. The application allows users to track their inventory. The project demonstrates proficiency in various aspects of Android development, including UI design, third-party library integration, HTTP calls, error handling, and architectural patterns.

## Features

- **Inventory**: Fetch products, Add, Edit, Delete products. Keep track of stocks.
- **Material3 Design**: The UI follows Material3 design guidelines for a modern and consistent look.
- **MVVM Architecture**: The application uses the Model-View-ViewModel (MVVM) architecture to separate concerns and improve maintainability.
- **Third-Party Libraries**: Utilizes Retrofit for network calls, Room for local data storage, Coroutines for asynchronous programming, and Hilt for dependency injection.
- **Error Handling**: Robust error handling to manage network issues and invalid user inputs.

## Technologies Used

- **Kotlin**: The primary programming language used for development.
- **Jetpack Compose**: Modern toolkit for building native UI.
- **Retrofit**: A type-safe HTTP client for Android.
- **Room**: Persistence library that provides an abstraction layer over SQLite.
- **Coroutines**: For managing background threads and simplifying asynchronous code.
- **Hilt**: Dependency injection library for Android.
- **Chart**: For visualizing data in the UI using Jetpack Compose.
- **Coil**: For loading network images
- **DummyJson API**: Provides fake products data.

## Project Structure

The project is structured according to the MVVM architecture pattern:

- **data**:  
  Contains the data layer of the application, including:
    - **Entities**: Data classes representing database tables.
    - **DAOs (Data Access Objects)**: Interfaces or classes for accessing and managing data in the database.
    - **DTOs (Data Transfer Objects)**: Data classes representing API response models.
    - **Repositories**: Classes that handle data operations, acting as a single source of truth for data from both local (database) and remote (API) sources.
- **di**:  
  Contains dependency injection setup using **Hilt**. This includes:
    - **Modules**: Classes that define how dependencies are provided (e.g., Retrofit, Room, ViewModels).
    - **Component**: Hilt’s dependency injection component for the application.
- **ui**:  
  Contains the UI layer of the application, including:
    - **Components**: Jetpack Compose components that define the UI.
    - **ViewModels**: Classes that manage UI-related data and logic, acting as a bridge between the UI and the data layer.
    - **Screens**: Composable functions representing individual screens or major UI sections.
- **util**:
  Contains utility classes and helper functions

## Screenshots

Below is a table showing a GIF of the application in action:

| GIF of the Application                               |
|------------------------------------------------------|
| ![SmartInventory App](gifs/smartinventory_gif_1.gif) | 


## APK

- Download and install the app here -> ![SmartInventory App](apk/smartinventory.apk)


## Getting Started

### Prerequisites

- Android Studio (latest version recommended)
- Kotlin plugin installed

### Installation

1. Clone the repository:
   ```bash
   git clone https://github.com/HenryUdorji/SmartInventory.git
   ```
2. Open the project in Android Studio.
   ```
3. Build and run the project on an emulator or physical device.

### Signing Configuration
- The application uses signing information stored in the `local.properties` file for secure build configurations. The following properties are required for signing the app:
  ```properties
  SIGNING_KEYSTORE_PASSWORD=your_keystore_password
  SIGNING_KEY_ALIAS=your_key_alias
  SIGNING_KEY_PASSWORD=your_key_password
  SIGNING_JKS_FILE_PATH=/path/to/your/keystore.jks
  ```
- Create a Keystore: If you don't already have a keystore, create one using the following command:
  ```bash
  keytool -genkey -v -keystore your_keystore.jks -keyalg RSA -keysize 2048 -validity 10000 -alias your_key_alias
  ```

### Usage

1. Launch the application.
2. Select the source and target currencies from the dropdown menus.
3. Enter the amount you wish to convert.
4. Press the "Convert" button to see the result.


## Error Handling

The application includes error handling for various scenarios, such as:

- **Network Errors**: Displaying a user-friendly message when the API call fails.
- **Invalid Input**: Ensuring the user enters a valid amount before attempting conversion.

## Conclusion

This Currency Converter Application demonstrates a solid understanding of modern Android development practices, including Jetpack Compose, MVVM architecture, and the use of third-party libraries like Retrofit, Room, Coroutines, and Hilt. The application is designed to be user-friendly, efficient, and scalable.

## License

This project is licensed under the MIT License. Visit the [MIT License website](https://mit-license.org/) for more details.

## Acknowledgments

- Jetpack Compose documentation for UI design guidance.
- Android developers community for various tutorials and resources.

---

Feel free to reach out if you have any questions or need further assistance with the project!