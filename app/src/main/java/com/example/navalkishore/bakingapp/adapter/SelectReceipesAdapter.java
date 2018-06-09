package com.example.navalkishore.bakingapp.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.navalkishore.bakingapp.model.Steps;
import com.example.navalkishore.bakingapp.R;

import java.util.ArrayList;

public class SelectReceipesAdapter extends RecyclerView.Adapter<SelectReceipesAdapter.SelectStepsViewHolder> {
    private ArrayList<Steps> mStepsList;
    private final SelectStepsAdapterOnClickHandler mClickHandler;

    public void setReceipsData(ArrayList<Steps> mainReceipeData) {
        mStepsList = mainReceipeData;
        notifyDataSetChanged();
    }

    public SelectReceipesAdapter(SelectStepsAdapterOnClickHandler handler) {
        this.mClickHandler = handler;
    }

    @NonNull
    @Override
    public SelectStepsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutIdForGridItem = R.layout.card_view_receipe_instruction;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;
        View view = inflater.inflate(layoutIdForGridItem, parent, shouldAttachToParentImmediately);
        return new SelectStepsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SelectStepsViewHolder holder, int position) {
        String textForTheCard = mStepsList.get(position).getShortDescription();
        holder.mTextView.setText(textForTheCard);

    }

    @Override
    public int getItemCount() {
        if (null == mStepsList) return 0;
        return mStepsList.size();
    }

    public interface SelectStepsAdapterOnClickHandler {
        void onClick(Steps stepSelected);
    }
    public class SelectStepsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        public final TextView mTextView;


        public SelectStepsViewHolder(View itemView) {
            super(itemView);
            mTextView=itemView.findViewById(R.id.textView_steps_label);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int adapterPosition = getAdapterPosition();
            Steps stepsSelected = mStepsList.get(adapterPosition);
            mClickHandler.onClick(stepsSelected);
        }
    }
}
