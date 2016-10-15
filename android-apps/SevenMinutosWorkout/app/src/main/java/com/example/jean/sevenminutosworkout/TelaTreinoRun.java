package com.example.jean.sevenminutosworkout;

import android.app.Activity;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.speech.tts.TextToSpeech;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

public class TelaTreinoRun extends Activity {

    String[] listaTreino = {"Abdominal Vela", "Barra", "Corrida", "Flexão Diamond", "Jacaré Estático",
            "Abdominal Canivete", "Carangueijo", "Minhoca", "Suicide", "Toe to Bars"};
    int[] listaTempos = {10, 10, 10, 10, 20, 30, 10, 20, 10, 30};

    int exercicioAtual = 0;
    int tempoExercicio;
    int tempoDescanso = 10;
    boolean estadoTreino = false;
    boolean falou3segs;

    ProgressBar minhaBarraProgresso;
    TextView textoExercicioAtual;
    TextView textoExercicioProximo;
    TextView textoTituloProximo;

    TextView textoTempo;
    ImageView imagemEstrela;
    RelativeLayout fundoTela;
    Button botaoPauseReset;
    CountDownTimer countDownTimer;

    TextToSpeech ttsobject;
    int result;
    int treinoSelecionado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_treino_run);

        botaoPauseReset = (Button) findViewById(R.id.botaoInicioPause);
        textoExercicioAtual = (TextView) findViewById(R.id.textoExericioAtual);
        textoExercicioProximo = (TextView) findViewById(R.id.textoExercicioProximo);
        textoTituloProximo = (TextView) findViewById(R.id.tituloProxExerc);
        textoTempo = (TextView) findViewById(R.id.textoTempo);
        minhaBarraProgresso = (ProgressBar) findViewById(R.id.meuProgressBar);
        imagemEstrela = (ImageView)findViewById(R.id.imageView);
        fundoTela = (RelativeLayout)findViewById(R.id.fundoViewTreino);

        tempoExercicio = listaTempos[exercicioAtual];
        textoTempo.setText(String.valueOf(tempoExercicio));
        minhaBarraProgresso.setMax(listaTreino.length * 2);
        minhaBarraProgresso.setProgress(1);

        treinoSelecionado = getIntent().getExtras().getInt("numeroTreino");
        String[] listaTreino = {};
        switch (treinoSelecionado){
            case 1: listaTreino = new String[]{"Aquecimento", "Corrida", "Barra", "Flexao", "Abdominal", "Agachamento", "Minhoca", "Jacare", "Urso", "Carangueijo", "Barra"};
                break;
            case 2: listaTreino = new String[]{"Corrida", "Barra", "Flexao", "Corrida", "Barra", "Flexao", "Corrida", "Barra", "Flexao", "Corrida", "Barra", "Flexao", "Corrida", "Barra", "Flexao"};
                break;
            default: listaTreino = new String[]{"Minhoca", "Jacare", "Urso", "Carangueijo", "Barra", "Minhoca", "Jacare", "Urso", "Carangueijo", "Barra"};
                break;
        }

        textoExercicioAtual.setText(String.valueOf(listaTreino[exercicioAtual]));
        textoExercicioProximo.setText(String.valueOf(listaTreino[exercicioAtual + 1]));

        ttsobject = new TextToSpeech(TelaTreinoRun.this, new TextToSpeech.OnInitListener(){
            @Override
            public void onInit(int status) {
                if(status == TextToSpeech.SUCCESS){
                    result = ttsobject.setLanguage(Locale.ENGLISH);
                    ttsobject.speak("Get Ready", TextToSpeech.QUEUE_FLUSH, null);
                }else{
                    Toast.makeText(getApplicationContext(), "Nao suporta VoiceToSpeech!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void clicaBotao(View view) {
        if(!estadoTreino){
            estadoTreino=true;
            botaoPauseReset.setText("Pause");
            proximoExercicio();
        }else{
            estadoTreino=false;
            botaoPauseReset.setText("Continue");
            stopTempo();
        }
    }

    public void atualizaLabelTempo(int secondsLeft) {

        if (secondsLeft<4 && !falou3segs){
            falou3segs=true;
            ttsobject.speak("3 seconds!", TextToSpeech.QUEUE_FLUSH, null);
        }
        textoTempo.setText(Integer.toString(secondsLeft));
    }

    public void stopTempo() {
        countDownTimer.cancel();
    }

    public void proximoExercicio(){
        ttsobject.speak("Start!", TextToSpeech.QUEUE_FLUSH, null);
        falou3segs=false;
        minhaBarraProgresso.setProgress(minhaBarraProgresso.getProgress()+1);
        countDownTimer = new CountDownTimer(tempoExercicio*1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                atualizaLabelTempo((int) millisUntilFinished / 1000);
            }
            @Override
            public void onFinish() {
                if(exercicioAtual<listaTreino.length){
                    iniciaDescanso();
                }else{
                    acabouTreino();
                }
            }
        }.start();

        fundoTela.setBackgroundColor(Color.parseColor("#373737"));
        textoExercicioAtual.setText(String.valueOf(listaTreino[exercicioAtual]));
        if(exercicioAtual<listaTreino.length-1){
            textoExercicioProximo.setText(String.valueOf(listaTreino[exercicioAtual+1]));
        }else{
            textoExercicioProximo.setText("...");
        }
        textoTempo.setText(String.valueOf(tempoExercicio));
        exercicioAtual++;
    }

    public void iniciaDescanso(){
        ttsobject.speak("Rest!", TextToSpeech.QUEUE_FLUSH, null);
        falou3segs=false;
        fundoTela.setBackgroundColor(Color.parseColor("#567EC3"));
        textoExercicioAtual.setText("Descansar!");
        tempoExercicio = tempoDescanso;
        minhaBarraProgresso.setProgress(minhaBarraProgresso.getProgress() + 1);
        countDownTimer = new CountDownTimer(tempoExercicio*1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                atualizaLabelTempo((int) millisUntilFinished / 1000);
            }
            @Override
            public void onFinish()
            {
                proximoExercicio();
            }
        }.start();
    }

    public void acabouTreino(){
        textoExercicioAtual.setText("Treino Concluido!");
        imagemEstrela.setScaleX(0);
        imagemEstrela.setScaleY(0);
        imagemEstrela.animate()
                .scaleX(0.85f)
                .scaleY(0.85f)
                .rotationBy(720)
                .setDuration(1000);
        imagemEstrela.animate().alpha(1f).setDuration(1000);
        fundoTela.setBackgroundColor(Color.parseColor("#373737"));
        MediaPlayer somPlin = MediaPlayer.create(getApplicationContext(), R.raw.buzina);
        somPlin.start();

        botaoPauseReset.setEnabled(false);
        textoExercicioAtual.setAlpha(0);
        textoExercicioProximo.setAlpha(0);
        textoTempo.setAlpha(0);
        textoTituloProximo.setText("Treino Concluido!");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(ttsobject != null){
            ttsobject.stop();
            ttsobject.shutdown();
        }
    }
}
