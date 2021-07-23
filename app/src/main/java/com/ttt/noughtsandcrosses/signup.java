package com.ttt.noughtsandcrosses;

import android.app.ProgressDialog;
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

public class signup extends AppCompatActivity {

    EditText username;
    EditText password;
    Button Register;
    ProgressDialog loading;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        username= (EditText) findViewById(R.id.editTextuser);
        password= (EditText) findViewById(R.id.editTextpass);

        Register= (Button) findViewById(R.id.btnLogin);

        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(username.getText().toString().isEmpty()){
                    username.setError("Enter username please");
                }
                else {
                    if(password.getText().toString().isEmpty()){
                        password.setError("Enter Password");


                    }

                    else{
                      loading=new ProgressDialog(signup.this);
                        loading.setMessage("Creating user");
                        loading.show();

                        Handler h=new Handler();
                        h.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                if(user_available(username.getText().toString())) {
                                    create_user();
                                    loading.cancel();
                                }
                                else {
                                   show_error();
                                    username.setError("username not available");
                                    loading.cancel();
                                }
                            }
                        },3000);


                    }
                }
            }
        });

    }
    public void show_error() {
        Toast.makeText(this,"This username is not available",Toast.LENGTH_SHORT).show();
    }
    public int create_user() {
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
            String query = "INSERT INTO tblplayers (playername,playerpass) VALUES ";
            query+=" ('" + username.getText().toString() + "','" + username.getText().toString()+"')";
            Statement st = conn.createStatement();
            st.executeUpdate(query);
            Toast.makeText(this,"Success",Toast.LENGTH_LONG).show();


            Handler h=new Handler();
            h.postDelayed(new Runnable() {
                @Override
                public void run() {
                    finish();
                }
            },3000);

        } catch (Exception e) {
            Toast.makeText(this, e.getMessage().toString(), Toast.LENGTH_SHORT).show();
        }
        return x;
    }

    public boolean user_available(String p) {
        Boolean x=false;
        int row=0;
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
            String query = "SELECT * FROM tblplayers WHERE playername='" + p +"'";
            Statement st = conn.createStatement();

            rs=st.executeQuery(query);

            while (rs.next()){
                row++;
            }

            if(row>0){
                x=false;
            }
            else {
                x=true;
            }

        } catch (Exception e) {
            Toast.makeText(this, e.getMessage().toString(), Toast.LENGTH_SHORT).show();
        }
        return x;
    }
}
