package com.example.navalkishore.bakingapp.rest;

public class ApiUtils {
    public static final String BASE_URL = "https://d17h27t6h515a5.cloudfront.net";

    public static ApiInterface getRecipeService() {
        return ApiClient.getClient(BASE_URL).create(ApiInterface.class);
    }
}
