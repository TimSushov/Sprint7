package steps;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import resours.*;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;

public class StepsCourier extends RequestSpec {

    @Step("Сourier Id Extract")
    public int courierIdExtract(String login, String password ) {
        CourierData courierData = new CourierData(login, password);

        int coeurierId = given()
                .spec(requestSpec())
                .header("Content-type", "application/json")
                .and()
                .body(courierData)
                .when()
                .post(Urls.COURIER_LOGIN)
                .then().extract().body().path("id");
        return coeurierId;
    }

    @Step("Create courier")
    public void createCourier(String login, String password, String name, int statusCode){
        CourierData courierData = new CourierData(login, password, name);
        Response response =
                given()
                        .spec(requestSpec())
                        .header("Content-type", "application/json")
                        .and()
                        .body(courierData)
                        .when()
                        .post(Urls.COURIER_CREATE);
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
                .spec(requestSpec())
                .header("Content-type", "application/json")
                .and()
                .body(courierData)
                .when()
                .post(Urls.COURIER_CREATE);
    }

    @Step("Create courier without login")
    public void createCourierWithoutLogin(String setPassword, String setName, int statusCode){
        CourierWithoutLoginData courierWithoutLoginData = new CourierWithoutLoginData(setPassword, setName);

        given()
                .spec(requestSpec())
                .header("Content-type", "application/json")
                .and()
                .body(courierWithoutLoginData)
                .when()
                .post(Urls.COURIER_CREATE)
                .then().statusCode(statusCode)
                .and()
                .assertThat().body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }

    @Step("Create courier without password")
    public void createCourierWithoutPassword(String setLogin, String setName, int statusCode){
        CourierWithoutPasswordData courierWithoutPasswordData = new CourierWithoutPasswordData(setLogin, setName);

        given()
                .spec(requestSpec())
                .header("Content-type", "application/json")
                .and()
                .body(courierWithoutPasswordData)
                .when()
                .post(Urls.COURIER_CREATE)
                .then().statusCode(statusCode)
                .and()
                .assertThat().body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }

    @Step("Create courier without name")
    public void createCourierWithoutName(String setLogin, String setPassword, int statusCode){
        CourierWithoutNameData courierWithoutNameData = new CourierWithoutNameData(setLogin, setPassword);

        given()
                .spec(requestSpec())
                .header("Content-type", "application/json")
                .and()
                .body(courierWithoutNameData)
                .when()
                .post(Urls.COURIER_CREATE)
                .then().statusCode(statusCode)
                .and()
                .assertThat().body("ok", equalTo(true));
    }

    @Step("Delete courier")
    public void deleteCourier(int id){
        given()
                .spec(requestSpec())
                .delete(Urls.setCourierDelete(id));
    }

    @Step("Login courier In System")
    public void loginCourierInSystem(String login, String password, int statusCode) {
        CourierData courierData = new CourierData(login, password);
        Response response =
                given()
                        .spec(requestSpec())
                        .header("Content-type", "application/json")
                        .and()
                        .body(courierData)
                        .when()
                        .post(Urls.COURIER_LOGIN);
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
        CourierInSystemWithoutLoginData courierInSystemWithoutLoginData = new CourierInSystemWithoutLoginData(setPassword);

        given()
                .spec(requestSpec())
                .header("Content-type", "application/json")
                .and()
                .body(courierInSystemWithoutLoginData)
                .when()
                .post(Urls.COURIER_LOGIN)
                .then().statusCode(statusCode)
                .and()
                .assertThat().body("message", equalTo("Недостаточно данных для входа"));
    }

    @Step("Login courier In System Without Password")
    public void loginCourierInSystemWithoutPassword(String setLogin, int statusCode) {
        CourierInSystemWithoutPasswordData courierInSystemWithoutPasswordData = new CourierInSystemWithoutPasswordData(setLogin);

        given()
                .spec(requestSpec())
                .header("Content-type", "application/json")
                .and()
                .body(courierInSystemWithoutPasswordData)
                .when()
                .post(Urls.COURIER_LOGIN)
                .then().statusCode(statusCode)
                .and()
                .assertThat().body("message", equalTo("Недостаточно данных для входа"));
    }

}
