# DataOrb Assignment

## Overview

The DataOrb Assignment is a payroll processing system developed by PPC, a payroll solution provider company. The system manages the payroll of various companies, ranging from small-scale to large-scale enterprises. It accepts employee data in either plain text (.txt) or CSV (.csv) format, facilitating the management of the employee life cycle from onboarding to exit.

## MVC Architecture

The project is designed following the Model-View-Controller (MVC) architecture, ensuring a structured and modular approach to development. This architecture separates the application into three interconnected components:

### Model
Represents the application's data and business logic. In this project, the `Employee` class and related data structures serve as the model, encapsulating information about employees and their payroll events.

### View
Handles the presentation and user interface. While not explicitly mentioned in the provided code, the view would typically represent the user interface through which users interact with the payroll system.

### Controller
Manages the communication between the model and the view. The `PayrollService` class acts as the controller, processing employee records, handling events, and managing employee data.

## Format of Employee Records

Employee data is provided in a structured format with specific fields for different events such as onboarding, salary, bonus, exit, and reimbursement. The provided records are in CSV format, allowing easy import and processing.

## Testing and Quality Assurance

The project incorporates unit testing using JUnit to ensure the correctness of the implemented functionalities. Testing covers critical aspects, including data processing, event handling, and reporting.

## How to Run

To run the project:

1. Clone the repository from the Git repository provided.
2. Open the project in your preferred IDE.
3. Ensure you have the necessary dependencies, including JUnit, configured.
4. Execute the `PayrollApp` class to run the payroll processing application.

## Assumptions and Design Choices

- The project assumes that the input data follows the specified format for employee records.
- The MVC architecture is chosen for its modularity, ease of maintenance, and separation of concerns.
- Unit testing is utilized to validate the functionality of individual components, ensuring a robust and error-free application.

## Future Improvements

- Implementing a graphical user interface (GUI) as part of the view component for a more user-friendly experience.
- Enhancing error handling to provide meaningful feedback to users in case of invalid or corrupted input data.

This brief overview summarizes the key aspects of the DataOrb Assignment, highlighting the architecture, format, testing, and design choices made in the development process.
