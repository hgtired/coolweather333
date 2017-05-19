package com.example.huaigetired.coolweather888;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<String> dataList=new ArrayList<>();

    private List<String> provinces = Arrays.asList(new String[]{"北京市", "浙江省", "江西省","江苏省","福建省","安徽省","辽宁省"});
    private List<String>  city01 = Arrays.asList(new String[]{"北京"});
    private List<String>  city17 = Arrays.asList(new String[]{"杭州", "湖州", "嘉兴", "宁波", "绍兴"
            , "台州", "温州", "丽水", "金华", "衢州", "舟山"});
    private List<String>  city18 = Arrays.asList(new String[]{"南昌","九江","景德镇","萍乡","新余","鹰潭","赣州"});
    private List<String>  country1701 = Arrays.asList(new String[]{"瓯海", "永嘉","鹿城"});
    private List<String>  country1801 = Arrays.asList(new String[]{"东湖区","西湖区","青云谱区","青山湖区","湾里区" });
    //1表示最顶层省直辖市级，2表示市级，3表现县区级
    private int level=1;
    private Button backButton =null;
    private ListView listView =null;
    private ArrayAdapter adapter;


    //选中的省或者直辖市的序号（0,1,2...)
    private int selectedProvinceIndex=0;
    private int selectedCityIndex=1;
    private int selectedCountryIndex=2;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.backButton = (Button) findViewById(R.id.button);
        listView = (ListView) findViewById(R.id.list_view);
        this.dataList.addAll(this.provinces);
        adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, this.dataList);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            ///回调 callback method
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(level==1){
                    selectedProvinceIndex=position;
                    showCities(selectedProvinceIndex);
                }else if(level==2){
                    selectedCityIndex=position;
                    showCounties(selectedCityIndex);
                }else{
                    //TODO 选定区县，去显示天气界面
                    if(level==3){

                            showWeather("CN101210612");//黄岩

                    }
                }
        }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(level==3){
                    showCities(MainActivity.this.selectedProvinceIndex);
                }else if(level==2){
                    level=1;
                    backButton.setVisibility(View.GONE);
                    MainActivity.this.dataList.clear();
                    MainActivity.this.dataList.addAll(provinces);
                    MainActivity.this.adapter.notifyDataSetChanged();
                }



            }
        });
    }

    private void showCities(int position) {
        level=2;
        this.backButton.setVisibility(View.VISIBLE);
        this.dataList.clear();
        if("北京市".equals(this.provinces.get(position))){
            this.dataList.addAll(city01);
        }else{
            if("江西省".equals(this.provinces.get(position))){
                this.dataList.addAll(city18);
            }
            this.dataList.addAll(city17);
        }
        this.adapter.notifyDataSetChanged();
    }

    private void showCounties(int selectedCityIndex) {
        level=3;
        this.dataList.clear();
                            this.dataList.addAll(country1701);

        adapter.notifyDataSetChanged();
    }



private void showWeather(String weatherID)  {
    Intent intent =new Intent(Intent.ACTION_VIEW);
    intent.setData(Uri.parse("http://guolin.tech/api/weather?cityid="+weatherID+"&key=9690da4309f04edab1cafc6f70c151bd"));
     startActivity(intent);
}
}