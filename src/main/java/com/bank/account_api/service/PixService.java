package com.bank.account_api.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bank.account_api.models.PixModel;
import com.bank.account_api.repository.PixRepository;

@Service
public class PixService {
    @Autowired
    PixRepository pixRepository;

    public void save(PixModel pixModel) {
        pixRepository.save(pixModel);
    }

    public boolean existsByKey(String key) {
        return pixRepository.existsByKey(key);
    }

    public List<PixModel> findAll() {
        return pixRepository.findAll();
    }

    public Optional<PixModel> findById(Long idPix) {
        return pixRepository.findById(idPix);
    }

    public void delete(PixModel pixModel) {
        pixRepository.delete(pixModel);

    }
}
