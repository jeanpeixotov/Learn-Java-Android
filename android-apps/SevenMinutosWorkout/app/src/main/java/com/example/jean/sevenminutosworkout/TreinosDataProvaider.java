package com.example.jean.sevenminutosworkout;

/**
 * Created by Jean on 31/07/2016.
 */
public class TreinosDataProvaider {
    private int listaIcones;
    private String listaTreinos, listaIniciais;

    public TreinosDataProvaider(int listaIcones, String listaTreinos, String listaIniciais){
        this.setListaIcones(listaIcones);
        this.setListaTreinos(listaTreinos);
        this.setListaIniciais(listaIniciais);
    }


    public int getListaIcones() {
        return listaIcones;
    }

    public void setListaIcones(int listaIcones) {
        this.listaIcones = listaIcones;
    }

    public String getListaTreinos() {
        return listaTreinos;
    }

    public void setListaTreinos(String listaTreinos) {
        this.listaTreinos = listaTreinos;
    }

    public String getListaIniciais() {
        return listaIniciais;
    }

    public void setListaIniciais(String listaIniciais) {
        this.listaIniciais = listaIniciais;
    }
}
