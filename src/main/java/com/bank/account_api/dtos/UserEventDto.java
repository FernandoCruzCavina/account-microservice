package com.bank.account_api.dtos;

import org.springframework.beans.BeanUtils;

import com.bank.account_api.models.UserModel;

import lombok.Data;

@Data
public class UserEventDto {
    private Long idUser;
    private String creationType;

    public UserModel convertToUserModel() {
        var userModel = new UserModel();

        BeanUtils.copyProperties(this, userModel);
        return userModel;
    }
}
