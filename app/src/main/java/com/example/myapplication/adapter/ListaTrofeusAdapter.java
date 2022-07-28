package com.example.myapplication.adapter;

import android.app.Application;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;

import java.util.List;

public class ListaTrofeusAdapter extends RecyclerView.Adapter<ListaTrofeusAdapter.ViewHolder> {

    private List<String> data;
    private List<Integer> imgs;
    Application aplication;

    public ListaTrofeusAdapter(List<String> data ,List<Integer> imgs){
        this.data = data;
        this.imgs=imgs;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rowItem = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_cidades_view, parent, false);
        return new ViewHolder(rowItem, aplication);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.textView.setText(this.data.get(position));
        holder.imgTrofeu.setImageResource(this.imgs.get(position));
    }

    @Override
    public int getItemCount() {
        return this.data.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView textView;
        private Context context;
        private ImageView imgTrofeu;

        public ViewHolder(View view , Application aplication) {
            super(view);
            view.setOnClickListener(this);
            this.textView = view.findViewById(R.id.labelCidade);
            this.imgTrofeu=  view.findViewById(R.id.imgCidade);
            context = itemView.getContext();
            int trofeuSelecionado = R.drawable.trofeu_ouro_logo;
            imgTrofeu.setImageResource(trofeuSelecionado);

        }

        @Override
        public void onClick(View view) {
           // Intent i = new Intent(context, PontosInteresse.class);
           /*
            i.putExtra("idUser",);
            i.putExtra("Cidade",);
             */
            //context.startActivity(i);
            Toast.makeText(view.getContext(), "position : " + getLayoutPosition() + " text : " + this.textView.getText(), Toast.LENGTH_SHORT).show();
        }
    }
}
