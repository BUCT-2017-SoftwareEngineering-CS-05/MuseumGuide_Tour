package com.example.ylsn.myapplication;

import android.app.ActivityGroup;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import static com.example.ylsn.myapplication.MapActivity.quanju;
import static com.example.ylsn.myapplication.MapActivity.quanjuID;

public class NavigationActivity extends ActivityGroup implements View.OnClickListener{
    private static final String TAG="GroupActivity";
    private Bundle mBundle=new Bundle();
    private LinearLayout first,second,three,four,container;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);
        container=(LinearLayout)findViewById(R.id.line);
        first=(LinearLayout)findViewById(R.id.first);
        second=(LinearLayout)findViewById(R.id.second);
        three=(LinearLayout)findViewById(R.id.three);
        four=(LinearLayout)findViewById(R.id.four);
        first.setOnClickListener(this);
        second.setOnClickListener(this);
        three.setOnClickListener(this);
        four.setOnClickListener(this);
        mBundle.putString("tag",TAG);
        changeContainerView(first);
    }
    @Override
    public void onClick(View v){
        if(v.getId()==R.id.first||v.getId()==R.id.second||v.getId()==R.id.three||v.getId()==R.id.four){
            changeContainerView(v);
        }
    }
    private void changeContainerView(View v){
        first.setSelected(false);
        second.setSelected(false);
        three.setSelected(false);
        four.setSelected(false);
        v.setSelected(true);
        if(v==first){
            toActivity("first",FirstActivity.class);
        }
        else if(v==second){
            toActivity("second",SecondActivity.class);
        }
        else if(v==three){
            toActivity("three",ThreeActivity.class);
        }
        else if(v==four){
            toActivity("four",FourActivity.class);
        }
    }
    private void toActivity(String lable,Class<?> cls){
        Intent intent=new Intent(this,cls).putExtras(mBundle);
        container.removeAllViews();
        View v=getLocalActivityManager().startActivity(lable,intent).getDecorView();
        v.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        container.addView(v);
    }
}

