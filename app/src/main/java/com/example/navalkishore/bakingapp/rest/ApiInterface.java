package com.example.navalkishore.bakingapp.rest;

import com.example.navalkishore.bakingapp.model.MainDish;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiInterface {
    @GET("/topher/2017/May/59121517_baking/baking.json")
    Call<ArrayList<MainDish>> getRecipes();
    }

