package com.example.myapplication;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
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
import com.example.myapplication.api.WebAPI;
import com.example.myapplication.model.UserModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

public class Login extends AppCompatActivity {

    TextView mostrador;
    TextView register;
    RequestQueue requestQueue;
    //EditText username, password;
    Button button;
    public static final String BASE_URL="http://192.168.1.69:8080/";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        Cache cache = new DiskBasedCache(getCacheDir(),1024*1024);
        Network network = new BasicNetwork(new HurlStack());
        requestQueue = new RequestQueue(cache,network);
        requestQueue.start();

        //mostrador = findViewById(R.id.mostrador);
        register = findViewById(R.id.registerBt);
        EditText usern = findViewById(R.id.username);
        EditText passw = findViewById(R.id.password);
        button = findViewById(R.id.loginBt);


        button.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                    String url = BASE_URL+"user/getUser";
                    JSONObject jsonObject = new JSONObject();
                        try {

                            jsonObject.put("username", usern.getText().toString());
                            jsonObject.put("pass", passw.getText().toString());
                            Response.Listener<JSONObject> sucessListener = new Response.Listener<JSONObject>() {
                                @RequiresApi(api = Build.VERSION_CODES.O)
                                @Override
                                public void onResponse(JSONObject response) {
                                    try {
                                        Intent i = new Intent(Login.this, Menu.class);
                                       // SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                                        UserModel user = new UserModel(response.getInt("id"),response.getString("username"),null,response.getString("email"), LocalDate.parse(response.getString("dataNascimento")),response.getInt("id_genero"),response.getInt("ntelemovel"),response.getInt("id_icon"));
                                        i.putExtra("user", user);
                                        startActivity(i);
                                        requestQueue.stop();
                                    } catch (JSONException  e) {
                                        e.printStackTrace();
                                    }

                                }};

                                Response.ErrorListener errorListener = new Response.ErrorListener() {

                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        Toast.makeText(getApplicationContext(), "Por favor valide novamente os valores! " + error, Toast.LENGTH_LONG).show();

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
            }
        );

        register.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        // mostrador.setText(textNome.getText().toString());
                        Intent i = new Intent(Login.this,Register.class);
                        startActivity(i);
                    }
                }
        );
    }
}

/*
if( userModel.getId_utilizador() != null){
        Intent i = new Intent(Login.this,MapaPortugal.class);
        i.putExtra("id_utilizador",userModel.getId_utilizador());
        i.putExtra("username",userModel.getUsername());
        i.putExtra("id_icon",userModel.getId_icon());
        startActivity(i);
        }*/
