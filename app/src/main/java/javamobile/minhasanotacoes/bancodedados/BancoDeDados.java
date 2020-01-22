package javamobile.minhasanotacoes.bancodedados;

import android.content.Context;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class BancoDeDados {

    public SQLiteDatabase banco;
    public GerenciarBanco gerenciarBanco;

    public BancoDeDados(Context context){
        // cria uma instância de GerenciarBanco
        gerenciarBanco = new GerenciarBanco(context);

    }

    public boolean criarAnotacao(String titulo, String conteudo){
        //acesso ao banco de dados para escrita
        banco = gerenciarBanco.getWritableDatabase();

        //cria um conjunto vazio de chave/valor usando o tamanho inicial padrão
        ContentValues valores = new ContentValues();
        valores.put("titulo",titulo);
        valores.put("conteudo", conteudo);

        //insere um registro na tabela e retorna um resultado
        long resultado = banco.insert("anotacoes",null, valores);

        //fecha o banco de dados de escrita
        banco.close();

        return resultado > 0;
    }
    public Cursor obterAnotacoes(){
        String[] colunas = {"_id","titulo"};
        //acesso ao banco de dados para leitura
        SQLiteDatabase db = gerenciarBanco.getReadableDatabase();
        Cursor cursor = db.query("anotacoes",colunas,null,null,null,null,"_id DESC");

        if(cursor != null){
            cursor.moveToFirst(); // move o cursor para a primeira linha
        }

        db.close(); // fecha o banco de dados de leitura
        return cursor; // retorna o resultado
    }

    public void atualizaAnotacao(int id, String titulo, String conteudo){
        //acesso ao banco de dados para leitura
        SQLiteDatabase db = gerenciarBanco.getReadableDatabase();
        String where = "_id = " + id;

        ContentValues valores = new ContentValues();
        valores.put("titulo", titulo);
        valores.put("conteudo",conteudo);

        db.update("anotacoes", valores, where, null);
        db.close();
    }

    public void excluiAnotacao(int id){
        SQLiteDatabase db = gerenciarBanco.getReadableDatabase();
        String where = "_id = " + id;

        db.delete("anotacoes", where, null);
        db.close();
    }

    public Cursor consultarAnotacaoPeloId(int notaId){
        Cursor cursor;
        String[] colunas = {"_id","titulo","conteudo"};
        String where = "_id = " + notaId; //condicao da pesquisa

        //acesso ao banco de dados para leitura
        SQLiteDatabase db = gerenciarBanco.getReadableDatabase();
        cursor = db.query("anotacoes",colunas,where,null,null,null,null,null);
        if (cursor!=null){
            cursor.moveToFirst(); // move o cursor para a primeira linha
        }
        db.close(); // fecha o banco de dados de leitura
        return cursor; // retorna o resultado
    }
}
