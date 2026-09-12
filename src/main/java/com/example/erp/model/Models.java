package com.example.erp.model;
import jakarta.persistence.*;import java.time.*;
public final class Models{private Models(){}
@Entity @Table(name="users") public static class User{@Id @GeneratedValue(strategy=GenerationType.IDENTITY) public Long id;@Column(unique=true) public String username;public String password;public String role;public String location;}
@Entity @Table(name="inventory",uniqueConstraints=@UniqueConstraint(columnNames={"item","location","batch"})) public static class Inventory{@Id @GeneratedValue(strategy=GenerationType.IDENTITY) public Long id;public String item;public String category;public String location;public String batch;public int physicalQuantity;public int reservedQuantity;@Version public long version;public int available(){return physicalQuantity-reservedQuantity;}}
@Entity public static class WorkOrder{@Id @GeneratedValue(strategy=GenerationType.IDENTITY) public Long id;public String location,item,assignedUser,status;public int requiredQuantity;public WorkOrder(){} }
@Entity public static class Transfer{@Id @GeneratedValue(strategy=GenerationType.IDENTITY) public Long id;public String sourceLocation,destinationLocation,item,status;public int quantity;public boolean received;}
@Entity public static class CustomerOrder{@Id @GeneratedValue(strategy=GenerationType.IDENTITY) public Long id;public String item,customer,status;public int quantity;public String location;public Instant createdAt=Instant.now();}
}
