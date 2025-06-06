package com.bank.account_api.services;

import com.bank.account_api.models.PaymentModel;

public interface PaymentService {
    PaymentModel save(PaymentModel paymentModel);
}
