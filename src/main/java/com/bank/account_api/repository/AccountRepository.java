package com.bank.account_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.bank.account_api.entity.Account;

public interface AccountRepository extends JpaRepository<Account, Long>{

}
