package com.example.myapplication;


import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.ResultReceiver;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.myapplication.gps_aplication.Constants;
import com.example.myapplication.gps_aplication.FetchAddressIntentService;
import com.example.myapplication.model.CidadeModel;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;

import org.json.JSONException;
import org.json.JSONObject;

public class SplashActivity extends AppCompatActivity {
    private static final int REQUEST_CODE_LOCATION_PERMISSION = 1;
    private String mensagem;
    private ResultReceiver resultReceiver;
    Boolean verification = false;;
    ImageView imagemFundo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_splash);

        resultReceiver = new AddressResultReceiver(new Handler());
        imagemFundo= findViewById(R.id.imagemFundo1);



        if (ContextCompat.checkSelfPermission(
                getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                    SplashActivity.this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    REQUEST_CODE_LOCATION_PERMISSION
            );
        } else {
            getCurrentLocation();
        }


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE_LOCATION_PERMISSION && grantResults.length > 0) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getCurrentLocation();
            } else {
                Toast.makeText(this, "Permission denied!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void getCurrentLocation() {




        LocationRequest locationRequest = new LocationRequest();
        locationRequest.setInterval(10000);
        locationRequest.setFastestInterval(3000);
        locationRequest.setPriority(locationRequest.PRIORITY_HIGH_ACCURACY);




        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
          return;
        }
        LocationServices.getFusedLocationProviderClient(SplashActivity.this)
                .requestLocationUpdates(locationRequest, new LocationCallback() {


                    @Override
                    public void onLocationResult(LocationResult locationResult) {
                        super.onLocationResult(locationResult);
                        LocationServices.getFusedLocationProviderClient(SplashActivity.this)
                                .removeLocationUpdates(this);

                        verification =true;


                        if(locationResult != null && locationResult.getLocations().size() > 0){
                            int latestLocationIndex = locationResult.getLocations().size() - 1;
                            double latitude = locationResult.getLocations().get(latestLocationIndex).getLatitude();
                            double longitude = locationResult.getLocations().get(latestLocationIndex).getLongitude();




                            Location location = new Location("providerNA");
                            location.setLatitude(latitude);
                            location.setLongitude(longitude);
                            fetchAddrressFromLatLog(location);

                        }else{


                        }

                    }

                }, Looper.getMainLooper());



    }

    private void fetchAddrressFromLatLog(Location location){



        Intent intent = new Intent(this, FetchAddressIntentService.class);
        intent.putExtra(Constants.RECEIVER,resultReceiver);
        intent.putExtra(Constants.LOCATION_DATA_EXTRA,location);
        startService(intent);




    }

    private class AddressResultReceiver extends ResultReceiver {
        AddressResultReceiver(Handler handler) {
            super(handler);
        }

        @Override
        protected void onReceiveResult(int resultCode, Bundle resultData){
            super.onReceiveResult(resultCode,resultData);


            if(resultCode == Constants.SUCCESS_RESULT){

                mensagem=resultData.getString(Constants.RESULT_DATA_KEY);
                String[] separated = mensagem.split(",");
                String[] SplitnomeCidade=separated[1].split(" ");
                    System.out.println("cidade split: " + SplitnomeCidade[2]);
                String nomeCidade="";

                    if(SplitnomeCidade.length==3)
                        nomeCidade=SplitnomeCidade[2];
                    else if(SplitnomeCidade.length==4)
                        nomeCidade=SplitnomeCidade[2]+"_"+SplitnomeCidade[3];

                imagemFundo.setImageDrawable(getDrawable(Utils.getBackgroundImage(nomeCidade)));
                System.out.println("O nome da cidade e:"+nomeCidade);
                GetCidade(nomeCidade);

            }else {
                GetCidade("Castelo Branco");
            }

        }
    }

    public void GetCidade(String cidade) {

        Cache cache = new DiskBasedCache(getCacheDir(),1024*1024);
        Network network = new BasicNetwork(new HurlStack());
        RequestQueue requestQueue = new RequestQueue(cache,network);
        requestQueue.start();
        if(cidade.contains(" "))
            cidade= cidade.substring(0,cidade.lastIndexOf(" ")) + "_"+
                    cidade.substring(cidade.lastIndexOf(" ")+1);

        String url = getString(R.string.BASE_URL)+"cidade/"+cidade;

        System.out.println("URL:"+url);


        try {

            Response.Listener<JSONObject> sucessListener = new Response.Listener<JSONObject>() {

                @RequiresApi(api = Build.VERSION_CODES.O)
                @Override
                public void onResponse(JSONObject response) {
                    try {


                        CidadeModel cidadeModel = new CidadeModel();
                        cidadeModel.setId_Cidade(response.getInt("id_Cidade"));
                        cidadeModel.setNome(response.getString("nome"));
                        cidadeModel.setDescricao(response.getString("descricao"));
                        cidadeModel.setId_Regiao(response.getInt("id_Regiao"));

                        Intent i = new Intent(SplashActivity.this,Login.class);
                        i.putExtra("cidade",cidadeModel);
                        startActivity(i);
                        requestQueue.stop();
                    } catch (JSONException e) {
                        Toast.makeText(getApplicationContext(), "Por favor certiique-se que o GPS está ligado e reinicie a aplicação.", Toast.LENGTH_LONG).show();
                    }
                }
            };

            Response.ErrorListener errorListener = new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getApplicationContext(), "Por favor certiique-se que o GPS está ligado e reinicie a aplicação.", Toast.LENGTH_LONG).show();

                }
            };

            JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, sucessListener, errorListener) ;
            requestQueue.add(request);

        } catch(Exception ex){
            Toast.makeText(getApplicationContext(), "" + ex + "", Toast.LENGTH_LONG).show();
        }

    }
}