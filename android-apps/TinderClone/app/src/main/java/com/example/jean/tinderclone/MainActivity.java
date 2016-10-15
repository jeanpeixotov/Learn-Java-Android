package com.example.jean.tinderclone;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    ImageView imagemCima;
    ImageView imagemBaixo;
    TextView textoNome;
    ImageView icone;
    RelativeLayout meuLayout;
    int contador = 0;
    boolean podeSwipe = true;

    String[] listaNomes = {"Rosquinha","Simpson","Joao","Bart","Lisa","Maggie","Hugo"};

    int[] listaImagens = {R.drawable.simps,R.drawable.simpsonparede,R.drawable.simpsons,
            R.drawable.simpsons12,R.drawable.simpss,R.drawable.thesimpsonssimpsonsfamilypicture};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imagemCima = (ImageView) findViewById(R.id.imagemCima);
        imagemBaixo = (ImageView) findViewById(R.id.imagemBaixo);
        textoNome = (TextView) findViewById(R.id.textoNome);
        icone = (ImageView) findViewById(R.id.imageView);
        meuLayout = (RelativeLayout) findViewById(R.id.meuLayout);
        textoNome.setText(listaNomes[0]);
        imagemCima.setImageResource(R.drawable.simpsons);
        imagemBaixo.setImageResource(R.drawable.simpsons12);

        imagemBaixo.setAlpha(0.0f);
        imagemBaixo.setRotation(-30.0f);
        imagemBaixo.setScaleX(0.35f);
        imagemBaixo.setScaleY(0.35f);



        meuLayout.setOnTouchListener(new OnSwipeTouchListener(this) {
            public void onSwipeTop() {
                Toast.makeText(MainActivity.this, "top", Toast.LENGTH_SHORT).show();

            }
            public void onSwipeRight() {
                Toast.makeText(MainActivity.this, "right", Toast.LENGTH_SHORT).show();
                animaFoto(false);
            }
            public void onSwipeLeft() {
                Toast.makeText(MainActivity.this, "left", Toast.LENGTH_SHORT).show();
                animaFoto(true);
            }
            public void onSwipeBottom() {
                Toast.makeText(MainActivity.this, "bottom", Toast.LENGTH_SHORT).show();

            }

        });
    }

    public void animaFoto(boolean estado){

        float direcao = 1000.0f;
        float giro = 30.0f;

        imagemCima.setImageResource(R.drawable.certo);
        if(estado){
            direcao = -1000.0f;
            giro = -30.0f;
            imagemCima.setImageResource(R.drawable.errado);
        }

        if(podeSwipe){
            contador++;
            podeSwipe=false;
            if(contador == listaImagens.length){
                contador = 0;
            }
            imagemCima.animate()
                    .translationXBy(direcao)
                    .rotationBy(giro)
                    .setDuration(500);
            imagemBaixo.animate()
                    .rotationBy(30.0f)
                    .alphaBy(1.0f)
                    .scaleX(1.0f)
                    .scaleY(1.0f)
                    .setDuration(500);
            icone.animate()
                    .alphaBy(1.0f)
                    .setDuration(250);

            new CountDownTimer(750,1000){
                @Override
                public void onTick(long millisecUntilDone) {

                }

                @Override
                public void onFinish() {
                    trocaTexturas();
                    podeSwipe=true;
                    icone.animate()
                            .alphaBy(-1.0f)
                            .setDuration(250);

                }
            }.start();
        }

    }

    public void trocaTexturas(){
        if(contador == listaImagens.length-1){
            imagemBaixo.setImageResource(listaImagens[0]);
        }else{
            imagemBaixo.setImageResource(listaImagens[contador+1]);
        }
        imagemCima.setImageResource(listaImagens[contador]);
        reposicionaCartas();
    }
    public void reposicionaCartas(){
        imagemCima.animate()
                .translationX(0)
                .rotation(0)
                .setDuration(0);
        imagemBaixo.animate()
                .rotation(-30)
                .alpha(0)
                .scaleX(0.35f)
                .scaleY(0.35f)
                .setDuration(0);
    }

}
