package com.example.myapplication;

import android.app.Application;
import android.app.Dialog;
import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.Window;
import android.widget.Button;

public class DialogResposta extends ContextWrapper {

    private final Context context;
    private final Application application;
    private Boolean respostaCerta;
    private final DialogResposta dialogResposta;


    public DialogResposta(Context base, Application application, boolean respostaCerta) {
        super(base);
        this.context=base;
        this.respostaCerta=respostaCerta;
        this.application=application;
        dialogResposta= new DialogResposta(context,application,respostaCerta);
    }

    public void showRespostaDialog(boolean opcaoEscolhida){
        Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_resposta);
        dialog.getWindow().setBackgroundDrawable( new ColorDrawable(Color.TRANSPARENT));
        this.respostaCerta=opcaoEscolhida;

        dialog.setCancelable(true);
        dialog.show();

       putDialogDetails(dialog);
    }


    public DialogResposta getDialogResposta() {
        return dialogResposta;
    }



    private void putDialogDetails(Dialog dialog){



        Button btLogoutDialog = dialog.findViewById(R.id.btEditLogout);

        btLogoutDialog.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                      //  Intent i = new Intent(context,Login.class);
                      //  i.putExtra("id",userModel.getId_utilizador());
                       // context.startActivity(i);

                    }
                }
        );

        Button btEditDialog = dialog.findViewById(R.id.btEditLogout);
      //  btEditDialog.setBackgroundTintList(AppCompatResources.getColorStateList(context, Utils.getColorDarkAvatar()));
        btEditDialog.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        dialogResposta.showRespostaDialog(respostaCerta);
                        dialog.cancel();
                    }
                }
        );




    }



}
