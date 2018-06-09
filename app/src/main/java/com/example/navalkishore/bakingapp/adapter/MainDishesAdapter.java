package com.example.navalkishore.bakingapp.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.navalkishore.bakingapp.model.MainDish;
import com.example.navalkishore.bakingapp.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MainDishesAdapter extends RecyclerView.Adapter<MainDishesAdapter.MainDishesAdapterViewHolder> {
    private ArrayList<MainDish> mMainDishList;
    private final MainDishesAdapterOnClickHandler mClickHandler;


    public void setMainDishData(ArrayList<MainDish> mainDishData) {
        mMainDishList = mainDishData;
        notifyDataSetChanged();
    }

    public MainDishesAdapter(MainDishesAdapterOnClickHandler handler) {
        this.mClickHandler = handler;
    }

    @NonNull
    @Override
    public MainDishesAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutIdForGridItem = R.layout.card_view_main_dish;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;
        View view = inflater.inflate(layoutIdForGridItem, parent, shouldAttachToParentImmediately);
        return new MainDishesAdapterViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull MainDishesAdapterViewHolder holder, int position) {
        String urlForTheImage = mMainDishList.get(position).getImage();
        if(urlForTheImage!=null&&urlForTheImage.length()!=0)
        {
            Picasso.with(holder.itemView.getContext()).load(urlForTheImage).into(holder.mImageView);

        }
        String textForTheCard = mMainDishList.get(position).getName();
        holder.mTextView.setText(textForTheCard);


    }

    @Override
    public int getItemCount() {
        if (null == mMainDishList) return 0;
        return mMainDishList.size();
    }


    public interface MainDishesAdapterOnClickHandler {
        void onClick(MainDish mainDishSelected);
    }
    public class MainDishesAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        public final ImageView mImageView;
        public final TextView mTextView;


        public MainDishesAdapterViewHolder(View itemView) {
            super(itemView);
            mImageView=itemView.findViewById(R.id.iv_main_receipe);
            mTextView=itemView.findViewById(R.id.tv_receipe_label);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int adapterPosition = getAdapterPosition();
            MainDish mainDishSelected = mMainDishList.get(adapterPosition);
            mClickHandler.onClick(mainDishSelected);
        }
    }
}
