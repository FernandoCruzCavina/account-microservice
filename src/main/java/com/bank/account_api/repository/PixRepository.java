package com.bank.account_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bank.account_api.models.PixModel;

public interface PixRepository extends JpaRepository<PixModel, Long> {
    boolean existsByKey(String key);
}
