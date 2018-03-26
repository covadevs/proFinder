package profinder.com.br.profinderproject;

import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Timer;
import java.util.TimerTask;

public class EditarProjetoActivity extends AppCompatActivity {
    private Usuario usuario;
    private Projeto projeto;
    private EditText mNomeProjeto;
    private EditText mNomeCoordenador;
    private EditText mDescricao;
    private EditText mCurso;
    private SeekBar mQntVagas;
    private TextView mQntVagasText;
    private Button bntEditar;
    private Toast toastSucesso;

    private String projetoId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_projeto);

        initComponents();
        popularCampos();
        listeners();
    }


    private void initComponents() {
        this.usuario = (Usuario) getIntent().getSerializableExtra("usuario");
        this.projeto = (Projeto) getIntent().getSerializableExtra("projeto");
        this.projetoId = projeto.getNome();
        this.mNomeProjeto = (EditText) findViewById(R.id.et_nomeprojeto);
        this.mNomeCoordenador = (EditText) findViewById(R.id.et_coordenador);
        this.mDescricao = (EditText) findViewById(R.id.et_descricao);
        this.mCurso = (EditText) findViewById(R.id.et_curso);
        this.mQntVagas = (SeekBar) findViewById(R.id.sb_qntvagas);
        this.mQntVagasText = (TextView) findViewById(R.id.tw_vagas_editarprojeto);
        this.bntEditar = (Button) findViewById(R.id.bt_editar_projeto);
        this.toastSucesso = Toast.makeText(getApplicationContext(), "Projeto editado com sucesso", Toast.LENGTH_LONG);
        this.mNomeCoordenador.setEnabled(false);
    }

    private void popularCampos() {
        this.mNomeProjeto.setText(this.projeto.getNome());
        this.mNomeCoordenador.setText(this.projeto.getCoordenador());
        this.mQntVagas.setProgress(this.projeto.getQntVagas());
        this.mQntVagasText.setText((getString(R.string.vagas,"", this.mQntVagas.getProgress())));
        this.mDescricao.setText(this.projeto.getDescricao());
        this.mCurso.setText(this.projeto.getCurso());
    }

    private void listeners() {
        this.mQntVagas.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                if(mQntVagas.getProgress() == 0) {
                    mQntVagasText.setText((getString(R.string.vagas,"", i+1)));
                    mQntVagas.setProgress(1);
                } else {
                    mQntVagasText.setText(getString(R.string.vagas,"", i));
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        this.bntEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean vazio = setError();

                if(!vazio) {
                    projeto.setNome(mNomeProjeto.getText().toString());
                    projeto.setDescricao(mDescricao.getText().toString());
                    projeto.setCurso(mCurso.getText().toString());
                    projeto.setQntVagas(mQntVagas.getProgress());
                    projeto.setNome(mNomeProjeto.getText().toString());

                    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
                    DatabaseReference databaseReference = firebaseDatabase.getReference().child("projeto");
                    databaseReference.child(projetoId).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            databaseReference.child(projeto.getNome()).setValue(projeto).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    toastSucesso.show();
                                    finish();
                                }
                            });
                        }
                    });
                }
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
