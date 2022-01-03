package com.github.cleversonlira.ifood.cadastro.application.config;

import javax.validation.ConstraintValidatorContext;

public interface DTO {
	
	default boolean isValid(ConstraintValidatorContext constraintValidatorContext) {
		return true;
	}

}
