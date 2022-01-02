package com.github.cleversonlira.ifood.cadastro.dto;

import org.mapstruct.Mapper;

import com.github.cleversonlira.ifood.cadastro.Prato;

@Mapper(componentModel = "cdi")
public interface PratoMapper {
	
	Prato toEntity(AdicionarPratoDTO dto);
	
}
