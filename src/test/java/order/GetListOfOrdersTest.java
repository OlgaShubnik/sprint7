package order;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class GetListOfOrdersTest {

    private static final String BASE_URL = "https://qa-scooter.praktikum-services.ru";

    private final OrderClient orderClient = new OrderClient();

    @Before
    public void setUp() {
        RestAssured.baseURI = BASE_URL;
    }

    @Test
    public void checkGetListOfOrders() {
        Response response = orderClient.getOrdersList();

        assertEquals("Код не соотвествует ожидаемому 200", 200, response.statusCode());
        assertNotNull(response.body().path("orders"));
    }
}