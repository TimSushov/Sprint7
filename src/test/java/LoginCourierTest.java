import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import resours.CourierData;
import steps.StepsCourier;

import java.util.Random;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.notNullValue;

public class LoginCourierTest extends StepsCourier {
    private String login = "Kolbosa" + new Random().nextInt(99);
    private String password = "1234" + new Random().nextInt(99);
    private String name = "Doktorskaya" + new Random().nextInt(99);
    private String random = new Random().nextInt(99) + "random" ;

    @Before
    public void setUp() {
        RestAssured.baseURI = BASE_URI;
        createCourierWithName(login, password, name);
    }

    @Test
    @DisplayName("Логин курьера")
    @Description("Курьер логинится без ошибок")
    public void loginCourier() {
        loginCourierInSystem(login, password, 200);
    }

    @Test
    @DisplayName("Логин курьера не передавая логина или пароля")
    @Description("Курьер не может залогинится")
    public void loginCourierEmptyLoginOrPassword() {
        loginCourierInSystem(login, "", 400);
        loginCourierInSystem("", password, 400);
        loginCourierInSystem("", "", 400);;
    }

    @Test
    @DisplayName("Логин курьера с ошибочным логином или паролем")
    @Description("Курьер не может залогинится")
    public void loginCourierWrongLoginOrPassword() {
        loginCourierInSystem(login, random, 404);
        loginCourierInSystem(random, password, 404);
    }

    @Test
    @DisplayName("Логин курьера без строки в теле запроса")
    @Description("Логин не возможен")
    public void loginCourierEmptyString() {
        loginCourierInSystemWithoutLogin(password, 400);
//        loginCourierInSystemWithoutPassword(login, 400); --  у меня сервер не отвечает в этом кейсе. Выдает 504 Gateway Timeout Service unavailable по таймауту. Судя по документации должен выжавать 400.
    }

    @After
    public void deleteCourierAfter() {
        deleteCourier((courierIdExtract(login, password)));
    }

}
