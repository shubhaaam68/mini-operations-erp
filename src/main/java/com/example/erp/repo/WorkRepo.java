package com.example.erp.repo;
import com.example.erp.model.Models.WorkOrder;
import org.springframework.data.jpa.repository.JpaRepository;
public interface WorkRepo extends JpaRepository<WorkOrder,Long>{}
