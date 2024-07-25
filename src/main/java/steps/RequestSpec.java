package steps;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.ErrorLoggingFilter;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.specification.RequestSpecification;
import resours.Urls;

public class RequestSpec {

    public RequestSpecification requestSpec(){
        RequestSpecification requestSpec =   new RequestSpecBuilder()
                .setBaseUri(Urls.BASE_URL)
                .addFilter(new RequestLoggingFilter())
                .addFilter(new ResponseLoggingFilter())
                .addFilter(new ErrorLoggingFilter())
                .build();

        return requestSpec;
    }

//    @Before
//    public void setUrl() {
//        RestAssured.baseURI = Urls.BASE_URL;
//    }

}
