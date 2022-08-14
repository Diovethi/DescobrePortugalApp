package com.example.myapplication;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
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
import com.example.myapplication.adapter.ListaTrofeusAdapter;
import com.example.myapplication.model.CidadeModel;
import com.example.myapplication.model.CidadeSelecionadaModel;
import com.example.myapplication.model.UserModel;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;


public class CidadeSelecionada extends AppCompatActivity {

    Button btVoltar;
    UserModel userModel;
    CidadeSelecionadaModel cidadeSelecionada;
    CidadeModel cidadeModel;
    ImageView userIcon;
    DialogUser dialogUser;
    RecyclerView recyclerView;
    ImageView imagemFundo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_trofeus_regiao);

        recyclerView = findViewById(R.id.listaCidades);
        btVoltar = findViewById(R.id.btVoltar);
        imagemFundo= findViewById(R.id.imagemFundo3);
        userIcon = findViewById(R.id.userIcon);

        userModel = (UserModel) getIntent().getExtras().get("user");
        cidadeModel=(CidadeModel) getIntent().getExtras().get("cidade");
        cidadeSelecionada=(CidadeSelecionadaModel) getIntent().getExtras().get("cidadeSelecionada");

        dialogUser = new DialogUser(this,getApplication(),getIntent(),userModel,cidadeModel);
        setDesignElements();

        getTrofeus(cidadeSelecionada.getId_Regiao());

        userIcon.setOnClickListener(
                view -> dialogUser.showUserDialog()
        );

        btVoltar.setOnClickListener(
                v -> {
                    Intent i = new Intent(CidadeSelecionada.this, Menu.class);
                    i.putExtra("user", userModel);
                    i.putExtra("cidade",cidadeModel);
                    startActivity(i);

                }
        );
    }

    private void getTrofeus(int idRegiao) {
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
                    ArrayList<String> dataNome = new ArrayList<>();
                    ArrayList<Integer> dataRespostasCertas = new ArrayList<>();
                    ArrayList<Integer> dataImg = new ArrayList<>();
                    try {
                        System.out.println(response.length());
                        for (int i = 0; i < response.length(); i++) {
                            dataRespostasCertas.add(response.getJSONObject(i).getInt("respostasCertas"));
                            dataNome.add(response.getJSONObject(i).getString("nomeCidade"));
                            dataImg.add(Utils.getTrofeuImg(response.getJSONObject(i).getInt("respostasCertas")));
                            System.out.println(response.getJSONObject(i).getString("nomeCidade") + " respostas certas: " + response.getJSONObject(i).getInt("respostasCertas"));
                        }
                        setlistAdapter(dataNome, dataImg, dataRespostasCertas);

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


    public void imageClicks(View view) {
        dialogUser.getDialogEditUser().imageClick(view);

    }

    public void addIconClicks(View view) {
        dialogUser.getDialogEditUser().addIconClick(view);
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private void setDesignElements(){
        btVoltar.setBackgroundTintList(AppCompatResources.getColorStateList(getApplicationContext(), Utils.getColorDarkAvatar(userModel.getId_icon().toString())));
        userIcon.setImageDrawable(getDrawable(Utils.getAvatarIconId(userModel.getId_icon().toString())));
        imagemFundo.setImageDrawable(getDrawable(Utils.getBackgroundImage(cidadeModel.getNome())));
    }

    private void setlistAdapter(List<String> dataNome, List<Integer> dataImg, List<Integer> dataRespostasCertas){
        recyclerView.setLayoutManager(new GridLayoutManager(this,3));
        recyclerView.setAdapter(new ListaTrofeusAdapter(dataNome,dataImg,dataRespostasCertas));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.HORIZONTAL));
    }


}


