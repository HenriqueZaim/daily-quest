package com.dailyquest.domain.services;

import java.net.URI;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

import com.dailyquest.api.config.security.auth.LoginService;
import com.dailyquest.domain.models.Usuario;
import com.dailyquest.domain.repositories.UsuarioRepository;
import com.dailyquest.domain.services.exceptions.AuthorizationException;
import com.dailyquest.domain.services.exceptions.DataIntegrityException;
import com.dailyquest.domain.services.exceptions.DomainException;
import com.dailyquest.domain.services.exceptions.ObjectNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.awt.image.BufferedImage;

@Service
public class UsuarioService {

    @Value("${img.prefix.client.profile}")
    private String prefix;

    @Value("${img.profile.size}")
    private Integer size;
    
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private LoginService loginService;

    @Autowired
    private S3Service s3Service;

    @Autowired
    private BCryptPasswordEncoder bCrypt;

    @Autowired
    private ImageService imageService;

    public Usuario findById(Integer usuarioId){
        // Verifica se está logado
        loginService.authenticated();
        
        // Busca usuário pelo ID informado 
        Optional<Usuario> usuario = usuarioRepository.findById(usuarioId);
        return usuario.orElseThrow(() -> new ObjectNotFoundException("Usuário não encontrado"));
    }

    public Usuario findByEmail(String email){
        // Busca usuário pelo EMAIL informado 
        Optional<Usuario> usuario = usuarioRepository.findByEmail(email);
        return usuario.orElseThrow(() -> new ObjectNotFoundException("Usuário não encontrado"));
    }

    public void deleteById(Integer usuarioId){
        if(!loginService.isLoggedUser(usuarioId))
            throw new AuthorizationException("Você não tem permissão para acessar este método.");

        findById(usuarioId);
        try {
            usuarioRepository.deleteById(usuarioId);
        } catch (Exception e) {
            throw new DataIntegrityException("Não foi continuar com a operação de exclusão");
        }  
    }

    public List<Usuario> findAll(){
        if(!loginService.isSystemAdmin())
            throw new AuthorizationException("Você não tem permissão para acessar este método.");

        return usuarioRepository.findAll();
    }

    public Usuario save(Usuario usuario){
        checkEmailExistence(usuario.getEmail());
        usuario.setSenha(bCrypt.encode(usuario.getSenha()));
        usuario.setDataHoraCriacao(OffsetDateTime.now());
        return usuarioRepository.save(usuario);
    }

    public void update(Usuario usuario, Integer usuarioId){
        if(!loginService.isLoggedUser(usuarioId))
            throw new AuthorizationException("Você não tem permissão para acessar este método.");

        Usuario usuarioExistente = findById(usuarioId);
        if(usuarioExistente.getEmail() != usuario.getEmail())
            checkEmailExistence(usuario.getEmail());
        
        usuario.setId(usuarioId);
        usuario.setDataHoraAtualizacao(OffsetDateTime.now());
        usuario.setDataHoraCriacao(usuarioExistente.getDataHoraCriacao());
        usuarioRepository.save(usuario);
    }

    public void updatePassword(String senha, Integer usuarioId){
        if(!loginService.isLoggedUser(usuarioId))
            throw new AuthorizationException("Você não tem permissão para acessar este método.");

        Usuario usuario = findById(usuarioId);
        usuario.setId(usuarioId);
        usuario.setSenha(bCrypt.encode(senha.trim()));
        usuario.setDataHoraAtualizacao(OffsetDateTime.now());
        usuarioRepository.save(usuario);
    }

    public void checkEmailExistence(String email){
        Optional<Usuario> user = usuarioRepository.findByEmail(email);
        if(user.isPresent())
            throw new DomainException("O e-mail inserido já está registrado no sistema");
    }

    public URI uploadProfilePicture(MultipartFile multipartFile, Integer usuarioId){
        if(!loginService.isLoggedUser(usuarioId))
            throw new AuthorizationException("Você não tem permissão para acessar este método.");

        Usuario user = findById(usuarioId);

        BufferedImage jpgImage = imageService.getJpgImageFromFile(multipartFile);
        jpgImage = imageService.cropSquare(jpgImage);
        jpgImage = imageService.resize(jpgImage, size);
        
        String fileName = prefix + user.getId() + ".jpg";

        return s3Service.uploadFile(imageService.getInputStream(jpgImage, "jpg"), fileName, "image");
    }

}