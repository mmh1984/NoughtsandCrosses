package com.ttt.noughtsandcrosses;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class gameover extends AppCompatActivity {
TextView scoredisplay;
    String getscore,getbg;

    TextView status;
    ProgressDialog d;
    Button back;
    Button close;
    String user,tile,turn;
    MediaPlayer sound1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gameover);


        getbg="";
        scoredisplay= (TextView) findViewById(R.id.score);
        status= (TextView) findViewById(R.id.status);
        if(getIntent() != null && getIntent().getExtras() != null) {
            getscore = getIntent().getExtras().getString("scores");
            scoredisplay.setText(getscore.toString());
            getbg=getIntent().getExtras().getString("status");
            user=getIntent().getExtras().getString("user");
            tile =getIntent().getExtras().getString("tile");
            turn=getIntent().getExtras().getString("turn");


            status.setText(getbg.toString());

            if(getbg.equals("You Win")){
                sound1=MediaPlayer.create(getApplicationContext(),R.raw.clapping);

            }
            else if(getbg.equals("You Lose")){
                sound1=MediaPlayer.create(getApplicationContext(),R.raw.boo);
            }
            sound1.start();
        }

        back= (Button) findViewById(R.id.btnplay);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(gameover.this,MainActivity.class);
                i.putExtra("score",scoredisplay.getText().toString());
                i.putExtra("user",user);
                i.putExtra("tile",tile);
                i.putExtra("turn",turn);
                sound1.stop();
                startActivity(i);
                finish();

            }
        });

        close= (Button) findViewById(R.id.close);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                d=new ProgressDialog(gameover.this);
                d.setMessage("Submitting scores to server");
                d.setTitle("Scores");
                d.show();
                Handler h=new Handler();
                h.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        submit_score(getscore);
                        d.cancel();
                        startActivity(new Intent(gameover.this,splash.class));
                        sound1.stop();
                        finish();
                    }
                },3000);
            }
        });

    }

    public int submit_score(String s) {
        int x = 0;
        try {
            Connection conn;
            ResultSet rs;

            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
            String url = "jdbc:jtds:sqlserver://194.82.34.247;instance=SQLEXPRESS;DatabaseName=db_1430719_androidgame";
            String driver = "net.sourceforge.jtds.jdbc.Driver";
            String userName = "user_db_1430719_androidgame";
            String password = "Amber2015";
            Class.forName(driver);
            conn = DriverManager.getConnection(url, userName, password);
            String query = "INSERT INTO tblscores (playername,playerscore) VALUES ('"+ user +"'," +s +")";
            Statement st = conn.createStatement();
           st.executeUpdate(query);





        } catch (Exception e) {
            Toast.makeText(this, e.getMessage().toString(), Toast.LENGTH_SHORT).show();
        }
        return x;
    }

}
