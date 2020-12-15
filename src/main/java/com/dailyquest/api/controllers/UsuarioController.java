package com.dailyquest.api.controllers;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import com.dailyquest.api.models.relatorio.RelatorioDTO;
import com.dailyquest.api.models.relatorio.RelatorioSimpleDTO;
import com.dailyquest.api.models.usuario.UsuarioSimpleDTO;
import com.dailyquest.api.models.usuario.UsuarioDTO;
import com.dailyquest.domain.models.Usuario;
import com.dailyquest.domain.services.RelatorioService;
import com.dailyquest.domain.services.UsuarioService;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
    private RelatorioService relatorioService;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping
    @ApiOperation( value = "Busca por todos os usuários registrados.", notes = "Precisa ser um administrador de sistema para ter acesso a todos os usuários registrados.", response = UsuarioSimpleDTO.class, nickname = "all-users")
    public ResponseEntity<List<UsuarioSimpleDTO>> findAll(){
        List<UsuarioSimpleDTO> registeredUsers = usuarioService.findAll().stream()
            .map(user -> modelMapper.map(user, UsuarioSimpleDTO.class)).collect(Collectors.toList());
        
        return ResponseEntity.ok().body(registeredUsers);
    }

    @GetMapping("/{usuarioId}")
    @ApiOperation( value = "Busca pelo usuário selecionado.", notes = "Precisa estar autenticado.", response = UsuarioSimpleDTO.class, nickname = "selected-user")
    public ResponseEntity<UsuarioSimpleDTO> findById(@PathVariable Integer usuarioId){
        UsuarioSimpleDTO usuarioModel = modelMapper.map(usuarioService.findById(usuarioId), UsuarioSimpleDTO.class);
        return ResponseEntity.ok().body(usuarioModel);
    }

    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    @ApiOperation(value = "Registra o usuário no sistema.",response = UsuarioSimpleDTO.class,nickname = "register-user")
    public UsuarioSimpleDTO save(@Valid @RequestBody UsuarioDTO usuarioDTO){
        Usuario usuario = modelMapper.map(usuarioDTO, Usuario.class);
        return modelMapper.map(usuarioService.save(usuario), UsuarioSimpleDTO.class);
    }

    @PutMapping("/{usuarioId}")
    @ApiOperation(value = "Atualizar o usuário no sistema.", notes = "Precisa estar autenticado.", response = Void.class, nickname = "update-user")
    public ResponseEntity<Void> update(@Valid @RequestBody UsuarioSimpleDTO usuarioSimpleDTO, @PathVariable Integer usuarioId){
        Usuario usuario = modelMapper.map(usuarioSimpleDTO, Usuario.class);
        usuarioService.update(usuario, usuarioId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{usuarioId}")
    @ApiOperation(value = "Remove o usuário selecionado do sistema.",notes = "Precisa estar autenticado.",response = Void.class,nickname = "delete-user")
    public ResponseEntity<Void> deleteById(@PathVariable Integer usuarioId){
        usuarioService.deleteById(usuarioId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{usuarioId}/relatorios")
    @ApiOperation(value = "Busca por todos os relatórios do usuário selecionado.",notes = "Precisa estar autenticado. Apenas o próprio usuário poderá consultar seus próprios relatórios.",response = RelatorioSimpleDTO.class,nickname = "selected-user-reports")
    public ResponseEntity<List<RelatorioSimpleDTO>> findReportsByUser(@PathVariable Integer usuarioId){
        List<RelatorioSimpleDTO> relatorios = relatorioService.findReportsByUser(usuarioId)
            .stream()
                .map(relatorio -> modelMapper.map(relatorio, RelatorioSimpleDTO.class))
                .collect(Collectors.toList());
        return ResponseEntity.ok().body(relatorios);
    }

    @GetMapping("/{usuarioId}/relatorios/{relatorioId}")
    @ApiOperation(value = "Busca pelo relatório completo do usuário selecionado.",notes = "Precisa estar autenticado. Apenas o próprio usuário poderá consultar seu próprio relatório.",response = RelatorioDTO.class,nickname = "selected-user-report")
    public ResponseEntity<RelatorioDTO> findSelectedReportFromUser(@PathVariable Integer usuarioId, @PathVariable Integer relatorioId){
        RelatorioDTO relatorio = modelMapper.map(relatorioService.findByUser(usuarioId, relatorioId), RelatorioDTO.class);
        return ResponseEntity.ok().body(relatorio);
    }

    @PostMapping("/{usuarioId}/picture")
    public ResponseEntity<Void> uploadProfilePicture(@RequestParam MultipartFile file, @PathVariable Integer usuarioId){
        URI uri = usuarioService.uploadProfilePicture(file, usuarioId);
        return ResponseEntity.created(uri).build();
    }

}