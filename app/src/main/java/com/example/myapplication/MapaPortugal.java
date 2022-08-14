package com.example.myapplication;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.JsonArrayRequest;
import com.example.myapplication.model.CidadeModel;
import com.example.myapplication.model.CidadeSelecionadaModel;
import com.example.myapplication.model.PontuacaoModel;
import com.example.myapplication.model.UserModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MapaPortugal extends AppCompatActivity {


    ImageView VianaCastelo;
    ImageView Braga;
    ImageView VilaReal;
    ImageView Braganca;
    ImageView Porto;
    ImageView Aveiro;
    ImageView Viseu;
    ImageView Guarda;
    ImageView Coimbra;
    ImageView CasteloBranco;
    ImageView Leiria;
    ImageView Santarem;
    ImageView Portalegre;
    ImageView Lisboa;
    ImageView Evora;
    ImageView Setubal;
    ImageView Beja;
    ImageView Faro;
    ImageView Madeira;
    ImageView Acores;

    PontuacaoModel pontuacaoModel;
    List<PontuacaoModel> pontuacaoModels;
    int numTrofeus;

    UserModel userModel;
    CidadeModel cidadeModel;
    DialogUser dialogUser;
    ArrayList<CidadeSelecionadaModel> cidadeSelecionada;



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        pontuacaoModel=new PontuacaoModel();
        pontuacaoModels= new ArrayList<>();
        cidadeSelecionada=  new ArrayList<CidadeSelecionadaModel>();

        numTrofeus=0;

        userModel = (UserModel) getIntent().getExtras().get("user");
        cidadeModel=(CidadeModel) getIntent().getExtras().get("cidade");

        dialogUser = new DialogUser(this,getApplication(),userModel, cidadeModel);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mapa_portugal);

         VianaCastelo= findViewById(R.id.vianaCastelo);
         Braga= findViewById(R.id.braga);
         VilaReal= findViewById(R.id.vilaReal);
         Braganca= findViewById(R.id.braganca);
         Porto= findViewById(R.id.porto);
         Aveiro= findViewById(R.id.aveiro);
         Viseu= findViewById(R.id.viseu);
         Guarda= findViewById(R.id.guarda);
         Coimbra= findViewById(R.id.imagemCidade);
         CasteloBranco= findViewById(R.id.castBranco);
         Leiria= findViewById(R.id.leiria);
         Santarem= findViewById(R.id.santarem);
         Portalegre= findViewById(R.id.portalegre);
         Lisboa= findViewById(R.id.lisboa);
         Evora= findViewById(R.id.evora);
         Setubal= findViewById(R.id.setubal);
         Beja= findViewById(R.id.beja);
         Faro= findViewById(R.id.faro);
         Madeira= findViewById(R.id.madeira);
         Acores= findViewById(R.id.acores);


        Cache cache = new DiskBasedCache(getCacheDir(),1024*1024);
        Network network = new BasicNetwork(new HurlStack());
        RequestQueue requestQueue = new RequestQueue(cache,network);
        requestQueue.start();


            String url = getString(R.string.BASE_URL) + "pergunta/RespostasCertasNasCidades/" + userModel.getId_utilizador();


        try {

            Response.Listener<JSONArray> sucessListener = new Response.Listener<JSONArray >() {

                @RequiresApi(api = Build.VERSION_CODES.O)
                @Override
                public void onResponse(JSONArray response) {

                    try {
                        //  JSONArray monumentoArray =response.getJSONArray("monumentoDto");
                        //MonumentoModel mModel = new MonumentoModel();

                        CidadeSelecionadaModel cidadeSelecionadaModel = new CidadeSelecionadaModel();

                        ArrayList<CidadeSelecionadaModel> cidadeSelecionada= new ArrayList<CidadeSelecionadaModel>();


                        for (int i = 0; i < response.length(); i++) {

                            cidadeSelecionadaModel.setId_Cidade(response.getJSONObject(i).getInt("id_cidade"));
                            cidadeSelecionadaModel.setNrespostaCorreta(response.getJSONObject(i).getInt("nrespostaCorreta"));
                            cidadeSelecionadaModel.setId_Regiao(response.getJSONObject(i).getInt("id_regiao"));
                            cidadeSelecionada.add(cidadeSelecionadaModel);


                            if(response.getJSONObject(i).getInt("id_cidade")==1||response.getJSONObject(i).getInt("id_cidade")==3||response.getJSONObject(i).getInt("id_cidade")==4){
                                CasteloBranco.setVisibility(View.VISIBLE);

                                MapaPortugal.this.CasteloBranco.setOnClickListener(
                                        new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {
                                                if(CasteloBranco.getVisibility()== View.VISIBLE) {
                                                    Intent i = new Intent(MapaPortugal.this, CidadeSelecionada.class);
                                                    i.putExtra("user", userModel);
                                                    i.putExtra("cidade", cidadeModel);
                                                    cidadeSelecionada.get(0).setId_Regiao(1);
                                                    i.putExtra("cidadeSelecionada",  cidadeSelecionada.get(0));
                                                  startActivity(i);
                                                }

                                            }
                                        }
                                );


                            }else if(response.getJSONObject(i).getInt("id_cidade")==2) {
                                Lisboa.setVisibility(View.VISIBLE);
                                MapaPortugal.this.Lisboa.setOnClickListener(
                                        new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {
                                                if(Madeira.getVisibility()== View.VISIBLE) {
                                                    Intent i = new Intent(MapaPortugal.this, CidadeSelecionada.class);
                                                    i.putExtra("user", userModel);
                                                    i.putExtra("cidade",  MapaPortugal.this.cidadeSelecionada.get(1));
                                                    startActivity(i);
                                                }

                                            }
                                        }
                                );
                            }

                            JSONObject jsonobject = response.getJSONObject(i);
                        }
                        requestQueue.stop();
                    } catch (JSONException ex) {
                        ex.printStackTrace();
                    }


                }

            };

            Response.ErrorListener errorListener = new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getApplicationContext(), "Por favor valide novamente os valores! " + error, Toast.LENGTH_LONG).show();
                    System.out.println(error);
                }
            };



            JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, sucessListener, errorListener) ;
            requestQueue.add(jsonArrayRequest);

        } catch(Exception ex){
            Toast.makeText(getApplicationContext(), "" + ex + "", Toast.LENGTH_LONG).show();
        }





        Madeira.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(Madeira.getVisibility()== View.VISIBLE) {
                            // mostrador.setText(textNome.getText().toString());
                            Intent i = new Intent(MapaPortugal.this, Menu.class);
                            //i.putExtra("id",id);
                            startActivity(i);
                        }
                    }
                }
        );

        Faro.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //test.getDrawable().getConstantState().equals(getResources().getDrawable(R.drawable.red).getConstantState()))
                       if(Faro.getDrawable().getConstantState().equals(getResources().getDrawable(R.drawable.medalnao).getConstantState())) {
                           Faro.setImageResource(R.drawable.medal);

                       }else {
                          // Algarve.setImageResource(R.drawable.medalnao);
                           Faro.setVisibility(View.INVISIBLE);
                       }
                    }
                }
        );

    }
}