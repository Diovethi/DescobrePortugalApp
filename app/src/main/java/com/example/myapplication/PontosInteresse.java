package com.example.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import com.example.myapplication.model.UserModel;

import java.time.LocalDate;

public class PontosInteresse extends Activity {

    Button btVoltar;
    Intent intent;
    String cidade;
    String idUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_pontos_interesse);

        btVoltar = findViewById(R.id.btVoltar);

        intent= getIntent();
        cidade= intent.getStringExtra("Cidade");
        idUser = intent.getStringExtra("id");

        Toast.makeText(this, "Cidade e:"+cidade, Toast.LENGTH_SHORT).show();


        btVoltar.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(PontosInteresse.this, ListaPontosInteresse.class);
                        // SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                        i.putExtra("id",idUser);
                        i.putExtra("Cidade",cidade);
                        startActivity(i);

                    }
                }
        );
    }
}
