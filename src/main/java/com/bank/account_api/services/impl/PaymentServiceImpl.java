package com.bank.account_api.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bank.account_api.models.PaymentModel;
import com.bank.account_api.repository.PaymentRepository;
import com.bank.account_api.services.PaymentService;

@Service
public class PaymentServiceImpl implements PaymentService {
    @Autowired
    PaymentRepository paymentRepository;

    @Override
    public PaymentModel save(PaymentModel paymentModel) {
        return paymentRepository.save(paymentModel);
    }

    @Override
    public List<PaymentModel> findBySenderAccount(Long senderAccount) {
        return paymentRepository.findBySenderAccount(senderAccount);
    }

}
