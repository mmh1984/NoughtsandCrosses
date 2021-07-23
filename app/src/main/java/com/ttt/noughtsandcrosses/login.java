package com.ttt.noughtsandcrosses;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Handler;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class login extends AppCompatActivity {
    EditText username;
    EditText userpassword;
    Button login;
    ProgressDialog loading;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        username= (EditText) findViewById(R.id.editTextuser);
        userpassword= (EditText) findViewById(R.id.editTextpass);

        login= (Button) findViewById(R.id.btnLogin);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(username.getText().toString().isEmpty()){
                    username.setError("Enter username please");
                }
                else {
                    if (userpassword.getText().toString().isEmpty()) {
                        userpassword.setError("Enter Password");


                    }
                    else {
                        loading=new ProgressDialog(login.this);
                        loading.setMessage("Loggin in...");
                        loading.show();
                        Handler h=new Handler();
                        h.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                check_login();

                                loading.cancel();

                            }
                        },3000);

                    }
                }
            }
        });
    }

    public void check_login() {
       int row=0;
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

            user=username.getText().toString();
            pass=userpassword.getText().toString();
            String query = "SELECT * FROM tblplayers WHERE playername='" + user +"' AND playerpass='" + pass +"'";
            Statement st = conn.createStatement();

            rs=st.executeQuery(query);

            while (rs.next()){
                row++;
            }

            if(row>0){


                  Toast.makeText(this,"Login successful",Toast.LENGTH_SHORT).show();
                Intent i=new Intent(login.this,playeroption.class);
                i.putExtra("username",user);
                startActivity(i);
                finish();
            }
            else {
                Toast.makeText(this,"Login failed",Toast.LENGTH_SHORT).show();
                loading.cancel();
            }

        } catch (Exception e) {
            Toast.makeText(this, e.getMessage().toString(), Toast.LENGTH_SHORT).show();
        }

    }
}
