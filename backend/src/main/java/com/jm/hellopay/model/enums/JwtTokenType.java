package com.jm.hellopay.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum JwtTokenType {

    ACCESS("access"),
    REFRESH("refresh");

    private final String value;
}
