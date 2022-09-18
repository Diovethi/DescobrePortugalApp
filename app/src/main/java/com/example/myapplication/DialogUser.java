package com.example.myapplication;

import android.app.Application;
import android.app.Dialog;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.content.res.AppCompatResources;

import com.example.myapplication.model.CidadeModel;
import com.example.myapplication.model.UserModel;

public class DialogUser extends ContextWrapper {

    private final Context context;
    private final UserModel userModel;
    private final CidadeModel cidadeModel;
    private final DialogEditUser dialogEditUser;


    public DialogUser(Context base,Application application ,Intent intent,UserModel userModel, CidadeModel cidadeModel) {
        super(base);
        this.context=base;
        this.userModel = userModel;
        this.cidadeModel=cidadeModel;
        dialogEditUser= new DialogEditUser(context,application,intent,userModel);
    }

    public void showUserDialog( ){
        Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_user);
        dialog.getWindow().setBackgroundDrawable( new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(true);
        dialog.show();

       putDialogDetails(dialog);
    }


    public DialogEditUser getDialogEditUser() {
        return dialogEditUser;
    }

    private void putDialogDetails(Dialog dialog){

        ImageView imageView = dialog.findViewById(R.id.logoImageDialog);
        imageView.setImageDrawable(context.getDrawable(Utils.getAvatarIconId(userModel.getId_icon().toString())));

        LinearLayout linearLayout =dialog.findViewById(R.id.BackgroundAvatarDialog);
        linearLayout.setBackground(context.getDrawable(Utils.getColorLightAvatar(userModel.getId_icon().toString())));

        TextView txtUsername= dialog.findViewById(R.id.txtUsername);
        txtUsername.setText(userModel.getUsername());
        txtUsername.setTextColor(AppCompatResources.getColorStateList(context, Utils.getColorDarkAvatar(userModel.getId_icon().toString())));

        TextView txtEmail= dialog.findViewById(R.id.txtEmail);
        txtEmail.setText(userModel.getEmail());
        txtEmail.setTextColor(AppCompatResources.getColorStateList(context, Utils.getColorDarkAvatar(userModel.getId_icon().toString())));


        Button btLogoutDialog = dialog.findViewById(R.id.btLogoutDialog);
        btLogoutDialog.setBackgroundTintList(AppCompatResources.getColorStateList(context, Utils.getColorLightAvatar(userModel.getId_icon().toString())));
        btLogoutDialog.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent i = new Intent(context,Login.class);
                        i.putExtra("cidade", cidadeModel);
                        context.startActivity(i);

                    }
                }
        );

        Button btEditDialog = dialog.findViewById(R.id.btEditLogout);
        btEditDialog.setBackgroundTintList(AppCompatResources.getColorStateList(context, Utils.getColorDarkAvatar(userModel.getId_icon().toString())));
        btEditDialog.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialogEditUser.showUserEditDialog();
                        dialog.cancel();
                    }
                }
        );

        ImageView trofeus = dialog.findViewById(R.id.trophyIcon);
        trofeus.setBackgroundTintList(AppCompatResources.getColorStateList(context, Utils.getColorDarkAvatar(userModel.getId_icon().toString())));
        trofeus.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent i = new Intent(DialogUser.this,MapaPortugal.class);
                        i.putExtra("user",userModel);
                        i.putExtra("cidade",cidadeModel);
                        startActivity(i);
                    }
                }
        );



    }

}
