package clients;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import models.Courier;
import models.CourierCreds;
import org.junit.After;
import org.junit.Test;

import static generators.CourierGenerator.randomCourier;
import static org.apache.http.HttpStatus.*;
import static org.junit.Assert.assertEquals;
import static utils.Utils.randomString;

public class CourierCreationTest {

    private final CourierClient courierClient = new CourierClient();

    private String id;

    @Test
    @DisplayName("Курьера можно создать, код ответа 200, запрос возвращает 'ok: true'")
    public void checkCreateCourier() {

        Courier courier = randomCourier();
        Response response = courierClient.create(courier);
        Response loginResponse = courierClient.login(CourierCreds.credsFromCourier(courier));

        assertEquals("Неверный статус код", SC_CREATED, response.statusCode());
        assertEquals(true, response.body().path("ok"));
        id = loginResponse.path("id").toString();
        assertEquals("Курьер не залогинен", SC_OK, loginResponse.statusCode());

    }

    @Test
    @DisplayName("Нельзя создать двух одинаковых курьеров, код ответа 409, 'message': 'Этот логин уже используется. Попробуйте другой.'")
    public void checkCannotCreateCourierWithSameLogin() {
        Courier courier = randomCourier();
        courierClient.create(courier);

        Courier sameLoginCourier = new Courier(courier.getLogin(), randomString(8), randomString(8));
        Response finalResponse = courierClient.create(sameLoginCourier);

        assertEquals("Код не соотвествует ожидаемому 409", SC_CONFLICT, finalResponse.statusCode());
        assertEquals("Этот логин уже используется. Попробуйте другой.", finalResponse.body().path("message"));
    }

    @Test
    @DisplayName("Нельзя создать курьера без логина, код ответа 400, 'message':'Недостаточно данных для создания учетной записи'")
    public void checkCannotCreateCourierWithoutLogin() {
        Courier courier = randomCourier();
        courier.setLogin(null);
        Response response = courierClient.create(courier);

        assertEquals("Код не соотвествует ожидаемому 400", SC_BAD_REQUEST, response.statusCode());
        assertEquals("Недостаточно данных для создания учетной записи", response.body().path("message"));
    }

    @Test
    @DisplayName("Нельзя создать курьера без пароля, код ответа 400, 'message':'Недостаточно данных для создания учетной записи'")
    public void checkCannotCreateCourierWithoutPassword() {
        Courier courier = randomCourier();
        courier.setPassword(null);
        Response response = courierClient.create(courier);

        assertEquals("Код не соотвествует ожидаемому 400", SC_BAD_REQUEST, response.statusCode());
        assertEquals("Недостаточно данных для создания учетной записи", response.body().path("message"));
    }

    @After
    public void tearDown() {
        if (id != null) {
            courierClient.delete(Integer.parseInt(id));
        }
    }
}