package com.example.erp.repo;
import com.example.erp.model.Models.*;import org.springframework.data.jpa.repository.*;import org.springframework.data.repository.query.Param;import java.util.*;
import jakarta.persistence.LockModeType;
public final class Repos{private Repos(){}
public interface UserRepo extends JpaRepository<User,Long>{Optional<User> findByUsername(String u);}
public interface InventoryRepo extends JpaRepository<Inventory,Long>{Optional<Inventory> findByItemAndLocationAndBatch(String i,String l,String b);@Lock(LockModeType.PESSIMISTIC_WRITE) @Query("select x from Inventory x where x.item=:item and x.location=:location and x.batch=:batch") Optional<Inventory> lock(@Param("item")String i,@Param("location")String l,@Param("batch")String b);}
public interface WorkRepo extends JpaRepository<WorkOrder,Long>{}
public interface TransferRepo extends JpaRepository<Transfer,Long>{}
public interface OrderRepo extends JpaRepository<CustomerOrder,Long>{}
}
