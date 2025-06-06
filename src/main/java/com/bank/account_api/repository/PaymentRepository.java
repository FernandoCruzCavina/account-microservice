package com.bank.account_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bank.account_api.models.PaymentModel;

public interface PaymentRepository extends JpaRepository<PaymentModel, Long> {

}
