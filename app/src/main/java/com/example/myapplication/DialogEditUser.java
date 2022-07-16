package com.example.myapplication;

import android.app.Activity;
import android.app.Application;
import android.app.Dialog;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.content.res.AppCompatResources;

import com.example.myapplication.api.WebAPI;
import com.example.myapplication.model.UserModel;

import java.util.HashMap;
import java.util.Set;

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
        confirm= dialog.findViewById(R.id.confirm);
        cancel = dialog.findViewById(R.id.cancel);
        spGenero =dialog.findViewById(R.id.Genero);



        confirm.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        WebAPI webAPI= new WebAPI(application);
                        if (i==null)
                        i=userModel.getId_icon().toString();
                       // String.valueOf(spGenero.getSelectedItemId())
                        webAPI.editUser(userModel.getId_utilizador(),txtUsername.getText().toString(),
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

}
