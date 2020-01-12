package javamobile.minhasanotacoes;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import javamobile.minhasanotacoes.bancodedados.BancoDeDados;


public class EditarAnotacao extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_anotacao);

        BancoDeDados bancoDeDados = new BancoDeDados(getBaseContext());
        final Cursor cursor = bancoDeDados.consultarAnotacaoPeloId(this.getIntent().getIntExtra("id",0));

        EditText titulo = (EditText) findViewById(R.id.campoTitulo);
        EditText conteudo = (EditText) findViewById(R.id.campoConteudo);

        titulo.setText(cursor.getString(cursor.getColumnIndexOrThrow("titulo")));
        conteudo.setText(cursor.getString(cursor.getColumnIndexOrThrow("conteudo")));


    }

    public void voltar(View v){
        Intent startNewActivity = new Intent(this, TelaInicial.class);
        startActivity(startNewActivity);
    }

    public void atualizarAnotacao(View v){
        BancoDeDados bancoDeDados = new BancoDeDados(getBaseContext());
        EditText titulo = (EditText) findViewById(R.id.campoTitulo);
        EditText conteudo = (EditText) findViewById(R.id.campoConteudo);

        String textoAtualizacaoBemSucedida = getString(R.string.atualizacaoBemSucedida);
        String textoAtualizacaoMalSucedida = getString(R.string.atualizacaoMalSucedida);

        if (!(titulo.getText().toString().trim().isEmpty())) {
            try {
                bancoDeDados.atualizaAnotacao(this.getIntent().getIntExtra("id", 0), titulo.getText().toString(), conteudo.getText().toString());
                Toast.makeText(getApplicationContext(), textoAtualizacaoBemSucedida, Toast.LENGTH_LONG).show();
            } catch (Exception ex) {
                Toast.makeText(getApplicationContext(), textoAtualizacaoMalSucedida, Toast.LENGTH_LONG).show();
            }
        }
        voltar(v);
    }

    public void excluirAnotacao(View v){
        BancoDeDados bancoDeDados = new BancoDeDados(getBaseContext());
        EditText titulo = (EditText) findViewById(R.id.campoTitulo);
        EditText conteudo = (EditText) findViewById(R.id.campoConteudo);

        String textoExclusaoBemSucedida = getString(R.string.exclusaoBemSucedida);
        String textoExclusaoMalSucedida = getString(R.string.exclusaoMalSucedida);

        try{
            bancoDeDados.excluiAnotacao(this.getIntent().getIntExtra("id",0));
            Toast.makeText(getApplicationContext(), textoExclusaoBemSucedida, Toast.LENGTH_LONG).show();

        }catch(Exception ex){
            Toast.makeText(getApplicationContext(), textoExclusaoMalSucedida, Toast.LENGTH_LONG).show();
        }
        voltar(v);
    }
}
