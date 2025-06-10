package com.bank.account_api.models;

import java.io.Serializable;
import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonInclude;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@Entity
@Table(name = "TB_PAYMENTS")
public class PaymentModel implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    private Long idPayment;

    @Column(length = 150)
    private String paymentDescription;

    @Column(nullable = true)
    private BigDecimal amountPaid;

    @Column(nullable = true)
    private Long paymentRequestDate;

    @Column(nullable = true)
    private Long paymentCompletionDate;

    @Column(nullable = true)
    private Long senderAccount;

    @Column(nullable = true)
    private Long receiverAccount;
}
