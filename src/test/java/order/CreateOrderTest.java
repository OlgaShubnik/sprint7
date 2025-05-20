package order;

import generators.OrderGenerator;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import models.Order;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(Parameterized.class)
public class CreateOrderTest {

    private static final String BASE_URL = "https://qa-scooter.praktikum-services.ru";

    private final OrderClient orderClient = new OrderClient();

    private final Order order;

    private Integer orderTrack;

    public CreateOrderTest(Order order) {
        this.order = order;
    }

    @Parameterized.Parameters
    public static Object[][] getTestData() {
        return new Object[][]{
                {OrderGenerator.getOrderBlackColor()},
                {OrderGenerator.getOrderGreyColor()},
                {OrderGenerator.getOrderTwoColors()},
                {OrderGenerator.getOrderNoColors()}
        };
    }

    @Before
    public void setUp() {
        RestAssured.baseURI = BASE_URL;
    }


    @Test
    @DisplayName("Тест на создание заказа, код ответа 201, в ответе есть track number")
    public void checkCreateOrder() {
        Response response = orderClient.create(order);
        orderTrack = response.body().path("track");

        assertEquals("Код не соотвествует ожидаемому 201", 201, response.statusCode());
        assertNotNull(response.body().path("track"));
    }

    @After
    public void tearDown() {
        if (orderTrack != null) {
            orderClient.cancelOrder(orderTrack);
        }
    }
}