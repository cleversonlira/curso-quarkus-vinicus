package com.github.cleversonlira.ifood.cadastro.interfaces;

import java.util.List;
import java.util.Optional;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.validation.Valid;
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

import org.eclipse.microprofile.openapi.annotations.enums.SecuritySchemeType;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.security.OAuthFlow;
import org.eclipse.microprofile.openapi.annotations.security.OAuthFlows;
import org.eclipse.microprofile.openapi.annotations.security.SecurityRequirement;
import org.eclipse.microprofile.openapi.annotations.security.SecurityScheme;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import com.github.cleversonlira.ifood.cadastro.application.config.ConstraintViolationResponse;
import com.github.cleversonlira.ifood.cadastro.application.dto.RestauranteDTO;
import com.github.cleversonlira.ifood.cadastro.domain.Prato;
import com.github.cleversonlira.ifood.cadastro.domain.Restaurante;
import com.github.cleversonlira.ifood.cadastro.interfaces.dto.AdicionarPratoDTO;
import com.github.cleversonlira.ifood.cadastro.interfaces.dto.AdicionarRestauranteDTO;
import com.github.cleversonlira.ifood.cadastro.interfaces.dto.PratoMapper;
import com.github.cleversonlira.ifood.cadastro.interfaces.dto.RestauranteMapper;


@Path("/restaurantes")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "restaurante")
@RolesAllowed("proprietario")
@SecurityScheme(securitySchemeName = "ifood-oauth", type = SecuritySchemeType.OAUTH2, flows = @OAuthFlows(password = @OAuthFlow(tokenUrl = "http://localhost:8180/auth/realms/ifood/protocol/openid-connect/token")))
@SecurityRequirement(name = "ifood-oauth", scopes = {})
public class RestauranteResource {
	
	@Inject
	RestauranteMapper restauranteMapper;
	
	@Inject
	PratoMapper pratoMapper;

	@GET
	public List<RestauranteDTO> listar() {
		return restauranteMapper.toListDTO(Restaurante.listAll());
	}

	@POST
	@Transactional
	@APIResponse(responseCode = "201", description = "Caso seja cadastrado com sucesso")
	@APIResponse(responseCode = "400", content = @Content(schema = @Schema(allOf = ConstraintViolationResponse.class)))
	public Response adicionar(@Valid AdicionarRestauranteDTO dto) {
		restauranteMapper.toEntity(dto).persist();
		return Response.status(Status.CREATED).build();
	}

	@PUT
	@Path("{id}")
	@Transactional
	public void atualizar(@PathParam("id") Long id, @Valid AdicionarRestauranteDTO dto) {
		Optional<Restaurante> restauranteOptional = Restaurante.findByIdOptional(id);
		if (restauranteOptional.isEmpty()) {
			throw new NotFoundException();
		}
		Restaurante restaurante = restauranteOptional.get(); 
		restauranteMapper.toEntity(dto, restaurante);
		restaurante.persist();
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
	public Response adicionar(@PathParam("idRestaurante") Long idRestaurante, @Valid AdicionarPratoDTO dto) {
		Optional<Restaurante> restauranteOptional = Restaurante.findByIdOptional(idRestaurante);
		if (restauranteOptional.isEmpty()) {
			throw new NotFoundException("Restauratruente não existe");
		}
		Prato entity = pratoMapper.toEntity(dto);
		entity.restaurante = Restaurante.findById(idRestaurante);		
		entity.persist();
		return Response.status(Status.CREATED).build();
	}

	@PUT
	@Path("{idRestaurante}/pratos/{id}")	
	@Transactional
	@Tag(name = "Prato")
	public void atualizar(@PathParam("idRestaurante") Long idRestaurante, @PathParam("id") Long id, @Valid AdicionarPratoDTO dto) {
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