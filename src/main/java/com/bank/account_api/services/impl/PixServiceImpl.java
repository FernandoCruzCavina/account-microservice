package com.bank.account_api.services.impl;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bank.account_api.dtos.PixDto;
import com.bank.account_api.enums.ActionType;
import com.bank.account_api.enums.PixKeyType;
import com.bank.account_api.exceptions.AccountNotFoundException;
import com.bank.account_api.exceptions.InvalidCpfException;
import com.bank.account_api.exceptions.InvalidEmailException;
import com.bank.account_api.exceptions.InvalidNumberPhoneException;
import com.bank.account_api.exceptions.InvalidRandomKeyException;
import com.bank.account_api.exceptions.InvalidUnknowKeyException;
import com.bank.account_api.exceptions.PixKeyAlreadyExistsException;
import com.bank.account_api.exceptions.PixNotFoundException;
import com.bank.account_api.exceptions.PixTypeAlreadyExistsException;
import com.bank.account_api.models.AccountModel;
import com.bank.account_api.models.PixModel;
import com.bank.account_api.publishers.PixEventPublisher;
import com.bank.account_api.repository.AccountRepository;
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

    @Autowired
    AccountRepository accountRepository;

    public PixModel save(PixModel pixModel) {

        CpfValidator cpfValidator = new CpfValidator();

        switch (pixModel.getKeyType()) {
            case CPF:
                if (pixModel.getKey()==null || !cpfValidator.isValid(pixModel.getKey())) {
                    throw new InvalidCpfException();
                }
                break;
            case CELULAR:
                if (pixModel.getKey()==null || !pixModel.getKey().matches("^\\+?\\d{10,15}$")) {
                    throw new InvalidNumberPhoneException();
                }
                break;
            case EMAIL:
                if (pixModel.getKey()==null || !pixModel.getKey().matches("^[\\w.-]+@[a-zA-Z\\d.-]+\\.[a-zA-Z]{2,}$")) {
                    throw new InvalidEmailException();
                }
                break;
            case CHAVEALEATORIA:
                if(pixModel.getKey()!=null || !pixModel.getKey().matches("^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-4[0-9a-fA-F]{3}-[89abAB][0-9a-fA-F]{3}-[0-9a-fA-F]{12}$")){
                    throw new InvalidRandomKeyException();
                }
                pixModel.setKey(UUID.randomUUID().toString());
                break;

            default:
                throw new InvalidUnknowKeyException();
        }

        return pixRepository.save(pixModel);
    }

    public Optional<PixModel> findById(Long idPix) {
        return pixRepository.findById(idPix);
    }

    @Override
    public PixModel findPixIntoCourse(Long idAccount, Long idPix) {
        return pixRepository.findPixIntoCourse(idAccount, idPix)
                .orElseThrow(PixNotFoundException::new);
    }

    @Override
    public List<PixModel> findAllByAccount(Long idAccount) {
        return pixRepository.findAllPixsIntoAccount(idAccount);
    }

    @Transactional
    @Override
    public PixModel savePix(long idAccount, PixDto pixDto) {

        AccountModel accountModel = accountRepository.findById(idAccount)
                .orElseThrow(AccountNotFoundException::new);

        var pixModel = new PixModel();

        BeanUtils.copyProperties(pixDto, pixModel);

        pixModel.setCreatedAt(new Date().getTime());
        pixModel.setLastUpdatedAt(new Date().getTime());

        pixModel.setAccountModel(accountModel);

        findByAccountModel_IdAccountAndKeyType(idAccount, pixModel.getKeyType())
                .ifPresent((account)->{throw new PixTypeAlreadyExistsException();});

        findByKey(pixModel.getKey())
                .ifPresent((account)->{throw new PixKeyAlreadyExistsException();});

        pixModel = save(pixModel);

        pixEventPublisher.publishPixEvent(pixModel.convertToPixEventDto(), ActionType.CREATE);
        return pixModel;

    }

    @Transactional
    @Override
    public void deletePix(Long idAccount, Long idPix) {
        var pixModel = pixRepository.findPixIntoCourse(idAccount, idPix)
                .orElseThrow(PixNotFoundException::new);

        pixRepository.delete(pixModel);

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

    @Override
    public PixModel updatePix(Long idAccount, Long idPix, PixDto pixDto) {
        PixModel existingPix = pixRepository.findPixIntoCourse(idAccount, idPix)
                .orElseThrow(PixNotFoundException::new);

        AccountModel accountModel = accountRepository.findById(idAccount)
                .orElseThrow(AccountNotFoundException::new);

        findByAccountModel_IdAccountAndKeyType(idAccount, pixDto.getKeyType())
                .filter(pix -> !pix.getIdPix().equals(idPix))
                .ifPresent(pix -> { throw new PixTypeAlreadyExistsException(); });

        findByKey(pixDto.getKey())
                .filter(pix -> !pix.getIdPix().equals(idPix))
                .ifPresent(pix -> { throw new PixKeyAlreadyExistsException(); });

        BeanUtils.copyProperties(pixDto, existingPix, "id", "createdAt", "accountModel");
        existingPix.setLastUpdatedAt(new Date().getTime());
        existingPix.setAccountModel(accountModel);

        PixModel updatedPix = save(existingPix);
        pixEventPublisher.publishPixEvent(updatedPix.convertToPixEventDto(), ActionType.UPDATE);

        return updatedPix;
    }
}
