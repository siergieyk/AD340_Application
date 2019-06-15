package com.example.spellchecker;

import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.content.Intent;
import android.support.v7.widget.Toolbar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;


public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private Button btn2;

    protected Button myButton;

    private EditText myName;
    private EditText myEmail;
    private EditText myPass;

    private String Name;
    private String Email;
    private String Pass;

    SharedPreferences myPreferences;
    private String sharedPrefFile = "com.example.spellchecker";

    private final String keyName = "name";
    private final String keyEmail = "email";
    private final String keyPass = "password";




    String toast4 = "Final toast!";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG, "onCreate: Main activity Started");
        Button button1 = findViewById(R.id.btn1);

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivity_zombie2();
            }
        });



        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);

        myPreferences = getSharedPreferences(sharedPrefFile, MODE_PRIVATE);
        myName = (EditText)findViewById(R.id.name);
        Log.i("Name", "created");

        myEmail = (EditText)findViewById(R.id.email);
        Log.i("Email", "created");

        myPass = (EditText)findViewById(R.id.pass);
        Log.i("Password", "created");

        Name = myPreferences.getString(keyName, "");
        Email = myPreferences.getString(keyEmail, "");
        Pass = myPreferences.getString(keyPass, "");

        myName.setText(Name);
        myEmail.setText(Email);
        myPass.setText(Pass);

        FirebaseApp.initializeApp(this);

        Log.i("Firebase started", " successfully");

        Button loginButton = (Button)findViewById(R.id.login);


//        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        loginButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                signIn();
            }
        });



        btn2 = findViewById(R.id.btn2);
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), CamsLocation.class));
                finish();

            }
        });

        myButton = (Button)findViewById(R.id.btn3);
        myButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(getApplicationContext(), MapsActivity.class));
                finish();
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





    public boolean validate(String name, String email, String password){

        if (name.length() < 1 || email.length() < 1 || password.length() < 1) {

            return  false;
        }

        return true;
    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.app_bar_info, menu);
        return true;

    }

    private void openActivity_zombie2() {
        Intent intent = new Intent(this, Movie.class);
        startActivity(intent);
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch(item.getItemId()) {
            case R.id.info:
                Intent intent = new Intent(MainActivity.this, aboutPage.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }
    }

    private void signIn() {


        final String name = myName.getText().toString();

        String email = myEmail.getText().toString();
        String password = myPass.getText().toString();

        SharedPreferences.Editor preferencesEditor = myPreferences.edit();
        preferencesEditor.putString(keyName, name);
        preferencesEditor.putString(keyEmail, email);
        preferencesEditor.putString(keyPass, password);

        preferencesEditor.apply();

        boolean isValid = validate(name, email, password);

        if (isValid) {

            Pass = myPass.getText().toString();
            Email = myEmail.getText().toString();

            FirebaseAuth mAuth = FirebaseAuth.getInstance();
            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            Log.d("Firebase", "Signed In:" + task.isSuccessful());

                            if (task.isSuccessful()) {
                                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();



                                UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                        .setDisplayName(name)
                                        .build();

                                user.updateProfile(profileUpdates)
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {

                                                    startActivity(new Intent(MainActivity.this, TeamActivity.class));
                                                }
                                            }
                                        });

                            } else {

                                Toast.makeText(MainActivity.this, "Fail",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }

                    });
        } else {
            myName.setText("Required");
            myEmail.setError("Required");
            myPass.setError("Required");
        }
    }
}

