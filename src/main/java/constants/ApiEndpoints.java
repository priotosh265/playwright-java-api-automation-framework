package constants;

import config.ConfigManager;

public class ApiEndpoints {

    // Restful Booker
    public static final String RESTFUL_BOOKER_BASE = ConfigManager.getRestfulBookerBaseUrl();
    public static final String PING = RESTFUL_BOOKER_BASE + "/ping";
    public static final String AUTH = RESTFUL_BOOKER_BASE + "/auth";
    public static final String BOOKING = RESTFUL_BOOKER_BASE + "/booking";
    public static final String BOOKING_BY_ID = RESTFUL_BOOKER_BASE + "/booking/%s";

    // Automation Exercise API
    public static final String PRODUCTS_LIST = "/api/productsList";
    public static final String BRANDS_LIST = "/api/brandsList";
    public static final String SEARCH_PRODUCT = "/api/searchProduct";
    public static final String VERIFY_LOGIN = "/api/verifyLogin";
    public static final String CREATE_ACCOUNT = "/api/createAccount";
    public static final String DELETE_ACCOUNT = "/api/deleteAccount";
    public static final String UPDATE_ACCOUNT = "/api/updateAccount";
    public static final String GET_USER_DETAIL_BY_EMAIL = "/api/getUserDetailByEmail";

    private ApiEndpoints() {
        // Prevent instantiation
    }

    public static String getBookingByIdPath(int bookingId) {
        return String.format(BOOKING_BY_ID, bookingId);
    }

    public static String getBookingByIdPath(String bookingId) {
        return String.format(BOOKING_BY_ID, bookingId);
    }
}
