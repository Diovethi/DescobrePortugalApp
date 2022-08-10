package com.example.myapplication;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.myapplication.adapter.ListaTrofeusAdapter;
import com.example.myapplication.model.CidadeModel;
import com.example.myapplication.model.CidadeSelecionadaModel;
import com.example.myapplication.model.UserModel;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Console;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.locks.ReentrantLock;


public class CidadeSelecionada extends AppCompatActivity {

    Button btVoltar;
    Intent intent;
    UserModel userModel;
    CidadeSelecionadaModel cidadeSelecionada;
    CidadeModel cidadeModel;


    List<CidadeModel> cidadeModels;
    Context context;
    private ArrayList<String> dataNome;
    private ArrayList<Integer> dataImg;
    private ArrayList<Integer> dataImgs;

    private CountDownLatch inputLatch = new CountDownLatch(3);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_trofeus_regiao);


        userModel = (UserModel) getIntent().getExtras().get("user");
        cidadeModel=(CidadeModel) getIntent().getExtras().get("cidade");
        cidadeSelecionada=(CidadeSelecionadaModel) getIntent().getExtras().get("cidadeSelecionada");

        dataNome= new ArrayList<String>();
        dataImg= new ArrayList<Integer>();
        dataImgs= new ArrayList<Integer>();

        generateData(cidadeSelecionada.getId_Regiao());

        System.out.println(CidadeSelecionada.this.dataImg+"-"+CidadeSelecionada.this.dataNome);


        RecyclerView recyclerView = findViewById(R.id.listaCidades);

       recyclerView.setLayoutManager(new GridLayoutManager(this,3));
       recyclerView.setAdapter(new ListaTrofeusAdapter(dataNome,dataImgs,dataImg));
       recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.HORIZONTAL));



        btVoltar = findViewById(R.id.btVoltar);


        btVoltar.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(CidadeSelecionada.this, Menu.class);
                        i.putExtra("user", userModel);
                        i.putExtra("cidade",cidadeModel);
                        startActivity(i);

                    }
                }
        );
    }



    private List<String> generateData(int idRegiao) {




        JSONArray monumentoArray = new JSONArray();

        Cache cache = new DiskBasedCache(getCacheDir(),1024*1024);
        Network network = new BasicNetwork(new HurlStack());
        RequestQueue requestQueue = new RequestQueue(cache,network);
        requestQueue.start();

        String url = getString(R.string.BASE_URL)+"pontuacao/PerguntasRegiao/"+idRegiao+"/"+userModel.getId_utilizador();

        System.out.println("URL:"+url);


        try {

            Response.Listener<JSONArray > sucessListener = new Response.Listener<JSONArray >() {

                @RequiresApi(api = Build.VERSION_CODES.O)
                @Override
                public void onResponse(JSONArray response) {

                    try {
                        //  JSONArray monumentoArray =response.getJSONArray("monumentoDto");
                        System.out.println(response.length());
                        for (int i = 0; i < response.length(); i++) {

                            CidadeSelecionada.this.dataNome.add(response.getJSONObject(i).getString("nomeCidade"));
                            CidadeSelecionada.this.dataImg.add(response.getJSONObject(i).getInt("respostasCertas"));
                            System.out.println(response.getJSONObject(i).getString("nomeCidade")+"-"+response.getJSONObject(i).getInt("respostasCertas"));
                        }


                        requestQueue.stop();

                        generateImg();

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


        return CidadeSelecionada.this.dataNome;

    }






    private List<Integer> generateImg()  {


    System.out.println(CidadeSelecionada.this.dataImg+"-"+CidadeSelecionada.this.dataNome);


        for (int i = 0; i < dataImg.size(); i++) {
            if(dataImg.get(i)<=3)
                CidadeSelecionada.this.dataImgs.add(R.drawable.trofeu_bronze_logo);
            else if(dataImg.get(i)<=5)
                CidadeSelecionada.this.dataImgs.add(R.drawable.trofeu_prata_logo);
            else
                CidadeSelecionada.this.dataImgs.add(R.drawable.trofeu_ouro_logo);
        }

        // Toast.makeText(this, data.size(), Toast.LENGTH_SHORT).show();
        return CidadeSelecionada.this.dataImgs;
    }
}
