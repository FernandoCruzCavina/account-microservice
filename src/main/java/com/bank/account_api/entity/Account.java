package com.bank.account_api.entity;

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
    private long date_opened;
    private String account_type;
    private long user_id; 

    public Account(double balance, String account_type, long user_id) {
        this.balance = balance;
        this.account_type = account_type;
        this.user_id = user_id;
    }

    public Account(double balance, long date_opened, String account_type, long user_id) {
        this.balance = balance;
        this.date_opened = date_opened;
        this.account_type = account_type;
        this.user_id = user_id;
    }
}
