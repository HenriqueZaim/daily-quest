package com.dailyquest.api.controllers;


import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.validation.Valid;

import com.dailyquest.api.models.grupo.GrupoDTO;
import com.dailyquest.api.models.grupo.GrupoSimpleDTO;
import com.dailyquest.api.models.participante.ParticipanteSimpleDTO;
import com.dailyquest.api.models.periodo.PeriodoDTO;
import com.dailyquest.api.models.periodo.PeriodoSimpleDTO;
import com.dailyquest.api.models.relatorio.RelatorioDTO;
import com.dailyquest.api.models.relatorio.RelatorioSimpleDTO;
import com.dailyquest.api.models.usuario.UsuarioSimpleDTO;
import com.dailyquest.domain.models.Grupo;
import com.dailyquest.domain.models.Participante;
import com.dailyquest.domain.models.Periodo;
import com.dailyquest.domain.models.Relatorio;
import com.dailyquest.domain.models.Usuario;
import com.dailyquest.domain.services.GrupoService;
import com.dailyquest.domain.services.ParticipanteService;
import com.dailyquest.domain.services.PeriodoService;
import com.dailyquest.domain.services.RelatorioService;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.ReflectionUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/grupos")
public class GrupoController {

    @Autowired
    private GrupoService grupoService;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ParticipanteService participanteService;

    @Autowired
    private PeriodoService periodoService;

    @Autowired
    private RelatorioService relatorioService;

    @GetMapping
    @ApiOperation( value = "Busca por todos os grupos registrados que o usuário logado participa.", notes = "Precisa estar autenticado.", response = GrupoSimpleDTO.class, nickname = "all-groups")
    public ResponseEntity<List<GrupoSimpleDTO>> findGroupsByLoggedUser() {
        List<GrupoSimpleDTO> grupos = grupoService.findGroupsByLoggedUser()
            .stream()
                .map(grupo -> modelMapper.map(grupo, GrupoSimpleDTO.class))
                .collect(Collectors.toList());

        return ResponseEntity.ok().body(grupos);
    }

    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    @ApiOperation( value = "Cria novo grupo sendo o usuário autenticado seu administrador", notes = "Precisa estar autenticado.", response = GrupoDTO.class, nickname = "new-group")
    public GrupoDTO save(@Valid @RequestBody GrupoDTO grupoDTO) {
        return modelMapper.map(grupoService.save(modelMapper.map(grupoDTO, Grupo.class)), GrupoDTO.class);
    }

    /////////////////////////

    @GetMapping("/{grupoId}")
    @ApiOperation( value = "Busca pelo grupo selecionado.", notes = "Precisa estar autenticado. Apenas os participantes deste grupo poderão efetuar este método.", response = GrupoDTO.class, nickname = "find-group")
    public ResponseEntity<GrupoDTO> findById(@PathVariable Integer grupoId) {
        GrupoDTO grupoDTO = modelMapper.map(grupoService.findById(grupoId), GrupoDTO.class);
        return ResponseEntity.ok().body(grupoDTO);
    }

    @PutMapping("/{grupoId}")
    @ApiOperation( value = "Atualiza informações do grupo.", notes = "Precisa estar autenticado. Apenas o administrador deste grupo poderá efetuar este método.", response = Void.class, nickname = "update-group")
    public ResponseEntity<Void> updateGroup(@Valid @RequestBody GrupoSimpleDTO grupoSimpleDTO,
            @PathVariable Integer grupoId) {
        grupoService.update(modelMapper.map(grupoSimpleDTO, Grupo.class), grupoId);
        return ResponseEntity.noContent().build();
    }

    /////////////////////////

    @GetMapping("/{grupoId}/periodos")
    @ApiOperation( value = "Busca por todos os períodos/sprints do grupo informado.", notes = "Precisa estar autenticado. Apenas quem está participando do grupo poderá efetuar este método.", response = Void.class, nickname = "all-periods")
    public ResponseEntity<List<PeriodoSimpleDTO>> findPeriods(@PathVariable Integer grupoId) {
        List<PeriodoSimpleDTO> periodos = periodoService.findByGroup(grupoId).stream()
                .map(periodo -> modelMapper.map(periodo, PeriodoSimpleDTO.class)).collect(Collectors.toList());
        return ResponseEntity.ok().body(periodos);
    }

    @PostMapping("/{grupoId}/periodos")
    @ResponseStatus(value = HttpStatus.CREATED)
    @ApiOperation( value = "Cria um novo período/sprint no grupo informado.", notes = "Precisa estar autenticado. Apenas um administrador do grupo poderá efetuar este método.", response = Void.class, nickname = "create-period")
    public PeriodoDTO addPeriod(@Valid @RequestBody PeriodoDTO periodoDTO, @PathVariable Integer grupoId) {
        Periodo periodo = modelMapper.map(periodoDTO, Periodo.class);
        return modelMapper.map(periodoService.save(periodo, grupoId), PeriodoDTO.class);
    }

    /////////////////////////

    @GetMapping("/{grupoId}/periodos/{periodoId}")
    @ApiOperation( value = "Busca em detalhes pelo período/sprint no grupo informado.", notes = "Precisa estar autenticado. Apenas quem está participando do grupo poderá efetuar este método.", response = Void.class, nickname = "find-period")
    public ResponseEntity<PeriodoDTO> findPeriod(@PathVariable Integer grupoId, @PathVariable Integer periodoId) {
        PeriodoDTO periodoDTO = modelMapper.map(periodoService.findById(periodoId, grupoId), PeriodoDTO.class);
        return ResponseEntity.ok().body(periodoDTO);
    }

    @PutMapping("/{grupoId}/periodos/{periodoId}")
    @ApiOperation( value = "Atualização dos dados do período/sprint no grupo informado.", notes = "Precisa estar autenticado. Apenas um administrador do grupo poderá efetuar este método.", response = Void.class, nickname = "update-period")
    public ResponseEntity<Void> updatePeriod(@Valid @RequestBody PeriodoSimpleDTO periodoSimpleDTO,
            @PathVariable Integer grupoId, @PathVariable Integer periodoId) {
        periodoService.update(modelMapper.map(periodoSimpleDTO, Periodo.class), periodoId, grupoId);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{grupoId}/periodos/{periodoId}")
    @ApiOperation( value = "Atualiza status do período/sprint no grupo informado.", notes = "Precisa estar autenticado. Apenas um administrador do grupo poderá efetuar este método.", response = Void.class, nickname = "update-status-period")
    public @ResponseBody void updatePeriodStatus(
                    @PathVariable Integer grupoId, 
                    @PathVariable Integer periodoId,
                    @RequestBody Map<Object, Object> fields) {
        
        Periodo periodo = periodoService.findById(periodoId, grupoId);
        fields.forEach((k, v) -> {
            Field field = ReflectionUtils.findRequiredField(Periodo.class, (String) k);
            field.setAccessible(true);
            try {
                Method valueOf = field.getType().getMethod("valueOf", String.class);
                Object value = valueOf.invoke(null, v);
                ReflectionUtils.setField(field, periodo, value);
            } catch (ReflectiveOperationException e) {
                System.out.println(e.getMessage());
            }
            
        });

        periodoService.patch(periodo);

    }

    /////////////////////////

    @PostMapping("/{grupoId}/participantes")
    @ResponseStatus(value = HttpStatus.CREATED)
    @ApiOperation( value = "Adiciona novo participante ao grupo.", notes = "Precisa estar autenticado. Apenas um administrador do grupo poderá efetuar este método.", response = Void.class, nickname = "add-partipant")
    public ParticipanteSimpleDTO addParticipant(@RequestBody UsuarioSimpleDTO usuarioSimpleDTO,
            @PathVariable Integer grupoId) {
        Usuario usuario = modelMapper.map(usuarioSimpleDTO, Usuario.class);
        Participante participante = participanteService.save(usuario, grupoId);
        return modelMapper.map(participante, ParticipanteSimpleDTO.class);
    }

    @GetMapping("/{grupoId}/participantes")
    @ApiOperation( value = "Busca por todos os participantes do grupo informado.", notes = "Precisa estar autenticado. Apenas quem está participando do grupo poderá efetuar este método.", response = Void.class, nickname = "all-partipants")
    public ResponseEntity<List<ParticipanteSimpleDTO>> findParticipants(@PathVariable Integer grupoId) {
        List<ParticipanteSimpleDTO> participantes = participanteService.findAllByGroup(grupoId).stream()
                .map(participante -> modelMapper.map(participante, ParticipanteSimpleDTO.class)).collect(Collectors.toList());
        return ResponseEntity.ok().body(participantes);
    }

    /////////////////////////

    @GetMapping("/{grupoId}/participantes/{usuarioId}")
    @ApiOperation( value = "Busca pelo participante do grupo informado.", notes = "Precisa estar autenticado. Apenas quem está participando do grupo poderá efetuar este método.", response = Void.class, nickname = "find-partipant")
    public ResponseEntity<ParticipanteSimpleDTO> findParticipant(@PathVariable Integer grupoId,
            @PathVariable Integer usuarioId) {
        ParticipanteSimpleDTO participante = modelMapper.map(participanteService.findById(grupoId, usuarioId),
                ParticipanteSimpleDTO.class);
        return ResponseEntity.ok().body(participante);
    }

    @DeleteMapping("/{grupoId}/participantes/{usuarioId}")
    @ApiOperation( value = "Exclui participante do grupo informado.", notes = "Precisa estar autenticado. Precisa ser administrador para excluir outros participantes. Participante poderá se excluir.", response = Void.class, nickname = "delete-partipant")
    public ResponseEntity<Void> deleteParticipant(@PathVariable Integer grupoId, @PathVariable Integer usuarioId) {
        participanteService.delete(grupoId, usuarioId);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{grupoId}/participantes/{usuarioId}")
    @ApiOperation( value = "Atualiza status do participante do grupo informado.", notes = "Precisa estar autenticado. Precisa ser administrador para efetuar este método.", response = Void.class, nickname = "update-status-partipant")
    public @ResponseBody void updateParticipantStatus(
                    @PathVariable Integer grupoId, 
                    @PathVariable Integer usuarioId,
                    @RequestBody Map<Object, Object> fields) {

        Participante participante = participanteService.findById(grupoId, usuarioId);
        fields.forEach((k, v) -> {
            Field field = ReflectionUtils.findRequiredField(Participante.class, (String) k);
            field.setAccessible(true);
            try {
                Method valueOf = field.getType().getMethod("valueOf", String.class);
                Object value = valueOf.invoke(null, v);
                ReflectionUtils.setField(field, participante, value);
            } catch (ReflectiveOperationException e) {
                System.out.println(e.getMessage());
            }
            
        });

        participanteService.updateAuthority(participante);
    }

    /////////////////////////

    @GetMapping("/{grupoId}/periodos/{periodoId}/relatorios")
    @ApiOperation( value = "Busca todos os relatórios do grupo no período informado.", notes = "Precisa estar autenticado. O administrador receberá todos os relatórios do período, e o participante receberá apenas seus relatórios do período informado.", response = RelatorioSimpleDTO.class, nickname = "find-reports-by-period")
    public ResponseEntity<List<RelatorioSimpleDTO>> findReportsByPeriod(@PathVariable Integer grupoId, @PathVariable Integer periodoId){
        List<RelatorioSimpleDTO> relatorios = relatorioService.findAllByPeriod(grupoId, periodoId)
            .stream()
                .map(relatorio -> modelMapper.map(relatorio, RelatorioSimpleDTO.class))
                .collect(Collectors.toList());

        return ResponseEntity.ok().body(relatorios);
    }

    @GetMapping("/{grupoId}/periodos/{periodoId}/relatorios/{relatorioId}")
    @ApiOperation( value = "Busca o relatório específico do grupo no período informado.", notes = "Precisa estar autenticado. Tanto o administrador do grupo quanto o participante - dono do relatório - poderá receber este relatório.", response = RelatorioDTO.class, nickname = "find-report-by-period-from-id")
    public ResponseEntity<RelatorioDTO> findReportByPeriodFromId(@PathVariable Integer grupoId, @PathVariable Integer periodoId, @PathVariable Integer relatorioId){
        RelatorioDTO relatorioDTO = modelMapper.map(relatorioService.findByPeriodFromId(grupoId, periodoId, relatorioId), RelatorioDTO.class);
        return ResponseEntity.ok().body(relatorioDTO);
    }

    @ResponseStatus(value = HttpStatus.CREATED)
    @PostMapping("/{grupoId}/periodos/{periodoId}/relatorios")
    @ApiOperation( value = "Cria um novo relatório para o usuário autenticado.", notes = "Precisa estar autenticado. Tanto o administrador do grupo quanto o participante poderá subir um relatório, tendo o participante limite de 1 por dia.", response = RelatorioSimpleDTO.class, nickname = "add-report")
    public RelatorioSimpleDTO saveReport(@RequestBody RelatorioSimpleDTO relatorioSimpleDTO, @PathVariable Integer grupoId, @PathVariable Integer periodoId){   
        Relatorio relatorio = modelMapper.map(relatorioSimpleDTO, Relatorio.class);
        relatorio = relatorioService.save(relatorio, grupoId, periodoId);
        return modelMapper.map(relatorio, RelatorioSimpleDTO.class);
    }

    @PutMapping("/{grupoId}/periodos/{periodoId}/relatorios/{relatorioId}")
    @ApiOperation( value = "Atualização dos dados do relatório no período informado.", notes = "Precisa estar autenticado. Apenas o próprio dono do relatório poderá atualizá-lo.", response = Void.class, nickname = "update-report")
    public ResponseEntity<Void> updateReport(@Valid @RequestBody RelatorioSimpleDTO relatorioSimpleDTO,
            @PathVariable Integer grupoId, @PathVariable Integer periodoId, @PathVariable Integer relatorioId) {
        relatorioService.update(modelMapper.map(relatorioSimpleDTO, Relatorio.class), periodoId, grupoId, relatorioId);
        return ResponseEntity.noContent().build();
    } 

}
