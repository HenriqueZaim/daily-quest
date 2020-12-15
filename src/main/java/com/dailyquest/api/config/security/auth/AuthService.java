package com.dailyquest.api.config.security.auth;

import java.util.Random;

import com.dailyquest.domain.models.Usuario;
import com.dailyquest.domain.services.UsuarioService;
import com.dailyquest.domain.services.exceptions.ObjectNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    
    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private BCryptPasswordEncoder b;

    private Random rand = new Random();

    public void sendNewPassword(String email){
        Usuario usuario = usuarioService.findByEmail(email);
        if(usuario == null)
            throw new ObjectNotFoundException("Email n encontrado");
    
        String newPass = newPassword();
        usuario.setSenha(b.encode(newPass));
        usuarioService.save(usuario);

        // emailService.sendNewPasswordEmail(cliente, newPass);
    }

    private String newPassword(){
        char[] vet = new char[10];
        for(int i = 0 ; i<10 ; i++){
            vet[i] = randomChar();
        }
        return new String(vet);
    }

    private char randomChar(){
        int opt = rand.nextInt(3);
        if(opt == 0){
            return (char) (rand.nextInt(10) + 48);
        }else if(opt == 1){
            return (char) (rand.nextInt(26) + 65);
        }else{
            return (char) (rand.nextInt(26) + 97);
        }
    }
}