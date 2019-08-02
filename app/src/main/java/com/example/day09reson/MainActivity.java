package com.example.day09reson;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    IteamAddapter iteamAddapter;
    List<Product> products;
    //BTVN
    JSonBTVN BTVN;
    TextView tvWeather,tvMain;
    Coord coord_OBJ;
    List<Weather> weather_OBJs;
    Main main_OBJ;
    Sys sys_OBJ;
    Wind wind_OBJ;
    Cloud clouds_OBJ;


//    String json = "{ \"id\": \"144\", \"publisher_id\": \"3\", \"content_type\": \"3\"," +
//            " \"tab\": \"0\", \"title\": \"Chinese Series\", \"avatar\": null," +
//            " \"status\": \"1\", \"deleted\": \"0\", \"user_created\": \"0\"," +
//            " \"parent_id\": \"1\", \"lft\": \"80\", \"rgt\": \"81\" }";

    String j = "[ { id: \"id001\", name: \"name001\", price: \"123\" }, { id: \"id002\"," +
            " name: \"name002\", price: \"123\" } , { id: \"id003\", name: \"name003\", price: \"123\" } ]";

    String jsonBTVN = "{\"coord\": { \"lon\": 139,\"lat\": 35}," +
    " \"weather\": [ { \"id\": 800, \"main\": \"Clear\", \"description\": \"clear sky\", \"icon\": \"01n\" } ]," +
            " \"base\": \"stations\"," +
    " \"main\": { \"temp\": 289.92, \"pressure\": 1009, \"humidity\": 92, \"temp_min\": 288.71, \"temp_max\": 290.93 }," +
            " \"wind\": { \"speed\": 0.47, \"deg\": 107.538 }," +
            " \"clouds\": { \"all\": 2 }," +
            " \"dt\": 1560350192," +
            " \"sys\": { \"type\": 3, \"id\": 2019346, \"message\": 0.0065, \"country\": \"JP\", \"sunrise\": 1560281377, \"sunset\": 1560333478 }," +
            " \"timezone\": 32400," +
            " \"id\": 1851632," +
            " \"name\": \"Shuzenji\"," +
            " \"cod\": 200 }";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvMain = findViewById(R.id.tvMain);
        tvWeather = findViewById(R.id.tvWeather);
        //Json Array
        products = new ArrayList<>();
        try {
            JSONArray productJsonArray = new JSONArray(j);

            for (int i = 0; i < productJsonArray.length(); i++) {
                JSONObject ob = productJsonArray.getJSONObject(i);

                String id = ob.getString("id");
                String name = ob.getString("name");
                String price = ob.getString("price");

                products.add(new Product(id, name, price));


            }
            //tvRvList.setText(products.get(0).getName());

            //tvRvList.setText(products.size() +"\n " + products.get(0).getName());

            //Toast.makeText(getBaseContext(),products.size() + "\n" + products.get(0).getName(),Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            e.printStackTrace();
        }

        //RvList
        recyclerView = findViewById(R.id.rvList);

        iteamAddapter = new IteamAddapter(products);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(), RecyclerView.VERTICAL, false);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(iteamAddapter);

        //BTVN
        try{
            JSONObject climate = new JSONObject(jsonBTVN);

            JSONObject coord = climate.getJSONObject("coord");
            int coord_lon = coord.getInt("lon");
            int coord_lat = coord.getInt("lat");
            coord_OBJ = new Coord(coord_lon,coord_lat);

            JSONArray weathers = climate.getJSONArray("weather");
            for(int i=0; i< weathers.length();i++){
                JSONObject weather = weathers.getJSONObject(i);
                String weather_id = weather.getString("id");
                String weather_main = weather.getString("main");
                String weather_des = weather.getString("description");
                String weather_icon = weather.getString("icon");
                weather_OBJs.add(new Weather(weather_id,weather_main,weather_des,weather_icon));
            }

            String base = climate.getString("base");
            JSONObject main = climate.getJSONObject("main");
            double main_temp = main.getDouble("temp");
            long main_pressure = main.getLong("pressure");
            long main_humidity = main.getLong("humidity");
            double main_temp_min = main.getDouble("temp_min");
            double main_temp_max = main.getDouble("temp_max");
            main_OBJ = new Main(main_temp,main_pressure,main_humidity,main_temp_min,main_temp_max);


            JSONObject wind = climate.getJSONObject("wind");
            double wind_speed = wind.getDouble("speed");
            double wind_deg = wind.getDouble("deg");
            wind_OBJ = new Wind(wind_speed,wind_deg);

            JSONObject clouds = climate.getJSONObject("clouds");
            int cloud_all = clouds.getInt("all");
            clouds_OBJ = new Cloud(cloud_all);

            String dt = climate.getString("dt");

            JSONObject sys =climate.getJSONObject("sys");
            String sys_type = sys.getString("type");
            String sys_id = sys.getString("id");
            String sys_message = sys.getString("message");
            String sys_country = sys.getString("country");
            String sys_sunrise = sys.getString("sunrise");
            String sys_sunset = sys.getString("sunset");
            sys_OBJ = new Sys(sys_type,sys_id, sys_message,sys_country,sys_sunrise,sys_sunset);

            String timeZone = climate.getString("timezone");
            String id = climate.getString("id");
            String mane = climate.getString("name");
            String cod = climate.getString("cod");

            BTVN = new JSonBTVN(coord_OBJ, (Weather) weather_OBJs, base, main_OBJ, wind_OBJ, clouds_OBJ, dt, sys_OBJ, timeZone, id, mane, cod);

            tvWeather.setText(weather_OBJs.get(0).getId()+"\n"+BTVN.getMain().getTemp());
        }catch (Exception ex){
            ex.printStackTrace();
        }

        Toast.makeText(MainActivity.this,jsonBTVN,Toast.LENGTH_LONG).show();


    }


    //        try {
//            JSONObject object = new JSONObject(json);
//
//            String id = object.getString("id");
//            String publisher_id = object.getString("publisher_id");
//            String content_type = object.getString("content_type");
//            String tab = object.getString("tab");
//            String title = object.getString("title");
//            String avatar = object.getString("avatar");
//            String status = object.getString("status");
//            String user_created = object.getString("user_created");
//            String parent_id = object.getString("parent_id");
//            String lft = object.getString("lft");
//            String rgt = object.getString("rgt");
//
//            tv.setText("id: " + id + "\n" + "publisher_id: " + publisher_id + "content_type: " + content_type);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    //json Array



}
