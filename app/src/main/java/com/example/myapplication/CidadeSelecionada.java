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
import com.example.myapplication.adapter.CustomAdapter;
import com.example.myapplication.adapter.ListaTrofeusAdapter;
import com.example.myapplication.model.CidadeModel;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class CidadeSelecionada extends AppCompatActivity {

    Button btVoltar;
    Intent intent;
    String cidade;
    String idUser;
    Integer idUtilizador;


    List<CidadeModel> cidadeModels;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_trofeus_regiao);


       RecyclerView recyclerView = findViewById(R.id.listaCidades);

       /* recyclerView.setLayoutManager(new GridLayoutManager(this,3));
        recyclerView.setAdapter(new ListaTrofeusAdapter(generateData(),generateImg()));
       recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.HORIZONTAL));
*/


        btVoltar = findViewById(R.id.btVoltar);

        intent= getIntent();
        cidade= intent.getStringExtra("Cidade");
        idUser = intent.getStringExtra("id");

        Toast.makeText(this, "Cidade e:"+cidade, Toast.LENGTH_SHORT).show();


        btVoltar.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(CidadeSelecionada.this, MapaPortugal.class);
                        // SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                        i.putExtra("id",idUser);
                        i.putExtra("Cidade",cidade);
                        startActivity(i);

                    }
                }
        );
    }

    private void generateData(int idRegiao) {

        JSONArray monumentoArray = new JSONArray();

        Cache cache = new DiskBasedCache(getCacheDir(),1024*1024);
        Network network = new BasicNetwork(new HurlStack());
        RequestQueue requestQueue = new RequestQueue(cache,network);
        requestQueue.start();

        //String url = getString(R.string.BASE_URL)+"cidade/regiao/"+idRegiao;
        String url = getString(R.string.BASE_URL)+"cidade/all";

        //System.out.println("URL:"+url);


        try {

            Response.Listener<JSONArray > sucessListener = new Response.Listener<JSONArray >() {

                @RequiresApi(api = Build.VERSION_CODES.O)
                @Override
                public void onResponse(JSONArray response) {

                    try {
                        //  JSONArray monumentoArray =response.getJSONArray("monumentoDto");
                        CidadeModel mModel = new CidadeModel();



                        for (int i = 0; i < response.length(); i++) {

                            JSONObject jsonobject = response.getJSONObject(i);
                            mModel.setNome(response.getJSONObject(i).getString("nome"));
                            cidadeModels.add(mModel);

                        }

                        RecyclerView recyclerView = findViewById(R.id.recycler_view);


                        Cache cache = new DiskBasedCache(getCacheDir(),1024*1024);
                        Network network = new BasicNetwork(new HurlStack());
                        RequestQueue requestQueue = new RequestQueue(cache,network);
                        requestQueue.start();

                        List<String> dataNome = new ArrayList<>();
                        List<Integer> dataImg = new ArrayList<>();

                      for(int i = 0; i< cidadeModels.size(); i++){

                            dataNome.add(cidadeModels.get(i).getNome());
                            System.out.println(cidadeModels.get(i).getNome());
                            //dataId.add(cidadeModels.get(i).getId_Cidade());



                          String url = getString(R.string.BASE_URL)+"pontuacao/get/"+idUser+cidadeModels.get(i).toString();

                          System.out.println("URL:"+url);


                          try {

                              Response.Listener<JSONObject> sucessListener = new Response.Listener<JSONObject>() {

                                  @SuppressLint("ResourceType")
                                  @RequiresApi(api = Build.VERSION_CODES.O)
                                  @Override
                                  public void onResponse(JSONObject response) {
                                      try {
                                          response.getInt("");

                                            
                                          //monumentoModel.setImg(response.getInt("imagem"));

                                         // labelCidade.setText(monumentoModel.getDescricao());

                                         // imagemCidade.setImageResource(R.drawable.muralhas);

                                          // imagemCidade.setImageResource(700024);

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

                              JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, sucessListener, errorListener) ;
                              requestQueue.add(request);

                          } catch(Exception ex){
                              Toast.makeText(getApplicationContext(), "" + ex + "", Toast.LENGTH_LONG).show();
                          }




                        }





                        recyclerView.setLayoutManager(new LinearLayoutManager(context));
                        recyclerView.setAdapter(new ListaTrofeusAdapter(dataNome,generateImg()));
                       // recyclerView.setAdapter(new CustomAdapter(dataNome,dataId));
                        recyclerView.addItemDecoration(new DividerItemDecoration(context, DividerItemDecoration.VERTICAL));


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



    }





       /* List<String> data = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            data.add(i + "th Element");
        }
        // Toast.makeText(this, data.size(), Toast.LENGTH_SHORT).show();
        return data;

        */
    //}

    private List<Integer> generateImg() {



        List<Integer> dataImg = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            if(i%2==1)
                dataImg.add(R.drawable.trofeu_ouro_logo);
            else if(i%3==1)
                dataImg.add(R.drawable.trofeu_prata_logo);
            else
                dataImg.add(R.drawable.trofeu_bronze_logo);
        }

        // Toast.makeText(this, data.size(), Toast.LENGTH_SHORT).show();
        return null;
    }
}
