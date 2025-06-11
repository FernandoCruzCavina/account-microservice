package com.bank.account_api.services;

import java.util.List;

import com.bank.account_api.models.PaymentModel;

public interface PaymentService {
    PaymentModel save(PaymentModel paymentModel);

    List<PaymentModel> findBySenderAccountOrReceiverAccount(Long senderAccount, Long receiveAccount);
}
