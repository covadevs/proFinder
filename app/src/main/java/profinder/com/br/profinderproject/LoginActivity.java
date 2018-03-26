package profinder.com.br.profinderproject;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class LoginActivity extends AppCompatActivity {
    private EditText mUsuario;
    private EditText mSenha;
    private TextView mSignUp; //cadastre-se
    private Button mEntrar;
    private FirebaseDatabase database;
    private DatabaseReference ref;
    private Snackbar snackbar;

    private Usuario u;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initComponentes();
        actionListeners();
    }

    private void initComponentes() {
        this.mUsuario = (EditText) findViewById(R.id.username_login);
        this.mSenha = (EditText) findViewById(R.id.password_login);
        this.mSignUp = (TextView) findViewById(R.id.signup);
        this.mEntrar = (Button) findViewById(R.id.entrar);
        this.snackbar = Snackbar.make(findViewById(R.id.login_layout), "Usuário ou senha inválidos", Snackbar.LENGTH_LONG);
    }

    private void actionListeners() {
        this.mSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, CadastroActivity.class);
                startActivity(intent);
            }
        });

        snackbar.setAction("FECHAR", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                snackbar.dismiss();
            }
        });

        this.mEntrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean vazio = false;
                if (mUsuario.getText().toString().length() == 0) {
                    mUsuario.setError("Campo vazio!");
                    vazio = true;
                }

                if (mSenha.getText().toString().length() == 0) {
                    mSenha.setError("Campo vazio!");
                    vazio = true;
                }

                if(!vazio) {
                    database = FirebaseDatabase.getInstance();
                    ref = database.getReference("usuario");
                    ref.child(mUsuario.getText().toString());
                    ref.addChildEventListener(new ChildEventListener() {
                        @Override
                        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                            u = dataSnapshot.getValue(Usuario.class);

                            if (u != null) {
                                if (u.getUsuario().equals(mUsuario.getText().toString()) && u.getSenha().equals(mSenha.getText().toString())) {
                                    if(u.isProfessor()) {
                                        Intent intent = new Intent(LoginActivity.this, ProfessorActivity.class);
                                        intent.putExtra("usuario", u);
                                        startActivity(intent);
                                        finish();
                                    } else {
                                        Intent intent = new Intent(LoginActivity.this, AlunoActivity.class);
                                        intent.putExtra("usuario", u);
                                        startActivity(intent);
                                        finish();
                                    }
                                } else {
                                    snackbar.show();
                                }
                            }
                        }

                        @Override
                        public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                        }

                        @Override
                        public void onChildRemoved(DataSnapshot dataSnapshot) {

                        }

                        @Override
                        public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }
            }
        });
    }
}
