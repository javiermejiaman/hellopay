package com.jm.hellopay.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum JwtClaim {

    TOKEN_TYPE("tokenType");

    private final String value;
}
