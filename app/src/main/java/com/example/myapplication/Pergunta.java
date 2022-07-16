package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Pergunta extends AppCompatActivity {

    TextView logout;
    TextView edit;
    Button btnN;
    Button btnS;

    int id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_pergunta);

        logout = findViewById(R.id.logout);
        edit = findViewById(R.id.edit);
        btnN = findViewById(R.id.btnN);
        btnS = findViewById(R.id.mapaBt);

        logout.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        // mostrador.setText(textNome.getText().toString());
                        Intent i = new Intent(Pergunta.this,Login.class);
                        startActivity(i);
                    }
                }
        );

        edit.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        // mostrador.setText(textNome.getText().toString());
                        Intent i = new Intent(Pergunta.this,Register.class);
                        i.putExtra("id",id);
                        startActivity(i);
                    }
                }
        );

        btnN.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        // mostrador.setText(textNome.getText().toString());
                        Intent i = new Intent(Pergunta.this,MapaPortugal.class);
                        i.putExtra("id",id);
                        startActivity(i);
                    }
                }
        );

        btnS.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        // mostrador.setText(textNome.getText().toString());
                        Intent i = new Intent(Pergunta.this,Quiz.class);
                        i.putExtra("id",id);
                        startActivity(i);
                    }
                }
        );
    }
}