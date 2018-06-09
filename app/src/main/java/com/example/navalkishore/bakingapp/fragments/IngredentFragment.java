package com.example.navalkishore.bakingapp.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.navalkishore.bakingapp.R;
import com.example.navalkishore.bakingapp.activities.BakingAppConstants;

public class IngredentFragment extends android.support.v4.app.Fragment{
    private String mIngredientString;
    public IngredentFragment()
    {



    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        mIngredientString=getArguments().getString(BakingAppConstants.IntentConstants.INGREDENTS_KEY);
        View rootView = inflater.inflate(R.layout.fragment_ingredent_list, container, false);
        TextView mTextViewForIngredents = (TextView) rootView.findViewById(R.id.tv_ingredents_list);
        mTextViewForIngredents.setText(mIngredientString);
        return rootView;
    }
}
