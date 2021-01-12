package com.dailyquest.api.controllers;

import java.net.URI;

import javax.validation.Valid;

import com.dailyquest.api.models.usuario.UsuarioSimpleDTO;
import com.dailyquest.api.models.usuario.UsuarioDTO;
import com.dailyquest.domain.models.Usuario;
import com.dailyquest.domain.services.UsuarioService;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import io.swagger.annotations.ApiOperation;
@RestController
@RequestMapping("/usuarios")
public class UsuarioController {
    
    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping
    @ApiOperation( value = "Busca pelo usuário autenticado no momento.", notes = "Precisa estar autenticado.", response = UsuarioSimpleDTO.class, nickname = "get-user")
    public ResponseEntity<UsuarioSimpleDTO> authenticatedUser(){
        UsuarioSimpleDTO authenticatedUser = modelMapper.map(usuarioService.authenticatedUser(), UsuarioSimpleDTO.class);
        return ResponseEntity.ok().body(authenticatedUser);
    }

    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    @ApiOperation(value = "Registra o usuário no sistema.",response = UsuarioSimpleDTO.class,nickname = "create-user")
    public UsuarioSimpleDTO save(@Valid @RequestBody UsuarioDTO usuarioDTO){
        Usuario usuario = modelMapper.map(usuarioDTO, Usuario.class);
        return modelMapper.map(usuarioService.save(usuario), UsuarioSimpleDTO.class);
    }

    @PutMapping
    @ApiOperation(value = "Atualizar o usuário no sistema.", notes = "Precisa estar autenticado.", response = Void.class, nickname = "update-user")
    public ResponseEntity<Void> update(@Valid @RequestBody UsuarioSimpleDTO usuarioSimpleDTO){
        Usuario usuario = modelMapper.map(usuarioSimpleDTO, Usuario.class);
        usuarioService.update(usuario);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping
    @ApiOperation(value = "Remove o usuário selecionado do sistema.",notes = "Precisa estar autenticado.",response = Void.class,nickname = "delete-user")
    public ResponseEntity<Void> deleteById(){
        usuarioService.deleteUser();
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/picture")
    public ResponseEntity<Void> uploadProfilePicture(@RequestParam MultipartFile file){
        URI uri = usuarioService.uploadProfilePicture(file);
        return ResponseEntity.created(uri).build();
    }

}