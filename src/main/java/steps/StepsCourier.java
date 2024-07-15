package steps;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import resours.CourierData;
import resours.Urls;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;

public class StepsCourier extends Urls {

    @Step("Сourier Id Extract")
    public int courierIdExtract(String login, String password ) {
        CourierData courierData = new CourierData(login, password);
        int coeurierId = given()
                .header("Content-type", "application/json")
                .and()
                .body(courierData)
                .when()
                .post(COURIER_LOGIN)
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
                        .post(COURIER_CREATE);
        response.then().statusCode(statusCode);
        if(statusCode == 201) {
            response.then().assertThat().body("ok", equalTo(true));
        } else if (statusCode == 400) {
            response.then().assertThat().body("message", equalTo("Недостаточно данных для создания учетной записи"));
        } else if (statusCode == 409) {
            response.then().assertThat().body("message", equalTo("Этот логин уже используется. Попробуйте другой."));
        }
    }

    @Step("Create courier")
    public void createCourierWithName(String login, String password, String name){
        CourierData courierData = new CourierData(login, password, name);

        given()
                .header("Content-type", "application/json")
                .and()
                .body(courierData)
                .when()
                .post(COURIER_CREATE);
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
                .post(COURIER_CREATE)
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
                .post(COURIER_CREATE)
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
                .post(COURIER_CREATE)
                .then().statusCode(statusCode)
                .and()
                .assertThat().body("ok", equalTo(true));
    }

    @Step("Delete courier")
    public void deleteCourier(int id){
        given()
                .delete(setCourierDelete(id));
    }

    @Step("Login courier In System")
    public void loginCourierInSystem(String login, String password, int statusCode) {
        CourierData courierData = new CourierData(login, password);
        Response response =
                given()
                        .header("Content-type", "application/json")
                        .and()
                        .body(courierData)
                        .when()
                        .post(COURIER_LOGIN);
        response.then().statusCode(statusCode);
        if(statusCode == 200) {
            response.then().assertThat().body("id", notNullValue());
        } else if (statusCode == 400) {
            response.then().assertThat().body("message", equalTo("Недостаточно данных для входа"));
        } else if (statusCode == 404) {
            response.then().assertThat().body("message", equalTo("Учетная запись не найдена"));
        }
    }

    @Step("Login courier In System Without Login")
    public void loginCourierInSystemWithoutLogin(String setPassword, int statusCode) {
        String json ="{\n" +
                "    \"password\": \""+ setPassword +"\"\n" +
                "}";
        given()
                .header("Content-type", "application/json")
                .and()
                .body(json)
                .when()
                .post(COURIER_LOGIN)
                .then().statusCode(statusCode)
                .and()
                .assertThat().body("message", equalTo("Недостаточно данных для входа"));
    }

    @Step("Login courier In System Without Password")
    public void loginCourierInSystemWithoutPassword(String setLogin, int statusCode) {
        String json ="{\n" +
                "    \"login\": \""+ setLogin +"\"\n" +
                "}";
        given()
                .header("Content-type", "application/json")
                .and()
                .body(json)
                .when()
                .post(COURIER_LOGIN)
                .then().statusCode(statusCode)
                .and()
                .assertThat().body("message", equalTo("Недостаточно данных для входа"));
    }

}
