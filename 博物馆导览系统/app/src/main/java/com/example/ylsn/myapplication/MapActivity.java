package com.example.ylsn.myapplication;
import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.LayoutInflaterCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.MapView;
import com.amap.api.services.geocoder.GeocodeAddress;
import com.amap.api.services.geocoder.GeocodeQuery;
import com.amap.api.services.geocoder.GeocodeResult;
import com.amap.api.services.geocoder.GeocodeSearch;
import com.amap.api.services.geocoder.RegeocodeResult;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;
import java.util.Locale;

import static com.amap.api.col.stln3.uc.i;
class Exhibition{
    public int Eid;
    public int Midex;
    public String Ename;
    public String Eintro;
    public void setEname(String name){
        Ename=name;
    }
    public void setEintro(String pwd){
        Eintro=pwd;
    }
    public void setEid(int nick){
        Eid=nick;
    }
    public void setMidex(int nick){
        Midex=nick;
    }
    public String getEname(){
        return Ename;
    }
    public String getEintro(){
        return Eintro;
    }
    public int getEid(){
        return Eid;
    }
    public int getMidex(){
        return Midex;
    }
}
class Myadapter<T> extends ArrayAdapter{
    public Myadapter(@NonNull Context context, int resource, @NonNull String[] objects) {
        super(context, resource, objects);
    }

    @Override
    public int getCount() {
        //返回数据的统计数量，大于0项则减去1项，从而不显示最后一项
        int i = super.getCount();
        return i>0?i-1:i;
    }
}
public class MapActivity extends AppCompatActivity{
public static String quanju;
    public static int quanjuID;
    private MapView mapView = null;
    private AMap aMap = null;
    private Spinner spin;
    private double latitude,lat;
    private double longitude,lng;
    private String str0;
    private double min;
    private int flag=0;
    private int flag0=1;
    private String result;
    private String[] Adapt={"故宫博物院","中国科学技术馆","中国地质博物馆","中国人民革命军事博物馆","中国航空博物馆","北京鲁迅博物馆","首都博物馆","北京自然博物馆","中国人民抗日战争纪念馆",
        "周口店猿人遗址博物馆",
        "中国国家博物馆",
        "中国农业博物馆",
        "北京天文馆",
        "文化部恭王府博物馆",
            "天津博物馆",
        "天津自然博物馆",
        "周恩来邓颖超纪念馆",
        "河北博物院",
        "西柏坡纪念馆",
        "邯郸市博物馆",
        "山西博物院",
        "中国煤炭博物馆",
        "八路军太行纪念馆",
        "内蒙古博物院",
        "鄂尔多斯博物馆",
        "辽宁省博物馆",
        "九·一八”历史博物馆",
        "旅顺博物馆",
        "沈阳故宫博物院",
        "吉林省自然博物馆",
        "吉林省博物院",
        "伪满皇宫博物院",
        "东北烈士纪念馆",
        "铁人王进喜纪念馆",
        "爱辉历史陈列馆",
        "黑龙江省博物馆",
        "大庆博物馆",
        "上海博物馆",
        "上海鲁迅纪念馆",
        "中共一大会址纪念馆",
        "上海科技馆",
        "陈云纪念馆",
        "南京博物院",
        "侵华日军南京大屠杀遇难同胞纪念馆",
        "南通博物苑",
        "苏州博物馆",
        "扬州博物馆",
        "常州博物馆",
        "南京市博物总馆",
        "浙江省博物馆",
        "浙江自然博物馆",
        "中国丝绸博物馆",
        "宁波博物馆",
        "杭州博物馆",
        "温州博物馆",
        "安徽省博物馆",
        "安徽中国徽州文化博物馆",
        "福建博物院",
        "古田会议纪念馆",
        "泉州海外交通史博物馆", "中国闽台缘博物馆",
        "中央苏区（闽西）历史博物馆",
        "井冈山革命博物馆",
        "江西省博物馆",
        "瑞金中央革命根据地纪念馆",
        "南昌八一起义纪念馆","安源路矿工人运动纪念馆",
"青岛市博物馆", "中国甲午战争博物馆",
        "青州博物馆",
        "山东博物馆",
        "烟台市博物馆",
        "潍坊市博物馆",
        "河南博物院",
        "郑州博物馆",
        "洛阳博物馆",
        "南阳汉画馆",
        "开封市博物馆",
        "鄂豫皖苏区首府革命博物馆",
        "湖北省博物馆",
        "荆州博物馆",
        "武汉博物馆",
        "辛亥革命武昌起义纪念馆",
        "湖南省博物馆",
        "韶山毛泽东故居纪念馆",
        "刘少奇故居纪念馆",
        "长沙简牍博物馆",
        "广东省博物馆",
        "西汉南越王博物馆",
        "孙中山故居纪念馆",
        "深圳博物馆",
        "广州博物馆",
        "广东民间工艺博物馆",
        "广西壮族自治区博物馆",
        "广西民族博物馆",
        "海南省博物馆",
        "自贡恐龙博物馆",
        "三星堆博物馆",
        "成都武侯祠博物馆",
        "邓小平故居陈列馆",
        "成都杜甫草堂博物馆",
        "四川博物院",
        "成都金沙遗址博物馆",
        "自贡市盐业历史博物馆",
        "遵义会议纪念馆",
        "云南省博物馆",
        "云南民族博物馆",
        "重庆中国三峡博物馆",
        "重庆红岩革命历史博物馆",
        "重庆自然博物馆",
        "西藏博物馆",
        "陕西历史博物馆",
        "秦始皇兵马俑博物馆",
        "延安革命纪念馆",
        "汉阳陵博物馆",
        "西安碑林博物馆",
        "西安半坡博物馆",
        "西安博物院",
        "宝鸡青铜器博物院",
        "西安大唐西市博物馆",
        "甘肃省博物馆",
        "天水市博物馆",
        "敦煌研究院",
        "固原博物馆",
        "宁夏博物馆",
        "青海省博物馆",
        "新疆维吾尔自治区博物馆",
        "吐鲁番博物馆","请选择您感兴趣的博物馆"};

    public boolean userMap(final Exhibition E) {
        new Thread() {
            public void run() {
                try {
                    BufferedReader reader = null;
                    JSONObject userJSON = new JSONObject();
                    userJSON.put("eid", E.getEid());
                    userJSON.put("midex", E.getMidex());
                    userJSON.put("ename", E.getEname());
                    userJSON.put("eintro", E.getEintro());
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
                    result="";
                    String ffflag="";
                    while ((ffflag=reader.readLine())!=null)
                    { result+=ffflag;}
                    JSONObject obj=new JSONObject(result);
                    JSONArray obj1=obj.getJSONArray("search");
                    JSONObject obj2=obj1.getJSONObject(0);
                    String sss="";
                        sss = "您现在正处于" + E.getEname() + ",该博物馆近期会有" + obj2.getString("ename") + "、"+obj1.getJSONObject(1).getString("ename")+"等活动，想要了解更多关于该博物馆的信息，请进入该博物馆进行了解";

                    TextView view0=(TextView) findViewById(R.id.text);
                    view0.setText(sss);
                    flag0=0;
                    quanju=E.getEname();
                } catch (
                        Exception e)
                {
                }
            }
        }.start();
        return true;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        mapView = (MapView) findViewById(R.id.map);
        //保存地图状态
        mapView.onCreate(savedInstanceState);
        if(aMap == null) {
            //显示地图
            aMap = mapView.getMap();
        }

        spin=(Spinner)findViewById(R.id.spinner);
        Myadapter ada = new Myadapter(this, android.R.layout.simple_spinner_dropdown_item, Adapt);
      //  ada.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin.setAdapter(ada);
        spin.setSelection(Adapt.length-1,true);
        spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Spinner sp;
                sp = (Spinner) findViewById(R.id.spinner);
                str0=sp.getSelectedItem().toString();
                getGeoPointBystr(str0);
                quanjuID=position+1;
                flag0=1;
                Exhibition E = new Exhibition();
                E.setMidex(position+1);
                E.setEname(str0);
                userMap(E);
                if(flag0==1){
                    String sss="";
                    sss = "您现在正处于" + E.getEname() + ",该博物馆近期没有什么活动，想要了解更多关于该博物馆的信息，请进入该博物馆进行了解";
                    TextView view0=(TextView) findViewById(R.id.text);
                    view0.setText(sss);
                    quanju=E.getEname();
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
    public Address getGeoPointBystr(String str) {
        Address address_temp = null;
        if (str != null) {
            Geocoder gc = new Geocoder(MapActivity.this, Locale.CHINA);
            List<Address> addressList = null;
            try {

                addressList = gc.getFromLocationName(str, 1);
                if (!addressList.isEmpty()) {
                    address_temp = addressList.get(0);
// 计算经纬度
                     latitude = address_temp.getLatitude();
                     longitude = address_temp.getLongitude();
// 生产GeoPoint
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return address_temp;
    }

    public static boolean isInstallApk(Context context, String pkgname) {
        List<PackageInfo> packages = context.getPackageManager().getInstalledPackages(0);
        for (int i = 0; i < packages.size(); i++) {
            PackageInfo packageInfo = packages.get(i);
            if (packageInfo.packageName.equals(pkgname)) {
                return true;
            } else {
                continue;
            }
        }
        return false;
    }
    private void goBaiduMap(Context context,double latitude, double longtitude, String address) {
        if (isInstallApk(context, "com.baidu.BaiduMap")) {
            try {
                Intent intent = Intent.getIntent("intent://map/direction?destination=latlng:"
                        + latitude + ","
                        + longtitude + "|name:" + address + //终点：该地址会在导航页面的终点输入框显示
                        "&mode=driving&" + //选择导航方式 此处为驾驶
                        "region=" + //
                        "&src=#Intent;scheme=bdapp;package=com.baidu.BaiduMap;end");
                context.startActivity(intent);
            } catch (URISyntaxException e) {
                Log.e("goError", e.getMessage());
            }
        } else {
            Toast.makeText(context, "您尚未安装百度地图", Toast.LENGTH_SHORT).show();
        }
    }
    private void goGaodeMap(Context context,double latitude, double longtitude, String address) {
        if (isInstallApk(context, "com.autonavi.minimap")) {
            try {
                Intent intent = Intent.getIntent("androidamap://navi?sourceApplication=&poiname=" + address + "&lat=" + latitude
                        + "&lon=" + longtitude + "&dev=0");
                context.startActivity(intent);
            } catch (URISyntaxException e) {
                Log.e("goError", e.getMessage());
            }
        } else {
            Toast.makeText(context, "您尚未安装高德地图", Toast.LENGTH_SHORT).show();
        }
    }
    public void onClick(View v){
        if(v==findViewById(R.id.in)){
            Intent intent=new Intent(MapActivity.this,NavigationActivity.class);
            startActivity(intent);

        }
        if(v==findViewById(R.id.direct)){
            if (isInstallApk(MapActivity.this, "com.baidu.BaiduMap")){
                goBaiduMap(MapActivity.this,latitude,longitude,str0);
                return;
            }
            if (isInstallApk(MapActivity.this, "com.autonavi.minimap")){
                  goGaodeMap(MapActivity.this,latitude,longitude,str0);
            }
        }
    }
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }

}
