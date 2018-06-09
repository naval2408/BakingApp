package com.example.navalkishore.bakingapp.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.navalkishore.bakingapp.activities.BakingAppConstants;
import com.example.navalkishore.bakingapp.activities.DetailStepsActivity;
import com.example.navalkishore.bakingapp.activities.SelectReceipeActivity;
import com.example.navalkishore.bakingapp.model.Steps;
import com.example.navalkishore.bakingapp.R;
import com.example.navalkishore.bakingapp.adapter.SelectReceipesAdapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;


public class SelectReceipsFragment extends Fragment {
    private String mReceipeLabel;
    private ArrayList<Steps> mListOfSteps=new ArrayList<Steps>();
    RecyclerView mRecylerViewForSteps;

    public SelectReceipsFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //mIngredientString=getArguments().getString(BakingAppConstants.IntentConstants.INGREDENTS_KEY);
        mReceipeLabel=getArguments().getString(BakingAppConstants.IntentConstants.RECEIPE_LABEL_KEY);
        Bundle getParsableKeys = getArguments().getBundle(BakingAppConstants.IntentConstants.PARCABLE_BUNDLE_FOR_STEPS);
        for(String key: getParsableKeys.keySet())
        {
            mListOfSteps.add((Steps) getParsableKeys.getSerializable(key));
        }
        Collections.sort(mListOfSteps, new Comparator<Steps>() {
            @Override
            public int compare(Steps o1, Steps o2) {
                if(o1.getId()>o2.getId())
                {
                    return 1;
                }
                else if (o2.getId()>o1.getId())
                {
                    return -1;
                }
                else
                    return 0;
            }
        });



        View rootView = inflater.inflate(R.layout.fragment_select_receipe, container, false);
        mRecylerViewForSteps = (RecyclerView)  rootView.findViewById(R.id.rv_select_receipes);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
        mRecylerViewForSteps.setLayoutManager(linearLayoutManager);
        mRecylerViewForSteps.setHasFixedSize(true);
        SelectReceipesAdapter selectReceipesAdapter = new SelectReceipesAdapter((SelectReceipeActivity) getContext());
        mRecylerViewForSteps.setAdapter(selectReceipesAdapter);
        selectReceipesAdapter.setReceipsData(mListOfSteps);
        return rootView;


    }

}
