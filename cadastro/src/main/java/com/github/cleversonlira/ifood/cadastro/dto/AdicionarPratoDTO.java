package com.github.cleversonlira.ifood.cadastro.dto;

import java.math.BigDecimal;
import java.util.Optional;

import javax.validation.ConstraintValidatorContext;

import com.github.cleversonlira.ifood.cadastro.Prato;
import com.github.cleversonlira.ifood.cadastro.config.DTO;
import com.github.cleversonlira.ifood.cadastro.config.ValidDTO;

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
