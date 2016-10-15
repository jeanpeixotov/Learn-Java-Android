package com.example.jean.sevenminutosworkout;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jean on 31/07/2016.
 */
public class TreinosAdapter extends ArrayAdapter{

    List list = new ArrayList();

    public TreinosAdapter(Context context, int resource) {
        super(context, resource);
    }
    @Override
    public void add(Object object) {
        super.add(object);
        list.add(object);
    }
    @Override
    public int getCount() {
        return this.list.size();
    }
    public Object getItem(int position){
        return this.list.get(position);
    }

     public View getView(int position, View convertView, ViewGroup parent){
         View row;
         row = convertView;
         DataHandler handler;

         if(convertView == null){
             LayoutInflater inflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
             row =  inflater.inflate(R.layout.celulatreino, parent,false);
             handler = new DataHandler();
             handler.icone = (ImageView)row.findViewById(R.id.iconeTreino);
             handler.treino = (TextView) row.findViewById(R.id.textoNomeTreino);
             handler.inicial = (TextView) row.findViewById(R.id.textoIdTreino);
             row.setTag(handler);
         }else{
            handler = (DataHandler)row.getTag();
         }
         TreinosDataProvaider dataProvaider;
         dataProvaider = (TreinosDataProvaider) this.getItem(position);
         handler.icone.setImageResource(dataProvaider.getListaIcones());
         handler.treino.setText(dataProvaider.getListaTreinos());
         handler.inicial.setText(dataProvaider.getListaIniciais());
         return row;
     }


    private class DataHandler {
        ImageView icone;
        TextView treino,inicial;
    }
}
