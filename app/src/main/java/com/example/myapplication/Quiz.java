package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Quiz extends AppCompatActivity {

    TextView logout;
    TextView edit;
    TextView pergunta;
    Button resposta1;
    Button resposta2;
    Button resposta3;
    Button resposta4;

    int id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_quiz);

        logout = findViewById(R.id.logout);
        edit = findViewById(R.id.edit);
        pergunta = findViewById(R.id.pergunta);
        resposta1= findViewById(R.id.resposta1);
        resposta2= findViewById(R.id.resposta2);
        resposta3= findViewById(R.id.resposta3);
        resposta4= findViewById(R.id.resposta4);

        pergunta.setText("teste");

        logout.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        // mostrador.setText(textNome.getText().toString());
                        Intent i = new Intent(Quiz.this, Login.class);
                        startActivity(i);
                    }
                }
        );

        edit.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        // mostrador.setText(textNome.getText().toString());
                        Intent i = new Intent(Quiz.this, Register.class);
                        i.putExtra("id",id);
                        startActivity(i);
                    }
                }
        );

        resposta1.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        // mostrador.setText(textNome.getText().toString());
                        Intent i = new Intent(Quiz.this, Login.class);
                        startActivity(i);
                    }
                }
        );

        resposta2.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        // mostrador.setText(textNome.getText().toString());
                        Intent i = new Intent(Quiz.this, Login.class);
                        startActivity(i);
                    }
                }
        );

        resposta3.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        // mostrador.setText(textNome.getText().toString());
                        Intent i = new Intent(Quiz.this, Login.class);
                        startActivity(i);
                    }
                }
        );

        resposta4.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        // mostrador.setText(textNome.getText().toString());
                        resposta4.setBackgroundColor(0x0000FF00 );
                        resposta3.invalidate();
                        resposta2.invalidate();
                        resposta1.invalidate();
                        RespostaQuery( 4);
                    }
                }
        );


    }


    public void RespostaQuery(Integer resposta4){


    }
}