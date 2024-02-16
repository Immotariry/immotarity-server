package com.github.immotarity.domain.user.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
public enum Role {
    STAFF("STAFF"),
    RESEARCHER("RESEARCHER"),
    MANAGER("MANAGER"),
    EXECUTIVE("EXECUTIVE"),
    ADMIN("ADMIN");

    private final String authority;
}
