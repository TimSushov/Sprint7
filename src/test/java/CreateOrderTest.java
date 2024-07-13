import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.notNullValue;

@RunWith(Parameterized.class)
public class CreateOrderTest {
    private String color;

    public CreateOrderTest(String color) {
        this.color = color;
    }

    @Parameterized.Parameters ()
    public static Object[][] getOrderData() {
        return new Object[][]{
                {"GREY"},
                {"GREY"},
                {"GREY \", \" BLACK"},
                {" "},
        };
    }

    @Before
    public void setUp() {
        RestAssured.baseURI = "https://qa-scooter.praktikum-services.ru";
    }

    @Test
    @DisplayName("Создать заказ с разным цветом самоката")
    @Description("Тестируем с серым, черным, без цвета, оба цвета")
    public void createOrderWithAnyColor() {
        createOrderSetColor(color, 201);
    }

    @Step("Create Order And Set Color")
    public void createOrderSetColor(String color, int statusCode) {
        String json ="{\n" +
                "    \"firstName\": \"Naruto\",\n" +
                "    \"lastName\": \"Uchiha\",\n" +
                "    \"address\": \"Konoha, 142 apt.\",\n" +
                "    \"metroStation\": 4,\n" +
                "    \"phone\": \"+7 800 355 35 35\",\n" +
                "    \"rentTime\": 5,\n" +
                "    \"deliveryDate\": \"2020-06-06\",\n" +
                "    \"comment\": \"Saske, come back to Konoha\",\n" +
                "    \"color\": [\n" +
                "        \""+ color +"\"\n" +
                "    ]\n" +
                "}";
        given()
                .header("Content-type", "application/json")
                .and()
                .body(json)
                .when()
                .post("/api/v1/orders")
                .then().statusCode(statusCode)
                .and()
                .assertThat().body("track", notNullValue());
    }
}
