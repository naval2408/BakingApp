package com.example.navalkishore.bakingapp.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.navalkishore.bakingapp.R;
import com.example.navalkishore.bakingapp.activities.BakingAppConstants;
import com.example.navalkishore.bakingapp.activities.DetailStepsActivity;
import com.example.navalkishore.bakingapp.model.Steps;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class DetailStepsFragment extends Fragment {
    Steps currentSteps;
    private static SimpleExoPlayer mExoPlayer;
    SimpleExoPlayerView mSimpleExoPlayerView;
    TextView mStepDetail;
    Button mPreviousButton;
    Button mNextButton;
    private boolean mediaPlaying = true;
    private boolean mediaPaused = false;
    private boolean exoPlayerInitilized;
    private ProgressBar mProgressBarForExoPlayer;
    private long currentPosition;
    private Bundle parsableBundle;
    private ArrayList<Steps> mStepsList;
    private int currentPositionOfStep;
    private ButtonFunctionality buttonFunctionality;
    boolean isTablet;




    public DetailStepsFragment()
    {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        int orientation = getActivity().getResources().getConfiguration().orientation;
        DisplayMetrics displayMetrics = new DisplayMetrics();
        WindowManager windowmanager = (WindowManager) getActivity().getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
        windowmanager.getDefaultDisplay().getMetrics(displayMetrics);
        int width = Math.round(displayMetrics.widthPixels / displayMetrics.density);
        if(width>600)
        {
            isTablet=true;
        }
        else
        {
            isTablet=false;
        }
        mStepsList=new ArrayList<>();
         super.onCreateView(inflater, container, savedInstanceState);
        if(savedInstanceState != null) {
            currentPosition = savedInstanceState.getLong(BakingAppConstants.EXO_POSITION_KEY);
            mediaPlaying = savedInstanceState.getBoolean(BakingAppConstants.EXO_PAUSED_KEY);
        }
        parsableBundle = getArguments().getBundle(BakingAppConstants.IntentConstants.PARCABLE_BUNDLE_FOR_STEPS);
        currentPositionOfStep = getArguments().getInt(BakingAppConstants.IntentConstants.CURRENT_POSITION_OF_STEPS);
        for(String key: parsableBundle.keySet())
        {
            mStepsList.add((Steps) parsableBundle.getSerializable(key));
        }
        Collections.sort(mStepsList, new Comparator<Steps>() {
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
        currentSteps = mStepsList.get(currentPositionOfStep);
        View rootView = inflater.inflate(R.layout.fragment_steps_detail, container, false);
        mStepDetail=(TextView) rootView.findViewById(R.id.tv_steps_detail_label);
        mStepDetail.setText(currentSteps.getDescription());
        mPreviousButton = (Button) rootView.findViewById(R.id.button_previous);
        if(!isTablet) {
            mNextButton = (Button) rootView.findViewById(R.id.button_next);
            initButtons();
        }
        mSimpleExoPlayerView = (SimpleExoPlayerView) rootView.findViewById(R.id.exoplayerView);
         mProgressBarForExoPlayer=(ProgressBar)rootView.findViewById(R.id.progressBarForExoPlayer);
         mSimpleExoPlayerView.setDefaultArtwork(BitmapFactory.decodeResource
                     (getResources(), R.drawable.q_mark));
         if(currentSteps.getVideoURL()!=null&&(!currentSteps.getVideoURL().isEmpty()))
         {
             Uri videoURI = Uri.parse(currentSteps.getVideoURL());
             initializePlayer(videoURI);


         }
         else {
             mSimpleExoPlayerView.setVisibility(View.GONE);
             mProgressBarForExoPlayer.setVisibility(View.GONE);

         }

        if (mExoPlayer != null) {
            mExoPlayer.addListener(new ExoPlayer.EventListener() {
                @Override
                public void onTimelineChanged(Timeline timeline, Object manifest) {

                }

                @Override
                public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {

                }

                @Override
                public void onLoadingChanged(boolean isLoading) {

                }

                @Override
                public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
                    if (playbackState == ExoPlayer.STATE_BUFFERING) {
                        mProgressBarForExoPlayer.setVisibility(View.VISIBLE);
                    } else {
                         mProgressBarForExoPlayer.setVisibility(View.INVISIBLE);
                    }
                    if (playWhenReady && playbackState == mExoPlayer.STATE_READY) {

                        mediaPlaying = true;
                    } else if (playWhenReady) {

                    } else {
                        if (!mediaPaused) {
                            mediaPlaying = !mediaPlaying;
                        }
                    }

                }

                @Override
                public void onPlayerError(ExoPlaybackException error) {

                }

                @Override
                public void onPositionDiscontinuity() {

                }
            });
        }
        return rootView;
    }

    private void initializePlayer(Uri mediaUri) {
        if (mExoPlayer == null) {

            TrackSelector trackSelector = new DefaultTrackSelector();
            LoadControl loadControl = new DefaultLoadControl();
            mExoPlayer = ExoPlayerFactory.newSimpleInstance(getActivity(), trackSelector, loadControl);
            mSimpleExoPlayerView.setPlayer(mExoPlayer);

            String userAgent = Util.getUserAgent(getContext(), "BakingApp");
            MediaSource mediaSource = new ExtractorMediaSource(mediaUri, new DefaultDataSourceFactory(
                    getContext(), userAgent), new DefaultExtractorsFactory(), null, null);
            mExoPlayer.prepare(mediaSource);
            if (currentPosition > 0l) {
                mExoPlayer.seekTo(currentPosition);
            }
            exoPlayerInitilized = true;
            mExoPlayer.setPlayWhenReady(true);
        }
    }

    private void releasePlayer() {
        if (mExoPlayer != null) {
            mExoPlayer.stop();
            mExoPlayer.release();
            mExoPlayer = null;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        releasePlayer();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        if (mExoPlayer != null) {
            outState.putLong(BakingAppConstants.EXO_POSITION_KEY, mExoPlayer.getCurrentPosition());
            outState.putBoolean(BakingAppConstants.EXO_PAUSED_KEY, mediaPlaying);
        }
    }

    public void onResume() {
        super.onResume();
        mediaPaused = false;
        if (exoPlayerInitilized) {
            mExoPlayer.setPlayWhenReady(mediaPlaying);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mExoPlayer != null) {
            mExoPlayer.getCurrentPosition();
            mediaPaused = true;
            mExoPlayer.setPlayWhenReady(false);
        }
    }

    public void initButtons()
    {
        if(currentPositionOfStep==0)
        {
            mPreviousButton.setVisibility(View.INVISIBLE);
            mNextButton.setVisibility(View.VISIBLE);
            mNextButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    buttonFunctionality.onNextButtonClicked(mStepsList.get(currentPositionOfStep),currentPositionOfStep);

                }
            });
        }

        else if(currentPositionOfStep==mStepsList.size()-1)
        {
            mNextButton.setVisibility(View.INVISIBLE);
            mPreviousButton.setVisibility(View.VISIBLE);
            mPreviousButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    buttonFunctionality.onPreviousButtonClicked(mStepsList.get(currentPositionOfStep),currentPositionOfStep);

                }
            });
        }
        else {
            mNextButton.setVisibility(View.VISIBLE);
            mPreviousButton.setVisibility(View.VISIBLE);
            mNextButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    buttonFunctionality.onNextButtonClicked(mStepsList.get(currentPositionOfStep),currentPositionOfStep);


                }
            });



                    mPreviousButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            buttonFunctionality.onPreviousButtonClicked(mStepsList.get(currentPositionOfStep),currentPositionOfStep);

                        }
                    });
                }

        }

        public interface ButtonFunctionality
        {
            void onPreviousButtonClicked(Steps prevStep, int currentPositionForSteps);
            void onNextButtonClicked(Steps nextStep, int currentPositionForSteps);
        }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        WindowManager windowmanager = (WindowManager) getActivity().getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
        windowmanager.getDefaultDisplay().getMetrics(displayMetrics);
        int width = Math.round(displayMetrics.widthPixels / displayMetrics.density);
        if (context instanceof ButtonFunctionality&&(width<600)) {

            buttonFunctionality = (ButtonFunctionality) context;
        }
        else if (width>600)
        {
            buttonFunctionality=null;
        }

        else {
            throw new RuntimeException(context.toString());
        }
    }



}
