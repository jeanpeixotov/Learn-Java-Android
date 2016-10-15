package com.example.jean.sevenminutosworkout;

import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {
    int treinoSelecionado;
    ImageView imagemTreino;
    TextToSpeech tssobject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final ListView listView;

        String[] lista_nomes_treinos;
        String[] inicial_treinos;

        TreinosAdapter adapter;

        int[] lista_icones = { R.drawable.estrelaouro, R.drawable.estrelaouro, R.drawable.estrelaouro,
                R.drawable.estrelaouro, R.drawable.estrelaouro, R.drawable.estrelaouro, R.drawable.estrelaouro,
                R.drawable.estrelaouro, R.drawable.estrelaouro, R.drawable.estrelaouro};

        listView = (ListView) findViewById(R.id.listaTreino);
        imagemTreino = (ImageView) findViewById(R.id.imagemTreino);
        imagemTreino.animate().alpha(1f).setDuration(2000);

        lista_nomes_treinos = getResources().getStringArray(R.array.lista_treinos);
        inicial_treinos = getResources().getStringArray(R.array.inicial_treinos);
        int i=0;
        adapter = new TreinosAdapter(getApplicationContext(),R.layout.celulatreino);
        listView.setAdapter(adapter);

        for(String titles:lista_nomes_treinos){
            TreinosDataProvaider dataProvaider = new TreinosDataProvaider(lista_icones[i],titles ,inicial_treinos[i]);
            adapter.add(dataProvaider);
            i++;
        }
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                treinoSelecionado = position;
                Toast.makeText(getApplicationContext(),"Hello "+ String.valueOf(position),Toast.LENGTH_LONG).show();
                loadTelaExericicos();
            }
        });

        tssobject = new TextToSpeech(MainActivity.this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status == TextToSpeech.SUCCESS){
                    tssobject.speak("Select your Trainning!",TextToSpeech.QUEUE_FLUSH, null);
                }else{
                    Toast.makeText(getApplicationContext()," Não suporta VoiceToSpeech!",Toast.LENGTH_SHORT).show();
                }
            }
        });


    }
    public void loadTelaExericicos(){
        Intent intent = new Intent(getApplicationContext(), TelaExercicios.class);
        intent.putExtra("numeroTreino", treinoSelecionado);
        startActivity(intent);
    }
//
//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        if(ttsobject != null){
//            ttsobject.stop():
//            ttsobject.shutdown():
//        }
//    }
}
