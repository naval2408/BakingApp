package com.example.navalkishore.bakingapp;

import android.content.Context;

import com.example.navalkishore.bakingapp.model.Ingredient;
import com.example.navalkishore.bakingapp.model.MainDish;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class BakingAppHelperClass {


    public static String getStringArrayForIngredentsAndSteps(List<Ingredient> ingredientList)
    {
        String ingredentListCombinedString = "";
        for(Ingredient placeholder:ingredientList)
        {
            ingredentListCombinedString = ingredentListCombinedString+placeholder.toString()+"\n";

        }
        ingredentListCombinedString = ingredentListCombinedString.substring(0,ingredentListCombinedString.length()-1);
        return ingredentListCombinedString;


    }
}
