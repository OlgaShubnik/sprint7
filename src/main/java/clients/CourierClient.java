package clients;

import io.restassured.response.Response;
import models.Courier;
import models.CourierCreds;
import utils.BaseClient;

import static io.restassured.RestAssured.given;

public class CourierClient extends BaseClient {

    private static final String API_V1_COURIER = "api/v1/courier";
    private static final String API_V1_COURIER_LOGIN = "api/v1/courier/login";

    public Response create(Courier courier) {
        return given()
                .spec(getBaseSpec())
                .and()
                .body(courier)
                .when()
                .post(API_V1_COURIER);
    }

    public Response login(CourierCreds creds) {
        return given()
                .spec(getBaseSpec())
                .and()
                .body(creds)
                .when()
                .post(API_V1_COURIER_LOGIN);
    }

    public Response delete(int id) {
        return given()
                .spec(getBaseSpec())
                .when()
                .delete(API_V1_COURIER + "/" + id);
    }
}
