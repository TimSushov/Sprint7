package resours;

public class Urls {

    public static final String BASE_URI = "https://qa-scooter.praktikum-services.ru";
    public static final String COURIER_LOGIN = "/api/v1/courier/login";
    public static final String COURIER_CREATE = "/api/v1/courier";
    private static final String COURIER_DELETE = "/api/v1/courier/";
    public static final String CREATE_ORDER_OR_GET_LIST_ORDERS = "api/v1/orders";

    public static String setCourierDelete(int id) {
        return COURIER_DELETE + id;
    }


}
