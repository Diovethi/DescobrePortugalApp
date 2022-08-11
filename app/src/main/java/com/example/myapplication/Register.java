package com.example.myapplication;

import android.annotation.SuppressLint;
import android.app.Application;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.api.WebAPI;

import java.util.ArrayList;
import java.util.Calendar;

public class Register extends AppCompatActivity {


   static boolean sucess=false;
    TextView usern;
    TextView passw;
    TextView gen;
    TextView mail;
    TextView ntel;
    DatePicker datan;
    Button confirm;
    Button cancel;
    HorizontalScrollView horizontalScrollView;
    ImageView addUserIcon;
    Spinner spinner;

    private View v;
    private String i = null;

     Application mApplication;

    public static void sucess() {
         sucess=true;
    }


    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_register);

        addUserIcon= findViewById(R.id.logoImage);
        horizontalScrollView=findViewById(R.id.horizontalScrollView);
        usern = findViewById(R.id.Username);
        passw = findViewById(R.id.Password);
        //gen = findViewById(R.id.genero);
        mail = findViewById(R.id.email);
        confirm= findViewById(R.id.btnProximo);
        cancel= findViewById(R.id.btnSair);
        ntel= findViewById(R.id.nTelemovel);
        datan=findViewById(R.id.dataNasc);
        spinner= findViewById(R.id.genero);



       /*
       confirm.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        // mostrador.setText(textNome.getText().toString());
                        Intent i = new Intent(Register.this,Login.class);
                        i.putExtra("username",username.getText().toString());
                        startActivity(i);
                    }
                }
        );
        */


        EditText datan = (EditText) findViewById(R.id.editText1);
        datan.setInputType(InputType.TYPE_NULL);
        datan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                DatePickerDialog picker = new DatePickerDialog(Register.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                                String mes;
                                if(monthOfYear<10){
                                    mes="0"+monthOfYear;
                                }else mes= ""+monthOfYear;


                                String dia;
                                if(dayOfMonth<10){
                                    dia="0"+dayOfMonth;
                                }else dia= ""+dayOfMonth;

                                datan.setText(year+ "-" + mes  + "-" + dia );
                                //String data=( datan.getYear()+"-"+mes+"-"+dia);
                            }
                        }, year, month, day);
                picker.show();
            }
        });







        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("Maculino");
        arrayList.add("Feminino");
        arrayList.add("Outros");

        //create array adapter and provide arrary list to it
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,arrayList);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);






        confirm.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        String username = usern.getText().toString();
                        String password = passw.getText().toString();
                        String genero= String.valueOf(spinner.getSelectedItemId());
                        String email =mail.getText().toString();
                        String nTelemovel=ntel.getText().toString();

                        String dataNasc=datan.getText().toString();




                        if(  !mail.getText().toString().contains("@") && !mail.getText().toString().contains(".") ){
                            //Toast.makeText(mApplication,"Email invalid!",Toast.LENGTH_LONG).show();
                        }else {

                            WebAPI webAPI= new WebAPI(getApplication());
                            webAPI.addUser(username, email, genero, password, dataNasc, nTelemovel,i);

                        }
                        System.out.println("entrou ");




                    }
                }
        );



        cancel.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent i = new Intent(Register.this,Login.class);
                        startActivity(i);

                    }
                }
        );
    }

    public void imageClick(View view) {
        i =  view.getContentDescription().toString();
        v= view ;
        horizontalScrollView.setVisibility(View.GONE);
        addUserIcon.setVisibility(View.VISIBLE);
        addUserIcon.setImageDrawable(getDrawable(Utils.getAvatarIconId(i)));

    }

    public void addIconClick(View view) {
        view.setVisibility(View.INVISIBLE);
        horizontalScrollView.setVisibility(View.VISIBLE);
    }


    }