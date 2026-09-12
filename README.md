# Mini Operations ERP — Technical Case Study 2

A compact full-stack Operations ERP implementing Authentication/Roles, Inventory, Work Orders, Internal Transfers, Customer Orders/Reservations, backend validation, transactional inventory updates, tests and Swagger.

## Stack
Java 17, Spring Boot 3.5, Spring Security, Spring Data JPA/Hibernate, H2 by default, PostgreSQL supported, HTML/JavaScript frontend, JUnit/Mockito, OpenAPI/Swagger.

## Run
Requires Java 17+ and Maven 3.9+.

`mvn spring-boot:run`

Open `http://localhost:8080`.

## Demo credentials
- admin / password — ADMIN
- ops / password — OPERATIONS_USER
- sales / password — SALES_USER

## Database
Default is file-backed H2 (`./data/erp`) so the project runs immediately. PostgreSQL can be selected using `DATABASE_URL`, `DATABASE_DRIVER`, `DATABASE_USERNAME`, and `DATABASE_PASSWORD` environment variables.

## API
Swagger: `/swagger-ui.html`

Main endpoints: `/api/inventory`, `/api/work-orders`, `/api/transfers`, `/api/transfers/{id}/dispatch`, `/api/transfers/{id}/receive`, `/api/orders/reserve`.

## Business rules
Available = physical - reserved. Reservation and transfer dispatch use pessimistic row locking inside database transactions. Dispatch decreases source physical stock; receipt increases destination physical stock; a transfer cannot be received twice.

## Tests
`mvn test`

Includes checks for reservation over-commit, transfer over-commit and duplicate receipt. The application also uses method-level role authorization for restricted operations.

## ER model
Users, Inventory, WorkOrder, Transfer and CustomerOrder are relational entities. Inventory is uniquely constrained by item + location + batch. Transfers reference source/destination locations by value and preserve a clear Requested → Dispatched → Received lifecycle.

## Demo flow
Login → role-based dashboard → Inventory → Work Order → Transfer → Dispatch/Receive in Swagger → Customer Reservation.

## Note
AI tools are permitted by the assessment brief. Candidates should understand the submitted code and be ready for live changes/verification.

<!-- deployment marker: role-based responsive dashboard is on main -->
