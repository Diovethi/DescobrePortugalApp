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
import com.example.myapplication.adapter.CustomAdapter;
import com.example.myapplication.model.CidadeModel;
import com.example.myapplication.model.MonumentoModel;
import com.example.myapplication.model.UserModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ListaPontosInteresse extends AppCompatActivity {



    Button btVoltar;
    ImageView userIcon;
    DialogUser dialogUser;
    ImageView imagemFundo;
    MonumentoModel monumentoModel;

    UserModel userModel;
    CidadeModel cidadeModel;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_lista_pontos_interesse);

        btVoltar = findViewById(R.id.btVoltar);
        userIcon = findViewById(R.id.userIcon);
        imagemFundo= findViewById(R.id.imagemFundo3);
        monumentoModel=new MonumentoModel();
        userModel = (UserModel) getIntent().getExtras().get("user");
        cidadeModel=(CidadeModel) getIntent().getExtras().get("cidade");
        dialogUser = new DialogUser(this,getApplication(),userModel,cidadeModel);

        setDesignElements(userModel);
        System.out.println("id cidade:"+cidadeModel.getId_Cidade());


    context=this;

        generateData(cidadeModel.getId_Cidade());




        btVoltar.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(ListaPontosInteresse.this, Menu.class);
                        // SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                        i.putExtra("user", userModel);
                        i.putExtra("cidade",cidadeModel);
                        startActivity(i);

                    }
                }
        );
    }

    private void generateData(int idCidade) {

         JSONArray monumentoArray = new JSONArray();

        Cache cache = new DiskBasedCache(getCacheDir(),1024*1024);
        Network network = new BasicNetwork(new HurlStack());
        RequestQueue requestQueue = new RequestQueue(cache,network);
        requestQueue.start();

        String url = getString(R.string.BASE_URL)+"monumento/"+idCidade+"/all";



        try {

            Response.Listener<JSONArray > sucessListener = new Response.Listener<JSONArray >() {

                @RequiresApi(api = Build.VERSION_CODES.O)
                @Override
                public void onResponse(JSONArray response) {

                    try {
                        //  JSONArray monumentoArray =response.getJSONArray("monumentoDto");
                        MonumentoModel mModel = new MonumentoModel();

                        List<String> dataNome = new ArrayList<>();
                        List<Integer> dataId = new ArrayList<>();

                        for (int i = 0; i < response.length(); i++) {


                            JSONObject jsonobject = response.getJSONObject(i);

                            mModel.setId_Monumento(response.getJSONObject(i).getInt("id_Monumento"));
                            mModel.setNome(response.getJSONObject(i).getString("nome"));

                            dataNome.add(mModel.getNome());
                            dataId.add(mModel.getId_Monumento());


                        }

                        RecyclerView recyclerView = findViewById(R.id.recycler_view);

                        recyclerView.setLayoutManager(new GridLayoutManager(context,3));

                        recyclerView.setAdapter(new CustomAdapter(dataNome,dataId,userModel,cidadeModel));
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

        userIcon.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialogUser.showUserDialog();
                    }
                }
        );

    }

    public void imageClicks(View view) {
        dialogUser.getDialogEditUser().imageClick(view);

    }

    public void addIconClicks(View view) {
        dialogUser.getDialogEditUser().addIconClick(view);
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    public void setDesignElements(UserModel userModel){
        btVoltar.setBackgroundTintList(AppCompatResources.getColorStateList(getApplicationContext(), Utils.getColorDarkAvatar(userModel.getId_icon().toString())));
        userIcon.setImageDrawable(getDrawable(Utils.getAvatarIconId(userModel.getId_icon().toString())));
        imagemFundo.setImageDrawable(getDrawable(Utils.getBackgroundImage(cidadeModel.getNome())));
    }



}
