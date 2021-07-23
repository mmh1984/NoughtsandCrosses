package com.ttt.noughtsandcrosses;

import android.app.Dialog;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.StringTokenizer;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    String box[];
    Button boxes[];
    int boxid[];
    Animation rotate;
    int playerscore=0;
    String status="";

    String player,turn,user;
    String playersymbol,computersymbol;
    MediaPlayer clicksound;
    Button Score,Exit,How;
    GridLayout game1;
    int playerbg,computerbg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        clicksound=MediaPlayer.create(getApplicationContext(),R.raw.clicksound);
        rotate= AnimationUtils.loadAnimation(getApplicationContext(),R.anim.rotate);
        load_box();

        How= (Button) findViewById(R.id.btnhow);

        How.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog instructions=new Dialog(MainActivity.this);
               instructions.setTitle("How to play?");
                instructions.setContentView(R.layout.instructions);
                instructions.show();
            }
        });






        Score= (Button) findViewById(R.id.btnScore);
        Exit= (Button) findViewById(R.id.btnExit);
        game1= (GridLayout) findViewById(R.id.gridgame);
        Exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });



        if(getIntent() != null && getIntent().getExtras() != null){

            playerscore = Integer.parseInt(getIntent().getExtras().getString("score"));
            Score.setText(String.valueOf(playerscore));
            user=getIntent().getExtras().getString("user");
            player=getIntent().getExtras().getString("tile");
            turn=getIntent().getExtras().getString("turn");

            if(player.equals("X")){
                playerbg=R.drawable.x;
                computerbg=R.drawable.o;
                playersymbol="X";
                computersymbol="O";
            }
            else if(player.equals("O")){
                playerbg=R.drawable.o;
                computerbg=R.drawable.x;
                playersymbol="O";
                computersymbol="X";

            }

            if(turn.equals("android")){
                int random = generate_move();
                boxes[random].setBackgroundResource(computerbg);
                box[random]=computersymbol;
                boxes[random].setEnabled(false);
                boxes[random].startAnimation(rotate);
            }

        }
    }

 public void load_id() {

     boxid=new int[9];
     boxid[0]=R.id.btn1;
     boxid[1]=R.id.btn2;
     boxid[2]=R.id.btn3;
     boxid[3]=R.id.btn4;
     boxid[4]=R.id.btn5;
     boxid[5]=R.id.btn6;
     boxid[6]=R.id.btn7;
     boxid[7]=R.id.btn8;
     boxid[8]=R.id.btn9;

 }
    public void load_box() {
        try {
            load_id();
            box=new String [9];
            boxes=new Button[9];

            int x=0;
            while (x<9)
            {
                box[x]="empty";
                boxes[x]= (Button) findViewById(boxid[x]);
                boxes[x].setText("");
                boxes[x].setOnClickListener(this);
                boxes[x].setEnabled(true);
                boxes[x].setVisibility(View.VISIBLE);
                boxes[x].startAnimation(rotate);

                x++;
            }
        }
        catch (Exception ex){
            Log.d("error",ex.getMessage());
        }


    }

    public void hidebuttons() {
        for(int x=0;x<9;x++){
            boxes[x].setVisibility(View.GONE);
        }
    }
    @Override
    public void onClick(View v) {

if(get_available()> 0) {
    for (int x = 0; x < 9; x++) {
        clicksound.seekTo(0);
        if (v.getId() == boxid[x]) {
            boxes[x].setBackgroundResource(playerbg);
            boxes[x].startAnimation(rotate);
            boxes[x].setEnabled(false);
            box[x] = playersymbol;

            clicksound.start();

            if (iswinner(playersymbol) == true) {
                playerscore++;

                Score.setText(String.valueOf(playerscore));
                game_over("player");
                 break;

            }
            else {
                if(get_available()>0) {

                    int random = generate_move();
                    boxes[random].setBackgroundResource(computerbg);
                    box[random] = computersymbol;
                    boxes[random].setEnabled(false);
                    boxes[random].startAnimation(rotate);

                    if (iswinner(computersymbol) == true) {

                        game_over("computer");
                    }
                    else {
                        if(get_available()==0){
                            game_over("draw");
                        }
                    }
                }
                else {
                    game_over("draw");
                    Toast.makeText(this,String.valueOf(get_available()),Toast.LENGTH_SHORT).show();
                }
            }




        }
    }
}
        else {
    game_over("draw");
    Toast.makeText(this,String.valueOf(get_available()),Toast.LENGTH_SHORT).show();

}
    }

    public void game_over(String winner) {

        if(winner.equals("player")){
           status="You Win";
        }
        else if(winner.equals("computer")){
            status="You Lose";
        }
        else {
            status="Draw";
        }

        Handler h=new Handler();
        h.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i=new Intent(MainActivity.this,gameover.class);
                i.putExtra("scores",String.valueOf(playerscore));
                i.putExtra("status",String.valueOf(status));
                i.putExtra("user",user);
                i.putExtra("tile",player);
                i.putExtra("turn",turn);
                startActivity(i);
                finish();
            }
        },3000);

    }

    public boolean iswinner(String symbol) {
        boolean winner=false;
        try {






                    //check pattern1 (horizontal)
                    if (box[0].equals(symbol) && box[1].equals(symbol) && box[2].equals(symbol)) {
                        winner = true;
                        hidebuttons();
                        game1.setBackgroundResource(R.drawable.hortop);
                    }

            else if (box[3].equals(symbol) && box[4].equals(symbol) && box[5].equals(symbol)) {
                        hidebuttons();
                        game1.setBackgroundResource(R.drawable.hormid);
                        winner = true;
            }

                    else if (box[6].equals(symbol) && box[7].equals(symbol) && box[8].equals(symbol)) {
                        winner = true;
                        hidebuttons();
                        game1.setBackgroundResource(R.drawable.horbottom);

                    }
                    //check pattern 2 (vertical)
                    else if (box[0].equals(symbol) && box[3].equals(symbol) && box[6].equals(symbol)) {
                        winner = true;
                        hidebuttons();
                        game1.setBackgroundResource(R.drawable.verleft);
                    }
                    else if (box[1].equals(symbol) && box[4].equals(symbol) && box[7].equals(symbol)) {
                        winner = true;
                        hidebuttons();
                        game1.setBackgroundResource(R.drawable.vermid);
                    }
                    else if (box[2].equals(symbol) && box[5].equals(symbol) && box[8].equals(symbol)) {
                        winner = true;
                        hidebuttons();
                        game1.setBackgroundResource(R.drawable.verright);
                    }

                    //check pattern (diagonal)
                    else if (box[0].equals(symbol) && box[4].equals(symbol) && box[8].equals(symbol)) {
                        winner = true;
                        hidebuttons();
                        game1.setBackgroundResource(R.drawable.diagdown);
                    }

                    else if (box[2].equals(symbol) && box[4].equals(symbol) && box[6].equals(symbol)) {
                        winner = true;
                        hidebuttons();
                        game1.setBackgroundResource(R.drawable.diagup);
                    }






        }
        catch (Exception ex){
            Log.d("Error",ex.getMessage());
        }
        return winner;
    }

    public int get_available() {
        int size=0;

        for(int x=0;x<9;x++){
            if(box[x].toString().equals("empty")){
                size++;
            }
        }
        return size;
    }

    public int generate_move() {
        ArrayList<Integer> empty=new ArrayList<>();;
       int randomindex;
        randomindex=0;
        try {

            int size;
            size=0;

          for (int x=0;x<9;x++){
              if(box[x].toString().equals("empty")){
                  empty.add(x);
              }
          }
            size=empty.size();
            randomindex= (int) (Math.random()* size);



        }
        catch (Exception ex){
            Log.d("error",ex.getMessage());
        }
        return empty.get(randomindex);
    }
}
