package com.ttt.noughtsandcrosses;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Handler;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class highscore extends AppCompatActivity {
ArrayList<String> names,scores;
    ProgressDialog loading;
    ListView l1,l2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_highscore);
       l1= (ListView) findViewById(R.id.list1);
        l2= (ListView) findViewById(R.id.list2);

        loading=new ProgressDialog(highscore.this);
        loading.setMessage("Fetching scores");
        loading.show();

        Handler h=new Handler();
        h.postDelayed(new Runnable() {
            @Override
            public void run() {
                 view_scores();
                loading.cancel();
            }
        },2000);

}

    public void view_scores() {
        load_scores();

        ListAdapter listname=new ArrayAdapter<>(this,R.layout.liststyle,names);
        ListAdapter listscore=new ArrayAdapter<>(this,R.layout.liststyle,scores);
        l1.setAdapter(listname);
        l2.setAdapter(listscore);
    }

    public void load_scores() {

        try {
            Connection conn;
            ResultSet rs;
            String user,pass;
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
            String url = "jdbc:jtds:sqlserver://194.82.34.247;instance=SQLEXPRESS;DatabaseName=db_1430719_androidgame";
            String driver = "net.sourceforge.jtds.jdbc.Driver";
            String userName = "user_db_1430719_androidgame";
            String password = "Amber2015";
            Class.forName(driver);
            conn = DriverManager.getConnection(url, userName, password);


            String query = "SELECT TOP 10 * FROM tblscores ORDER BY playerscore DESC ";
            Statement st = conn.createStatement();

            rs=st.executeQuery(query);

            String line="";
            scores=new ArrayList<>();
            names=new ArrayList<>();
            while (rs.next()){
               names.add(rs.getString(2));
                scores.add(rs.getString(3));
            }





        } catch (Exception e) {
            Toast.makeText(this, e.getMessage().toString(), Toast.LENGTH_SHORT).show();
        }

    }
}
