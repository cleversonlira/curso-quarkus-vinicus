package com.github.cleversonlira.ifood.cadastro;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.eclipse.microprofile.openapi.annotations.tags.Tag;


@Path("/restaurantes")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "Restaurante")
public class RestauranteResource {

	@GET
	public List<Restaurante> listar() {
		return Restaurante.listAll();
	}

	@POST
	@Transactional
	public Response adicionar(Restaurante dto) {
		dto.persist();
		return Response.status(Status.CREATED).build();
	}

	@PUT
	@Path("{id}")
	@Transactional
	public void atualizar(@PathParam("id") Long id, Restaurante dto) {
		Optional<Restaurante> restauranteOptional = Restaurante.findByIdOptional(id);
		if (restauranteOptional.isEmpty()) {
			throw new NotFoundException();
		}
		restauranteOptional.get().nome = dto.nome;
	}

	@DELETE
	@Path("{id}")
	@Transactional
	public void deletar(@PathParam("id") Long id) {
		Optional<Restaurante> restauranteOptional = Restaurante.findByIdOptional(id);
		restauranteOptional.ifPresentOrElse(Restaurante::delete, () -> {
			throw new NotFoundException();
		});
	}
	
//	----------------------------PRATOS--------------------------------
	
	@GET
	@Path("{idRestaurante}/pratos")
	@Tag(name = "Prato")
	public List<Restaurante> listarPratos(@PathParam("idRestaurante") Long idRestaurante) {
		Optional<Restaurante> restauranteOptional = Restaurante.findByIdOptional(idRestaurante);
		if (restauranteOptional.isEmpty()) {
			throw new NotFoundException("Restaurante não existe");
		}
		return Prato.list("restaurante", restauranteOptional.get());
		
	}
	
	@GET
	@Path("{idRestaurante}/pratos/{id}")
	@Tag(name = "Prato")
	public Response buscarPrato(@PathParam("idRestaurante") Long idRestaurante, @PathParam("id") Long id) {		
		Optional<Restaurante> restauranteOptional = Restaurante.findByIdOptional(idRestaurante);
		if (restauranteOptional.isEmpty()) {
			throw new NotFoundException("Restaurante não existe");
		}
		Prato prato = (Prato) Prato.find("id", id);
		if (prato.equals(null)) {
			throw new NotFoundException("Prato não existe");
		}
		return Response.ok(prato).build();
	}
	
	@POST
	@Path("{idRestaurante}/pratos/")
	@Transactional
	@Tag(name = "Prato")
	public Response adicionar(@PathParam("idRestaurante") Long idRestaurante, Prato dto) {
		Optional<Restaurante> restauranteOptional = Restaurante.findByIdOptional(idRestaurante);
		if (restauranteOptional.isEmpty()) {
			throw new NotFoundException("Restaurante não existe");
		}
		dto.persist();
		return Response.status(Status.CREATED).build();
	}

	@PUT
	@Path("{idRestaurante}/pratos/{id}")	
	@Transactional
	@Tag(name = "Prato")
	public void atualizar(@PathParam("idRestaurante") Long idRestaurante, @PathParam("id") Long id, Prato dto) {
		Optional<Restaurante> restauranteOptional = Restaurante.findByIdOptional(idRestaurante);
		if (restauranteOptional.isEmpty()) {
			throw new NotFoundException("Restaurante não existe");
		}
		Optional<Prato> pratoOptional = Prato.findByIdOptional(id);
		if (pratoOptional.isEmpty()) {
			throw new NotFoundException("Prato não existe");
		}
		Prato prato = pratoOptional.get();
		prato.preco = dto.preco;
		prato.persist();
	}

	@DELETE
	@Path("{idRestaurante}/pratos/{id}}")
	@Transactional
	@Tag(name = "Prato")
	public void deletarPrato(@PathParam("idRestaurante") Long idRestaurante, @PathParam("id") Long id) {
		Optional<Restaurante> restauranteOptional = Restaurante.findByIdOptional(idRestaurante);
		if (restauranteOptional.isEmpty()) {
			throw new NotFoundException("Restaurante não existe");
		}
		Optional<Prato> pratoOptional = Prato.findByIdOptional(id);
		
		pratoOptional.ifPresentOrElse(Prato::delete, () -> {
			throw new NotFoundException("Prato não existe");
		});
	}

}