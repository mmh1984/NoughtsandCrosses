package com.ttt.noughtsandcrosses;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

public class playeroption extends AppCompatActivity implements View.OnClickListener{
Button x,o;
    String user,tile;
    Animation out;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playeroption);
        x= (Button) findViewById(R.id.button2);
        o= (Button) findViewById(R.id.button);

              x.setOnClickListener(this);
              o.setOnClickListener(this);
        out= AnimationUtils.loadAnimation(getApplicationContext(),R.anim.slideup);
        user=getIntent().getExtras().getString("username");
        tile="";

    }

    @Override
    public void onClick(View v) {

        Intent i=new Intent(playeroption.this,playeroption2.class);
        if(v.getId()==R.id.button){

          tile="O";
        }
        else if(v.getId()==R.id.button2){

          tile="X";
        }
        i.putExtra("user",user);
        i.putExtra("tile",tile);
        startActivity(i);
        finish();
    }
}
