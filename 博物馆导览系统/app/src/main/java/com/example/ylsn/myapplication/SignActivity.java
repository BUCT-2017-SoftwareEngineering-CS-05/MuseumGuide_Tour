package com.example.ylsn.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.OutputStream;
import java.net.URL;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;

public class SignActivity extends AppCompatActivity {
    public char result;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign);
    }

    public void onClick(View view) {
        EditText phone = (EditText) findViewById(R.id.count);
        EditText pwd = (EditText) findViewById(R.id.password);
        EditText nick = (EditText) findViewById(R.id.email);
        String Userid = phone.getText().toString();
        String Userpwd = pwd.getText().toString();
        String Nickname = nick.getText().toString();
        User user = new User();
        user.setNickname(Nickname);
        user.setUserid(Userid);
        user.setUserpwd(Userpwd);
        userRegister(user);
    }

    public boolean userRegister(final User user) {
        new Thread() {
            public void run() {
                try {
                    BufferedReader reader = null;
                    JSONObject userJSON = new JSONObject();
                    userJSON.put("userid", user.getUserid());
                    userJSON.put("userpwd", user.getUserpwd());
                    userJSON.put("nickname", user.getUserNickname());
                    String content = String.valueOf(userJSON);
                    String url = "http://144.202.86.233:5000/api/Users/Create";
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
                    JSONObject obj=new JSONObject(reader.readLine());
                    result=obj.getString("status").charAt(0);
                    TextView textflag=(TextView) findViewById(R.id.textflag);
                    if(result=='1'){
                        textflag.setText("注册成功！！！");
                      }
                    if(result=='0') {
                        textflag.setText("该账号已经被注册！！！");
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
  class User{
      public String Userid;
      public String Userpwd;
      public String Nickname;
      public void setUserid(String id){
          Userid=id;
      }
      public void setUserpwd(String pwd){
          Userpwd=pwd;
      }
      public void setNickname(String nick){
          Nickname=nick;
      }
      public String getUserpwd(){
          return Userpwd;
      }
      public String getUserNickname(){
          return Nickname;
      }
      public String getUserid(){
          return Userid;
      }
  }

