package com.bank.account_api.models;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
    private long id;

    private double balance;

    @Column(name = "date_opened")
    private long dateOpened;

    @Column(name = "account_type")
    private String accountType;

    @Column(name = "user_id")
    private long userId;

    @Column(nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime creationDate;

    @Column(nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime lastUpdateDate;

    @OneToMany(mappedBy = "account")
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
