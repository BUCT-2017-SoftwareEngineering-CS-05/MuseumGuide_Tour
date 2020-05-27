package com.example.ylsn.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private String userid;
    private String userpwd;
    private char result;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void onClick(View view){
        if(view==findViewById(R.id.login)){
            userLogin();
        }
        if(view==findViewById(R.id.found)){
            Intent login_to_found=new Intent(this,FoundActivity.class);
            startActivity(login_to_found);}
        if(view==findViewById(R.id.sig)){
            Intent login_to_sign=new Intent(this,SignActivity.class);
            startActivity(login_to_sign);
        }
    }
    public boolean userLogin() {
        new Thread() {
            public void run() {
                try {
                    EditText Userid0=(EditText)findViewById(R.id.account);
                    EditText Userpwd0=(EditText)findViewById(R.id.pass0);
                    userid=Userid0.getText().toString();
                    userpwd=Userpwd0.getText().toString();
                    JSONObject jsonObj=new JSONObject();
                    JSONObject userjson1=new JSONObject();
                    jsonObj.put("userid",userid);
                    jsonObj.put("userpwd",userpwd);
                    BufferedReader reader = null;
                    String content = String.valueOf(jsonObj);
                    String url = "http://144.202.86.233:5000/api/Users/Login";
                    HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
                    connection.setRequestMethod("POST");
                    connection.setDoOutput(true);
                    connection.setDoInput(true);
                    connection.setUseCaches(false);
                    connection.setRequestProperty("Connection", "Keep-Alive");
                    connection.setRequestProperty("Charset", "UTF-8");
                    // 设置文件类型:
                    connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
                    OutputStream os = connection.getOutputStream();
                    os.write(content.getBytes());
                    os.flush();
                    os.close();
                    reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    JSONObject obj=new JSONObject(reader.readLine().toString());
                    result=obj.getString("status").toString().charAt(0);
                    TextView textflag=(TextView) findViewById(R.id.textflag);
                    if(result=='1'){
                        textflag.setText("登录成功！！！");
                        Intent login_to_map = new Intent(MainActivity.this, MapActivity.class);
                        startActivity(login_to_map);
                    }
                    if(result=='0') {
                        textflag.setText("登录失败！！！");
                    }
                } catch (
                        Exception e)
                {
                }
            }
        }.start();
        return true;
    }
}
