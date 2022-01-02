package com.github.cleversonlira.ifood.cadastro.dto;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.github.cleversonlira.ifood.cadastro.Restaurante;

@Mapper(componentModel = "cdi")
public interface RestauranteMapper {
	
	@Mapping(target = "nome", source = "nomeFantasia")
	Restaurante toEntity(AdicionarRestauranteDTO dto);
	
	@Mapping(target = "nome", source = "nomeFantasia")
	void toEntity(AdicionarRestauranteDTO dto, @MappingTarget Restaurante restaurante);
	
	@Mapping(target = "nomeFantasia", source = "nome")
	List<RestauranteDTO> toListDTO(List<Restaurante> restaurantes);

}
