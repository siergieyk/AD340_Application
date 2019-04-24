package com.example.spellchecker;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class Adapter extends ArrayAdapter<String> {

    String[] movieName;
    String[] movieDesc;
    int[] moviePic;
    Context mContext;
    String[] moviePrem;

    public Adapter(Context context, String[] movieName, String[] movieDesc, int[] moviePic, String[] moviePrem) {
        super(context, R.layout.listview_item);
        this.movieName = movieName;
        this.movieDesc = movieDesc;
        this.moviePic = moviePic;
        this.mContext = context;
        this.moviePrem = moviePrem;
    }

    @Override
    public int getCount() {
        return movieName.length;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder mViewHolder = new ViewHolder();
        if (convertView == null) {
            LayoutInflater mInflater = (LayoutInflater) mContext.
                    getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.listview_item, parent, false);
            mViewHolder.Pic = (ImageView) convertView.findViewById(R.id.imageView);
            mViewHolder.Name = (TextView) convertView.findViewById(R.id.textView);
            mViewHolder.Desc = (TextView) convertView.findViewById(R.id.textView2);
            mViewHolder.Prem = (TextView) convertView.findViewById(R.id.textView4);



            convertView.setTag(mViewHolder);
        } else {
            mViewHolder = (ViewHolder) convertView.getTag();
        }
        mViewHolder.Pic.setImageResource(moviePic[position]);
        mViewHolder.Name.setText(movieName[position]);
        mViewHolder.Desc.setText(movieDesc[position]);
        mViewHolder.Prem.setText(moviePrem[position]);


        return convertView;
    }

    static class ViewHolder {
        ImageView Pic;
        TextView Name;
        TextView Desc;
        TextView Prem;
    }
}
