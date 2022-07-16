package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.model.UserModel;

public class Menu extends AppCompatActivity {

    TextView logout;
    TextView edit;
    TextView usernamelb;
    Button mapa;
    Button trofeu;
    ImageView userIcon;
    UserModel userModel= new UserModel();
    int id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_menu);
        userModel.setId_utilizador(getIntent().getExtras().getInt("id_utilizador"));
        userModel.setUsername(getIntent().getStringExtra("username"));
        userModel.setId_icon(getIntent().getExtras().getInt("id_icon"));

        logout = findViewById(R.id.logout);
        edit = findViewById(R.id.edit);
        mapa = findViewById(R.id.mapaBt);
        trofeu = findViewById(R.id.TrofeuBT);
        usernamelb = findViewById(R.id.UsernameLB);
        userIcon = findViewById(R.id.userIcon);


        userIcon.setImageDrawable(getAvatar(userModel.getId_icon().toString()));
        usernamelb.setText(userModel.getUsername());
        logout.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        // mostrador.setText(textNome.getText().toString());
                        Intent i = new Intent(Menu.this,Login.class);
                        startActivity(i);
                    }
                }
        );

        edit.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        // mostrador.setText(textNome.getText().toString());
                        Intent i = new Intent(Menu.this,Register.class);
                        i.putExtra("id",id);
                        startActivity(i);
                    }
                }
        );

        mapa.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        // mostrador.setText(textNome.getText().toString());
                        Intent i = new Intent(Menu.this,Pergunta.class);
                        i.putExtra("id",id);
                        startActivity(i);
                    }
                }
        );

        trofeu.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent i = new Intent(Menu.this,MapaPortugal.class);
                        i.putExtra("id",id);
                        startActivity(i);
                    }
                }
        );

    }
    private Drawable getAvatar(String i) {
        switch (i) {
            case "1":
                return getDrawable(R.drawable.avatar1);
            case "2":
                return getDrawable(R.drawable.avatar2);
            case "3":
                return getDrawable(R.drawable.avatar3);
            case "4":
                return getDrawable(R.drawable.avatar4);
            case "5":
                return getDrawable(R.drawable.avatar5);
            case "6":
                return getDrawable(R.drawable.avatar6);
            case "7":
                return getDrawable(R.drawable.avatar7);
            case "8":
                return getDrawable(R.drawable.avatar8);
            case "9":
                return getDrawable(R.drawable.avatar9);
            case "10":
                return getDrawable(R.drawable.avatar10);
            case "11":
                return getDrawable(R.drawable.avatar11);
            case "12":
                return getDrawable(R.drawable.avatar12);
            case "13":
                return getDrawable(R.drawable.avatar13);
            case "14":
                return getDrawable(R.drawable.avatar14);
            case "15":
                return getDrawable(R.drawable.avatar15);
            case "16":
                return getDrawable(R.drawable.avatar16);
            case "17":
                return getDrawable(R.drawable.avatar17);
            case "18":
                return getDrawable(R.drawable.avatar18);
            default:
                return getDrawable(R.drawable.add_user);
        }
    }
}