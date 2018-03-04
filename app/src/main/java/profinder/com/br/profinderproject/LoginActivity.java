package profinder.com.br.profinderproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {
    private TextView mUsuario;
    private TextView mSenha;
    private TextView mSignUp; //cadastre-se
    private Button mEntrar;
    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference ref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initComponentes();
        colocarUnderlineEmTexto(this.mSignUp);
        actionListeners();

    }

    private void colocarUnderlineEmTexto(TextView t) {
        SpannableString spannableString = new SpannableString(t.getText().toString());
        spannableString.setSpan(new UnderlineSpan(), 0, spannableString.length(), 0);
        t.setText(spannableString);
    }

    private void initComponentes() {
        this.mUsuario = (TextView) findViewById(R.id.username_login);
        this.mSenha = (TextView) findViewById(R.id.password_login);
        this.mSignUp = (TextView) findViewById(R.id.signup);
        this.mEntrar = (Button) findViewById(R.id.entrar);

        this.ref = database.getReference("usuario");
    }

    private void actionListeners() {
        this.mSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, CadastroActivity.class);
                startActivity(intent);
            }
        });

        this.mEntrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mUsuario.getText().toString().length() == 0) {
                    mUsuario.setError("Campo vazio!");
                }

                if (mSenha.getText().toString().length() == 0) {
                    mSenha.setError("Campo vazio!");
                }

                ref.child(mUsuario.getText().toString());
                ref.addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                        if(dataSnapshot.exists()) {
                            Usuario u = dataSnapshot.getValue(Aluno.class);
                            if(u.getSenha().equals(mSenha.getText().toString())) {
                                Intent intent = new Intent(LoginActivity.this, TesteEntradaActivity.class);
                                startActivity(intent);
                                finish();
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
                        Log.w("ERROFIREBASES", databaseError.getMessage());
                    }
                });
            }
        });
    }
}
