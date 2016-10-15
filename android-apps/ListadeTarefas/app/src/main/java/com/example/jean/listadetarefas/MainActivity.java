package com.example.jean.listadetarefas;

import android.app.Activity;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;


public class MainActivity extends Activity {

    private EditText meuTexto;
    private Button meuBotao;
    private ListView meuListView;
    private SQLiteDatabase bancoDados;
    private ArrayAdapter<String> itemAdaptador;
    private ArrayList<Integer> ids;
    private ArrayList<String> itens;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        meuTexto = (EditText) findViewById(R.id.editText);
        meuBotao = (Button) findViewById(R.id.button);
        meuListView = (ListView) findViewById(R.id.listView);
        carregarTarefas();

        meuListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long id) {
                alertaApagaTarefa(position);
                //apagarTarefas(ids.get(position));
                return false;
            }
        });

        meuBotao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adicionarTarefas(meuTexto.getText().toString());

            }
        });

    }

    private void carregarTarefas(){
        try{
            bancoDados = openOrCreateDatabase("MeuBancoMinhasTarefasAPP", MODE_PRIVATE, null);
            bancoDados.execSQL("CREATE TABLE IF NOT EXISTS minhastarefas(id INTEGER PRIMARY KEY AUTOINCREMENT, tarefa VARCHAR)");
//          String novaTarefa = meuTexto.getText().toString();
//          bancoDados.execSQL("INSERT INTO minhastarefas(tarefa) VALUES('" + novaTarefa + "')");
//          Cursor cursor = bancoDados.rawQuery("SELECT * FROM minhastarefas",null);
            Cursor cursor = bancoDados.rawQuery("SELECT * FROM minhastarefas ORDER BY id DESC",null);

            int indiceColunaId = cursor.getColumnIndex("id");
            int indiceColunaTarefa = cursor.getColumnIndex("tarefa");

            itens = new ArrayList<String>();
            ids = new ArrayList<Integer>();
            itemAdaptador = new ArrayAdapter<String>(getApplicationContext(),
                    android.R.layout.simple_list_item_2,
                    android.R.id.text1,
                    itens);

            meuListView.setAdapter(itemAdaptador);

            cursor.moveToFirst();
            while (cursor!= null){
                Log.i("Logx","ID" +cursor.getString(indiceColunaId)+"Tarefa"+cursor.getString(indiceColunaTarefa) );
                itens.add(cursor.getString(indiceColunaTarefa));
                ids.add(Integer.parseInt(cursor.getString(indiceColunaId)));
                cursor.moveToNext();
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    private void adicionarTarefas(String novaTarefa){
        try {
            if(novaTarefa.equals("")){
                Toast.makeText(MainActivity.this, "Insira uma tarefa", Toast.LENGTH_SHORT).show();
            }
            else{
                Toast.makeText(MainActivity.this, "Tarefa" + novaTarefa+"inserida", Toast.LENGTH_SHORT).show();
                meuTexto.setText("");
                bancoDados.execSQL("INSERT INTO minhastarefas(tarefa) VALUES('" + novaTarefa + "')");
                carregarTarefas();
            }
        }
        catch(Exception e){
            e.printStackTrace();

        }
    }

    private void apagarTarefas(Integer id){
        try{
            bancoDados.execSQL("DELETE FROM minhastarefas WHERE id="+id);
            Toast.makeText(MainActivity.this, "Tarefa removida", Toast.LENGTH_SHORT).show();
            carregarTarefas();
        }catch(Exception e){
            e.printStackTrace();

        }
    }
    private void alertaApagaTarefa(Integer idSelecionado){
        String tarefaSelecionada = itens.get(idSelecionado);
        final Integer numeroId = idSelecionado;
        new AlertDialog.Builder(MainActivity.this)
                .setTitle("Aaviso!!!")
                .setMessage("Deseja apagar a tarefa" +tarefaSelecionada+" ?")
                .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        apagarTarefas(ids.get(numeroId));
                    }
                }).setNegativeButton("Não",null).show();
    }



}
