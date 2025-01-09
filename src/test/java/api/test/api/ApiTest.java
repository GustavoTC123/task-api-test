package api.test.api;

import org.hamcrest.CoreMatchers;
import org.junit.BeforeClass;
import org.junit.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

public class ApiTest {

    @BeforeClass
    public static void setUp()
    {
        RestAssured.baseURI = "http://localhost:8001/tasks-backend";
    }

    @Test
    public void teste() {
        RestAssured.given()
                        .log().all()
                    .when()
                        .get("/todo")
                    .then()
                    .statusCode(200);
    }

    @Test
    public void adicionarTarefaComSucesso()
    {
        RestAssured.given()
                        .body("{\"task\": \"Teste via API\", \"dueDate\": \"2030-12-12\"}")
                        .contentType(ContentType.JSON)
                    .when()
                        .post("/todo")
                    .then()
                        .statusCode(201);
    }

    @Test
    public void adicionarTarefaInvalida()
    {
        RestAssured.given()
                        .body("{\"task\": \"Teste via API\", \"dueDate\": \"2020-12-12\"}")
                        .contentType(ContentType.JSON)
                    .when()
                        .post("/todo")
                    .then()
                        .body("message", CoreMatchers.is("Due date must not be in past"))
                        .statusCode(400);
    }

    @Test
    public void removerTarefaComSucesso()
    {
        //adicionar
       int id =  RestAssured.given()
                        .body("{\"task\": \"Teste via API\", \"dueDate\": \"2030-12-12\"}")
                        .contentType(ContentType.JSON)
                    .when()
                        .post("/todo")
                    .then()
                        .statusCode(201)
                            .extract().path("id");
   
        System.out.println(id);

        //Remover
        RestAssured.given()
                    .when()
                        .delete("/todo/"+id)
                    .then()
                        .statusCode(204);   
    }

}