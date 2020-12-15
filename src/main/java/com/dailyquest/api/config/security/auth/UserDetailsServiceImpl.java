package com.dailyquest.api.config.security.auth;

import com.dailyquest.domain.models.Usuario;
import com.dailyquest.domain.services.UsuarioService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service 
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UsuarioService usuarioService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario = usuarioService.findByEmail(username);
        if(usuario == null)
            throw new UsernameNotFoundException(username);

            // Todo: alterar tipo de usuario
        return new UserDetailsImpl(
            usuario.getId(),
            usuario.getEmail(),
            usuario.getSenha(),
            usuario.getTipoUsuario());
    }
    
}