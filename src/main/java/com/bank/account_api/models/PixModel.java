package com.bank.account_api.models;

import java.io.Serializable;

import org.springframework.beans.BeanUtils;

import com.bank.account_api.dtos.PixEventDto;
import com.bank.account_api.enums.PixKeyType;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
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
    private Long idPix;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private PixKeyType pixKeyType;

    @Column(name = "pix_key", nullable = false, unique = true)
    private String key;

    @Column(nullable = false)
    private Long createdAt;

    @Column(nullable = false)
    private Long lastUpdatedAt;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @ManyToOne(optional = false)
    private AccountModel accountModel;

    public PixEventDto convertToPixEventDto() {
        var pixEventDto = new PixEventDto();

        BeanUtils.copyProperties(this, pixEventDto);
        pixEventDto.setIdAccount(this.getAccountModel().getIdAccount());

        return pixEventDto;
    }
}
