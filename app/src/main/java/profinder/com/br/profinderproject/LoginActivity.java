package profinder.com.br.profinderproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity {
    private TextView mUsuario;
    private TextView mSenha;
    private TextView mSignUp; //cadastre-se
    private Button mEntrar;

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
    }

    private void actionListeners() {
        this.mSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, CadastroActivity.class);
                startActivity(intent);
            }
        });
    }
}
