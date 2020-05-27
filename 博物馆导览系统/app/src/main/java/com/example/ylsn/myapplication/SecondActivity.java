package com.example.ylsn.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import static com.example.ylsn.myapplication.MapActivity.quanjuID;

class Exihibition{
    public int midex;
    public void setmidex(int i){
        midex=i;
    }
    public int getmidex(){
        return midex;
    }
}
public class SecondActivity extends AppCompatActivity {
   private String str1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        Exihibition E=new Exihibition();
        E.setmidex(quanjuID);
        userMap(E);
    }
    public boolean userMap(final Exihibition E) {
        new Thread() {
            public void run() {
                try {
                    BufferedReader reader = null;
                    JSONObject userJSON = new JSONObject();
                    userJSON.put("midex", E.getmidex());
                    String content = String.valueOf(userJSON);
                    String url = "http://144.202.86.233:5000/api/Exhibitions/ExhibitionsOfMidex";
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
                   String result="";
                    String ffflag="";
                    while ((ffflag=reader.readLine())!=null)
                    { result+=ffflag;}
                    JSONObject obj=new JSONObject(result);
                    JSONArray obj1=obj.getJSONArray("search");
                    str1="";
                    for(int i=0;i<obj1.length();i++){
                        JSONObject obj2=obj1.getJSONObject(i);
                        str1=str1+obj2.getString("ename")+obj2.getString("eintro")+"\n\n";
                    }

                    TextView te=(TextView)findViewById(R.id.text0);
                    te.setText(ToDBC(str1));
                } catch (
                        Exception e)
                {
                }
            }
        }.start();
        return true;
    }
    public String ToDBC(String str1){
        char[] c=str1.toCharArray();
        for(int i=0;i<c.length;i++) {
            if (c[i] == 12288) {
                c[i] = (char) 32;
                continue;
            }
            if (c[i] > 65280 && c[i] < 65375) {
                c[i] = (char) (c[i] - 65248);
            }
        }
        return new String(c);
        }
    }
