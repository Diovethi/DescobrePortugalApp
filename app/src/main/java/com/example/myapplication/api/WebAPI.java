package com.example.myapplication.api;

import android.app.Application;

import android.content.Intent;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.myapplication.MapaPortugal;
import com.example.myapplication.model.UserModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;


public class WebAPI implements API {

    public static final String BASE_URL="http://192.168.1.69:8080/";
        //ip Igor : 192.168.1.105
        //IP Telmo :192.168.1.69

    private final Application mApplication;
    private RequestQueue mRequestQueue;
    private Boolean success;
    private Integer integer;

    public WebAPI (Application application){
        this.mApplication = application;
        this.mRequestQueue = Volley.newRequestQueue(application);
    }


    public UserModel getUserByUsernamePassword(String username, String password){
        String url = BASE_URL+"user/getUser";
        JSONObject jsonObject = new JSONObject();
        UserModel userModel = new UserModel();
        try {

            jsonObject.put("username", username);
            jsonObject.put("pass",password);



            Response.Listener<JSONObject> sucessListener= new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                      success=true;

                    try {
                        Toast.makeText(mApplication,"Sucessful response!!!",Toast.LENGTH_LONG).show();
                        userModel.setId_utilizador(response.getInt("id"));
                        userModel.setId_icon(response.getInt("id_icon"));
                        userModel.setUsername(response.getString("username"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    mRequestQueue.stop();
                }
            };

            Response.ErrorListener errorListener = new Response.ErrorListener(){

                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(mApplication,"Por favor valide novamente os valores! "+error,Toast.LENGTH_LONG).show();

                    success=false;
                    if (error == null || error.networkResponse == null) {
                        return;
                    }

                    String body;
                    final String statusCode = String.valueOf(error.networkResponse.statusCode);
                    try {
                        body = new String(error.networkResponse.data,"UTF-8");
                        System.out.println(body);
                    } catch (UnsupportedEncodingException e) {
                        // exception
                    }
                }
            };

            JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, jsonObject, sucessListener, null){};
            mRequestQueue.add(request);
            System.out.println("json "+jsonObject.toString());
            System.out.println("getBody "+request.getBody().getClass());

        } catch (JSONException e) {
            Toast.makeText(mApplication,"JSON exception",Toast.LENGTH_LONG).show();

        }catch (Exception ex){
            Toast.makeText(mApplication,""+ex+"",Toast.LENGTH_LONG).show();
        }

    userModel.setId_utilizador(integer);
        return userModel;
    }

    @Override

    public void addUser(String username, String email, String genero, String password, String dataNasc, String ntelemovel,String iconid){
        String url = BASE_URL+"user/addUser";
        JSONObject jsonObject = new JSONObject();

        try {

            jsonObject.put("username", username);
            jsonObject.put("id_genero", genero);
            jsonObject.put("email",email);
            jsonObject.put("password",password);
            jsonObject.put("dataNascimento",dataNasc);
            jsonObject.put("ntelemovel",ntelemovel);
            jsonObject.put("id_icon",iconid);


            Response.Listener<JSONObject> sucessListener= new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                     try {
                         Toast.makeText(mApplication,"Register Sucessful ",Toast.LENGTH_LONG).show();

                         Object userN=response.get("username").toString();
                        Object idUser=response.get("id").toString();
                         mApplication.getApplicationContext().startActivity(new Intent(mApplication.getApplicationContext(), MapaPortugal.class));
                        System.out.println(idUser+"- "+userN);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                }
            };

            Response.ErrorListener errorListener = new Response.ErrorListener(){

                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(mApplication,"bad response "+error,Toast.LENGTH_LONG).show();
                        System.out.println(error);
                    if (error == null || error.networkResponse == null) {
                        return;
                    }

                    String body;
                    //get status code here
                    final String statusCode = String.valueOf(error.networkResponse.statusCode);
                    //get response body and parse with appropriate encoding
                    try {
                        body = new String(error.networkResponse.data,"UTF-8");
                        System.out.println(body);
                    } catch (UnsupportedEncodingException e) {
                        // exception
                    }
                }
            };

            JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, jsonObject, sucessListener, errorListener);
            mRequestQueue.add(request);

        } catch (JSONException e) {
            Toast.makeText(mApplication,"JSON exception",Toast.LENGTH_LONG).show();

        }catch (Exception ex){
            Toast.makeText(mApplication,""+ex+"",Toast.LENGTH_LONG).show();
        }

    }

    public void editUser(Integer userId, String username, String email, String genero, String password, String dataNasc, String ntelemovel,String iconid){
        String url = BASE_URL+"user/addUser";
        JSONObject jsonObject = new JSONObject();

        try {

            jsonObject.put("id_utilizador",userId);
            jsonObject.put("username", username);
            jsonObject.put("id_genero", genero);
            jsonObject.put("email",email);
            jsonObject.put("password",password);
            jsonObject.put("dataNascimento",dataNasc);
            jsonObject.put("ntelemovel",ntelemovel);
            jsonObject.put("id_icon",iconid);


            Response.Listener<JSONObject> sucessListener= new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        Toast.makeText(mApplication,"Register Sucessful ",Toast.LENGTH_LONG).show();

                        Object userN=response.get("username").toString();
                        Object idUser=response.get("id").toString();
                        mApplication.getApplicationContext().startActivity(new Intent(mApplication.getApplicationContext(), MapaPortugal.class));
                        System.out.println(idUser+"- "+userN);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                }
            };

            Response.ErrorListener errorListener = new Response.ErrorListener(){

                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(mApplication,"bad response "+error,Toast.LENGTH_LONG).show();
                    System.out.println(error);
                    if (error == null || error.networkResponse == null) {
                        return;
                    }

                    String body;
                    //get status code here
                    final String statusCode = String.valueOf(error.networkResponse.statusCode);
                    //get response body and parse with appropriate encoding
                    try {
                        body = new String(error.networkResponse.data,"UTF-8");
                        System.out.println(body);
                    } catch (UnsupportedEncodingException e) {
                        // exception
                    }
                }
            };

            JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, jsonObject, sucessListener, errorListener);
            mRequestQueue.add(request);

        } catch (JSONException e) {
            Toast.makeText(mApplication,"JSON exception",Toast.LENGTH_LONG).show();

        }catch (Exception ex){
            Toast.makeText(mApplication,""+ex+"",Toast.LENGTH_LONG).show();
        }

    }


    @Override
    public void getPerguntabyIdCidade(Integer idCidade) {

    }

    @Override
    public void getPontuacaoByIdCidadeIdUtilizador(Integer idCidade, Integer idUtilizador) {

    }
}
