package com.example.exemploretrofitblog.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.exemploretrofitblog.Model.Repositorio;
import com.example.exemploretrofitblog.R;

import java.util.ArrayList;

public class AdapterRepositorio extends RecyclerView.Adapter<AdapterRepositorio.MyViewHolder> {
    private ArrayList<Repositorio> listaRepositorio;
    private Context contexto;

    public AdapterRepositorio(ArrayList<Repositorio> lista, Context context) {
        this.listaRepositorio = lista;
        this.contexto = context;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemLista = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_lista, parent, false);
                return new MyViewHolder(itemLista);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final Repositorio lista = listaRepositorio.get(position);
        holder.textName.setText(lista.getName());
        holder.btnVisualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(lista.getHtml_url()));
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                contexto.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return listaRepositorio.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView textName;
        Button btnVisualizar;
        public MyViewHolder(@NonNull View itemView){
            super(itemView);
            textName = itemView.findViewById(R.id.textName);
            btnVisualizar = itemView.findViewById(R.id.btnVisualizar);
        }
    }
}
