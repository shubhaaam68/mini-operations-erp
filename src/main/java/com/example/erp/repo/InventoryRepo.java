package com.example.erp.repo;
import com.example.erp.model.Models.Inventory;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import jakarta.persistence.LockModeType;
import java.util.Optional;
public interface InventoryRepo extends JpaRepository<Inventory,Long>{
 Optional<Inventory> findByItemAndLocationAndBatch(String i,String l,String b);
 @Lock(LockModeType.PESSIMISTIC_WRITE)
 @Query("select x from Inventory x where x.item=:item and x.location=:location and x.batch=:batch")
 Optional<Inventory> lock(@Param("item")String i,@Param("location")String l,@Param("batch")String b);
}
