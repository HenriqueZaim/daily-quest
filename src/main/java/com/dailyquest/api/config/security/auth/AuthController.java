package com.dailyquest.api.config.security.auth;

import javax.servlet.http.HttpServletResponse;

import com.dailyquest.api.config.security.auth.JWT.JWTUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/auth")
public class AuthController {
    
    @Autowired
    private JWTUtils jwtUtils;

    // @Autowired
    // private AuthService authService;

    @Autowired
    private LoginService loginService;

    @RequestMapping(value="/refresh_token", method=RequestMethod.POST)
    public ResponseEntity<Void> refreshToken(HttpServletResponse response) {
        UserDetailsImpl user = loginService.authenticated();
        String token = jwtUtils.generateToken(user.getUsername());
        response.addHeader("Authorization", "Bearer " + token);
        response.addHeader("access-control-expose-headers", "Authorization");
        return ResponseEntity.noContent().build();
    }

    // @RequestMapping(value="/forgot", method=RequestMethod.POST)
    // public ResponseEntity<Void> forgot(@Valid @RequestBody EmailDTO emailDTO) {
    //     authService.sendNewPassword(emailDTO.getEmail());
    //     return ResponseEntity.noContent().build();
    // }
}