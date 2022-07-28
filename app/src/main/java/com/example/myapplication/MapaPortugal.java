package com.example.myapplication;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

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
import com.example.myapplication.model.PontuacaoModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MapaPortugal extends AppCompatActivity {

    ImageView Madeira;
    ImageView Algarve;
    ImageView CasteloBranco;
    ImageView Lisboa;
    PontuacaoModel pontuacaoModel;
    List<PontuacaoModel> pontuacaoModels;
    int idCidade;
    int idUser;
    int numTrofeus;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        pontuacaoModel=new PontuacaoModel();
        pontuacaoModels= new ArrayList<>();

        idCidade=1;
        idUser=1;
        numTrofeus=0;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mapa_portugal);

        Madeira = findViewById(R.id.imageView22);
        Algarve = findViewById(R.id.algarve);
        CasteloBranco = findViewById(R.id.imageView7);
        Lisboa = findViewById(R.id.imageView21);


        Cache cache = new DiskBasedCache(getCacheDir(),1024*1024);
        Network network = new BasicNetwork(new HurlStack());
        RequestQueue requestQueue = new RequestQueue(cache,network);
        requestQueue.start();

        for(int i =0; i<23; i++) {
            // http://192.168.1.105:8080/monumento/1/all
            String url = getString(R.string.BASE_URL) + "pontuacao/get/" + idUser + "/" + i;

            System.out.println("URL:"+url);

            try {

                Response.Listener<JSONObject> sucessListener = new Response.Listener<JSONObject>() {

                    @SuppressLint("ResourceType")
                    @RequiresApi(api = Build.VERSION_CODES.O)
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            pontuacaoModel.setId_utilizador(response.getInt("id_utilizador"));
                            pontuacaoModel.setNrespostasCertas(response.getInt("nrespostasCertas"));
                        if( pontuacaoModel.getNrespostasCertas()==null)
                            numTrofeus=0;
                        else
                            numTrofeus=pontuacaoModel.getNrespostasCertas();

                            if(numTrofeus > 0) {
                                Algarve.setImageResource(R.drawable.medal);

                            }else {

                                Algarve.setVisibility(View.INVISIBLE);
                            }

                            requestQueue.stop();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };

                Response.ErrorListener errorListener = new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), "Por favor valide novamente os valores! " + error, Toast.LENGTH_LONG).show();

                    }
                };

                JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, sucessListener, errorListener);
                requestQueue.add(request);

            } catch (Exception ex) {
                Toast.makeText(getApplicationContext(), "" + ex + "", Toast.LENGTH_LONG).show();
            }
        }









      CasteloBranco.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        // mostrador.setText(textNome.getText().toString());
                        Intent i = new Intent(MapaPortugal.this,CidadeSelecionada.class);
                        //i.putExtra("id",id);
                        startActivity(i);


                    }
                }
        );


        Madeira.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        // mostrador.setText(textNome.getText().toString());
                        Intent i = new Intent(MapaPortugal.this,Menu.class);
                        //i.putExtra("id",id);
                        startActivity(i);
                    }
                }
        );

        Algarve.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //test.getDrawable().getConstantState().equals(getResources().getDrawable(R.drawable.red).getConstantState()))
                       if(Algarve.getDrawable().getConstantState().equals(getResources().getDrawable(R.drawable.medalnao).getConstantState())) {
                           Algarve.setImageResource(R.drawable.medal);

                       }else {
                          // Algarve.setImageResource(R.drawable.medalnao);
                           Algarve.setVisibility(View.INVISIBLE);
                       }
                    }
                }
        );

    }
}