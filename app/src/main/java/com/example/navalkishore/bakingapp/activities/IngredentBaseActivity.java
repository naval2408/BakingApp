package com.example.navalkishore.bakingapp.activities;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import com.example.navalkishore.bakingapp.R;
import com.example.navalkishore.bakingapp.fragments.IngredentFragment;


public class IngredentBaseActivity extends AppCompatActivity {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.base_ingredent_layout);
        Bundle bundle = new Bundle();
        getSupportActionBar().setTitle("Ingredients");
        bundle.putString(BakingAppConstants.IntentConstants.INGREDENTS_KEY, getIntent().getStringExtra(BakingAppConstants.IntentConstants.INGREDENTS_KEY));
        if (savedInstanceState == null) {
            final FragmentManager fragmentManager = getSupportFragmentManager();
            IngredentFragment ingredentFragment = new IngredentFragment();
            ingredentFragment.setArguments(bundle);
            fragmentManager.beginTransaction()
                    .add(R.id.ingredent_container, ingredentFragment)
                    .commit();


        }
    }
}
