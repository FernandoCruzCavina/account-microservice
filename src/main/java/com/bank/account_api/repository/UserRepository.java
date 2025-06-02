package com.bank.account_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bank.account_api.models.UserModel;

public interface UserRepository extends JpaRepository<UserModel, Long>{
    
}
