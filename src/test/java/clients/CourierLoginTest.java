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
import static org.junit.Assert.assertNotNull;
import static utils.Utils.randomString;

public class CourierLoginTest {

    private final CourierClient courierClient = new CourierClient();

    private String id;

    @Test
    @DisplayName("Курьер может авторизоваться, успешный запрос возвращает 'id'")
    public void loginWithCorrectData() {
        Courier courier = randomCourier();
        courierClient.create(courier);
        Response loginResponse = courierClient.login(CourierCreds.credsFromCourier(courier));
        id = loginResponse.body().path("id").toString();

        assertEquals("Курьер не залогинен", SC_OK, loginResponse.statusCode());
        assertNotNull(loginResponse.body().path("id"));
    }

    @Test
    @DisplayName("Если нет поля 'login' запрос возвращает ошибку ")
    public void loginWithoutLoginData() {
        Courier courier = randomCourier();
        courierClient.create(courier);
        CourierCreds courierCreds = CourierCreds.credsFromCourier(courier);
        courierCreds.setLogin(null);
        Response loginResponse = courierClient.login(courierCreds);

        assertEquals("Неправильный код ответа", SC_BAD_REQUEST, loginResponse.statusCode());
        assertEquals("Недостаточно данных для входа", loginResponse.body().path("message"));
    }

    @Test
    @DisplayName("Если нет поля 'password' запрос возвращает ошибку ")
    public void loginWithoutPasswordData() {
        Courier courier = randomCourier();
        courierClient.create(courier);
        CourierCreds courierCreds = CourierCreds.credsFromCourier(courier);
        courierCreds.setPassword("");
        Response loginResponse = courierClient.login(courierCreds);

        assertEquals("Неправильный код ответа", SC_BAD_REQUEST, loginResponse.statusCode());
        assertEquals("Недостаточно данных для входа", loginResponse.body().path("message"));
    }

    @Test
    @DisplayName("Запрос с неправильным логином выдает ошибку")
    public void loginIncorrectLoginData() {
        Courier courier = randomCourier();
        courierClient.create(courier);
        CourierCreds courierCreds = CourierCreds.credsFromCourier(courier);
        courierCreds.setLogin(randomString(10));
        Response loginResponse = courierClient.login(courierCreds);

        assertEquals("Неправильный код ответа", SC_NOT_FOUND, loginResponse.statusCode());
        assertEquals("Учетная запись не найдена", loginResponse.body().path("message"));
    }

    @Test
    @DisplayName("Запрос с неправильным паролем выдает ошибку")
    public void loginIncorrectPassword() {
        Courier courier = randomCourier();
        courierClient.create(courier);
        CourierCreds courierCreds = CourierCreds.credsFromCourier(courier);
        courierCreds.setPassword(randomString(10));
        Response loginResponse = courierClient.login(courierCreds);

        assertEquals("Неправильный код ответа", SC_NOT_FOUND, loginResponse.statusCode());
        assertEquals("Учетная запись не найдена", loginResponse.body().path("message"));
    }

    @After
    public void tearDown() {
        if (id != null) {
            courierClient.delete(Integer.parseInt(id));
        }
    }
}