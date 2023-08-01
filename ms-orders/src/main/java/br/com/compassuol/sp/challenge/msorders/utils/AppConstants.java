package br.com.compassuol.sp.challenge.msorders.utils;

public class AppConstants {
    public static final String DEFAULT_PAGE_NUMBER = "0";
    public static final String DEFAULT_PAGE_SIZE = "10";
    public static final String DEFAULT_SORT_BY = "id";
    public static final String DEFAULT_SORT_DIR = "asc";
    public enum OrderStatus {
        PENDING, APPROVED, DELIVERED, CANCELED
    }
}
