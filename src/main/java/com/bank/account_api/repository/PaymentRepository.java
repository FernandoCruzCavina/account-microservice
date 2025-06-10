package com.bank.account_api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bank.account_api.models.PaymentModel;

public interface PaymentRepository extends JpaRepository<PaymentModel, Long> {

    List<PaymentModel> findBySenderAccountOrReceiverAccount(Long senderAccount, Long receiveAccount);

}
