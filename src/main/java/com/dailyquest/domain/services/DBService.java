package com.dailyquest.domain.services;

import java.text.ParseException;
import java.time.OffsetDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.dailyquest.domain.models.Grupo;
import com.dailyquest.domain.models.Participante;
import com.dailyquest.domain.models.ParticipantePK;
import com.dailyquest.domain.models.Periodo;
import com.dailyquest.domain.models.Relatorio;
import com.dailyquest.domain.models.Usuario;
import com.dailyquest.domain.models.enums.StatusPeriodo;
import com.dailyquest.domain.models.enums.TipoPeriodo;
import com.dailyquest.domain.models.enums.TipoPermissao;
import com.dailyquest.domain.models.enums.TipoUsuario;
import com.dailyquest.domain.repositories.GrupoRepository;
import com.dailyquest.domain.repositories.RelatorioRepository;
import com.dailyquest.domain.repositories.UsuarioRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DBService {

    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private GrupoRepository grupoRepository;
    @Autowired
    private RelatorioRepository relatorioRepository;

    public void instantiateTestDatabase() throws ParseException {

        Usuario user1 = new Usuario(null, "henriquezsanches@gmail.com", "$2a$10$IEQa4Gb7e2Hm.vPLO15SrupIE3LQSY/n7V1kXH2nNQgyCnHPY8RdG",  Stream.of(TipoUsuario.ROLE_USUARIO).collect(Collectors.toSet()), Collections.emptySet(), Collections.emptyList());
        Usuario user2 = new Usuario(null, "user2@mail.com", "$2a$10$IEQa4Gb7e2Hm.vPLO15SrupIE3LQSY/n7V1kXH2nNQgyCnHPY8RdG",  Stream.of(TipoUsuario.ROLE_ADMIN).collect(Collectors.toSet()), Collections.emptySet(), Collections.emptyList());
        Usuario user3 = new Usuario(null, "user3@mail.com", "$2a$10$IEQa4Gb7e2Hm.vPLO15SrupIE3LQSY/n7V1kXH2nNQgyCnHPY8RdG",  Stream.of(TipoUsuario.ROLE_USUARIO).collect(Collectors.toSet()), Collections.emptySet(), Collections.emptyList());
        Usuario user4 = new Usuario(null, "user4@mail.com", "$2a$10$IEQa4Gb7e2Hm.vPLO15SrupIE3LQSY/n7V1kXH2nNQgyCnHPY8RdG",  Stream.of(TipoUsuario.ROLE_USUARIO).collect(Collectors.toSet()), Collections.emptySet(), Collections.emptyList());
        Usuario user5 = new Usuario(null, "user5@mail.com", "$2a$10$IEQa4Gb7e2Hm.vPLO15SrupIE3LQSY/n7V1kXH2nNQgyCnHPY8RdG",  Stream.of(TipoUsuario.ROLE_USUARIO).collect(Collectors.toSet()), Collections.emptySet(), Collections.emptyList());
        Usuario user6 = new Usuario(null, "user6@mail.com", "$2a$10$IEQa4Gb7e2Hm.vPLO15SrupIE3LQSY/n7V1kXH2nNQgyCnHPY8RdG",  Stream.of(TipoUsuario.ROLE_USUARIO).collect(Collectors.toSet()), Collections.emptySet(), Collections.emptyList());
        Usuario user7 = new Usuario(null, "user7@mail.com", "$2a$10$IEQa4Gb7e2Hm.vPLO15SrupIE3LQSY/n7V1kXH2nNQgyCnHPY8RdG",  Stream.of(TipoUsuario.ROLE_USUARIO).collect(Collectors.toSet()), Collections.emptySet(), Collections.emptyList());
        Usuario user8 = new Usuario(null, "user8@mail.com", "$2a$10$IEQa4Gb7e2Hm.vPLO15SrupIE3LQSY/n7V1kXH2nNQgyCnHPY8RdG",  Stream.of(TipoUsuario.ROLE_USUARIO).collect(Collectors.toSet()), Collections.emptySet(), Collections.emptyList());
        Usuario user9 = new Usuario(null, "user9@mail.com", "$2a$10$IEQa4Gb7e2Hm.vPLO15SrupIE3LQSY/n7V1kXH2nNQgyCnHPY8RdG",  Stream.of(TipoUsuario.ROLE_USUARIO).collect(Collectors.toSet()), Collections.emptySet(), Collections.emptyList());

        usuarioRepository.saveAll(
            Arrays.asList(
                user1, 
                user2,
                user3, 
                user4,
                user5, 
                user6,
                user7, 
                user8,
                user9
            )
        );

        Grupo grup1 = new Grupo();
        grup1.setNome("grupo1");
        grup1.setImagemUrl("imagem1");
        grup1.setDescricao("desc grupo 1");
        grup1.setDataHoraCriacao(OffsetDateTime.now());
        grup1.setParticipantes(Stream.of(
            new Participante(new ParticipantePK(grup1, user1), TipoPermissao.ADMIN),
            new Participante(new ParticipantePK(grup1, user2), TipoPermissao.PARTICIPANTE),
            new Participante(new ParticipantePK(grup1, user3), TipoPermissao.PARTICIPANTE)
        ).collect(Collectors.toSet()));

        Grupo grup2 = new Grupo();
        grup2.setNome("grupo2");
        grup2.setImagemUrl("imagem2");
        grup2.setDescricao("desc grupo 2");
        grup2.setDataHoraCriacao(OffsetDateTime.now());
        grup2.setParticipantes(Stream.of(
            new Participante(new ParticipantePK(grup2, user4), TipoPermissao.ADMIN),
            new Participante(new ParticipantePK(grup2, user5), TipoPermissao.PARTICIPANTE),
            new Participante(new ParticipantePK(grup2, user6), TipoPermissao.PARTICIPANTE)
        ).collect(Collectors.toSet()));


        Grupo grup3 = new Grupo();
        grup3.setNome("grupo3");
        grup3.setImagemUrl("imagem3");
        grup3.setDescricao("desc grupo 3");
        grup3.setDataHoraCriacao(OffsetDateTime.now());
        grup3.setParticipantes(Stream.of(
            new Participante(new ParticipantePK(grup3, user7), TipoPermissao.ADMIN),
            new Participante(new ParticipantePK(grup3, user8), TipoPermissao.PARTICIPANTE),
            new Participante(new ParticipantePK(grup3, user9), TipoPermissao.PARTICIPANTE)
        ).collect(Collectors.toSet()));

        Periodo periodo11 = new Periodo(StatusPeriodo.INATIVO, TipoPeriodo.MENSAL, OffsetDateTime.now(), OffsetDateTime.now(), Collections.emptyList(), grup1);
        Periodo periodo12 = new Periodo(StatusPeriodo.ATIVO, TipoPeriodo.MENSAL, OffsetDateTime.now(), OffsetDateTime.now(), Collections.emptyList(), grup1);

        Periodo periodo21 = new Periodo(StatusPeriodo.INATIVO, TipoPeriodo.MENSAL, OffsetDateTime.now(), OffsetDateTime.now(), Collections.emptyList(), grup2);
        Periodo periodo22 = new Periodo(StatusPeriodo.ATIVO, TipoPeriodo.MENSAL, OffsetDateTime.now(), OffsetDateTime.now(), Collections.emptyList(), grup2);

        Periodo periodo31 = new Periodo(StatusPeriodo.INATIVO, TipoPeriodo.MENSAL, OffsetDateTime.now(), OffsetDateTime.now(), Collections.emptyList(), grup3);
        Periodo periodo32 = new Periodo(StatusPeriodo.ATIVO, TipoPeriodo.MENSAL, OffsetDateTime.now(), OffsetDateTime.now(), Collections.emptyList(), grup3);

        Relatorio relatorio11 = new Relatorio("relatorio", "Assunto", OffsetDateTime.now(), periodo11, user1);
        Relatorio relatorio12 = new Relatorio("relatorio", "Assunto", OffsetDateTime.now(), periodo11, user1);
        Relatorio relatorio13 = new Relatorio("relatorio", "Assunto", OffsetDateTime.now(), periodo12, user1);

        Relatorio relatorio21 = new Relatorio("relatorio", "Assunto", OffsetDateTime.now(), periodo11, user2);
        Relatorio relatorio22 = new Relatorio("relatorio", "Assunto", OffsetDateTime.now(), periodo11, user2);
        Relatorio relatorio23 = new Relatorio("relatorio", "Assunto", OffsetDateTime.now(), periodo12, user2);

        Relatorio relatorio31 = new Relatorio("relatorio", "Assunto", OffsetDateTime.now(), periodo11, user3);
        Relatorio relatorio32 = new Relatorio("relatorio", "Assunto", OffsetDateTime.now(), periodo11, user3);
        Relatorio relatorio33 = new Relatorio("relatorio", "Assunto", OffsetDateTime.now(), periodo12, user3);

        Relatorio relatorio41 = new Relatorio("relatorio", "Assunto", OffsetDateTime.now(), periodo21, user4);
        Relatorio relatorio42 = new Relatorio("relatorio", "Assunto", OffsetDateTime.now(), periodo21, user4);
        Relatorio relatorio43 = new Relatorio("relatorio", "Assunto", OffsetDateTime.now(), periodo22, user4);

        Relatorio relatorio51 = new Relatorio("relatorio", "Assunto", OffsetDateTime.now(), periodo21, user5);
        Relatorio relatorio52 = new Relatorio("relatorio", "Assunto", OffsetDateTime.now(), periodo21, user5);
        Relatorio relatorio53 = new Relatorio("relatorio", "Assunto", OffsetDateTime.now(), periodo22, user5);

        Relatorio relatorio61 = new Relatorio("relatorio", "Assunto", OffsetDateTime.now(), periodo21, user6);
        Relatorio relatorio62 = new Relatorio("relatorio", "Assunto", OffsetDateTime.now(), periodo21, user6);
        Relatorio relatorio63 = new Relatorio("relatorio", "Assunto", OffsetDateTime.now(), periodo22, user6);

        Relatorio relatorio71 = new Relatorio("relatorio", "Assunto", OffsetDateTime.now(), periodo31, user7);
        Relatorio relatorio72 = new Relatorio("relatorio", "Assunto", OffsetDateTime.now(), periodo31, user7);
        Relatorio relatorio73 = new Relatorio("relatorio", "Assunto", OffsetDateTime.now(), periodo32, user7);

        Relatorio relatorio81 = new Relatorio("relatorio", "Assunto", OffsetDateTime.now(), periodo31, user8);
        Relatorio relatorio82 = new Relatorio("relatorio", "Assunto", OffsetDateTime.now(), periodo31, user8);
        Relatorio relatorio83 = new Relatorio("relatorio", "Assunto", OffsetDateTime.now(), periodo32, user8);

        Relatorio relatorio91 = new Relatorio("relatorio", "Assunto", OffsetDateTime.now(), periodo31, user9);
        Relatorio relatorio92 = new Relatorio("relatorio", "Assunto", OffsetDateTime.now(), periodo31, user9);
        Relatorio relatorio93 = new Relatorio("relatorio", "Assunto", OffsetDateTime.now(), periodo32, user9);

        periodo11.setRelatorios(
            Stream.of(
                relatorio11,
                relatorio12,
                relatorio21,
                relatorio22,
                relatorio31,
                relatorio32
            ).collect(Collectors.toList())
        );

        periodo12.setRelatorios(
            Stream.of(
                relatorio13,
                relatorio23,
                relatorio33
            ).collect(Collectors.toList())
        );

        periodo21.setRelatorios(
            Stream.of(
                relatorio41,
                relatorio42,
                relatorio51,
                relatorio52,
                relatorio61,
                relatorio62
            ).collect(Collectors.toList())
        );

        periodo22.setRelatorios(
            Stream.of(
                relatorio43,
                relatorio53,
                relatorio63
            ).collect(Collectors.toList())
        );

        periodo31.setRelatorios(
            Stream.of(
                relatorio71,
                relatorio72,
                relatorio81,
                relatorio82,
                relatorio91,
                relatorio92
            ).collect(Collectors.toList())
        );

        periodo32.setRelatorios(
            Stream.of(
                relatorio73,
                relatorio83,
                relatorio93
            ).collect(Collectors.toList())
        );
        
        grup1.setPeriodos(Stream.of(
            periodo11,
            periodo12
        ).collect(Collectors.toList()));

        grup2.setPeriodos(Stream.of(
            periodo21,
            periodo22
        ).collect(Collectors.toList()));

        grup3.setPeriodos(Stream.of(
            periodo31,
            periodo32
        ).collect(Collectors.toList()));

        grupoRepository.saveAll(Arrays.asList(grup1, grup2, grup3));

        relatorioRepository.saveAll(
            Arrays.asList(
                relatorio11,
                relatorio12,
                relatorio13,
                relatorio21,
                relatorio22,
                relatorio23,
                relatorio31,
                relatorio32,
                relatorio33,
                relatorio41,
                relatorio42,
                relatorio43,
                relatorio51,
                relatorio52,
                relatorio53,
                relatorio61,
                relatorio62,
                relatorio63,
                relatorio71,
                relatorio72,
                relatorio73,
                relatorio81,
                relatorio82,
                relatorio83,
                relatorio91,
                relatorio92,
                relatorio93
            )
        );
    }
}
