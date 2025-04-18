package com.bank.account_api.service;

import org.springframework.data.jpa.repository.JpaRepository;
import com.bank.account_api.entity.Account;

public interface AccountRepository extends JpaRepository<Account, Long>{

}
