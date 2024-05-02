package com.projcet.tstorycopyproject.common.security;

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
