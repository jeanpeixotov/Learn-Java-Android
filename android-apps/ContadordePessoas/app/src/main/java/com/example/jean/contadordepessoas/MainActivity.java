package com.example.jean.contadordepessoas;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    int ContadorH = 0, ContadorM = 0, TotalPessoas = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        final TextView campoTexto = (TextView) findViewById(R.id.txtPessoas);
        final Button botaoHomem = (Button) findViewById(R.id.btnH);
        final Button botaoMulher = (Button) findViewById(R.id.btnM);
        final Button botaoReset = (Button) findViewById(R.id.btnReset);

        botaoHomem.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                ContadorH++;
                TotalPessoas++;
                String mensagem = Integer.toString(TotalPessoas);
                campoTexto.setText("Total: "+ mensagem + " pessoas");
                botaoHomem.setText(Integer.toString(ContadorH));
            }
        });

        botaoMulher.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                ContadorM++;
                TotalPessoas++;
                String mensagem = Integer.toString(TotalPessoas);
                campoTexto.setText("Total: "+ mensagem + " pessoas");
                botaoMulher.setText(Integer.toString(ContadorM));
            }
        });

        botaoReset.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                TotalPessoas = 0;
                ContadorM = 0;
                ContadorH = 0;
                String mensagem = Integer.toString(TotalPessoas);
                campoTexto.setText("Total: "+ mensagem + " pessoas");
                botaoMulher.setText(Integer.toString(ContadorM));
                botaoHomem.setText(Integer.toString(ContadorH));
            }
        });


    }


}
