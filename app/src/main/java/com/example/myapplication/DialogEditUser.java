package com.example.myapplication;

import android.app.Application;
import android.app.Dialog;
import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
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
import com.example.myapplication.api.WebAPI;
import com.example.myapplication.model.UserModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.time.LocalDate;

public class DialogEditUser extends ContextWrapper {

    private final Context context;
    private final Application application;
    private final UserModel userModel;
    private View v;
    private String i = null;
    private HorizontalScrollView horizontalScrollView;
    private ImageView addUserIcon;
    private int darkIconColor;
    private int lightIconColor;
    private Dialog dialog;
    private Button cancel,confirm;
    private Spinner spGenero;

    TextView txtNTelemovel,txtDataNasc,txtUsername,txtPassword,txtEmail;

    public DialogEditUser(Context base,Application application, UserModel userModel) {
        super(base);
        this.context=base;
        this.userModel = userModel;
        this.application = application;
        dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_edituser);

        darkIconColor = Utils.getColorDarkAvatar(userModel.getId_icon().toString());
        lightIconColor = Utils.getColorLightAvatar(userModel.getId_icon().toString());

        addUserIcon= dialog.findViewById(R.id.logoImage);
        horizontalScrollView=dialog.findViewById(R.id.horizontalScrollView);
        txtPassword= dialog.findViewById(R.id.Password);
        txtDataNasc= dialog.findViewById(R.id.dataNasc);
        txtEmail= dialog.findViewById(R.id.Email);
        txtUsername= dialog.findViewById(R.id.Username);
        txtNTelemovel= dialog.findViewById(R.id.nTelemovel);
        confirm= dialog.findViewById(R.id.btnProximo);
        cancel = dialog.findViewById(R.id.btnSair);
        spGenero =dialog.findViewById(R.id.Genero);



        confirm.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        WebAPI webAPI= new WebAPI(application);
                        if (i==null)
                        i=userModel.getId_icon().toString();
                       // String.valueOf(spGenero.getSelectedItemId())
                       editUser(userModel.getId_utilizador(),txtUsername.getText().toString(),
                                txtEmail.getText().toString(),"1",
                                txtPassword.getText().toString() , txtDataNasc.getText().toString(),
                                txtNTelemovel.getText().toString(),i);

                    }
                }
        );



    }

    public void showUserEditDialog( ){

        dialog.getWindow().setBackgroundDrawable( new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(true);
        dialog.show();

       putDialogDetails(dialog);
    }

    public void imageClick(View view) {
        i =  view.getContentDescription().toString();
        v= view ;
        horizontalScrollView.setVisibility(View.GONE);
        addUserIcon.setVisibility(View.VISIBLE);
        addUserIcon.setImageDrawable(getDrawable(Utils.getAvatarIconId(i)));
        darkIconColor = Utils.getColorDarkAvatar(i);
        lightIconColor = Utils.getColorLightAvatar(i);
        setElmentColors();
    }

    public void addIconClick(View view) {
        view.setVisibility(View.INVISIBLE);
        horizontalScrollView.setVisibility(View.VISIBLE);
    }

    private void putDialogDetails(Dialog dialog){
        addUserIcon.setImageDrawable(context.getDrawable(Utils.getAvatarIconId(userModel.getId_icon().toString())));
        txtUsername.setText(userModel.getUsername());
        txtEmail.setText(userModel.getEmail());
        txtDataNasc.setText(userModel.getDataNascimento().toString());
        txtNTelemovel.setText(userModel.getnTelemovel().toString());



        cancel.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.cancel();
                    }
                }
        );
        setElmentColors();
    }

    private void setElmentColors(){

        txtUsername.setTextColor(AppCompatResources.getColorStateList(context, darkIconColor));
        txtUsername.setHintTextColor(AppCompatResources.getColorStateList(context, darkIconColor));
        txtUsername.setBackgroundTintList(AppCompatResources.getColorStateList(context, lightIconColor));

        txtPassword.setTextColor(AppCompatResources.getColorStateList(context, darkIconColor));
        txtPassword.setHintTextColor(AppCompatResources.getColorStateList(context, darkIconColor));
        txtPassword.setBackgroundTintList(AppCompatResources.getColorStateList(context, lightIconColor));

        txtEmail.setTextColor(AppCompatResources.getColorStateList(context, darkIconColor));
        txtEmail.setHintTextColor(AppCompatResources.getColorStateList(context, darkIconColor));
        txtEmail.setBackgroundTintList(AppCompatResources.getColorStateList(context, lightIconColor));

        txtDataNasc.setTextColor(AppCompatResources.getColorStateList(context, darkIconColor));
        txtDataNasc.setHintTextColor(AppCompatResources.getColorStateList(context, darkIconColor));
        txtDataNasc.setBackgroundTintList(AppCompatResources.getColorStateList(context, lightIconColor));

        txtNTelemovel.setBackgroundTintList(AppCompatResources.getColorStateList(context, lightIconColor));
        txtNTelemovel.setTextColor(AppCompatResources.getColorStateList(context, darkIconColor));

        confirm.setBackgroundTintList(AppCompatResources.getColorStateList(context, darkIconColor));
        cancel.setBackgroundTintList(AppCompatResources.getColorStateList(context, lightIconColor));

    }

    public void editUser(Integer userId, String username, String email, String genero, String password, String dataNasc, String ntelemovel,String iconid){
        Cache cache = new DiskBasedCache(getCacheDir(),1024*1024);
        Network network = new BasicNetwork(new HurlStack());
        RequestQueue requestQueue = new RequestQueue(cache,network);
        requestQueue.start();
        String url = getString( R.string.BASE_URL)+"user/editUser";
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
                @RequiresApi(api = Build.VERSION_CODES.O)
                @Override
                public void onResponse(JSONObject response) {
                    Toast.makeText(getApplicationContext(),"Edition Sucessful",Toast.LENGTH_LONG).show();
                    try {
                        userModel.setId_utilizador(response.getInt("id_utilizador"));
                        userModel.setEmail(response.getString("email"));
                        userModel.setId_icon(response.getInt("id_icon"));
                        userModel.setUsername(response.getString("username"));
                        userModel.setnTelemovel(response.getInt("ntelemovel"));
                        userModel.setDataNascimento(LocalDate.parse(response.getString("dataNascimento")));

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            };

            Response.ErrorListener errorListener = new Response.ErrorListener(){

                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getApplicationContext(),"bad response "+error,Toast.LENGTH_LONG).show();
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
            requestQueue.add(request);

        } catch (JSONException e) {
            Toast.makeText(getApplicationContext(),"JSON exception",Toast.LENGTH_LONG).show();

        }catch (Exception ex){
            Toast.makeText(getApplicationContext(),""+ex+"",Toast.LENGTH_LONG).show();
        }

    }

}
