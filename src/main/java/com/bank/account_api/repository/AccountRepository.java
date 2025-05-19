package com.bank.account_api.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bank.account_api.models.AccountModel;

public interface AccountRepository extends JpaRepository<AccountModel, Long> {

    Optional<AccountModel> findByUserId(long userId);

}
