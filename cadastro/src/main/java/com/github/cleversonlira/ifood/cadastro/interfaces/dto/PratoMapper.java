package com.github.cleversonlira.ifood.cadastro.interfaces.dto;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import com.github.cleversonlira.ifood.cadastro.application.dto.PratoDTO;
import com.github.cleversonlira.ifood.cadastro.domain.Prato;

@Mapper(componentModel = "cdi")
public interface PratoMapper {
	
	Prato toEntity(AdicionarPratoDTO dto);
	
    PratoDTO toDTO(Prato p);

    void toPrato(AtualizarPratoDTO dto, @MappingTarget Prato prato);
	
}
