package generators;

import models.Order;

public class OrderGenerator {

    public static Order getOrderGreyColor() {
        return new Order(
                "Николай",
                "Петров",
                "Ленина, 6",
                "Красноармейская",
                "+79155033323",
                "3",
                "2025-06-20",
                "Комментарий",
                new String[]{"GREY"});
    }

    public static Order getOrderBlackColor() {
        return new Order(
                "Петр",
                "Петров",
                "Мира, 5",
                "Беговая",
                "+79155033321",
                "2",
                "2025-06-20",
                "Комментарий",
                new String[]{"BLACK"});
    }

    public static Order getOrderTwoColors() {
        return new Order(
                "Роман",
                "Романов",
                "Щетинина, 33",
                "Беговая",
                "+79195033321",
                "2",
                "2025-06-20",
                "",
                new String[]{"GREY", "BLACK"});
    }

    public static Order getOrderNoColors() {
        return new Order(
                "Сергей",
                "Сергеев",
                "Тверская, 5",
                "Сокольническая",
                "+79195033001",
                "2",
                "2025-06-20",
                "",
                null);
    }
}