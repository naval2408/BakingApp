package com.example.navalkishore.bakingapp.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.navalkishore.bakingapp.BakingAppHelperClass;
import com.example.navalkishore.bakingapp.BakingAppWidgetProvider;
import com.example.navalkishore.bakingapp.adapter.SelectReceipesAdapter;
import com.example.navalkishore.bakingapp.fragments.DetailStepsFragment;
import com.example.navalkishore.bakingapp.fragments.IngredentFragment;
import com.example.navalkishore.bakingapp.model.MainDish;
import com.example.navalkishore.bakingapp.model.Steps;
import com.example.navalkishore.bakingapp.R;
import com.example.navalkishore.bakingapp.fragments.SelectReceipsFragment;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SelectReceipeActivity extends AppCompatActivity implements SelectReceipesAdapter.SelectStepsAdapterOnClickHandler{

    MainDish inputMainDishObject;
    @BindView(R.id.card_view_ingredent_label)
    CardView mCardViewForIngredentLabel;
    List<Steps> stepsArrayList;
    Bundle bundleForParsable;
    boolean isTablet;



    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.base_steps_layout);
        ButterKnife.bind(this);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        WindowManager windowmanager = (WindowManager) getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
        windowmanager.getDefaultDisplay().getMetrics(displayMetrics);
        final int width = Math.round(displayMetrics.widthPixels / displayMetrics.density);
        if(width>600)
        {
            isTablet=true;
        }
        else
        {
            isTablet=false;
        }
        Intent intentThatStartedThisActivity = getIntent();
        if (intentThatStartedThisActivity != null) {
            if (intentThatStartedThisActivity.hasExtra(BakingAppConstants.IntentConstants.INPUT_DISH_KEY)) {
                inputMainDishObject = (MainDish) intentThatStartedThisActivity.getSerializableExtra(BakingAppConstants.IntentConstants.INPUT_DISH_KEY);

            }
        }
        final String stringRepresentataionForIgredents =BakingAppHelperClass.getStringArrayForIngredentsAndSteps(inputMainDishObject.getIngredients());
        BakingAppWidgetProvider.setIngredentList(stringRepresentataionForIgredents,inputMainDishObject.getName());

        if(isTablet)
        {
            Bundle bundle = new Bundle();
            bundle.putString(BakingAppConstants.IntentConstants.INGREDENTS_KEY,stringRepresentataionForIgredents);
            final FragmentManager fragmentManager = getSupportFragmentManager();
            IngredentFragment ingredentFragment = new IngredentFragment();
            ingredentFragment.setArguments(bundle);
            fragmentManager.beginTransaction()
                    .replace(R.id.ingredent_container, ingredentFragment)
                    .commit();

        }


        mCardViewForIngredentLabel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!isTablet) {
                    Intent intent = new Intent(SelectReceipeActivity.this, IngredentBaseActivity.class);
                    intent.putExtra(BakingAppConstants.IntentConstants.INGREDENTS_KEY, stringRepresentataionForIgredents);
                    startActivity(intent);
                }
                else {
                    Bundle bundle = new Bundle();
                    bundle.putString(BakingAppConstants.IntentConstants.INGREDENTS_KEY,stringRepresentataionForIgredents);
                    final FragmentManager fragmentManager = getSupportFragmentManager();
                    IngredentFragment ingredentFragment = new IngredentFragment();
                    ingredentFragment.setArguments(bundle);
                    fragmentManager.beginTransaction()
                            .replace(R.id.ingredent_container, ingredentFragment)
                            .commit();

                }
            }
        });

        Bundle bundle = new Bundle();
        bundle.putString(BakingAppConstants.IntentConstants.RECEIPE_LABEL_KEY,inputMainDishObject.getName());
        stepsArrayList = inputMainDishObject.getSteps();
        bundleForParsable=new Bundle();
        for (Steps steps : stepsArrayList) {
            bundleForParsable.putSerializable(BakingAppConstants.IntentConstants.STEPS_KEY + steps.getId(), steps);
        }
        bundle.putBundle(BakingAppConstants.IntentConstants.PARCABLE_BUNDLE_FOR_STEPS, bundleForParsable);

        if (savedInstanceState == null) {

                final FragmentManager fragmentManager = getSupportFragmentManager();
                SelectReceipsFragment selectReceipsFragment = new SelectReceipsFragment();
                selectReceipsFragment.setArguments(bundle);
                fragmentManager.beginTransaction()
                        .add(R.id.steps_container, selectReceipsFragment)
                        .commit();
        }
    }


    @Override
    public void onClick(Steps stepSelected) {
        if (!isTablet) {
            Context context = this;
            Class destinationClass = DetailStepsActivity.class;
            Intent intentToStartDetailActivity = new Intent(context, destinationClass);
            Bundle getParsableBundle = bundleForParsable;
            intentToStartDetailActivity.putExtra(BakingAppConstants.IntentConstants.PARCABLE_BUNDLE_FOR_STEPS, getParsableBundle);
            intentToStartDetailActivity.putExtra(BakingAppConstants.IntentConstants.CURRENT_POSITION_OF_STEPS, stepsArrayList.indexOf(stepSelected));
            intentToStartDetailActivity.putExtra(BakingAppConstants.IntentConstants.RECEIPE_LABEL_KEY, inputMainDishObject.getName());
            startActivity(intentToStartDetailActivity);

        }
        else {
            Bundle masterBundel = new Bundle();
            Bundle getParsableBundle = bundleForParsable;
            masterBundel.putBundle(BakingAppConstants.IntentConstants.PARCABLE_BUNDLE_FOR_STEPS,getParsableBundle);
            masterBundel.putInt(BakingAppConstants.IntentConstants.CURRENT_POSITION_OF_STEPS,stepsArrayList.indexOf(stepSelected));
            masterBundel.putString(BakingAppConstants.IntentConstants.RECEIPE_LABEL_KEY, inputMainDishObject.getName());
            final FragmentManager fragmentManager = getSupportFragmentManager();
            DetailStepsFragment detailStepsFragment = new DetailStepsFragment();
            detailStepsFragment.setArguments(masterBundel);
            fragmentManager.beginTransaction()
                    .replace(R.id.ingredent_container, detailStepsFragment)
                    .commit();

        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.add_to_widget, menu);
        getSupportActionBar().setTitle(inputMainDishObject.getName());
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.item_add_to_widget)
        {
            BakingAppWidgetProvider.updateMyWidgets(this);
            Toast.makeText(this,"Added to the widget",Toast.LENGTH_SHORT).show();

        }
        return true;
    }
}
