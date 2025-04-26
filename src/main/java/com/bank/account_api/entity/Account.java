package com.bank.account_api.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
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
public class Account {
    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private long id;
    private double balance;
    @Column(name = "date_opened")
    private long dateOpened;
    @Column(name = "account_type")
    private String accountType;
    @Column(name = "user_id")
    private long userId; 

    public Account(double balance, String accountType, long userId) {
        this.balance = balance;
        this.accountType = accountType;
        this.userId = userId;
    }

    public Account(double balance, long dateOpened, String accountType, long userId) {
        this.balance = balance;
        this.dateOpened = dateOpened;
        this.accountType = accountType;
        this.userId = userId;
    }
}
