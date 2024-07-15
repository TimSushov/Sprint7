import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import resours.CourierData;
import steps.StepsCourier;

import java.util.Random;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;

public class CreateCourierTest extends StepsCourier {
    private String login = "Kolbosa" + new Random().nextInt(99);
    private String password = "1234" + new Random().nextInt(99);;
    private String name = "Doktorskaya" + new Random().nextInt(99);;



    @Before
    public void setUp() {
        RestAssured.baseURI = BASE_URI;
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

}
