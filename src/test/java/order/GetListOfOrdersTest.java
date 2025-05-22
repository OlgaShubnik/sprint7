package order;

import io.restassured.response.Response;
import org.junit.Test;

import static org.apache.http.HttpStatus.SC_OK;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class GetListOfOrdersTest {

    private final OrderClient orderClient = new OrderClient();

    @Test
    public void checkGetListOfOrders() {
        Response response = orderClient.getOrdersList();

        assertEquals("Код не соотвествует ожидаемому 200", SC_OK, response.statusCode());
        assertNotNull(response.body().path("orders"));
    }
}