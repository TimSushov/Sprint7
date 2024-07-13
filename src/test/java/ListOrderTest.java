import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;

public class ListOrderTest {

    @Before
    public void setUp() {
        RestAssured.baseURI = "https://qa-scooter.praktikum-services.ru";
    }

    @Test
    @DisplayName("Запрашиваем список заказов")
    @Description("В тело ответа возвращается список заказов")
    public void getOrderList() {
        getListOrder(200);
    }

    @Step("Create Order And Set Color")
    public void getListOrder(int statusCode) {

        given()
                .get("api/v1/orders")
                .then().statusCode(statusCode)
                .and()
                .assertThat().body("pageInfo.limit", equalTo(30))
                .and()
                .assertThat().body("orders.id[29]", notNullValue());
    }
}