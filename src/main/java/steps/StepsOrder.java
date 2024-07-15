package steps;

import io.qameta.allure.Step;
import resours.OrderData;
import resours.Urls;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;

public class StepsOrder extends Urls {
    @Step("Create Order And Set Color")
    public void getListOrder(int statusCode) {

        given()
                .get(CREATE_ORDER_OR_GET_LIST_ORDERS)
                .then().statusCode(statusCode)
                .and()
                .assertThat().body("pageInfo.limit", equalTo(30))
                .and()
                .assertThat().body("orders.id[29]", notNullValue());
    }

    @Step("Create Order And Set Color")
    public void createOrderSetColor(String firstName, String lastName, String address, int metroStation, String phone, int rentTime, String deliveryDate, String comment, List<String> color, int statusCode) {
        OrderData orderData = new OrderData(firstName, lastName, address, metroStation, phone, rentTime, deliveryDate, comment, color);

        given()
                .header("Content-type", "application/json")
                .and()
                .body(orderData)
                .when()
                .post(CREATE_ORDER_OR_GET_LIST_ORDERS)
                .then().statusCode(statusCode)
                .and()
                .assertThat().body("track", notNullValue());
    }

}
