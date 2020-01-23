package javamobile.minhasanotacoes;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.view.Gravity;
import android.view.View;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import javamobile.minhasanotacoes.bancodedados.BancoDeDados;

public class CriarAnotacao extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_criar_anotacao);
    }

    //metodo para voltar a atividade principal
    public void voltar (View v){
        Intent startNewActivity = new Intent(this, TelaInicial.class);
        startActivity(startNewActivity);
    }

    public void criarAnotacao(View v){
        //retorna o banco de dados
        BancoDeDados bancoDeDados = new BancoDeDados(getBaseContext());

        EditText titulo = (EditText) findViewById(R.id.campoTitulo);
        EditText conteudo = (EditText) findViewById(R.id.campoConteudo);


        String textoCriacaoBemSucedida = getString(R.string.criacaoBemSucedida);
        String textoCriacaoMalSucedida = getString(R.string.criacaoMalSucedida);
        String textoCriacaoTituloVazio = getString(R.string.criarAnotacaoTituloVazio);

        if (!(titulo.getText().toString().trim().isEmpty())) {
            //cria um nova anotacao
            boolean resultado = bancoDeDados.criarAnotacao(titulo.getText().toString(), conteudo.getText().toString());

            if (resultado) {
                Toast.makeText(getApplicationContext(), textoCriacaoBemSucedida, Toast.LENGTH_SHORT).show();

            } else {
                Toast.makeText(getApplicationContext(), textoCriacaoMalSucedida, Toast.LENGTH_LONG).show();
            }

            voltar(v);

        }else {
            Toast toast = Toast.makeText(getApplicationContext(), textoCriacaoTituloVazio, Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER_VERTICAL,0,0);
            toast.show();
        }

    }
}
