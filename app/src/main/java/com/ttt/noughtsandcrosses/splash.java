package com.ttt.noughtsandcrosses;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class splash extends AppCompatActivity {

    TextView title;
    Button login,register,highscore;
    ImageView logo;
    Animation slideup,fadein;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        slideup= AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fadein);
        fadein=AnimationUtils.loadAnimation(getApplicationContext(),R.anim.bounce);

        title= (TextView) findViewById(R.id.title);
        logo= (ImageView) findViewById(R.id.logo);

        logo.startAnimation(slideup);
        title.startAnimation(fadein);

        register= (Button) findViewById(R.id.btnregister);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(splash.this,signup.class));
            }
        });

        login= (Button) findViewById(R.id.btnStart);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(splash.this,login.class));
            }
        });

        highscore= (Button) findViewById(R.id.btnhighscore);
        highscore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(splash.this,highscore.class));
            }
        });

    }
}
