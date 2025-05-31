package com.bank.account_api.services;

import java.util.List;
import java.util.Optional;

import com.bank.account_api.models.PixModel;

public interface PixService {
    public void save(PixModel pixModel);

    public Optional<PixModel> findById(Long idPix);

    public void delete(PixModel pixModel);

    public Optional<PixModel> findPixIntoCourse(Long idAccount, Long idPix);

    public List<PixModel> findAllByAccount(Long idAccount);

}
