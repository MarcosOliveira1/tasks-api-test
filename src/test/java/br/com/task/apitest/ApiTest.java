package br.com.task.apitest;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.hamcrest.CoreMatchers;
import org.junit.BeforeClass;
import org.junit.Test;

public class ApiTest {

    @BeforeClass
    public static void setup() {
        RestAssured.baseURI = "http://localhost:8001/tasks-backend";
    }

    @Test
    public void deveRetornarTarefas() {
        RestAssured.given()
                    .log().all()
                .when()
                    .get("/todo")
                .then()
                    .statusCode(200)
                    .log().all();
    }

    @Test
    public void deveSalvarTarefa() {
        RestAssured.given()
                    .body("{\"task\": \"test rest assured\",\"dueDate\": \"2022-01-01\"}")
                    .contentType(ContentType.JSON)
                .log().all()
                .when()
                    .post("/todo")
                .then()
                    .statusCode(201)
                .log().all();
    }

    @Test
    public void NaoDeveSalvarTarefaInvalida() {
        RestAssured.given()
                .body("{\"task\": \"test rest assured\",\"dueDate\": \"2010-01-01\"}")
                .contentType(ContentType.JSON)
                .log().all()
                .when()
                .post("/todo")
                .then()
                .statusCode(400)
                .body("message", CoreMatchers.is("Due date must not be in past"))
                .log().all();
    }

}
