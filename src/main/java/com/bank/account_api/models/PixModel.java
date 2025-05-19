package com.bank.account_api.models;

import java.io.Serializable;

import com.bank.account_api.enums.PixKeyType;
import com.fasterxml.jackson.annotation.JsonInclude;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@Entity
@Table(name = "TB_PIXS")
public class PixModel implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private Long idPIX;

    @Column(nullable = false)
    private PixKeyType pixKeyType;

    @Column(name = "pix_key", nullable = false, unique = true)
    private String key;

}
