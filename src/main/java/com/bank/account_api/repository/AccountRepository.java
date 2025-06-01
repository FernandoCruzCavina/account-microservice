package com.bank.account_api.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.bank.account_api.models.AccountModel;

public interface AccountRepository extends JpaRepository<AccountModel, Long> {
    boolean existsByAccountNumber(String accountNumber);

    @Query("SELECT a FROM AccountModel a JOIN a.pixs p WHERE p.key = :pixKey")
    Optional<AccountModel> findByPixKey(@Param("pixKey") String pixKey);
}
