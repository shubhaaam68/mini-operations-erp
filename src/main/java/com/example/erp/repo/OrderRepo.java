package com.example.erp.repo;
import com.example.erp.model.Models.CustomerOrder;
import org.springframework.data.jpa.repository.JpaRepository;
public interface OrderRepo extends JpaRepository<CustomerOrder,Long>{}
