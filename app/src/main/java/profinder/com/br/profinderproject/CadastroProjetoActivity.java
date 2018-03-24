package profinder.com.br.profinderproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CadastroProjetoActivity extends AppCompatActivity {
    private EditText mNomeProjeto;
    private EditText mNomeCoordenador;
    private SeekBar mQntVagas;
    private EditText mDescricao;
    private EditText mCurso;
    private Button mCadastrarProjeto;
    private Usuario u;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_projeto);
        initComponents();
        listeners();
    }

    private void initComponents() {
        this.mNomeProjeto = (EditText) findViewById(R.id.nome_projeto);
        this.mNomeCoordenador = (EditText) findViewById(R.id.nome_coordenador);
        this.mQntVagas = (SeekBar) findViewById(R.id.qnt_vagas);
        this.mDescricao = (EditText) findViewById(R.id.descricao_projeto);
        this.mCurso = (EditText) findViewById(R.id.curso_projeto);
        this.mCadastrarProjeto = (Button) findViewById(R.id.button_cadastrar_projeto);
        u = (Usuario)getIntent().getSerializableExtra("usuario");
        this.mNomeCoordenador.setText(u.getNome());
    }

    private void listeners() {
        this.mCadastrarProjeto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference databaseReference = database.getReference();
                String nome = mNomeProjeto.getText().toString();
                String coordenador = mNomeCoordenador.getText().toString();
                Integer qntVagas = mQntVagas.getProgress();
                String descricao = mCadastrarProjeto.getText().toString();
                String curso = mCurso.getText().toString();
                Projeto projeto = new Projeto(nome, coordenador, qntVagas, descricao, curso);
                databaseReference.child("projeto").child(nome).setValue(projeto);

                Intent intent = new Intent(CadastroProjetoActivity.this, ProfessorActivity.class);
                intent.putExtra("usuario", u);
                startActivity(intent);
                finish();
            }
        });
    }
}
