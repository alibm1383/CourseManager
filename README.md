# 📘 Course Management System

A simple Java console-based application for managing users, courses, assignments, and enrollments in an educational system. The project uses JDBC with SQL Server and follows a basic layered structure.

---

# 🧩 Features

* User management (Admin / Teacher / Student)
* Course creation and management
* Assignment handling per course
* Student enrollment in courses
* Grade (point) management
* Basic authentication system with hashed passwords
* Role-based access for Admin and Teacher

---

# 🏗 Project Structure

* Models → Entity classes (User, Course, Assignment, Role)
* Repositories → Database operations and queries
* Context → SQL Server connection handler
* Utilities → Password hashing utility
* Main → Console-based application flow

---

# 🗄 Database

The system is built on SQL Server and works with the following tables:

* Users
* Roles
* Courses
* Assignments
* Enrollments

Each repository directly communicates with the database using JDBC queries.

---

# 🔐 Authentication

Passwords are hashed using SHA-256 before being stored in the database.

```java id="auth1"
PasswordUtils.hashPassword(password);
```

Login validation is done by comparing hashed values instead of plain text.

---

# ⚙️ How to Run

1. Clone the project
2. Add SQL Server JDBC driver to the project
3. Create database `EducationDB`
4. Run required tables script
5. Update database credentials in `Context.java`
6. Run `Main.java`

---

# 👨‍🏫 Roles & Access

## 🧑‍💼 Admin

* Create users
* Create courses
* Remove users
* Remove courses

## 👨‍🏫 Teacher

* Manage assigned courses
* Add and remove students
* Manage assignments
* Assign grades
* View enrolled students

---

# 📌 Notes

* Console-based application (no GUI)
* Uses plain JDBC without external frameworks
* Focus on OOP and layered architecture
