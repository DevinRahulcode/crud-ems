Features

Create Employee: Add a new employee with first name, last name, email, and salary.
Read Employees: Retrieve a list of all employees or a specific employee by ID.
Update Employee: Modify existing employee details.
Delete Employee: Remove an employee by ID.
Input Validation: Ensures valid data (valid email, non-empty fields).
Duplicate Email Check: Prevents multiple employees from having the same email.


Technologies Used

Java: 17
Spring Boot: 3.5.6
Spring Data JPA: For database operations
MySQL: Database for storing employee records
Hibernate Validator: For input validation
Maven: Build tool
JUnit & Mockito: For unit testing
Lombok: To reduce boilerplate code


Configure MySQL : 

spring.datasource.url=jdbc:mysql://localhost:3306/employee_ms
spring.datasource.username=<your-username>
spring.datasource.password=<your-password>
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.jpa.hibernate.ddl-auto=update


API Calls: 

To Get All Employees : http://localhost:8080/api/all-employee
To Get Employee by Id : http://localhost:8080/api/employee/{id}
Create Employee : http://localhost:8080/api/create-employee
update Employee : http://localhost:8080/api/update-employee/{id}
delete Employee : http://localhost:8080/api/delete-employee/{id}


Run Specific Test Class:

mvn test -Dtest=EmployeeControllerTest
mvn test -Dtest=EmployeeServiceTest
mvn test -Dtest=EmployeeMapperTest
mvn test -Dtest=EmployeeRequestDTOTest