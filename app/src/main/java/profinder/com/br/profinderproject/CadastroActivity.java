package profinder.com.br.profinderproject;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.LinkedList;
import java.util.Timer;
import java.util.TimerTask;

public class CadastroActivity extends AppCompatActivity {
    private EditText mNome;
    private EditText mUsuario;
    private EditText mEmail;
    private EditText mSenha;
    private EditText mReTypeSenha;
    private Button mCadastrar;
    private RadioGroup mTipoUsuaario;

    private Toast toastSucesso;
    private boolean senhaConfere;
    private boolean usuarioExiste;

    private FirebaseDatabase database;
    private DatabaseReference myRef;

    private TextWatcher textWatcher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        initComponentes();
        acaoesDosBotoes();
        verificacaoDeRetype();
        verificacaoUsuario();
    }

    private void initComponentes() {
        this.mNome = (EditText) findViewById(R.id.nome);
        this.mUsuario = (EditText) findViewById(R.id.usuario);
        this.mEmail = (EditText) findViewById(R.id.email);
        this.mSenha = (EditText) findViewById(R.id.senha);
        this.mReTypeSenha = (EditText) findViewById(R.id.retypesenha);
        this.mCadastrar = (Button) findViewById(R.id.cadastrar);
        this.mTipoUsuaario = (RadioGroup) findViewById(R.id.tipo_usuario);
        this.toastSucesso = Toast.makeText(getApplicationContext(), "Usuario cadastrado com sucesso", Toast.LENGTH_SHORT);
        this.database = FirebaseDatabase.getInstance();
        this.myRef = database.getReference();
        this.senhaConfere = false;
        this.usuarioExiste = false;
    }

    public void acaoesDosBotoes() {
        this.mCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean vazio = setError();

                if(!vazio && senhaConfere && !usuarioExiste) {
                    mUsuario.removeTextChangedListener(textWatcher);
                    mNome.removeTextChangedListener(textWatcher);
                    int radioButtonID = mTipoUsuaario.getCheckedRadioButtonId();
                    View radioButtom = mTipoUsuaario.findViewById(radioButtonID);
                    int idx = mTipoUsuaario.indexOfChild(radioButtom);
                    RadioButton r = (RadioButton) mTipoUsuaario.getChildAt(idx);
                    String selectedText = r.getText().toString();

                    if (selectedText.equalsIgnoreCase("professor")) {
                        Usuario u = new Usuario(mNome.getText().toString(),
                                mUsuario.getText().toString(),
                                mEmail.getText().toString(),
                                mSenha.getText().toString(), true);
                        myRef.child("usuario").child(mNome.getText().toString()).setValue(u).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                toastSucesso.show();
                            }
                        });
                        finish();
                    } else if (selectedText.equalsIgnoreCase("aluno")) {
                        Usuario u = new Usuario(mNome.getText().toString(),
                                mUsuario.getText().toString(),
                                mEmail.getText().toString(),
                                mSenha.getText().toString(), false);
                        myRef.child("usuario").child(mNome.getText().toString()).setValue(u).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                toastSucesso.show();
                            }
                        });
                        finish();
                    }
                }
            }
        });
    }

    public boolean setError() {
        boolean vazio = false;
        if(this.mNome.getText().toString().isEmpty()) {
            this.mNome.setError("Campo vazio");
            vazio = true;
        }

        if(this.mReTypeSenha.getText().toString().isEmpty()) {
            this.mReTypeSenha.setError("Campo vazio");
            vazio = true;
        }

        if(this.mSenha.getText().toString().isEmpty()) {
            this.mSenha.setError("Campo vazio");
            vazio = true;
        }

        if(this.mUsuario.getText().toString().isEmpty()) {
            this.mUsuario.setError("Campo vazio");
            vazio = true;
        }

        if(this.mEmail.getText().toString().isEmpty()) {
            this.mEmail.setError("Campo vazio");
            vazio = true;
        }

        if(!this.mEmail.getText().toString().isEmpty() &&
                !Patterns.EMAIL_ADDRESS.matcher(this.mEmail.getText().toString()).matches()) {
            this.mEmail.setError("Email inválido");
            vazio = true;
        }

        return vazio;
    }

    public void verificacaoDeRetype() {
        this.mReTypeSenha.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(!charSequence.toString().equals(mSenha.getText().toString())) {
                    mReTypeSenha.setError("Senha não confere");
                    senhaConfere = false;
                } else {
                    senhaConfere = true;
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    public void verificacaoUsuario() {
        textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                new Timer().schedule(new TimerTask() {
                    @Override
                    public void run() {
                        verificarSeUsuarioExiste(charSequence.toString());
                    }
                }, 300);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        };
        this.mUsuario.addTextChangedListener(textWatcher);
        this.mNome.addTextChangedListener(textWatcher);
    }

    public void verificarSeUsuarioExiste(String usuario) {
        myRef.child("usuario").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.hasChild(usuario)) {
                    usuarioExiste = true;
                    if(mUsuario.isFocused()) {
                        mUsuario.setError("Usuário inválido");
                    } else if (mNome.isFocused()) {
                        mNome.setError("Nome inválido");
                    }
                } else {
                    usuarioExiste = false;
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
