package com.example.myapplication;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;

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
import com.example.myapplication.model.MonumentoModel;
import com.example.myapplication.model.UserModel;

import org.json.JSONException;
import org.json.JSONObject;

public class PontosInteresse extends AppCompatActivity {

    Button btVoltar;

    EditText labelCidade;
    TextView labelTitulo;
    TextView bordaCidade;
    MonumentoModel monumentoModel;
    ImageView imagemCidade;
    UserModel userModel;
    CidadeModel cidadeModel;
    ImageView userIcon;
    DialogUser dialogUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_pontos_interesse);

        monumentoModel= new MonumentoModel();
        btVoltar = findViewById(R.id.btVoltar);
        labelCidade= findViewById(R.id.labelCidade);
        bordaCidade= findViewById(R.id.bordaCidade);
        imagemCidade= findViewById(R.id.imagemCidade);
        labelTitulo=findViewById(R.id.titulo);

        userIcon = findViewById(R.id.userIcon);

        userModel = (UserModel) getIntent().getExtras().get("user");
        cidadeModel=(CidadeModel) getIntent().getExtras().get("cidade");
        monumentoModel = (MonumentoModel) getIntent().getExtras().get("Monumento");

        dialogUser = new DialogUser(this,getApplication(),userModel,cidadeModel);
        setDesignElements(userModel);
        System.out.println(cidadeModel.getId_Cidade());

        Cache cache = new DiskBasedCache(getCacheDir(),1024*1024);
        Network network = new BasicNetwork(new HurlStack());
        RequestQueue requestQueue = new RequestQueue(cache,network);
        requestQueue.start();

        String url = getString(R.string.BASE_URL)+"monumento/"+monumentoModel.getId_Monumento();

        System.out.println("URL:"+url);


        try {

            Response.Listener<JSONObject> sucessListener = new Response.Listener<JSONObject>() {

                @SuppressLint("ResourceType")
                @RequiresApi(api = Build.VERSION_CODES.O)
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        monumentoModel.setId_Monumento(response.getInt("id_Monumento"));
                        monumentoModel.setDescricao(response.getString("descricao")) ;
                        monumentoModel.setNome(response.getString("nome"));
                        //monumentoModel.setImg(response.getInt("imagem"));

                        labelTitulo.setText(monumentoModel.getNome());
                        labelCidade.setText(monumentoModel.getDescricao());

                        System.out.println("idMonumento: "+monumentoModel.getId_Monumento());

                            if(monumentoModel.getId_Monumento()==1)
                                imagemCidade.setImageResource(R.drawable.muralhas);
                            else if(monumentoModel.getId_Monumento()==2)
                                imagemCidade.setImageResource(R.drawable._50px_jardim_do_pa_o_episcopal);
                            else if(monumentoModel.getId_Monumento()==3)
                                imagemCidade.setImageResource(R.drawable.castelobranco);

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


        userIcon.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialogUser.showUserDialog();
                    }
                }
        );

        btVoltar.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(PontosInteresse.this, ListaPontosInteresse.class);
                        // SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                        i.putExtra("user", userModel);
                        i.putExtra("cidade",cidadeModel);
                        startActivity(i);

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
        bordaCidade.setBackgroundTintList(AppCompatResources.getColorStateList(getApplicationContext(), Utils.getColorLightAvatar(userModel.getId_icon().toString())));
        labelTitulo.setTextColor(AppCompatResources.getColorStateList(getApplicationContext(), Utils.getColorDarkAvatar(userModel.getId_icon().toString())));
    }
}
