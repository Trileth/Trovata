package com.example.jeffe.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.jeffe.model.GrupoProduto;


public class SpinnerGrupoAdapter extends ArrayAdapter<GrupoProduto> {

    private Context context;
    private GrupoProduto[] lista;

    public SpinnerGrupoAdapter(Context context, int textViewResourceId,
                               GrupoProduto[] values) {
        super(context, textViewResourceId, values);
        this.context = context;
        this.lista = values;
    }

    @Override
    public int getCount(){
        return lista.length;
    }

    @Override
    public GrupoProduto getItem(int position){
        return lista[position];
    }

    @Override
    public long getItemId(int position){
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView label = new TextView(context);
        label.setText(lista[position].getId()+" - "+lista[position].getDescricao());
        return label;
    }

    @Override
    public View getDropDownView(int position, View convertView,
                                ViewGroup parent) {
        TextView label = new TextView(context);
        label.setText(lista[position].getId()+" - "+lista[position].getDescricao());

        return label;
    }
}
