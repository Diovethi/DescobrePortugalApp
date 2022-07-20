package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;

import android.content.Intent;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;

import com.example.myapplication.model.UserModel;

public class Menu extends AppCompatActivity {


    Button btnN;
    Button btnS;
    ImageView userIcon;
    UserModel userModel;
    DialogUser dialogUser;
    String userId;
    String cidade;
    Intent intent;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_menu);

        btnN = findViewById(R.id.btVoltar);
        btnS = findViewById(R.id.btnS);
        userIcon = findViewById(R.id.userIcon);
        userModel = (UserModel) getIntent().getExtras().get("user");
        dialogUser = new DialogUser(this,getApplication(),userModel);

        intent= getIntent();
        cidade= intent.getStringExtra("Cidade");
        userId=intent.getStringExtra("userId");

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
                        Intent i = new Intent(Menu.this,MapaPortugal.class);
                        i.putExtra("id",userModel.getId_utilizador());
                        i.putExtra("Cidade",cidade);
                        startActivity(i);
                    }
                }
        );


        btnS.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        // mostrador.setText(textNome.getText().toString());
                        Intent i = new Intent(Menu.this,Quiz.class);
                        i.putExtra("idUser",userModel.getId_utilizador());
                        i.putExtra("Cidade",cidade);
                        startActivity(i);
                    }
                }
        );


       /* mapa.setOnClickListener(
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

}