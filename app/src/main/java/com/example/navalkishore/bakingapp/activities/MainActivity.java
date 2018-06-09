package com.example.navalkishore.bakingapp.activities;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.navalkishore.bakingapp.BakingAppHelperClass;
import com.example.navalkishore.bakingapp.model.MainDish;
import com.example.navalkishore.bakingapp.adapter.MainDishesAdapter;
import com.example.navalkishore.bakingapp.R;
import com.example.navalkishore.bakingapp.rest.ApiClient;
import com.example.navalkishore.bakingapp.rest.ApiInterface;
import com.example.navalkishore.bakingapp.rest.ApiUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements MainDishesAdapter.MainDishesAdapterOnClickHandler {
    @BindView(R.id.pb_loading_data_from_file)
    ProgressBar mProgressBar;
    @BindView(R.id.rv_main_dishes)
    RecyclerView mRecylerView;
    @BindView(R.id.tv_error_message)
    TextView mErrorMessage;
    private MainDishesAdapter mMainDishAdapter;
    private ApiInterface mApiInterface;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        WindowManager windowmanager = (WindowManager) getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
        windowmanager.getDefaultDisplay().getMetrics(displayMetrics);
        int width = Math.round(displayMetrics.widthPixels / displayMetrics.density);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(MainActivity.this,LinearLayoutManager.VERTICAL,false);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3, GridLayoutManager.VERTICAL, false);
        if(width>600)
        {
            mRecylerView.setLayoutManager(gridLayoutManager);
        }
        else
        {
            mRecylerView.setLayoutManager(linearLayoutManager);
        }


        mRecylerView.setHasFixedSize(true);
        mMainDishAdapter= new MainDishesAdapter(this);
        mRecylerView.setAdapter(mMainDishAdapter);
        mApiInterface = ApiUtils.getRecipeService();
        getDataAndFireUpUi();


    }




    private void showErrorMessage() {
        mRecylerView.setVisibility(View.INVISIBLE);
        mErrorMessage.setVisibility(View.VISIBLE);
    }


    private void showMainDishDataView() {
        mErrorMessage.setVisibility(View.INVISIBLE);
        mRecylerView.setVisibility(View.VISIBLE);

    }
    @Override
    public void onClick(MainDish mainDishSelected) {
        Context context = this;
        Class destinationClass = SelectReceipeActivity.class;
        Intent intentToStartDetailActivity = new Intent(context, destinationClass);
        intentToStartDetailActivity.putExtra(BakingAppConstants.IntentConstants.INPUT_DISH_KEY,  mainDishSelected);
        startActivity(intentToStartDetailActivity);

    }

    public void getDataAndFireUpUi()
    {
        mProgressBar.setVisibility(View.VISIBLE);
       mApiInterface.getRecipes().enqueue(new Callback<ArrayList<MainDish>>() {
           @Override
           public void onResponse(Call<ArrayList<MainDish>> call, Response<ArrayList<MainDish>> response) {
               mProgressBar.setVisibility(View.INVISIBLE);
               showMainDishDataView();
               mMainDishAdapter.setMainDishData(response.body());

           }

           @Override
           public void onFailure(Call<ArrayList<MainDish>> call, Throwable t) {
               mProgressBar.setVisibility(View.INVISIBLE);
               showErrorMessage();

           }
       });

    }
}


