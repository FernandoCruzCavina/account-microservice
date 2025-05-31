package com.bank.account_api.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bank.account_api.models.PixModel;
import com.bank.account_api.repository.PixRepository;
import com.bank.account_api.services.PixService;

@Service
public class PixServiceImpl implements PixService {
    @Autowired
    PixRepository pixRepository;

    public void save(PixModel pixModel) {
        pixRepository.save(pixModel);
    }

    public Optional<PixModel> findById(Long idPix) {
        return pixRepository.findById(idPix);
    }

    public void delete(PixModel pixModel) {
        pixRepository.delete(pixModel);

    }

    @Override
    public Optional<PixModel> findPixIntoCourse(Long idAccount, Long idPix) {
        return pixRepository.findPixIntoCourse(idAccount, idPix);
    }

    @Override
    public List<PixModel> findAllByAccount(Long idAccount) {
        return pixRepository.findAllPixsIntoAccount(idAccount);
    }
}
