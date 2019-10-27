package com.example.sc;

import android.app.Activity;
import android.content.Context;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.WebSocket;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.ACCESS_WIFI_STATE;
import static android.Manifest.permission.CHANGE_WIFI_STATE;

public class MainActivity extends AppCompatActivity {
    private static final int MY_PERMISSIONS_REQUEST_READ_CONTACTS = 1;
    public String infoString="";
    private WifiManager wifiManager;
    private Element [] nets;
    private List<ScanResult> wifiList;
    public List<Element> ListOne=new ArrayList<>();
    public List<Element> ListSecond=new ArrayList<>();
    public PointWifi point_test;
    public static String Getstr="";
    public TextView txt33;
    public static WebSocket _ws;
    public static SocketManager sm = new SocketManager();;
    public static CallBack callBack;
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        txt33=(TextView)findViewById(R.id.tvQQ);
        String[] PERMS_INITIAL={
                ACCESS_WIFI_STATE,CHANGE_WIFI_STATE,ACCESS_COARSE_LOCATION
        };
        ActivityCompat.requestPermissions(this, PERMS_INITIAL, 1);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

             //  ListOne=detectWifi();
                detectWifi();
                Snackbar.make(view, "Сканирование...", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        FloatingActionButton fab2 = findViewById(R.id.fab2);
        fab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                _ws = sm.Connect();
                _ws.send("Get Zone:3007");
                txt33.setText(Getstr.split("/n")[0]);
              /*  new Thread(new Runnable() {
                    public void run() {
                        Get test =new Get();
                        try {
                          Getstr  = test.run("http://mrblackdog.ddns.net:48706/Log/GettingString?Id=3008");
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();*/

              //получаем ответ от серверa
             //  Getstr=GetString("http://mrblackdog.ddns.net:48706/Log/GettingString?Id=3008");


                //txt33.setText(Getstr);
                //String Gets="";
              //  ListSecond =detectWifi();
               // detectWifi2();



//               String Getstr=GetString("http://mrblackdog.ddns.net:48706/Log/GettingString?Id=3008");

                  //  point_test = new PointWifi(Getstr,"test1");

            //    AdapterDbElements adapterElements = new AdapterDbElements(MainActivity.this);
             //   ListView netList = (ListView) findViewById(R.id.listItem);
              //  netList.setAdapter(adapterElements);
               // int a=oveplap(ListOne,ListSecond);
             //   String as=Integer.toString(a);
              //  TextView tvSecurity = (TextView) item.findViewById(R.id.tvSecurity);

               // txt33.setText(as);
              //  txt33.setText(point_test.name.toString());
               // txt33.setText(Gets.split("\n")[0]);
                Snackbar.make(view, "Загрузка...", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

      //  requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, 1001);
      //  ActivityCompat.requestPermissions(this, PERMS_INITIAL, 1);
    }


    public void detectWifi2(){

        this.wifiManager = (WifiManager)getApplicationContext().getSystemService(Context.WIFI_SERVICE);

        this.wifiManager.startScan();
        this.wifiList = this.wifiManager.getScanResults();
        Log.d("TAG", wifiList.toString());

        this.nets = new Element[wifiList.size()];

        for (int i = 0; i < wifiList.size(); i++) {
            String item = wifiList.get(i).toString();
            String[] vector_item = item.split(",");
            String BSSID =  wifiList.get(i).BSSID;
            String item_essid = vector_item[0];
            String item_capabilities = vector_item[2];
            String item_level = vector_item[3];
            String ssid = item_essid.split(": ")[1];
            String security = item_capabilities.split(": ")[1];
            String level = item_level.split(":")[1];
            nets[i] = new Element(ssid,BSSID ,security, level);
            ListSecond.add(nets[i]);
        }
    }
    public void detectWifi(){

        this.wifiManager = (WifiManager)getApplicationContext().getSystemService(Context.WIFI_SERVICE);

        this.wifiManager.startScan();
        this.wifiList = this.wifiManager.getScanResults();
        Log.d("TAG", wifiList.toString());

        this.nets = new Element[wifiList.size()];

        for (int i = 0; i < wifiList.size(); i++) {
            String item = wifiList.get(i).toString();
            String[] vector_item = item.split(",");
            String BSSID =  wifiList.get(i).BSSID;
            String item_essid = vector_item[0];
            String item_capabilities = vector_item[2];
            String item_level = vector_item[3];
            String ssid = item_essid.split(": ")[1];
            String security = item_capabilities.split(": ")[1];
            String level = item_level.split(":")[1];
            nets[i] = new Element(ssid,BSSID ,security, level);
            ListOne.add(nets[i]);
        }

        AdapterElements adapterElements = new AdapterElements(this);
        ListView netList = (ListView) findViewById(R.id.listItem);
        netList.setAdapter(adapterElements);
    }

    public void OnSocketMessage(String s)
    {
        Toast.makeText(this,s,Toast.LENGTH_SHORT).show();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    class AdapterDbElements extends ArrayAdapter<Object> {
        Activity context;

        public AdapterDbElements(Activity context) {
            super(context, R.layout.items, point_test.WifiInfoList.toArray());
            this.context = context;
        }

        public View getView(int position, View convertView, ViewGroup parent){
            LayoutInflater inflater = context.getLayoutInflater();
            View item = inflater.inflate(R.layout.items, null);

            TextView tvSsid = (TextView) item.findViewById(R.id.tvSSID);
            tvSsid.setText(nets[position].getTitle());

            TextView tvBssid = (TextView) item.findViewById(R.id.tvBSSID);
            tvBssid.setText(nets[position].getBSSID());

            TextView tvSecurity = (TextView) item.findViewById(R.id.tvSecurity);
            tvSecurity.setText(nets[position].getSecurity());

            TextView tvLevel = (TextView) item.findViewById(R.id.tvLevel);
            String level = nets[position].getLevel();
            tvLevel.setText(level);

            return item;
        }

    }

    class AdapterElements extends ArrayAdapter<Object> {
        Activity context;

        public AdapterElements(Activity context) {
            super(context, R.layout.items, nets);
            this.context = context;
        }

        public View getView(int position, View convertView, ViewGroup parent){
            LayoutInflater inflater = context.getLayoutInflater();
            View item = inflater.inflate(R.layout.items, null);

            TextView tvSsid = (TextView) item.findViewById(R.id.tvSSID);
            tvSsid.setText(nets[position].getTitle());

            TextView tvBssid = (TextView) item.findViewById(R.id.tvBSSID);
            tvBssid.setText(nets[position].getBSSID());

            TextView tvSecurity = (TextView) item.findViewById(R.id.tvSecurity);
            tvSecurity.setText(nets[position].getSecurity());

            TextView tvLevel = (TextView) item.findViewById(R.id.tvLevel);
            String level = nets[position].getLevel();
            tvLevel.setText(level);

            return item;
        }

    }

    public int oveplap(List<Element> listBase , List<Element> comparisons)
    {
        int NumberofMenshion=0;
        for (Element itemBase :listBase)
        {
            for (Element item :comparisons)
            {
                if (item.getBSSID().equals(itemBase.getBSSID()))
                {
                    float diff=  Math.abs(Float.parseFloat(item.getLevel()) -Float.parseFloat(itemBase.getLevel()));
                    if (diff<10)
                    {
                        NumberofMenshion++;
                    }
                }
            }
        }
        return NumberofMenshion;
    }
// Создает Лист точек для сравнения со сканом, информация поступает с Сервака clientPointList()
    public List<PointWifi> clientPoinList()
    {
        List<PointWifi> pointWifisList=new ArrayList<>();
        //делаем запрос на получение количества точек
        //int sizePoint =GetKOlpoint
        int sizePoint=10;//для примера
        for (int i=0;i<sizePoint;i++)
        {
            String namePoint=Integer.toString(i); //название точки ( типо её координата )
            //посылаем запрос на получении листа wifiInfo точкиоди
            String srtingWifiInfo="";//прихт строка с информацией для примера
            pointWifisList.add(new PointWifi(namePoint,srtingWifiInfo));//формируем лист точек
        }
        return pointWifisList;
    }
 //возвращает индекс точки с максимальным совпадением сетей ReturnCoordinats
    public int ReturnCoordinats(List<Element> listElement,List<PointWifi> listPointWifi)
    {
        int max=0;
        int i=0;
        int number;
        int maxindex=0;
        for (PointWifi item:listPointWifi)
        {
            number=oveplap(item.WifiInfoList,listElement);
            if (number>max)
            {
                max=number;
                maxindex=i;
            }
            i++;
        }
        return maxindex;
    }

//работает не трогать!
    public String GetString(String url) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("http://mrblackdog.ddns.net:48706/Log/Getting?Id=3007")
                .build();
        //infoString=response.body().string();
        client.newCall(request).enqueue(new Callback() {

            @Override public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }
            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                    infoString =response.body().string();
            }
          //  @Override public void onResponse(Call call, Response response) throws IOException {
        //        if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);
      //          infoString = response.body().string();
             //   else  infoString = response.message().toString();
      //      }
        });

        Toast.makeText(this, infoString, Toast.LENGTH_SHORT).show();
        return infoString;
    }


    public void GetZone(float x, float y, float z){
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("http://mrblackdog.ddns.net:533/rawmeas/getMeasurments?Name=Client&X="+x+"&Y="+y+"&Z="+z)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override public void onResponse(Call call, Response response) throws IOException {
                if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);
            }
        });
    }


}

interface CallBack {
    void callingback();

}
    class APIResponseObject {
        int responseCode;
        String response;

        APIResponseObject(int responseCode, String response) {
            this.responseCode = responseCode;
            this.response = response;
        }
    }


