package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class MapaPortugal extends AppCompatActivity {

    ImageView Madeira;
    ImageView Algarve;
    ImageView CasteloBranco;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mapa_portugal);

        Madeira = findViewById(R.id.madeira);
        Algarve = findViewById(R.id.algarve);
        CasteloBranco = findViewById(R.id.casteloBranco);

        CasteloBranco.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        // mostrador.setText(textNome.getText().toString());
                        Intent i = new Intent(MapaPortugal.this,CidadeSelecionada.class);
                        //i.putExtra("id",id);
                        startActivity(i);
                    }
                }
        );

        Madeira.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        // mostrador.setText(textNome.getText().toString());
                        Intent i = new Intent(MapaPortugal.this,Menu.class);
                        //i.putExtra("id",id);
                        startActivity(i);
                    }
                }
        );

        Algarve.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //test.getDrawable().getConstantState().equals(getResources().getDrawable(R.drawable.red).getConstantState()))
                       if(Algarve.getDrawable().getConstantState().equals(getResources().getDrawable(R.drawable.medalnao).getConstantState())) {
                           Algarve.setImageResource(R.drawable.medal);
                       }else {
                           Algarve.setImageResource(R.drawable.medalnao);
                       }
                    }
                }
        );

    }
}