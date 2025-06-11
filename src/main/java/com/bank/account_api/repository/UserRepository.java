package com.bank.account_api.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.bank.account_api.models.UserModel;


public interface UserRepository extends JpaRepository<UserModel, Long>{
    
    @Query(value = "select * from tb_users where account_model_id_account = :idAccount", nativeQuery = true)
    Optional<UserModel> findUserIntoAccount(@Param("idAccount") Long idAccount);
}
