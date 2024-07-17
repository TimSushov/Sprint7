import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Test;
import steps.StepsCourier;

import java.util.Random;


public class CreateCourierTest {
    private String login = "Servelat" + new Random().nextInt(99);
    private String password = "12345" + new Random().nextInt(99);;
    private String name = "Doktorskaya" + new Random().nextInt(99);;

    StepsCourier stepsCourier = new StepsCourier();

    @Test
    @DisplayName("Создание курьера")
    @Description("Курьер создается без ошибок")
    public void createCourier() {
        stepsCourier.createCourier(login, password, name, 201);
    }

    @Test
    @DisplayName("Создание курьера с существующими данными")
    @Description("Создание второго одинакого курьера не возможно")
    public void createEqalCourier() {
        stepsCourier.createCourier(login, password, name, 201);
        stepsCourier.createCourier(login, password, name, 409);
    }

    @Test
    @DisplayName("Создание курьера с незаполненными данными")
    @Description("Создание возможно только без указания имени")
    public void createCourierNotFullData() {
        stepsCourier.createCourier("", password, name, 400);
        stepsCourier.createCourier(login, "", name, 400);
        stepsCourier.createCourier(login, password, "", 201);
    }

    @Test
    @DisplayName("Создание курьера без строки в теле запроса")
    @Description("Создание возможно только без строки имени")
    public void createCourierEmptyString() {
        stepsCourier.createCourierWithoutLogin(password, name, 400);
        stepsCourier.createCourierWithoutPassword(login, name, 400);
        stepsCourier.createCourierWithoutName(login, password, 201);
    }

    @Test
    @DisplayName("Создание курьера с существующим логином")
    @Description("Создание возможно только с уникальным логином")
    public void createEqalLoginCourier() {
        stepsCourier.createCourier(login, password, name, 201);
        stepsCourier.createCourier(login, "0000", "Servelat", 409);
    }

    @After
    public void deleteCourierAfter() {
        stepsCourier.deleteCourier((stepsCourier.courierIdExtract(login, password)));
    }

}
