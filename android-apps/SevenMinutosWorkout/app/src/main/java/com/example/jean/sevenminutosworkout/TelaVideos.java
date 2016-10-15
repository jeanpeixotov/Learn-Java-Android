package com.example.jean.sevenminutosworkout;

import android.os.Bundle;
import android.provider.Browser;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

public class TelaVideos extends AppCompatActivity {
    WebView meuWebView;
    TextView meuTitulo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_videos);

        meuWebView = (WebView) findViewById(R.id.meusVideos);
        meuTitulo = (TextView) findViewById(R.id.textoNomeExercicio);

        meuWebView.setWebViewClient(new MyBrowser());
        meuWebView.getSettings().setJavaScriptEnabled(true);

        meuWebView.loadUrl("https://www.youtube.com/watch?v=b1BNCW0WwVs");
        meuWebView.setWebChromeClient(new WebChromeClient());

        String nomeVideo = getIntent().getExtras().getString("nomeVideo");
        meuTitulo.setText(nomeVideo);

    }
    private class MyBrowser extends WebViewClient{
        public boolean overrideUrlLoading(WebView view,String url){
            view.loadUrl(url);
            return true;
        }
    }

}
