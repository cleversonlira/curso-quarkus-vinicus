package com.github.cleversonlira.ifood.cadastro.domain;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;


@Entity(name = "prato")
public class Prato extends PanacheEntityBase {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long id;
	
	public String nome;
	
	public String descricao;
	
	@ManyToOne
	public Restaurante restaurante;
	
	public BigDecimal preco;

}
