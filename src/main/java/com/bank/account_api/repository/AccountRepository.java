package com.bank.account_api.repository;

import java.util.List;
import java.util.Optional;

// import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.bank.account_api.models.AccountModel;
import com.bank.account_api.models.PixModel;

public interface AccountRepository extends JpaRepository<AccountModel, Long> {

    Optional<AccountModel> findByUserId(long userId);

    @Query(value = "select * from tb_pixs where account_id = :accountId", nativeQuery = true)
    List<PixModel> findAllPixIntoAccount(@Param("accountId") long accountId);

    // boolean existsByKey(String key);

}
