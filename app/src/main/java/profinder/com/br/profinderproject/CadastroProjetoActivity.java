package profinder.com.br.profinderproject;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Timer;
import java.util.TimerTask;

public class CadastroProjetoActivity extends AppCompatActivity {
    private EditText mNomeProjeto;
    private EditText mNomeCoordenador;
    private SeekBar mQntVagas;
    private EditText mDescricao;
    private EditText mCurso;
    private Button mCadastrarProjeto;
    private TextView mVagasCount;
    private Usuario u;

    private Toast toastSucesso;

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
        this.mVagasCount = (TextView) findViewById(R.id.valor_seekbar);
        this.mVagasCount.setText(getString(R.string.vagas,"", this.mQntVagas.getProgress()));
        u = (Usuario)getIntent().getSerializableExtra("usuario");
        this.mNomeCoordenador.setText(u.getNome());
        this.mNomeCoordenador.setEnabled(false);

        this.toastSucesso = Toast.makeText(getApplicationContext(), "Projeto cadastrado com sucesso", Toast.LENGTH_LONG);
    }

    private void listeners() {
        this.mCadastrarProjeto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean vazio = setError();

                if(!vazio) {
                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference databaseReference = database.getReference();
                    String nome = mNomeProjeto.getText().toString();
                    String coordenador = mNomeCoordenador.getText().toString();
                    Integer qntVagas = mQntVagas.getProgress();
                    String descricao = mDescricao.getText().toString();
                    String curso = mCurso.getText().toString();
                    Projeto projeto = new Projeto(nome, coordenador, qntVagas, descricao, curso);
                    databaseReference.child("projeto").child(nome).setValue(projeto).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            toastSucesso.show();
                            finish();
                        }
                    });
                }
            }
        });

        this.mQntVagas.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                if(mQntVagas.getProgress() == 0) {
                    mVagasCount.setText(getString(R.string.vagas,"", i+1));
                    mQntVagas.setProgress(1);
                } else {
                    mVagasCount.setText(getString(R.string.vagas,"", i));
                }

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    public boolean setError() {
        boolean vazio = false;
        if(this.mNomeProjeto.getText().toString().isEmpty()) {
            this.mNomeProjeto.setError("Campo vazio");
            vazio = true;
        }

        if(this.mNomeCoordenador.getText().toString().isEmpty()) {
            this.mNomeCoordenador.setError("Campo vazio");
            vazio = true;
        }

        if(this.mDescricao.getText().toString().isEmpty()) {
            this.mDescricao.setError("Campo vazio");
            vazio = true;
        }

        if(this.mCurso.getText().toString().isEmpty()) {
            this.mCurso.setError("Campo vazio");
            vazio = true;
        }

        return vazio;
    }
}
