package com.bank.account_api.dtos;

import java.math.BigDecimal;

import org.springframework.beans.BeanUtils;

import com.bank.account_api.models.PaymentModel;

import lombok.Data;

@Data
public class PaymentEventDto {
    private Long idPayment;
    private String paymentDescrption;
    private BigDecimal amountPaid;
    private Long paymentRequestDate;
    private Long paymentCompletionDate;
    private Long senderAccount;
    private Long receiverAccount;

    public PaymentModel convertToPaymentModel() {
        var paymentModel = new PaymentModel();

        BeanUtils.copyProperties(this, paymentModel);
        return paymentModel;
    }
}
