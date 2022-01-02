package com.github.cleversonlira.ifood.cadastro.dto;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.github.cleversonlira.ifood.cadastro.Restaurante;

@Mapper(componentModel = "cdi")
public interface RestauranteMapper {
	
	@Mapping(target = "nome", source = "nomeFantasia")
	Restaurante toEntity(AdicionarRestauranteDTO dto);

}
