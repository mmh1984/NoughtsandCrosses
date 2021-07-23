package com.ttt.noughtsandcrosses;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class playeroption2 extends AppCompatActivity implements View.OnClickListener  {
String user,tile;
    Button turn;
    Intent i;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playeroption2);

        user=getIntent().getExtras().getString("user");
        tile=getIntent().getExtras().getString("tile");

        Button player= (Button) findViewById(R.id.playerturn);
        Button ai= (Button) findViewById(R.id.androidturn);


       i=new Intent(playeroption2.this,MainActivity.class);
        i.putExtra("user",user);
        i.putExtra("tile",tile);
        i.putExtra("score","0");

        player.setOnClickListener(this);
        ai.setOnClickListener(this);

}

    @Override
    public void onClick(View v) {

        if(v.getId()==R.id.playerturn){
            i.putExtra("turn","player");
            startActivity(i);
            finish();
        }
        else if(v.getId()==R.id.androidturn){
            i.putExtra("turn","android");
            startActivity(i);
            finish();
        }
    }



}
