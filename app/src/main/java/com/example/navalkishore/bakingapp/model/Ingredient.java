package com.example.navalkishore.bakingapp.model;

import java.io.Serializable;

public class Ingredient implements Serializable{
    private Double quantity;

    public Double getQuantity() {
        return quantity;
    }

    public String getMeasure() {
        return measure;
    }

    public String getIngredient() {
        return ingredient;
    }

    private String measure;
    private String ingredient;

    @Override
    public String toString() {
        return quantity+" "+getMappedQuantity(measure)+" of "+ingredient;
    }

    public String getMappedQuantity(String quantity)
    {
        switch (quantity)
        {
            case "CUP":
                return "cup";

            case "TBLSP":
                return "tablespoon";

            case "TSP":
                return "teaspoon";

            case "K":
                return "kilo";

            case "G":
                return "gram";

            case "OZ":
                return "ounce";

            case "UNIT":
                return "unit";


            default: return quantity;

        }

    }
}
