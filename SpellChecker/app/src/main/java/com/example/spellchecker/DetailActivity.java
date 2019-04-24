package com.example.spellchecker;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

public class DetailActivity extends AppCompatActivity {

    Toolbar Toolbar;
    ImageView ImageView;
//    TextView TextView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Toolbar = (Toolbar) findViewById(R.id.toolbar2);
        ImageView = (ImageView) findViewById(R.id.imageView2);
//        TextView = (TextView) findViewById(R.id.textView4);

        Bundle mBundle = getIntent().getExtras();
        if (mBundle != null) {

            ImageView.setImageResource(mBundle.getInt("moviePic"));
            Toolbar.setTitle(mBundle.getString("movieName"));
//            TextView.setText(mBundle.getString("moviePrem"));
        }


    }
}
