package com.example.spellchecker;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class ViewAdapter extends RecyclerView.Adapter<ViewAdapter.ViewHolder> {

    private ArrayList<TrafficCamera> myData;
    private LayoutInflater myInflater;




    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = myInflater.inflate(R.layout.activity_recycle_view, parent, false);
        return new ViewHolder(view);
    }

    public ViewAdapter(Context context, ArrayList<TrafficCamera> data) {
        this.myInflater = LayoutInflater.from(context);
        this.myData = data;
    }



    @Override
    public int getItemCount() {
        if (myData != null)
            return myData.size();
        else return 0;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {

        holder.myLabelText.setText(myData.get(position).getCameralabel());
        if (!TextUtils.isEmpty(myData.get(position).getImageurl().getUrl()))
            Picasso.get()
                    .load(myData.get(position).getImageurl().getUrl())
                    .error(R.mipmap.ic_launcher)
                    .into(holder.myCameraImage);
        else Picasso.get()
                .load(R.mipmap.ic_launcher);

    }

    public TrafficCamera getItem(int id) {
        return myData.get(id);
    }


    class ViewHolder extends RecyclerView.ViewHolder {

        ImageView myCameraImage;
        TextView myLabelText;


        ViewHolder(View itemView) {
            super(itemView);
            myCameraImage = itemView.findViewById(R.id.cameraImage);
            myLabelText = itemView.findViewById(R.id.labelText);

        }



    }
}