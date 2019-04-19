package com.example.spellchecker;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

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

        myButton = (Button)findViewById(R.id.sendBtn);
        myButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(getApplicationContext(),sent,Toast.LENGTH_LONG).show();

            }
        });

        myButton = (Button)findViewById(R.id.btn1);
        myButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(getApplicationContext(),toast1,Toast.LENGTH_LONG).show();

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
    }

