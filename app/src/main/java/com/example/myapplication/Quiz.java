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
import com.example.myapplication.model.PerguntaModel;

import org.json.JSONException;
import org.json.JSONObject;

public class Quiz extends AppCompatActivity {

    TextView logout;
    TextView edit;
    TextView pergunta;
    Button btOpcao1;
    Button btOpcao2;
    Button btOpcao3;
    Button btOpcao4;

    PerguntaModel perguntaModel;

    String idUser;
    String cidade;
    Intent intent;

    int id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_quiz);

        logout = findViewById(R.id.logout);
        edit = findViewById(R.id.edit);
        pergunta = (TextView) findViewById(R.id.pergunta);
        btOpcao1 = findViewById(R.id.btOpcao1);
        btOpcao2= findViewById(R.id.btOpcao2);
        btOpcao3 = findViewById(R.id.btOpcao3);
        btOpcao4 = findViewById(R.id.btOpcao4);

        intent= getIntent();
        cidade= intent.getStringExtra("Cidade");
        idUser=intent.getStringExtra("idUser");

        perguntaModel=new PerguntaModel();

        GetPergunta(cidade,idUser);

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
                        // mostrador.setText(textNome.getText().toString());
                        Intent i = new Intent(Quiz.this, Login.class);
                        startActivity(i);
                    }
                }
        );

        btOpcao2.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        // mostrador.setText(textNome.getText().toString());
                        Intent i = new Intent(Quiz.this, Login.class);
                        startActivity(i);
                    }
                }
        );

        btOpcao3.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        // mostrador.setText(textNome.getText().toString());
                        Intent i = new Intent(Quiz.this, Login.class);
                        startActivity(i);
                    }
                }
        );

        btOpcao4.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        // mostrador.setText(textNome.getText().toString());
                        btOpcao4.setBackgroundColor(0x0000FF00 );
                        btOpcao3.invalidate();
                        btOpcao2.invalidate();
                        btOpcao1.invalidate();

                    }
                }
        );


    }





    public void GetPergunta(String cidade, String idUser) {


        Cache cache = new DiskBasedCache(getCacheDir(),1024*1024);
        Network network = new BasicNetwork(new HurlStack());
        RequestQueue requestQueue = new RequestQueue(cache,network);
        requestQueue.start();

        String url = getString(R.string.BASE_URL)+"pergunta/cidade/"+cidade+"/"+idUser;

        System.out.println("URL:"+url);

        /*

    "id_Pergunta": 1,
    "descricao": "Qual foi o ano que Castelo branco foi elevada a cidade?",
    "id_Cidade": 1,
    "opcoesDto": [
        {
            "id_Opcao": 2,
            "descricao": "1771",
            "opcaoCorreta": true,
            "id_Pegunta": 1
        },
        {
            "id_Opcao": 3,
            "descricao": "1690",
            "opcaoCorreta": false,
            "id_Pegunta": 1
        },
        {
            "id_Opcao": 4,
            "descricao": "1723",
            "opcaoCorreta": false,
            "id_Pegunta": 1
        },
        {
            "id_Opcao": 5,
            "descricao": "1812",
            "opcaoCorreta": false,
            "id_Pegunta": 1
        }
    ]
}
         */

        JSONObject jsonObject = new JSONObject();


        try {

          /*  jsonObject.put("cidade", cidade);
            jsonObject.put("idUser", idUser);*/

            Response.Listener<JSONObject> sucessListener = new Response.Listener<JSONObject>() {

                @RequiresApi(api = Build.VERSION_CODES.O)
                @Override
                public void onResponse(JSONObject response) {
                    try {

                        perguntaModel.setDescricao(response.getString("descricao")) ;


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

            JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, jsonObject, sucessListener, errorListener) ;
            requestQueue.add(request);

        } catch(Exception ex){
            Toast.makeText(getApplicationContext(), "" + ex + "", Toast.LENGTH_LONG).show();
        }

    }

}