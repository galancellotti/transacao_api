package com.basicTransaction_api.repository;

import com.basicTransaction_api.demain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
