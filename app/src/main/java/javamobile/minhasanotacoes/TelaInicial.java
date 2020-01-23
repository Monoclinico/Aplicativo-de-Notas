package javamobile.minhasanotacoes;


import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import javamobile.minhasanotacoes.bancodedados.BancoDeDados;

public class TelaInicial extends AppCompatActivity {

    ViewHolder mViewHolder = new ViewHolder();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_inicial);

        //captura os elementos
        mViewHolder.listListaDeNotas = (ListView) findViewById(R.id.ListaDeNotas);
        mViewHolder.textSemAnotacao = (TextView) findViewById(R.id.text_sem_anotacao);
        mViewHolder.textNumberItens = (TextView) findViewById(R.id.text_number_itens);
        //------------------------------------------------

        //cria o banco de dados se necessario e retorna o banco de dados
        BancoDeDados bancoDeDados = new BancoDeDados(getBaseContext());

        //obtém o resultado da query de obterResultados
        final Cursor cursor = bancoDeDados.obterAnotacoes();



        if (cursor.getCount()>0) {
            String[] nomeColunas = new String[]{"_id", "titulo"};
            int[] idViews = new int[]{R.id.labelId, R.id.labelTitulo};

            //mapeia colunas de um cursor para TextViews ou ImageViews de um elemento xml(modelo_lista)
            SimpleCursorAdapter adaptador = new SimpleCursorAdapter(getBaseContext(),
                    R.layout.modelo_lista, cursor, nomeColunas, idViews, 0);

            mViewHolder.textSemAnotacao.setVisibility(View.GONE);
            mViewHolder.listListaDeNotas.setVisibility(View.VISIBLE);

            //coloca o objeto adaptador no elemento ListaDeNotas
            mViewHolder.listListaDeNotas.setAdapter(adaptador);

            //criar um evento de click para cada um dos elementos da lista
            mViewHolder.listListaDeNotas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    cursor.moveToPosition(position);
                    Intent intent = new Intent(TelaInicial.this, EditarAnotacao.class);
                    intent.putExtra("id", cursor.getInt(cursor.getColumnIndexOrThrow("_id")));
                    startActivity(intent);
                    finish();
                }
            });

        }else{
            mViewHolder.listListaDeNotas.setVisibility(View.GONE);
            mViewHolder.textSemAnotacao.setVisibility(View.VISIBLE);
        }

        mViewHolder.textNumberItens.setText(String.valueOf(mViewHolder.listListaDeNotas.getCount()));

    }

    public void abrirTelaCriarNovaAnotacao(View v){
        //inicia a atividade de criação de notas
        Intent startNewActivity = new Intent(this, CriarAnotacao.class);
        startActivity(startNewActivity);
    }

    private static class ViewHolder{
        ListView listListaDeNotas;
        TextView textSemAnotacao;
        TextView textNumberItens;
    }
}
