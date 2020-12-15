package com.dailyquest.api.config.security.auth;

import com.dailyquest.domain.models.Usuario;
import com.dailyquest.domain.models.enums.TipoUsuario;
import com.dailyquest.domain.services.UsuarioService;
import com.dailyquest.domain.services.exceptions.AuthorizationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class LoginService {

    @Autowired
    private UsuarioService usuarioService;
    
    public UserDetailsImpl authenticated(){
        try {
            return (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        } catch (Exception e) {
            throw new AuthorizationException("Faça login para continuar");
        }
    }

    public Usuario userAuthenticated(){
        return usuarioService.findById(authenticated().getId());
    }

    public boolean isSystemAdmin(){
        return userAuthenticated().getTipoUsuario().contains(TipoUsuario.ROLE_ADMIN);
    }

    public boolean isLoggedUser(Integer usuarioId){
        return authenticated().getId() == usuarioId;
    }
}