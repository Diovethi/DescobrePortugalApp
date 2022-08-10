package com.example.myapplication;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;

import android.content.Intent;

import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
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
import com.example.myapplication.model.CidadeModel;
import com.example.myapplication.model.UserModel;

import org.json.JSONException;
import org.json.JSONObject;

public class Menu extends AppCompatActivity {


    Button btnN;
    Button btnS;
    ImageView userIcon;
    UserModel userModel;
    DialogUser dialogUser;
    Integer nPerguntas;
    CidadeModel cidadeModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_menu);

        nPerguntas = GetTotalPerguntas();
        btnN = findViewById(R.id.btVoltar);
        btnS = findViewById(R.id.btnS);
        userIcon = findViewById(R.id.userIcon);
        userModel = (UserModel) getIntent().getExtras().get("user");
        cidadeModel=(CidadeModel) getIntent().getExtras().get("cidade");
        dialogUser = new DialogUser(this,getApplication(),userModel);

        setDesignElements(userModel);

        userIcon.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialogUser.showUserDialog();
                    }
                }
        );

        btnN.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        // mostrador.setText(textNome.getText().toString());
                        // Intent i = new Intent(Menu.this,ListaPontosInteresse.class);

                      Intent i = new Intent(Menu.this,ListaPontosInteresse.class);
                        i.putExtra("user", userModel);
                        i.putExtra("cidade",cidadeModel);
                     /*
                        Intent i = new Intent(Menu.this,MapaPortugal.class);
                        i.putExtra("user", userModel);
                        i.putExtra("cidade", cidadeModel);
                         */
                        startActivity(i);
                    }
                }
        );


        btnS.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                      GetProgresso();
                    }
                }
        );



    /*    trofeu.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent i = new Intent(Menu.this,MapaPortugal.class);
                        i.putExtra("id",userModel.getId_utilizador());
                        startActivity(i);
                    }
                }
        );
*/
    }

    public void imageClicks(View view) {
        dialogUser.getDialogEditUser().imageClick(view);

    }

    public void addIconClicks(View view) {
        dialogUser.getDialogEditUser().addIconClick(view);
    }

    public void setDesignElements(UserModel userModel){
        btnN.setBackgroundTintList(AppCompatResources.getColorStateList(getApplicationContext(), Utils.getColorLightAvatar(userModel.getId_icon().toString())));
        btnS.setBackgroundTintList(AppCompatResources.getColorStateList(getApplicationContext(), Utils.getColorDarkAvatar(userModel.getId_icon().toString())));
        userIcon.setImageDrawable(getDrawable(Utils.getAvatarIconId(userModel.getId_icon().toString())));
    }

    public Integer GetProgresso() {
        ProgressBar progressBar= findViewById(R.id.progressBar);
        progressBar.setIndeterminateTintList(AppCompatResources.getColorStateList(getApplicationContext(), Utils.getColorDarkAvatar(userModel.getId_icon().toString())));
        progressBar.setVisibility(View.VISIBLE);
        btnS.setVisibility(View.GONE);
        final int[] nPergunta = {0};
        Cache cache = new DiskBasedCache(getCacheDir(),1024*1024);
        Network network = new BasicNetwork(new HurlStack());
        RequestQueue requestQueue = new RequestQueue(cache,network);
        requestQueue.start();
        String url = getString(R.string.BASE_URL)+"pontuacao/progresso/"+1+"/"+userModel.getId_utilizador();

        System.out.println("URL:"+url);


        try {

            Response.Listener<JSONObject> sucessListener = new Response.Listener<JSONObject>() {

                @RequiresApi(api = Build.VERSION_CODES.O)
                @Override
                public void onResponse(JSONObject response) {
                    try {
                         nPergunta[0] = response.getInt("npergunta");
                        progressBar.setVisibility(View.GONE);
                        btnS.setVisibility(View.VISIBLE);
                        if(nPergunta[0] >=6){
                            Toast.makeText(getApplicationContext(), "Já respondeu a todas as perguntas disponíveis para esta cidade! ", Toast.LENGTH_LONG).show();
                        }
                        else {
                            // mostrador.setText(textNome.getText().toString());
                            Intent i = new Intent(Menu.this, Quiz.class);
                            i.putExtra("user", userModel);
                            i.putExtra("cidade", cidadeModel);
                            i.putExtra("npergunta", nPergunta[0]);

                            startActivity(i);
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
                        Toast.makeText(getApplicationContext(), "Erro a receber o Progresso das perguntas! " + error, Toast.LENGTH_LONG).show();

                }
            };

            JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, sucessListener, errorListener) ;
            requestQueue.add(request);

        } catch(Exception ex){
            Toast.makeText(getApplicationContext(), "" + ex + "", Toast.LENGTH_LONG).show();
        }

        return nPergunta[0];
    }

    public Integer GetTotalPerguntas() {
        final Integer[] nPerguntas = {0};
        Cache cache = new DiskBasedCache(getCacheDir(),1024*1024);
        Network network = new BasicNetwork(new HurlStack());
        RequestQueue requestQueue = new RequestQueue(cache,network);
        requestQueue.start();
        String url = getString(R.string.BASE_URL)+"pergunta/nPerguntas/"+1;

        System.out.println("URL:"+url);


        try {

            Response.Listener<JSONObject> sucessListener = new Response.Listener<JSONObject>() {

                @RequiresApi(api = Build.VERSION_CODES.O)
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        nPerguntas[0] = response.getInt("nperguntas");
                        requestQueue.stop();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            };
            Response.ErrorListener errorListener = new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getApplicationContext(), "Erro a receber o Progresso das perguntas! " + error, Toast.LENGTH_LONG).show();

                }
            };

            JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, sucessListener, errorListener) ;
            requestQueue.add(request);

        } catch(Exception ex){
            Toast.makeText(getApplicationContext(), "" + ex + "", Toast.LENGTH_LONG).show();
        }

        return nPerguntas[0];
    }




}