package com.github.cleversonlira.ifood.cadastro.interfaces.dto;

import java.math.BigDecimal;
import java.util.Optional;

import javax.validation.ConstraintValidatorContext;

import com.github.cleversonlira.ifood.cadastro.application.config.DTO;
import com.github.cleversonlira.ifood.cadastro.application.config.ValidDTO;
import com.github.cleversonlira.ifood.cadastro.domain.Prato;

@ValidDTO
public class AdicionarPratoDTO implements DTO {

	public String nome;
	public String descricao;
	public BigDecimal preco;

	@Override
	public boolean isValid(ConstraintValidatorContext constraintValidatorContext) {
		return (Optional.ofNullable(Prato.find("nome", nome)).isPresent()) 
				? false : true;
	}

}
