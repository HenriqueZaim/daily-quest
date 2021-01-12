package com.dailyquest.api.config.security.auth;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

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

    @JsonProperty(access = Access.WRITE_ONLY)
    private String senha;

}