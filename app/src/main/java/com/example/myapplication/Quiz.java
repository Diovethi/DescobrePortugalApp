package com.example.myapplication;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.cardview.widget.CardView;

import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.myapplication.model.CidadeModel;
import com.example.myapplication.model.OpcaoModel;
import com.example.myapplication.model.PerguntaModel;
import com.example.myapplication.model.UserModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Quiz extends AppCompatActivity {

    ImageView userIcon;
    TextView pergunta;
    Button btOpcao1;
    Button btOpcao2;
    Button btOpcao3;
    Button btOpcao4;
    DialogUser dialogUser;
    CardView cardPergunta;
    ImageView imagemFundo;
    boolean respostaCerta;

    Integer nPergunta, nPerguntasMax;
    UserModel userModel;
    PerguntaModel perguntaModel;
    CidadeModel cidadeModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        userModel = (UserModel) getIntent().getExtras().get("user");
        cidadeModel = (CidadeModel) getIntent().getExtras().get("cidade");
        nPergunta = getIntent().getIntExtra("npergunta", 0);
        nPerguntasMax = getIntent().getIntExtra("nPerguntasMax", 1)-1;
        userIcon = findViewById(R.id.userIcon);
        pergunta = findViewById(R.id.labelCidade);
        btOpcao1 = findViewById(R.id.btOpcao1);
        btOpcao2 = findViewById(R.id.btOpcao2);
        btOpcao3 = findViewById(R.id.btOpcao3);
        btOpcao4 = findViewById(R.id.btOpcao4);
        cardPergunta = findViewById(R.id.cardPergunta);
        dialogUser = new DialogUser(this, getApplication(), getIntent(), userModel, cidadeModel);
        imagemFundo = findViewById(R.id.imagemFundo3);
        perguntaModel = new PerguntaModel();

        setDesignElements(userModel);
        GetPergunta(cidadeModel.getNome());

        userIcon.setOnClickListener(
                view -> dialogUser.showUserDialog()
        );

        btOpcao1.setOnClickListener(
                view -> {
                    SelecionarResposta(0);
                }
        );

        btOpcao2.setOnClickListener(
                view -> SelecionarResposta(1)
        );

        btOpcao3.setOnClickListener(
                view -> SelecionarResposta(2)
        );

        btOpcao4.setOnClickListener(
                view -> SelecionarResposta(3)
        );


    }


    public void GetPergunta(String cidade) {

        Cache cache = new DiskBasedCache(getCacheDir(), 1024 * 1024);
        Network network = new BasicNetwork(new HurlStack());
        RequestQueue requestQueue = new RequestQueue(cache, network);
        requestQueue.start();

        String url = getString(R.string.BASE_URL) + "pergunta/cidade/" + cidade + "/" + nPergunta;

        System.out.println("URL:" + url);


        try {

            Response.Listener<JSONObject> sucessListener = new Response.Listener<JSONObject>() {

                @RequiresApi(api = Build.VERSION_CODES.O)
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        perguntaModel.setId_Pergunta(response.getInt("id_Pergunta"));
                        perguntaModel.setDescricao(response.getString("descricao"));
                        perguntaModel.setId_Cidade(response.getInt("id_Cidade"));
                        JSONArray opcaoArray = response.getJSONArray("opcoesDto");
                        List<OpcaoModel> opcaoModels = new ArrayList<>();
                        for (int i = 0; i < opcaoArray.length(); i++) {
                            OpcaoModel opcaoModel = new OpcaoModel();
                            opcaoModel.setId_Opcao(opcaoArray.getJSONObject(i).getInt("id_Opcao"));
                            opcaoModel.setDescricao(opcaoArray.getJSONObject(i).getString("descricao"));
                            opcaoModel.setOpcaoCorreta(opcaoArray.getJSONObject(i).getBoolean("opcaoCorreta"));
                            opcaoModel.setId_Pegunta(opcaoArray.getJSONObject(i).getInt("id_Pegunta"));
                            opcaoModels.add(opcaoModel);
                        }
                        perguntaModel.setOpcoes(opcaoModels);

                        pergunta.setText(perguntaModel.getDescricao());
                        btOpcao1.setText(perguntaModel.getOpcaoModels().get(0).getDescricao());
                        btOpcao2.setText(perguntaModel.getOpcaoModels().get(1).getDescricao());
                        btOpcao3.setText(perguntaModel.getOpcaoModels().get(2).getDescricao());
                        btOpcao4.setText(perguntaModel.getOpcaoModels().get(3).getDescricao());

                        requestQueue.stop();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            };

            Response.ErrorListener errorListener = error -> {
                //Toast.makeText(getApplicationContext(), "Por favor valide novamente os valores! " + error, Toast.LENGTH_LONG).show();

            };

            JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, sucessListener, errorListener);
            requestQueue.add(request);

        } catch (Exception ex) {
            Toast.makeText(getApplicationContext(), "" + ex + "", Toast.LENGTH_LONG).show();
        }

    }

    public void SelecionarResposta(Integer n) {

        if (perguntaModel.getOpcaoModels().get(n).getOpcaoCorreta()) {
            respostaCerta = true;
        } else {
            respostaCerta = false;
        }



        Cache cache = new DiskBasedCache(getCacheDir(), 1024 * 1024);
        Network network = new BasicNetwork(new HurlStack());
        RequestQueue requestQueue = new RequestQueue(cache, network);
        requestQueue.start();

        String url = getString(R.string.BASE_URL) + "pergunta/responder";

        JSONObject jsonObject = new JSONObject();
        try {

            jsonObject.put("id_utilizador", userModel.getId_utilizador());
            jsonObject.put("id_opcao", perguntaModel.getOpcaoModels().get(n).getId_Opcao());

            Response.Listener<JSONObject> sucessListener = response -> {
                showRespostaDialog();
                requestQueue.stop();

            };

            Response.ErrorListener errorListener = error -> {
                Toast.makeText(getApplicationContext(), "Por favor valide novamente os valores! " + error, Toast.LENGTH_LONG).show();
                System.out.println(error);
            };

            JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, jsonObject, sucessListener, errorListener);
            requestQueue.add(request);

        } catch (JSONException e) {
            Toast.makeText(getApplicationContext(), "JSON exception", Toast.LENGTH_LONG).show();
        } catch (Exception ex) {
            Toast.makeText(getApplicationContext(), "" + ex + "", Toast.LENGTH_LONG).show();
        }
    }

    private void showRespostaDialog() {

        Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_resposta);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(false);
        putDialogDetails(dialog);
        dialog.show();


    }

    private void putDialogDetails(Dialog dialog) {

        ImageView logoImage = dialog.findViewById(R.id.logoImage);
        if (!respostaCerta)
            logoImage.setBackground(getDrawable(R.drawable.incorrect_icon));

        Button btnSair = dialog.findViewById(R.id.btnSair);

        btnSair.setOnClickListener(
                view -> {
                    Intent i = new Intent(getApplicationContext(), Menu.class);
                    i.putExtra("user", userModel);
                    i.putExtra("cidade", cidadeModel);
                    startActivity(i);
                }
        );

        Button btnProximo = dialog.findViewById(R.id.btnProximo);

        btnProximo.setOnClickListener(
                view -> {
                    dialog.cancel();
                    nPergunta++;
                    GetPergunta(cidadeModel.getNome());
                }
        );

        if (nPergunta >= nPerguntasMax) {
            btnProximo.setEnabled(false);
            btnProximo.setActivated(false);
            btnProximo.setVisibility(View.GONE);
            getPontuacao(dialog);


        }

        btnSair.setBackgroundTintList(AppCompatResources.getColorStateList(getApplicationContext(), Utils.getColorLightAvatar(userModel.getId_icon().toString())));
        btnProximo.setBackgroundTintList(AppCompatResources.getColorStateList(getApplicationContext(), Utils.getColorDarkAvatar(userModel.getId_icon().toString())));

    }

    public void imageClicks(View view) {
        dialogUser.getDialogEditUser().imageClick(view);
    }

    public void addIconClicks(View view) {
        dialogUser.getDialogEditUser().addIconClick(view);
    }

    public void setDesignElements(UserModel userModel) {
        cardPergunta.setBackgroundTintList(AppCompatResources.getColorStateList(getApplicationContext(), Utils.getColorLightAvatar(userModel.getId_icon().toString())));
        btOpcao1.setBackgroundTintList(AppCompatResources.getColorStateList(getApplicationContext(), Utils.getColorDarkAvatar(userModel.getId_icon().toString())));
        btOpcao2.setBackgroundTintList(AppCompatResources.getColorStateList(getApplicationContext(), Utils.getColorDarkAvatar(userModel.getId_icon().toString())));
        btOpcao3.setBackgroundTintList(AppCompatResources.getColorStateList(getApplicationContext(), Utils.getColorDarkAvatar(userModel.getId_icon().toString())));
        btOpcao4.setBackgroundTintList(AppCompatResources.getColorStateList(getApplicationContext(), Utils.getColorDarkAvatar(userModel.getId_icon().toString())));
        userIcon.setImageDrawable(getDrawable(Utils.getAvatarIconId(userModel.getId_icon().toString())));
        imagemFundo.setImageDrawable(getDrawable(Utils.getBackgroundImage(cidadeModel.getNome())));
    }

    private void getPontuacao(Dialog dialog) {
        Cache cache = new DiskBasedCache(getCacheDir(), 1024 * 1024);
        Network network = new BasicNetwork(new HurlStack());
        RequestQueue requestQueue = new RequestQueue(cache, network);
        requestQueue.start();

        String url = getString(R.string.BASE_URL) + "pontuacao/get/" + userModel.getId_utilizador() + "/" + cidadeModel.getId_Cidade();

        System.out.println("URL:" + url);
        Response.Listener<JSONObject> sucessListener = response -> {
            try {
                    LinearLayout layoutFimPerguntas = dialog.findViewById(R.id.layoutFimPerguntas);
                    TextView txtFimPerguntas = dialog.findViewById(R.id.txtFimPerguntas);
                    nPerguntasMax+=1;
                    Integer respostascertas= response.getInt("nrespostasCertas");
                   // if (respostaCerta)
                 //      respostascertas+=1;
                    txtFimPerguntas.setText("Acertou " + respostascertas + " de " + nPerguntasMax + " perguntas.");
                    layoutFimPerguntas.setVisibility(View.VISIBLE);
                requestQueue.stop();
            } catch (JSONException ex) {
                ex.printStackTrace();
            }
        };
        Response.ErrorListener errorListener = error -> Toast.makeText(getApplicationContext(), "Erro a receber o Progresso das perguntas! " + error, Toast.LENGTH_LONG).show();

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, sucessListener, errorListener);
        requestQueue.add(request);
    }


}