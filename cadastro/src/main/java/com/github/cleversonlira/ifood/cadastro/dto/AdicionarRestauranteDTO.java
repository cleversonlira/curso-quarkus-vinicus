package com.github.cleversonlira.ifood.cadastro.dto;

import javax.validation.ConstraintValidatorContext;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.github.cleversonlira.ifood.cadastro.Restaurante;
import com.github.cleversonlira.ifood.cadastro.config.DTO;

public class AdicionarRestauranteDTO implements DTO {

	@NotBlank
	public String proprietario;

	@NotBlank
	@Pattern(regexp = "[0-9]{2}\\.[0-9]{3}\\.[0-9]{3}\\/[0-9]{4}\\-[0-9]{2}")

	public String cnpj;

	@Size(min = 3, max = 30)
	public String nomeFantasia;
	public LocalizacaoDTO localizacao;

	@Override
	public boolean isValid(ConstraintValidatorContext constraintValidatorContext) {
		constraintValidatorContext.disableDefaultConstraintViolation();
		if (Restaurante.find("cnpj", this.cnpj).count() > 0) {
			constraintValidatorContext.buildConstraintViolationWithTemplate("CNPJ já cadastrado")
					.addPropertyNode("cnpj")
					.addConstraintViolation();
			return false;
		}
		return true;
	}

}
