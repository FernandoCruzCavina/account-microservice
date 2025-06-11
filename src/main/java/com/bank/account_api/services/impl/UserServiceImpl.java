package com.bank.account_api.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bank.account_api.models.AccountModel;
import com.bank.account_api.models.UserModel;
import com.bank.account_api.repository.UserRepository;
import com.bank.account_api.services.UserService;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Override
    public UserModel save(UserModel userModel) {
        return userRepository.save(userModel);
    }

    // @Override
    // public long findByAccountModel(AccountModel accountModel) {
    //     var userModelOpt = userRepository.findByAccountModel(accountModel);
    //     if (userModelOpt.isEmpty()) {
    //         throw new RuntimeException("User not found for the given account id");
    //     }
    //     return userModelOpt.get(1).getUserId();
    // }

    @Override
    public long findByAccountId(Long idAccount) {
        var userModelOpt = userRepository.findUserIntoAccount(idAccount);
        if (userModelOpt.isEmpty()) {
            throw new RuntimeException("User not found for the given account id");
        }
        return userModelOpt.get().getUserId();
    }

}
