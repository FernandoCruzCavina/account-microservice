package com.bank.account_api.services;

import com.bank.account_api.models.UserModel;

public interface UserService {
    UserModel save(UserModel accountModel);
    
    long findByAccountId(Long idAccount);
}
