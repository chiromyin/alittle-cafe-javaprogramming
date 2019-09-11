package com.example.littlecafeshop;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {

    private Context mContext;
    private List<Items> mData;

    public RecyclerViewAdapter(Context mContext, List<Items> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view;
        LayoutInflater mInflater = LayoutInflater.from(mContext);
        view = mInflater.inflate(R.layout.cardview_item,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        myViewHolder.tittle.setText(mData.get(i).getTitle());
        myViewHolder.description.setText(mData.get(i).getDescription());
        Picasso.with(mContext).load(mData.get(i).getImgURL()).into(myViewHolder.img);
        myViewHolder.price.setText(mData.get(i).getPrice());
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView tittle;
        TextView description;
        ImageView img;
        TextView price;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tittle = itemView.findViewById(R.id.name);
            description = itemView.findViewById(R.id.detail);
            img = itemView.findViewById(R.id.image);
            price = itemView.findViewById(R.id.cost);
        }
    }

}
