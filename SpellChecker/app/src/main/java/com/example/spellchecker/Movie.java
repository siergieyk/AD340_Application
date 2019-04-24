package com.example.spellchecker;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

public class Movie extends AppCompatActivity {

    Toolbar Toolbar;
    ListView ListView;

    String[] moviePrem = {"1984", "2009, Tommy Wirkola", "2004, Michele Soavi", "2007, Juan Carlos Fresnadillo",
            "1986, Fred Dekker", "2012, Chris Butler,  Sam Fell", "2009, Ruben Fleischer", "2007, Robert Rodriguez",
            "2016, Sang-ho Yen", "1981, Lucio Fulci", "1985, George Romero", "1988, Wes Craven" , "1985, Stuart Gordon",
            "1971, Lucio Fulci", "2004, Zack Snyder", "1985, Dan O'Bannon", "1992, Peter Jackson", "2002, Danny Boyle",
            "1968, George Romero", "2004, Edgar Wright", "1978, George Romero"};

    String[] movieNames = {"Night of the Comet","Dead Snow","Cemetery Man", "28 Weeks Later",
            "Night of the Creeps", "ParaNorman", "Zombieland", "Planet Terror", "Train to Busan",
            "The Beyond", "Day of the Dead","The Serpent & the Rainbow", "Re-Animator", "Zombi 2",
            "Dawn of the Dead", "Return of the Living Dead", "Dead Alive", "28 Days Later",
            "Night of the Living Dead", "Shawn of the Dead", "Dawn of the Dead"};

    String[] movieDesc = {"1984", "2009, Tommy Wirkola", "2004, Michele Soavi", "2007, Juan Carlos Fresnadillo",
            "1986, Fred Dekker", "2012, Chris Butler,  Sam Fell", "2009, Ruben Fleischer", "2007, Robert Rodriguez",
            "2016, Sang-ho Yen", "1981, Lucio Fulci", "1985, George Romero", "1988, Wes Craven" , "1985, Stuart Gordon",
            "1971, Lucio Fulci", "2004, Zack Snyder", "1985, Dan O'Bannon", "1992, Peter Jackson", "2002, Danny Boyle",
            "1968, George Romero", "2004, Edgar Wright", "1978, George Romero"};

    int[] moviePic = {
            R.drawable.one,
            R.drawable.two,
            R.drawable.three,
            R.drawable.four,
            R.drawable.five,
            R.drawable.six,
            R.drawable.seven,
            R.drawable.eight,
            R.drawable.nine,
            R.drawable.ten,
            R.drawable.eleven,
            R.drawable.twelve,
            R.drawable.thirteen,
            R.drawable.fourteen,
            R.drawable.fifteen,
            R.drawable.sixteen,
            R.drawable.seventeen,
            R.drawable.eighteen,
            R.drawable.nineteen,
            R.drawable.twenty,
            R.drawable.twentyone};



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);

        Toolbar = (Toolbar) findViewById(R.id.toolbar);
        Toolbar.setTitle(getResources().getString(R.string.app_name));


        ListView = (ListView) findViewById(R.id.listview);
        Adapter myAdapter = new Adapter(Movie.this, movieNames, movieDesc, moviePic, moviePrem);
        ListView.setAdapter(myAdapter);
        ListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent Intent = new Intent(Movie.this, DetailActivity.class);
                Intent.putExtra("movieName", movieNames[i]);
                Intent.putExtra("movieDesc", movieDesc[i]);
                Intent.putExtra("moviePic", moviePic[i]);
                Intent.putExtra("moviePrem", moviePrem[i]);


                startActivity(Intent);
            }
        });



    }

}
