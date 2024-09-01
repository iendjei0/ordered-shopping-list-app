# Ordered Shopping List App

A Spring Boot application that allows you to manage a shopping list by adding ingredients, summing up duplicates, and sorting them based on a predefined order. The application currently uses a local H2 database for storage.

## Features

- **Add Ingredients**: Add ingredients to your shopping list. Each ingredient can have multiple counts, and duplicates are summed up.
- **Drag-and-Drop Ordering**: Define the order of your saved ingredients in the settings using a drag-and-drop interface.
- **Summing and Sorting**: The current shopping list is automatically summed up and sorted according to the order you have defined for your ingredients.

## Screenshots
<div style="display: flex; justify-content: space-between;">
  <img src="https://github.com/iendjei0/ordered-shopping-list-app/blob/main/images/home.png" style="width: 48%;"/>
  <img src="https://github.com/iendjei0/ordered-shopping-list-app/blob/main/images/settings.png" style="width: 48%;"/>
</div>

## Getting Started

### Prerequisites

- Java 17 or higher
- Maven 3.6.0 or higher

### Running the Application

1. **Clone the repository**:
    ```bash
    git clone https://github.com/iendjei0/ordered-shopping-list-app
    cd shopping-list-app/backend
    ```

2. **Build the project**:
    ```bash
    mvn clean install
    ```

3. **Run the application**:
    ```bash
    mvn spring-boot:run
    ```

4. The application will start on `http://localhost:8080`.

## Configuration

- The application is configured to use a local H2 database by default. If you want to use a different database, you can modify the `application.properties` file located in the `src/main/resources` directory.