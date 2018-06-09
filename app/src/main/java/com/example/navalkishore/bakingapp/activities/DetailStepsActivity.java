package com.example.navalkishore.bakingapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.example.navalkishore.bakingapp.fragments.DetailStepsFragment;
import com.example.navalkishore.bakingapp.model.Steps;
import com.example.navalkishore.bakingapp.R;
import com.example.navalkishore.bakingapp.fragments.SelectReceipsFragment;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import butterknife.ButterKnife;

public class DetailStepsActivity extends AppCompatActivity implements DetailStepsFragment.ButtonFunctionality{

    String mReceipeLabel;
    ArrayList<Steps> mSteps;
    int currentPositionOfStep;
    Bundle parsableBundle;

    protected void onCreate(Bundle savedInstanceState) {
         parsableBundle = new Bundle();
         mSteps = new ArrayList<>();
        Intent intentThatStartedThisActivity = getIntent();
        if (intentThatStartedThisActivity != null) {
            if (intentThatStartedThisActivity.hasExtra(BakingAppConstants.IntentConstants.CURRENT_POSITION_OF_STEPS)) {
                currentPositionOfStep=intentThatStartedThisActivity.getIntExtra(BakingAppConstants.IntentConstants.CURRENT_POSITION_OF_STEPS,-1);
                }

            if (intentThatStartedThisActivity.hasExtra(BakingAppConstants.IntentConstants.PARCABLE_BUNDLE_FOR_STEPS)) {
                parsableBundle=intentThatStartedThisActivity.getBundleExtra(BakingAppConstants.IntentConstants.PARCABLE_BUNDLE_FOR_STEPS);
            }

            if (intentThatStartedThisActivity.hasExtra(BakingAppConstants.IntentConstants.RECEIPE_LABEL_KEY)) {
                    mReceipeLabel = intentThatStartedThisActivity.getStringExtra(BakingAppConstants.IntentConstants.RECEIPE_LABEL_KEY);

                }
                android.support.v7.app.ActionBar ab = getSupportActionBar();
                ab.setTitle(mReceipeLabel);

            }
        if(this.getResources().getConfiguration().orientation==2)
        {
            getSupportActionBar().hide();
        }

        for(String key: parsableBundle.keySet())
        {
            mSteps.add((Steps) parsableBundle.getSerializable(key));
        }
        Collections.sort(mSteps, new Comparator<Steps>() {
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


        Bundle bundle = new Bundle();
            bundle.putBundle(BakingAppConstants.IntentConstants.PARCABLE_BUNDLE_FOR_STEPS,parsableBundle);
            bundle.putString(BakingAppConstants.IntentConstants.RECEIPE_LABEL_KEY,mReceipeLabel);
            bundle.putInt(BakingAppConstants.IntentConstants.CURRENT_POSITION_OF_STEPS,currentPositionOfStep);
            super.onCreate(savedInstanceState);
            setContentView(R.layout.detail_steps_layout);
            ButterKnife.bind(this);

            if (savedInstanceState == null) {

                DetailStepsFragment detailStepsFragment= new DetailStepsFragment();
                detailStepsFragment.setArguments(bundle);
                 FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction()
                 .add(R.id.detail_recipes_container, detailStepsFragment)
                .commit();


            }
        }

    @Override
    public void onPreviousButtonClicked(Steps prevStep, int currentPositionForSteps) {
        Bundle bundle = new Bundle();
        bundle.putBundle(BakingAppConstants.IntentConstants.PARCABLE_BUNDLE_FOR_STEPS,parsableBundle);
        bundle.putString(BakingAppConstants.IntentConstants.RECEIPE_LABEL_KEY,mReceipeLabel);
        bundle.putInt(BakingAppConstants.IntentConstants.CURRENT_POSITION_OF_STEPS,currentPositionForSteps-1);
        DetailStepsFragment detailStepsFragment= new DetailStepsFragment();
        detailStepsFragment.setArguments(bundle);
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.detail_recipes_container, detailStepsFragment)
                .commit();


    }

    @Override
    public void onNextButtonClicked(Steps nextStep, int currentPositionForSteps) {
        Bundle bundle = new Bundle();
        bundle.putBundle(BakingAppConstants.IntentConstants.PARCABLE_BUNDLE_FOR_STEPS,parsableBundle);
        bundle.putString(BakingAppConstants.IntentConstants.RECEIPE_LABEL_KEY,mReceipeLabel);
        bundle.putInt(BakingAppConstants.IntentConstants.CURRENT_POSITION_OF_STEPS,currentPositionForSteps+1);
        DetailStepsFragment detailStepsFragment= new DetailStepsFragment();
        detailStepsFragment.setArguments(bundle);
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.detail_recipes_container, detailStepsFragment)
                .commit();
    }
}

