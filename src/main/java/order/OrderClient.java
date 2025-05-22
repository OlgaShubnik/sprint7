package order;

import io.restassured.response.Response;
import models.Order;
import utils.BaseClient;

import static io.restassured.RestAssured.given;

public class OrderClient extends BaseClient {
    private static final String ORDER_URL = "api/v1/orders";

    //"Создание заказа"
    public Response create(Order order) {
        return given()
                .spec(getBaseSpec())
                .and()
                .body(order)
                .when()
                .post(ORDER_URL);
    }

    //"Получение списка заказов курьера"
    public Response getOrdersList() {
        return given()
                .spec(getBaseSpec())
                .when()
                .get(ORDER_URL);
    }

    //"Отмена заказа"
    public Response cancelOrder(int trackId) {
        return given()
                .spec(getBaseSpec())
                .and()
                .queryParam("track", trackId)
                .when()
                .put(ORDER_URL + "/cancel");
    }
}