package com.bank.account_api.models;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "account")
public class AccountModel implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private long idAccount;

    @Column(name = "balance")
    private double balance;

    @Column(name = "date_opened")
    private long dateOpened;

    @Column(name = "account_type")
    private String accountType;

    @Column(name = "user_id")
    private long userId;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @OneToMany(mappedBy = "account", fetch = FetchType.LAZY)
    @Fetch(FetchMode.SUBSELECT)
    private Set<PixModel> pixs;

    public AccountModel(double balance, String accountType, long userId) {
        this.balance = balance;
        this.accountType = accountType;
        this.userId = userId;
    }

    public AccountModel(double balance, long dateOpened, String accountType, long userId) {
        this.balance = balance;
        this.dateOpened = dateOpened;
        this.accountType = accountType;
        this.userId = userId;
    }

}
