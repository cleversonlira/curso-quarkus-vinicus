package com.github.cleversonlira.ifood.cadastro;

import static io.restassured.RestAssured.given;

import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
//@QuarkusTestResource(CadastroTestLifecycleManager.class)
public class RestauranteResourceTest {

	@Test
	public void testBuscarRestaurantes() {
		given().when().get("/restaurantes").then().statusCode(200);
	}

}