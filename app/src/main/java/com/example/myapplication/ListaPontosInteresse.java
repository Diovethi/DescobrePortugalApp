package com.example.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.adapter.CustomAdapter;

import java.util.ArrayList;
import java.util.List;

public class ListaPontosInteresse extends Activity {



    Button btVoltar;
    Intent intent;
    String cidade;
    String idUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_lista_pontos_interesse);

        ArrayList<String> listaNomes = new ArrayList<>();
        listaNomes.add("Horse");
        listaNomes.add("Cow");
        listaNomes.add("Camel");
        listaNomes.add("Sheep");
        listaNomes.add("Goat");

        RecyclerView recyclerView = findViewById(R.id.recycler_view);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new CustomAdapter(generateData()));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));





        btVoltar = findViewById(R.id.btVoltar);

        intent= getIntent();
        cidade= intent.getStringExtra("Cidade");
        idUser = intent.getStringExtra("id");

        Toast.makeText(this, "Cidade e:"+cidade, Toast.LENGTH_SHORT).show();


        btVoltar.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(ListaPontosInteresse.this, Menu.class);
                        // SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                        i.putExtra("id",idUser);
                        i.putExtra("Cidade",cidade);
                        startActivity(i);

                    }
                }
        );
    }

    private List<String> generateData() {
        List<String> data = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            data.add(String.valueOf(i) + "th Element");
        }
        // Toast.makeText(this, data.size(), Toast.LENGTH_SHORT).show();
        return data;
    }



}
