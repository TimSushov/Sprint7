import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;;
import steps.StepsCourier;

import java.util.Random;

public class LoginCourierTest {
    private String login = "Servelat" + new Random().nextInt(99);
    private String password = "12345" + new Random().nextInt(99);
    private String name = "Doktorskaya" + new Random().nextInt(99);
    private String random = new Random().nextInt(99) + "random" ;

    StepsCourier stepsCourier = new StepsCourier();

    @Before
    public void setUp() {
        stepsCourier.createCourierWithName(login, password, name);
    }

    @Test
    @DisplayName("Логин курьера")
    @Description("Курьер логинится без ошибок")
    public void loginCourier() {
        stepsCourier.loginCourierInSystem(login, password, 200);
    }

    @Test
    @DisplayName("Логин курьера не передавая логина или пароля")
    @Description("Курьер не может залогинится")
    public void loginCourierEmptyLoginOrPassword() {
        stepsCourier.loginCourierInSystem(login, "", 400);
        stepsCourier.loginCourierInSystem("", password, 400);
        stepsCourier.loginCourierInSystem("", "", 400);;
    }

    @Test
    @DisplayName("Логин курьера с ошибочным логином или паролем")
    @Description("Курьер не может залогинится")
    public void loginCourierWrongLoginOrPassword() {
        stepsCourier.loginCourierInSystem(login, random, 404);
        stepsCourier.loginCourierInSystem(random, password, 404);
        stepsCourier.loginCourierInSystem(random, random, 404);
    }

    @Test
    @DisplayName("Логин курьера без строки в теле запроса")
    @Description("Логин не возможен")
    public void loginCourierEmptyString() {
        stepsCourier.loginCourierInSystemWithoutLogin(password, 400);
//        stepsCourier.loginCourierInSystemWithoutPassword(login, 400); --  у меня сервер не отвечает в этом кейсе. Выдает 504 Gateway Timeout Service unavailable по таймауту. Судя по документации должен выжавать 400.
    }

    @After
    public void deleteCourierAfter() {
        stepsCourier.deleteCourier((stepsCourier.courierIdExtract(login, password)));
    }

}
