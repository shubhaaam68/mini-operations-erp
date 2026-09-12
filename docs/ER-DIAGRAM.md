# ER Diagram

```mermaid
erDiagram
  USER { bigint id PK string username UK string password string role string location }
  INVENTORY { bigint id PK string item string category string location string batch int physicalQuantity int reservedQuantity bigint version }
  WORK_ORDER { bigint id PK string location string item int requiredQuantity string assignedUser string status }
  TRANSFER { bigint id PK string sourceLocation string destinationLocation string item int quantity string status boolean received }
  CUSTOMER_ORDER { bigint id PK string item string location string customer int quantity string status datetime createdAt }
```

Inventory is uniquely constrained by item + location + batch. Transfers represent Requested -> Dispatched -> Received. Reservation and dispatch are transactional and lock the affected inventory row.
