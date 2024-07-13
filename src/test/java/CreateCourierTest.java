import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import resours.CourierData;

import java.util.Random;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;

public class CreateCourierTest {
    private String login = "Kolbosa" + new Random().nextInt(99);
    private String password = "1234" + new Random().nextInt(99);;
    private String name = "Doktorskaya" + new Random().nextInt(99);;

    @Before
    public void setUp() {
        RestAssured.baseURI = "https://qa-scooter.praktikum-services.ru";
    }

    @Test
    @DisplayName("Создание курьера")
    @Description("Курьер создается без ошибок")
    public void createCourier() {
        createCourier(login, password, name, 201);
    }

    @Test
    @DisplayName("Создание курьера с существующими данными")
    @Description("Создание второго одинакого курьера не возможно")
    public void createEqalCourier() {
        createCourier(login, password, name, 201);
        createCourier(login, password, name, 409);
    }

    @Test
    @DisplayName("Создание курьера с незаполненными данными")
    @Description("Создание возможно только без указания имени")
    public void createCourierNotFullData() {
        createCourier("", password, name, 400);
        createCourier(login, "", name, 400);
        createCourier(login, password, "", 201);
    }

    @Test
    @DisplayName("Создание курьера без строки в теле запроса")
    @Description("Создание возможно только без строки имени")
    public void createCourierEmptyString() {
        createCourierWithoutLogin(password, name, 400);
        createCourierWithoutPassword(login, name, 400);
        createCourierWithoutName(login, password, 201);
    }

    @Test
    @DisplayName("Создание курьера с существующим логином")
    @Description("Создание возможно только с уникальным логином")
    public void createEqalLoginCourier() {
        createCourier(login, password, name, 201);
        createCourier(login, "0000", "Servelat", 409);
    }

    @After
    public void deleteCourierAfter() {
        deleteCourier((courierIdExtract(login, password)));
    }

    @Step("Сourier Id Extract")
    public int courierIdExtract(String login, String password ) {
        CourierData courierData = new CourierData(login, password);
        int coeurierId = given()
                    .header("Content-type", "application/json")
                    .and()
                    .body(courierData)
                    .when()
                    .post("/api/v1/courier/login")
               .then().extract().body().path("id");
        return coeurierId;
    }

    @Step("Create courier")
    public void createCourier(String login, String password, String name, int statusCode){
        CourierData courierData = new CourierData(login, password, name);
        Response response =
                given()
                        .header("Content-type", "application/json")
                        .and()
                        .body(courierData)
                        .when()
                        .post("/api/v1/courier");
        response.then().statusCode(statusCode);
                        if(statusCode == 201) {
                            response.then().assertThat().body("ok", equalTo(true));
                        } else if (statusCode == 400) {
                            response.then().assertThat().body("message", equalTo("Недостаточно данных для создания учетной записи"));
                        } else if (statusCode == 409) {
                            response.then().assertThat().body("message", equalTo("Этот логин уже используется. Попробуйте другой."));
                        }
        }

    @Step("Create courier without login")
    public void createCourierWithoutLogin(String setPassword, String setName, int statusCode){
        String json = "{\n" +
                "    \"password\": \""+setPassword+"\",\n" +
                "    \"firstName\": \""+setName+"\"\n" +
                "}";
           given()
                .header("Content-type", "application/json")
                .and()
                .body(json)
                .when()
                .post("/api/v1/courier")
                .then().statusCode(statusCode)
                .and()
                .assertThat().body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }

    @Step("Create courier without password")
    public void createCourierWithoutPassword(String setLogin, String setName, int statusCode){
        String json = "{\n" +
                "    \"login\": \""+setLogin+"\",\n" +
                "    \"firstName\": \""+setName+"\"\n" +
                "}";
            given()
                    .header("Content-type", "application/json")
                    .and()
                    .body(json)
                    .when()
                    .post("/api/v1/courier")
                    .then().statusCode(statusCode)
                    .and()
                    .assertThat().body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }

    @Step("Create courier without name")
    public void createCourierWithoutName(String setLogin, String setPassword, int statusCode){
            String json = "{\n" +
                    "    \"login\": \""+setLogin+"\",\n" +
                    "    \"password\": \""+setPassword+"\"\n" +
                    "}";
            given()
                    .header("Content-type", "application/json") // заполни header
                    .and()
                    .body(json)
                    .when()
                    .post("/api/v1/courier")
                    .then().statusCode(statusCode)
                    .and()
                    .assertThat().body("ok", equalTo(true));
    }

    @Step("Delete courier")
    public void deleteCourier(int id){
                given()
                        .delete("/api/v1/courier/{id}", id);
    }
}
