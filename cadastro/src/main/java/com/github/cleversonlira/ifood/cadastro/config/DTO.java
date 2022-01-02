package com.github.cleversonlira.ifood.cadastro.config;

import javax.validation.ConstraintValidatorContext;

public interface DTO {
	
	default boolean isValid(ConstraintValidatorContext constraintValidatorContext) {
		return true;
	}

}
