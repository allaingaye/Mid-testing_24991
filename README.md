# 📚 AUCA Library Management System

#  Project Overview
The AUCA Library Management System is a dynamic web application developed as part of a Software Testing  Project. It is built using Java (Maven, Servlets, JSP)

The system automates library operations by managing books, memberships, borrowing activities, shelves, rooms, and user roles. It integrates role‑based access control and location management to ensure secure and efficient handling of library resources.

This project emphasizes software testing principles by validating user actions, enforcing borrowing limits, checking authentication, and ensuring data integrity across modules.

#  Objectives
Apply software testing techniques to validate core library operations.

Ensure secure authentication with hashed passwords.

Enforce membership rules and borrowing limits through test cases.

Verify librarian workflows for managing shelves, rooms, and book assignments.

Test fine calculation logic for late returns.

Demonstrate role‑based access control through functional and integration testing.

# Back-End

Java (Servlets, Maven)

Hibernate ORM

JDBC for database connectivity

PostgreSQL (auca_library_db)

#  System Features
Location Management
Users can create hierarchical locations: Province → District → Sector → Cell → Village.

Phone numbers are linked to locations, enabling quick lookup of a user’s province.

User Accounts & Security
Users can register accounts with hashed passwords for secure authentication.

Role‑based access ensures different privileges for librarians, administrators, students, and teachers.

Role-Based Access Control
Librarians: Full privileges (manage books, shelves, rooms, approve memberships).

HOD, Dean, Registrar, Manager: Read‑only access; cannot borrow books.

Students & Teachers: Can register for memberships and borrow books.

Membership Management
Gold: 50 Rwf/day, up to 5 books.

Silver: 30 Rwf/day, up to 3 books.

Striver: 10 Rwf/day, up to 2 books.

Borrowing limits are validated against membership type.

Book & Shelf Management
Librarians can assign books to shelves.

Shelves can be assigned to rooms.

System can count books in a specific room.

Borrowing & Fines
Fine initialized to 0 Rwf at borrow date.

Late returns automatically generate charges based on membership rate.

#  Software Testing Focus
This project is designed to demonstrate testing strategies:

Unit Testing: Validate membership rules, fine calculation, and borrowing limits.

Integration Testing: Ensure modules (user accounts, books, shelves, rooms) work together correctly.

Functional Testing: Verify role‑based access control and location lookups.

Security Testing: Confirm password hashing and authentication.

Boundary Testing: Check borrowing limits per membership type.

Regression Testing: Ensure updates do not break existing features.

#  Database Schema
Database: auca_library_db  
Key tables:

users (with hashed passwords, roles, membership type)

locations (province, district, sector, cell, village)

books, shelves, rooms

borrow_records (tracks borrow date, return date, fines)

#  Getting Started
Clone the repository.

Configure PostgreSQL and create the database auca_library_db.

Update JDBC connection settings  servlet config.

Run the project with Maven:

mvn clean install

mvn tomcat7:run

Access the application at: http://localhost:8080/auca-library
