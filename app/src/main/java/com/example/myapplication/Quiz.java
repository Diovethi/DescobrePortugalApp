package com.example.myapplication;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
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
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.myapplication.model.OpcaoModel;
import com.example.myapplication.model.PerguntaModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Quiz extends AppCompatActivity {

    TextView logout;
    TextView edit;
    TextView pergunta;
    Button btOpcao1;
    Button btOpcao2;
    Button btOpcao3;
    Button btOpcao4;
    boolean opcaoEscolhida;
    String userId;
    String cidade;
    Intent intent;

    RequestQueue requestQueue;

    PerguntaModel perguntaModel;



    DialogResposta dialogResposta;

    int id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_quiz);

        dialogResposta = new DialogResposta(this,getApplication(), true);

        logout = findViewById(R.id.logout);
        edit = findViewById(R.id.edit);
        pergunta = (TextView) findViewById(R.id.labelCidade);
        btOpcao1 = findViewById(R.id.btOpcao1);
        btOpcao2= findViewById(R.id.btOpcao2);
        btOpcao3 = findViewById(R.id.btOpcao3);
        btOpcao4 = findViewById(R.id.btOpcao4);

        intent= getIntent();
        cidade= intent.getStringExtra("Cidade");
        userId=intent.getStringExtra("idUser");

        perguntaModel=new PerguntaModel();

       /* userId = "1";
        cidade="Castelo Branco";
        */

        GetPergunta(cidade,userId);

        System.out.println("pergunta:"+perguntaModel.getDescricao());


        pergunta.setText(perguntaModel.getDescricao());

        logout.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        // mostrador.setText(textNome.getText().toString());
                        Intent i = new Intent(Quiz.this, Login.class);
                        startActivity(i);
                    }
                }
        );

        edit.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        // mostrador.setText(textNome.getText().toString());
                        Intent i = new Intent(Quiz.this, Register.class);
                        i.putExtra("id",id);
                        startActivity(i);
                    }
                }
        );

        btOpcao1.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        SelecionarResposta(0);
                        // mostrador.setText(textNome.getText().toString());
                       // Intent i = new Intent(Quiz.this, Login.class);
                        //startActivity(i);
                    }
                }
        );

        btOpcao2.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        SelecionarResposta(1);

                        // mostrador.setText(textNome.getText().toString());
                        //Intent i = new Intent(Quiz.this, Login.class);
                        //startActivity(i);
                    }
                }
        );

        btOpcao3.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        SelecionarResposta(2);


                        // mostrador.setText(textNome.getText().toString());
                        //Intent i = new Intent(Quiz.this, Login.class);
                        //startActivity(i);

                    }
                }
        );

        btOpcao4.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        // mostrador.setText(textNome.getText().toString());
                        SelecionarResposta(3);

                        // btOpcao4.setBackgroundColor(0x0000FF00 );
                        //btOpcao3.invalidate();
                        //btOpcao2.invalidate();
                        //btOpcao1.invalidate();

                    }
                }
        );


    }


public void SelecionarResposta(Integer n){
    if (perguntaModel.getOpcaoModels().get(n).getOpcaoCorreta()) {
        Toast.makeText(getApplicationContext(), "Acertou!!! ", Toast.LENGTH_SHORT).show();
        opcaoEscolhida=true;
    } else {
        Toast.makeText(getApplicationContext(), "Errou! ", Toast.LENGTH_SHORT).show();
        opcaoEscolhida=false;
    }




    GetPergunta(cidade,perguntaModel.getId_Pergunta().toString());


    String url = getString(R.string.BASE_URL)+"pergunta/responder";

    JSONObject jsonObject = new JSONObject();
    try {

        jsonObject.put("idopcao", n);
        jsonObject.put("idutilizador",userId );
        Response.Listener<JSONObject> sucessListener = new Response.Listener<JSONObject>() {

            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onResponse(JSONObject response) {
                //Intent i = new Intent(Quiz.this, Menu.class);
                // SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                dialogResposta.showRespostaDialog(opcaoEscolhida);

                //  startActivity(i);
                requestQueue.stop();

            }};

        Response.ErrorListener errorListener = new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Por favor valide novamente os valores! " + error, Toast.LENGTH_LONG).show();
                System.out.println(error);
            }
        };

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, jsonObject, sucessListener, errorListener) ;
        requestQueue.add(request);

    } catch(JSONException e){
        Toast.makeText(getApplicationContext(), "JSON exception", Toast.LENGTH_LONG).show();

    }catch(Exception ex){
        Toast.makeText(getApplicationContext(), "" + ex + "", Toast.LENGTH_LONG).show();
    }


}



    public void GetPergunta(String cidade, String idUser) {

        Cache cache = new DiskBasedCache(getCacheDir(),1024*1024);
        Network network = new BasicNetwork(new HurlStack());
        RequestQueue requestQueue = new RequestQueue(cache,network);
        requestQueue.start();

        String url = getString(R.string.BASE_URL)+"pergunta/cidade/"+cidade+"/"+idUser;

        System.out.println("URL:"+url);


        JSONObject jsonObject = new JSONObject();


        try {

            Response.Listener<JSONObject> sucessListener = new Response.Listener<JSONObject>() {

                @RequiresApi(api = Build.VERSION_CODES.O)
                @Override
                public void onResponse(JSONObject response) {


                    try {
                        perguntaModel.setId_Pergunta(response.getInt("id_Pergunta"));
                        perguntaModel.setDescricao(response.getString("descricao")) ;
                        perguntaModel.setId_Cidade(response.getInt("id_Cidade"));
                        JSONArray opcaoArray =response.getJSONArray("opcoesDto");
                        List<OpcaoModel> opcaoModels= new ArrayList<>();
                        for (int i=0; i<opcaoArray.length();i++) {
                            OpcaoModel opcaoModel = new OpcaoModel();
                            opcaoModel.setId_Opcao(opcaoArray.getJSONObject(i).getInt("id_Opcao"));
                            opcaoModel.setDescricao(opcaoArray.getJSONObject(i).getString("descricao"));
                            opcaoModel.setOpcaoCorreta(opcaoArray.getJSONObject(i).getBoolean("opcaoCorreta"));
                            opcaoModel.setId_Pegunta(opcaoArray.getJSONObject(i).getInt("id_Pegunta"));
                            opcaoModels.add(opcaoModel);
                        }
                        perguntaModel.setOpcoes(opcaoModels);

                        pergunta.setText(perguntaModel.getDescricao());
                        btOpcao1.setText(perguntaModel.getOpcaoModels().get(0).getDescricao());
                        btOpcao2.setText(perguntaModel.getOpcaoModels().get(1).getDescricao());
                        btOpcao3.setText(perguntaModel.getOpcaoModels().get(2).getDescricao());
                        btOpcao4.setText(perguntaModel.getOpcaoModels().get(3).getDescricao());

                       // perguntaModel.setOpcaoPojos((ArrayList<OpcaoModel>) response.getJSONArray("opcoesDto"));

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

}