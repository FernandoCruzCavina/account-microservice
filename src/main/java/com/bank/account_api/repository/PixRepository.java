package com.bank.account_api.repository;

// import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

// import com.bank.account_api.models.AccountModel;
import com.bank.account_api.models.PixModel;

public interface PixRepository extends JpaRepository<PixModel, Long> {

    // @EntityGraph(attributePaths = { "account" })
    // AccountModel findByAccount(String name);

}
