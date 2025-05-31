package com.bank.account_api.models;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Set;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.beans.BeanUtils;

import com.bank.account_api.dtos.AccountEventDto;
import com.bank.account_api.enums.AccountType;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@Entity
@Table(name = "TB_ACCOUNTS")
public class AccountModel implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idAccount;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private AccountType accountType;

    @Column(nullable = false, unique = true)
    private String accountNumber;

    @Column(nullable = false)
    private BigDecimal balance;

    @Column(nullable = false)
    private Long createdAt;

    @Column(nullable = false)
    private Long lastUpdatedAt;

    @Column(nullable = true)
    private String imageUrl;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @OneToMany(mappedBy = "accountModel")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Set<PixModel> pixs;

    public AccountEventDto convertToAccountEventDto() {
        var accountEventDto = new AccountEventDto();

        BeanUtils.copyProperties(this, accountEventDto);

        return accountEventDto;
    }
}
