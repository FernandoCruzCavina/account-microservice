package com.bank.account_api.services;

import java.util.List;
import java.util.Optional;

import com.bank.account_api.enums.PixKeyType;
import com.bank.account_api.models.PixModel;

public interface PixService {
    public PixModel save(PixModel pixModel);

    public Optional<PixModel> findById(Long idPix);

    public void delete(PixModel pixModel);

    public Optional<PixModel> findPixIntoCourse(Long idAccount, Long idPix);

    public List<PixModel> findAllByAccount(Long idAccount);

    PixModel savePix(PixModel pixModel, Long idAccount);

    void deletePix(PixModel pixModel);

    PixModel updateAccount(PixModel pixModel);

    Optional<PixModel> findByAccountModel_IdAccountAndKeyType(Long idAccount, PixKeyType keyType);

    Optional<PixModel> findByKey(String key);

}
