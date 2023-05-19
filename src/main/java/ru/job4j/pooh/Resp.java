package ru.job4j.pooh;

public class Resp {
    private final String text;
    private final String status;

    public Resp(String text, String status) {
        this.text = text;
        this.status = status;
    }

    /*status  - это HTTP response status codes. И, для примера,
     что если запрос прошел, то статус = 200, а если нет данных, то статус = 204.*/

    public String text() {
        return text;
    }

    public String status() {
        return status;
    }
}