package com.projcet.tstorycopyproject.global.security;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MyPrincipal {
    private long userPk;
    private String role;
}
