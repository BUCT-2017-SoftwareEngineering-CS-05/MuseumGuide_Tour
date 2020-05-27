package com.example.ylsn.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import static com.example.ylsn.myapplication.MapActivity.quanjuID;

class Maintable{
    public int midex;
    public void setmidex(int i){
        midex=i;
    }
    public int getmidex(){
        return midex;
    }
}
public class FirstActivity extends AppCompatActivity {
     private String result0;
    private String result1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);
        Maintable maintable=new Maintable();
        maintable.setmidex(quanjuID);
        userMap(maintable);
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
    public boolean userMap(final Maintable maintable) {
        new Thread() {
            public void run() {
                try {
                    BufferedReader reader = null;
                    JSONObject userJSON = new JSONObject();
                    userJSON.put("midex", maintable.getmidex());
                    String content = String.valueOf(userJSON);
                    String url = "http://144.202.86.233:5000/api/Maintables/MuseumFirstPageId";
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
                    result0="";
                    String ffflag="";
                    String result="";
                    while ((ffflag=reader.readLine())!=null)
                    { result+=ffflag;}
                    JSONObject obj=new JSONObject(result);
                    result0=obj.getString("mname");
                    result1=obj.getString("mbase");
                    Button t0=(Button) findViewById(R.id.title0);
                    t0.setText(result0);
                    TextView te=(TextView)findViewById(R.id.text0);
                    te.setText(ToDBC(result1));
                } catch (
                        Exception e)
                {
                }
            }
        }.start();
        return true;
    }
}
