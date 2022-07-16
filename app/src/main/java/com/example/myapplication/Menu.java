package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.myapplication.api.WebAPI;
import com.example.myapplication.model.UserModel;

import java.util.Set;

public class Menu extends AppCompatActivity {


    Button mapa;
    Button trofeu;
    ImageView userIcon;
    UserModel userModel;
    DialogUser dialogUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_menu);
        userModel = (UserModel) getIntent().getExtras().get("user");
        dialogUser = new DialogUser(this,getApplication(),userModel);
        mapa = findViewById(R.id.mapaBt);
        mapa.setBackgroundTintList(AppCompatResources.getColorStateList(getApplicationContext(), Utils.getColorLightAvatar(userModel.getId_icon().toString())));
        Button confirmEditDialog = dialogUser.getDialogEditUser().getDialog().findViewById(R.id.confirm);
        confirmEditDialog.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        WebAPI webAPI= new WebAPI(getApplication());

                        webAPI.editUser(userModel.getId_utilizador(),dialogUser.getDialogEditUser().getTxtUsername().getText().toString(),
                                dialogUser.getDialogEditUser().getTxtEmail().getText().toString(),String.valueOf(dialogUser.getDialogEditUser().getSpIconId().getSelectedItemId()),
                                dialogUser.getDialogEditUser().getTxtPassword().getText().toString() , dialogUser.getDialogEditUser().getTxtDataNasc().getText().toString(),
                                dialogUser.getDialogEditUser().getTxtNTelemovel().getText().toString(),dialogUser.getDialogEditUser().getI());

                        startActivity(getIntent());

                    }
                }
        );



    trofeu = findViewById(R.id.TrofeuBT);
        trofeu.setBackgroundTintList(AppCompatResources.getColorStateList(getApplicationContext(), Utils.getColorDarkAvatar(userModel.getId_icon().toString())));
        userIcon = findViewById(R.id.userIcon);

        userIcon.setImageDrawable(getDrawable(Utils.getAvatarIconId(userModel.getId_icon().toString())));
        userIcon.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialogUser.showUserDialog();
                    }
                }
        );



        mapa.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent i = new Intent(Menu.this,Pergunta.class);
                        i.putExtra("id",userModel.getId_utilizador());
                        startActivity(i);
                    }
                }
        );

        trofeu.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent i = new Intent(Menu.this,MapaPortugal.class);
                        i.putExtra("id",userModel.getId_utilizador());
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
}