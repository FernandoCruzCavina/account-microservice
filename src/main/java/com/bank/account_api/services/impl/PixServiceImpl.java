package com.bank.account_api.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bank.account_api.enums.ActionType;
import com.bank.account_api.models.PixModel;
import com.bank.account_api.publishers.PixEventPublisher;
import com.bank.account_api.repository.PixRepository;
import com.bank.account_api.services.PixService;

import jakarta.transaction.Transactional;

@Service
public class PixServiceImpl implements PixService {
    @Autowired
    PixRepository pixRepository;

    @Autowired
    PixEventPublisher pixEventPublisher;

    public PixModel save(PixModel pixModel) {
        return pixRepository.save(pixModel);
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

    @Transactional
    @Override
    public PixModel savePix(PixModel pixModel) {
        pixModel = save(pixModel);

        pixEventPublisher.publishPixEvent(pixModel.convertToPixEventDto(), ActionType.CREATE);
        return pixModel;
    }

    @Transactional
    @Override
    public void deletePix(PixModel pixModel) {
        delete(pixModel);

        pixEventPublisher.publishPixEvent(pixModel.convertToPixEventDto(), ActionType.DELETE);
    }

    @Transactional
    @Override
    public PixModel updateAccount(PixModel pixModel) {
        pixModel = save(pixModel);

        pixEventPublisher.publishPixEvent(pixModel.convertToPixEventDto(), ActionType.UPDATE);
        return pixModel;
    }
}
