package com.example.erp.repo;
import com.example.erp.model.Models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
public interface UserRepo extends JpaRepository<User,Long>{ Optional<User> findByUsername(String u); }
