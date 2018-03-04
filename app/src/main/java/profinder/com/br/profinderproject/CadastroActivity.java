package profinder.com.br.profinderproject;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CadastroActivity extends AppCompatActivity {
    private EditText mNome;
    private EditText mUsuario;
    private EditText mEmail;
    private EditText mSenha;
    private EditText mReTypeSenha;
    private Button mCadastrar;
    private RadioGroup mTipoUsuaario;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        initComponentes();
        acaoesDosBotoes();
    }

    private void initComponentes() {
        this.mNome = (EditText) findViewById(R.id.nome);
        this.mUsuario = (EditText) findViewById(R.id.usuario);
        this.mEmail = (EditText) findViewById(R.id.email);
        this.mSenha = (EditText) findViewById(R.id.senha);
        this.mReTypeSenha = (EditText) findViewById(R.id.retypesenha);
        this.mCadastrar = (Button) findViewById(R.id.cadastrar);
        this.mTipoUsuaario = (RadioGroup) findViewById(R.id.tipo_usuario);
    }

    public void acaoesDosBotoes() {
        this.mCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int radioButtonID = mTipoUsuaario.getCheckedRadioButtonId();
                View radioButtom = mTipoUsuaario.findViewById(radioButtonID);
                int idx = mTipoUsuaario.indexOfChild(radioButtom);
                RadioButton r = (RadioButton) mTipoUsuaario.getChildAt(idx);
                String selectedText = r.getText().toString();

                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myRef = database.getReference();
                if (selectedText.equalsIgnoreCase("professor")) {
                    Usuario u = new Professor(mNome.getText().toString(),
                                            mUsuario.getText().toString(),
                                            mEmail.getText().toString(),
                                            mSenha.getText().toString());
                    myRef.child("usuario").child(mUsuario.getText().toString()).setValue(u);
                    finish();
                } else if (selectedText.equalsIgnoreCase("aluno")) {
                    Usuario u = new Aluno(mNome.getText().toString(),
                            mUsuario.getText().toString(),
                            mEmail.getText().toString(),
                            mSenha.getText().toString());
                    myRef.child("usuario").child(mUsuario.getText().toString()).setValue(u);
                    finish();
                }
            }
        });
    }


}
