package com.bank.account_api.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.bank.account_api.models.PixModel;

public interface PixRepository extends JpaRepository<PixModel, Long> {
    // boolean existsByKey(String key);

    @Query(value = "select * from tb_pixs where account_model_id_account = :idAccount", nativeQuery = true)
    List<PixModel> findAllPixsIntoAccount(@Param("idAccount") Long idAccount);

    // @Query(value = "select * from tb_pixs where account_model_id_account =:
    // idAccount and pix_id =: idPix", nativeQuery = true)
    // Optional<PixModel> findPixIntoCourse(@Param("idAccount") Long idAccount,
    // @Param("idAccount") Long idPix);
    @Query(value = "SELECT * FROM tb_pixs WHERE account_model_id_account = ?1 AND id_pix = ?2", nativeQuery = true)
    Optional<PixModel> findPixIntoCourse(Long idAccount, Long idPix);
}
