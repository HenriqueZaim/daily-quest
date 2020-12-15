package com.dailyquest.api.config.security.auth;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoginDTO implements Serializable{

    private static final long serialVersionUID = 1L;
    private String email;
    private String senha;
}