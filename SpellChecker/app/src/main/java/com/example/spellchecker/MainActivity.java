package com.example.spellchecker;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.content.Intent;
import android.support.v7.widget.Toolbar;



public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;
    protected Button myButton;

    String sent = "Sent!";
    String toast1 = "This is my first Toast";
    String toast2 = "Check, Ckeck, 1, 2, 3";
    String toast3 = "What to write here ?";
    String toast4 = "Final toast!";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);

        myButton = (Button)findViewById(R.id.sendBtn);

        Button buttonMovies = (Button)findViewById(R.id.btn1);
        buttonMovies.setOnClickListener(new View.OnClickListener() {


            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Movie.class);
                        startActivity(intent);
            }


        });


        myButton = (Button)findViewById(R.id.btn2);
        myButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(getApplicationContext(),toast2,Toast.LENGTH_LONG).show();

            }
        });

        myButton = (Button)findViewById(R.id.btn3);
        myButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(getApplicationContext(),toast3,Toast.LENGTH_LONG).show();

            }
        });

        myButton = (Button)findViewById(R.id.btn4);
        myButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(getApplicationContext(),toast4,Toast.LENGTH_LONG).show();

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.app_bar_info, menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.info:
                Intent intent = new Intent(MainActivity.this, aboutPage.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }
    }
}

