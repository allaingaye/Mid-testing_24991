
AUCA Library Management System – Software Testing Project
Project Overview

The AUCA Library Management System is a software testing project designed to validate the functionality, reliability, and quality of a Library Management System. Instead of focusing on a user interface, the project emphasizes automated testing of the system's business logic, data access layer, and database interactions using mock data and test scenarios.

The system manages users, memberships, books, borrowing records, shelves, rooms, and locations based on the provided domain model.

Testing Scope

The project tests the following modules:

User Management
Membership Management
Book Management
Borrowing and Returning Books
Shelf and Room Management
Location Management
Testing Types
Unit Testing

Tests individual classes, methods, and business logic in isolation.

Integration Testing

Tests the interaction between application layers, Hibernate entities, repositories, services, and the PostgreSQL database.

Technologies and Tools
Java
Maven
JUnit 5
Hibernate ORM
PostgreSQL
Git & GitHub
Example Test Scenarios
Create a new user successfully
Validate membership registration
Add and retrieve books
Borrow an available book
Prevent borrowing of unavailable books
Return a borrowed book
Verify membership status validation
Validate database persistence using Hibernate

Project Goal
The goal of this project is to apply software testing principles and automated testing techniques to ensure that the Library Management System functions correctly, interacts properly with the PostgreSQL database through Hibernate, handles invalid inputs appropriately, and meets all specified business requirements while maintaining high software quality and reliability.
