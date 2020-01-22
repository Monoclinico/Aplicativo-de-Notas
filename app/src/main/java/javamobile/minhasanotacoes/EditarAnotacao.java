package javamobile.minhasanotacoes;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import javamobile.minhasanotacoes.bancodedados.BancoDeDados;


public class EditarAnotacao extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_anotacao);

        //retorna o banco de dados
        BancoDeDados bancoDeDados = new BancoDeDados(getBaseContext());

        //retorna o resgistro que tem o id fornecido
        final Cursor cursor = bancoDeDados.consultarAnotacaoPeloId(this.getIntent().getIntExtra("id",0));

        EditText titulo = (EditText) findViewById(R.id.campoTitulo);
        EditText conteudo = (EditText) findViewById(R.id.campoConteudo);

        //Retorna o índice baseado em zero para o nome da coluna e retorna o valor da coluna solicitada como uma String.
        titulo.setText(cursor.getString(cursor.getColumnIndexOrThrow("titulo")));
        conteudo.setText(cursor.getString(cursor.getColumnIndexOrThrow("conteudo")));


    }
    //metodo para voltar a atividade principal
    public void voltar(View v){
        Intent startNewActivity = new Intent(this, TelaInicial.class);
        startActivity(startNewActivity);
    }

    public void atualizarAnotacao(View v){
        //retorna o banco de dados
        BancoDeDados bancoDeDados = new BancoDeDados(getBaseContext());

        EditText titulo = (EditText) findViewById(R.id.campoTitulo);
        EditText conteudo = (EditText) findViewById(R.id.campoConteudo);

        String textoAtualizacaoBemSucedida = getString(R.string.atualizacaoBemSucedida);
        String textoAtualizacaoMalSucedida = getString(R.string.atualizacaoMalSucedida);
        String textoAtualizacaoTituloVazio = getString(R.string.atualizarAnotacaoTituloVazio);

        if (!(titulo.getText().toString().trim().isEmpty())) {
            try {
                //chama o metodo para atualizar o resgistro do banco de dados
                bancoDeDados.atualizaAnotacao(this.getIntent().getIntExtra("id", 0), titulo.getText().toString(), conteudo.getText().toString());
                Toast.makeText(getApplicationContext(), textoAtualizacaoBemSucedida, Toast.LENGTH_SHORT).show();
            } catch (Exception ex) {
                Toast.makeText(getApplicationContext(), textoAtualizacaoMalSucedida, Toast.LENGTH_LONG).show();
            }

            voltar(v);
        }else {
            Toast toast = Toast.makeText(getApplicationContext(), textoAtualizacaoTituloVazio, Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
            toast.show();
        }
    }

    public void excluirAnotacao(View v){
        //retorna o banco de dados
        BancoDeDados bancoDeDados = new BancoDeDados(getBaseContext());

        EditText titulo = (EditText) findViewById(R.id.campoTitulo);
        EditText conteudo = (EditText) findViewById(R.id.campoConteudo);

        String textoExclusaoBemSucedida = getString(R.string.exclusaoBemSucedida);
        String textoExclusaoMalSucedida = getString(R.string.exclusaoMalSucedida);

        try{
            //chama o metodo para exclusao de um registro do banco de dados
            bancoDeDados.excluiAnotacao(this.getIntent().getIntExtra("id",0));
            Toast.makeText(getApplicationContext(), textoExclusaoBemSucedida, Toast.LENGTH_SHORT).show();

        }catch(Exception ex){
            Toast.makeText(getApplicationContext(), textoExclusaoMalSucedida, Toast.LENGTH_LONG).show();
        }
        voltar(v);
    }
}
