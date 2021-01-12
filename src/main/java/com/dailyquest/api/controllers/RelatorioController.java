package com.dailyquest.api.controllers;

import java.util.List;
import java.util.stream.Collectors;

import com.dailyquest.api.config.security.auth.LoginService;
import com.dailyquest.api.models.relatorio.RelatorioDTO;
import com.dailyquest.api.models.relatorio.RelatorioSimpleDTO;
import com.dailyquest.domain.services.RelatorioService;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/relatorios")
public class RelatorioController {

    @Autowired
    private RelatorioService relatorioService;

    @Autowired
    private LoginService loginService;

    @Autowired
    private ModelMapper modelMapper;
    
    @GetMapping
    @ApiOperation(value = "Busca por todos os relatórios do usuário autenticado.",notes = "Precisa estar autenticado. Apenas o próprio usuário poderá consultar seus relatórios.",response = RelatorioSimpleDTO.class,nickname = "selected-user-reports")
    public ResponseEntity<List<RelatorioSimpleDTO>> findReportsByUser(){
        List<RelatorioSimpleDTO> relatorios = relatorioService.findAllByUser(loginService.userAuthenticated())
            .stream()
                .map(relatorio -> modelMapper.map(relatorio, RelatorioSimpleDTO.class))
                .collect(Collectors.toList());
        return ResponseEntity.ok().body(relatorios);
    }

    @GetMapping("/{relatorioId}")
    @ApiOperation(value = "Busca pelo relatório completo do usuário autenticado.",notes = "Precisa estar autenticado. Apenas o próprio usuário poderá consultar seu relatório.",response = RelatorioDTO.class,nickname = "selected-user-report")
    public ResponseEntity<RelatorioDTO> findSelectedReportFromUser(@PathVariable Integer relatorioId){
        RelatorioDTO relatorio = modelMapper.map(relatorioService.findByIdAndUsuario(relatorioId, loginService.userAuthenticated()), RelatorioDTO.class);
        return ResponseEntity.ok().body(relatorio);
    }
}
