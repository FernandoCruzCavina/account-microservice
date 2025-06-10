package com.bank.account_api.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bank.account_api.enums.ActionType;
import com.bank.account_api.enums.PixKeyType;
import com.bank.account_api.models.PixModel;
import com.bank.account_api.publishers.PixEventPublisher;
import com.bank.account_api.repository.PixRepository;
import com.bank.account_api.services.PixService;
import com.bank.account_api.utils.CpfValidator;

import jakarta.transaction.Transactional;

@Service
public class PixServiceImpl implements PixService {
    @Autowired
    PixRepository pixRepository;

    @Autowired
    PixEventPublisher pixEventPublisher;

    public PixModel save(PixModel pixModel) {

        CpfValidator cpfValidator = new CpfValidator();
        switch (pixModel.getKeyType()) {
            case CPF:
                if (!cpfValidator.isValid(pixModel.getKey())) {
                    throw new IllegalArgumentException("CPF inválido");
                }
                break;
            case PHONE:
                if (!pixModel.getKey().matches("^\\+?\\d{10,15}$")) {
                    throw new IllegalArgumentException("Número de celular inválido, use DDD + Número");
                }
                break;
            case EMAIL:
                if (!pixModel.getKey().matches("^[\\w.-]+@[a-zA-Z\\d.-]+\\.[a-zA-Z]{2,}$")) {
                    throw new IllegalArgumentException("Email inválido");
                }
                break;
            case RANDOMKEY:
                break;

            default:
                throw new IllegalArgumentException("Chave pix inválida");

        }

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
    public PixModel savePix(PixModel pixModel, Long idAccount) {
        Optional<PixModel> pixModelOptional = findByAccountModel_IdAccountAndKeyType(idAccount, pixModel.getKeyType());

        Optional<PixModel> findKeyPixModelOptional = findByKey(pixModel.getKey());

        if (pixModelOptional.isPresent()) {
            throw new IllegalArgumentException("O Tipo do pix já existe");
        }

        if (findKeyPixModelOptional.isPresent()) {
            throw new IllegalArgumentException("Essa chave pix já existe");
        }

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

    @Override
    public Optional<PixModel> findByAccountModel_IdAccountAndKeyType(Long idAccount, PixKeyType keyType) {
        return pixRepository.findByAccountModel_IdAccountAndKeyType(idAccount, keyType);
    }

    @Override
    public Optional<PixModel> findByKey(String key) {
        return pixRepository.findByKey(key);
    }
}
