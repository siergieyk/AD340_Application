package com.example.spellchecker;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

public class DetailActivity extends AppCompatActivity {

    Toolbar Toolbar;
    ImageView ImageView;
    TextView TextView;
    TextView TextViewDesc;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Toolbar = (Toolbar) findViewById(R.id.toolbar2);
        ImageView = (ImageView) findViewById(R.id.imageView2);
        TextViewDesc = (TextView) findViewById(R.id.textView6);
        TextView = (TextView) findViewById(R.id.textView4);

        Bundle mBundle = getIntent().getExtras();
        if (mBundle != null) {

            ImageView.setImageResource(mBundle.getInt("moviePic"));
            Toolbar.setTitle(mBundle.getString("movieName"));
            TextViewDesc.setText(mBundle.getString("movieDesc"));
            TextView.setText(mBundle.getString("moviePrem"));
        }


    }
}
