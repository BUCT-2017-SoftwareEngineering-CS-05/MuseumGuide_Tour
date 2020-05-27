package com.example.ylsn.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import static com.example.ylsn.myapplication.MapActivity.quanjuID;

public class ThreeActivity extends AppCompatActivity {
private String result;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_three);
        userMap();
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
    public boolean userMap() {
        new Thread() {
            public void run() {
                try {
                    BufferedReader reader = null;
                    String url = "http://144.202.86.233:5000/api/Collections/CollectionAllDetails?midex="+quanjuID;
                    HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
                    connection.setRequestMethod("GET");
                    connection.setUseCaches(false);
                    reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    result="";
                    String ffflag="";
                    while ((ffflag=reader.readLine())!=null)
                    { result+=ffflag;}
                    JSONObject obj=new JSONObject(result);
                    JSONArray enen=obj.getJSONArray("content");
                    String str1="";
                    for(int i=0;i<enen.length();i++){
                        JSONObject obj2=enen.getJSONObject(i);
                /*        TextView t0=new TextView(ThreeActivity.this);
                        t0.setTextColor(getResources().getColor(R.color.white));
                        t0.setTextSize(20);
                        t0.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                        t0.setText(obj2.getString("oname"));
                        line.addView(t0);
                        TextView t1=new TextView(ThreeActivity.this);
                        t1.setTextColor(getResources().getColor(R.color.white));
                        t1.setTextSize(15);
                        t1.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                        t1.setText(obj2.getString("oname"));
                        line.addView(t1); */
                    str1=str1+obj2.getString("oname")+obj2.getString("ointro")+"\n\n";
                    }
                    TextView t0=(TextView)findViewById(R.id.text0);
                    t0.setText(ToDBC(str1));
                }
                catch (Exception e)
                {
                }
            }
        }.start();
        return true;
    }
}

