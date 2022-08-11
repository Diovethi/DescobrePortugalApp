package com.example.myapplication.adapter;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.PontosInteresse;
import com.example.myapplication.R;
import com.example.myapplication.model.CidadeModel;
import com.example.myapplication.model.MonumentoModel;
import com.example.myapplication.model.UserModel;

import java.util.List;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder> {

    private List<String> dataNome;
    private List<Integer> dataId;
    private MonumentoModel monumento;
    Application aplication;
    UserModel userModel;
    CidadeModel cidadeModel;




    public CustomAdapter( List<String> dataNome, List<Integer> dataId, UserModel userModel, CidadeModel cidadeModel){
        this.dataNome = dataNome;
        this.dataId = dataId;
        this.cidadeModel=cidadeModel;
        this.userModel=userModel;

    }

    public CustomAdapter(MonumentoModel monumento ){
        this.monumento = monumento;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rowItem = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_pontointeresse_view, parent, false);
        return new ViewHolder(rowItem, aplication);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.textView.setText(this.dataNome.get(position));
        holder.textView.setId(this.dataId.get(position));


        if(this.dataId.get(position)==1)
            holder.imageView.setImageResource(R.drawable.muralhas);
        else if(this.dataId.get(position)==2)
            holder.imageView.setImageResource(R.drawable._50px_jardim_do_pa_o_episcopal);
        else if(this.dataId.get(position)==3)
            holder.imageView.setImageResource(R.drawable.castelobranco);




    }

    @Override
    public int getItemCount() {
        return this.dataNome.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView textView;
        private Context context;
        private ImageView imageView;


        public ViewHolder(View view , Application aplication) {
            super(view);
            view.setOnClickListener(this);
            this.textView = view.findViewById(R.id.textview);
            this.imageView= view.findViewById(R.id.imageView23);
            context = itemView.getContext();
        }

        @Override
        public void onClick(View view) {

            MonumentoModel monumentoModel= new MonumentoModel();
            monumentoModel.setId_Monumento(this.textView.getId());
            monumentoModel.setNome(this.textView.getText().toString());

           Intent i = new Intent(context, PontosInteresse.class);
            i.putExtra("Monumento",  monumentoModel);
            i.putExtra("user", CustomAdapter.this.userModel);
            i.putExtra("cidade",CustomAdapter.this.cidadeModel);

            context.startActivity(i);


            }
    }
}
