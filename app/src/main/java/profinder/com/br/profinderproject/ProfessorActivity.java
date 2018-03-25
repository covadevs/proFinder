package profinder.com.br.profinderproject;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.annimon.stream.Collector;
import com.annimon.stream.Collectors;
import com.annimon.stream.Stream;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class ProfessorActivity extends AppCompatActivity {
    private Usuario usuario;
    private ListView mListaProjetos;
    private List<Projeto> projetos = new LinkedList<>();
    private ProjetoAdapter projetoAdapter;
    private final FirebaseDatabase database = FirebaseDatabase.getInstance();
    private final DatabaseReference databaseReference = database.getReference().child("projeto");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_professor);
        initComponents();
        listeners();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProfessorActivity.this, CadastroProjetoActivity.class);
                intent.putExtra("usuario", usuario);
                startActivity(intent);
                finish();
            }
        });
    }

    private void initComponents() {
        this.mListaProjetos = (ListView) findViewById(R.id.lista_projetos_professor);
        this.projetoAdapter =  new ProjetoAdapter(this.projetos, this);
        this.mListaProjetos.setAdapter(this.projetoAdapter);

    }

    private void listeners() {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                usuario = (Usuario) getIntent().getSerializableExtra("usuario");
                Stream<Projeto> stream = Stream.of(getProjetos((Map<String, Object>) dataSnapshot.getValue()));
                projetoAdapter.setProjetos(stream.filter(p -> p.getCoordenador().equals(usuario.getNome())).collect(Collectors.toList()));
                projetoAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        this.mListaProjetos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Projeto projeto = projetoAdapter.getProjetos().get(i);
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(ProfessorActivity.this);
                AlertDialog alertDialog;
                View mView = getLayoutInflater().inflate(R.layout.listview_dialog, null);
                TextView twNomeProjeto = (TextView) mView.findViewById(R.id.tw_nome_projeto);
                TextView twCoordenador = (TextView) mView.findViewById(R.id.tw_coordenador);
                TextView twVagas = (TextView) mView.findViewById(R.id.tw_vagas);
                TextView twDescricao = (TextView) mView.findViewById(R.id.tw_descricao);
                TextView twCurso = (TextView) mView.findViewById(R.id.tw_curso);
                Button btEditar = (Button) mView.findViewById(R.id.bt_editar);
                Button btApagar = (Button) mView.findViewById(R.id.bt_apagar);

                twNomeProjeto.setText(projeto.getNome());
                twCoordenador.setText("Coordenador(ra): "+projeto.getCoordenador());
                twVagas.setText("Vagas: "+String.valueOf(projeto.getQntVagas()));
                twDescricao.setText("Descrição\n"+projeto.getDescricao());
                twDescricao.setMovementMethod(new ScrollingMovementMethod());
                twCurso.setText("Curso: "+projeto.getCurso());

                mBuilder.setView(mView);
                alertDialog = mBuilder.create();

                btEditar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent;
                    }
                });

                btApagar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(alertDialog.getContext());
                        AlertDialog alert = builder.setTitle("Deletar Projeto")
                                .setMessage("Deseja realmente deletar o projeto "+twNomeProjeto.getText().toString()+"?")
                                .setIcon(android.R.drawable.ic_dialog_alert)
                                .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
                                        DatabaseReference databaseReference = firebaseDatabase.getReference().child("projeto");

                                        databaseReference.child(twNomeProjeto.getText().toString()).removeValue();
                                        alertDialog.dismiss();
                                    }
                                })

                                .setNegativeButton("Não", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {

                                    }
                                }).create();

                        alert.setOnShowListener(new DialogInterface.OnShowListener() {
                            @Override
                            public void onShow(DialogInterface dialogInterface) {
                                alert.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(getResources().getColor(R.color.Peru));
                                alert.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(getResources().getColor(R.color.Peru));
                            }
                        });
                        alert.show();
                    }
                });

                alertDialog.show();
            }
        });
    }

    private List<Projeto> getProjetos(Map<String, Object> projetos) {
        List<Projeto> projetosLista = new LinkedList<>();

        if(projetos != null) {
            for (Map.Entry<String, Object> entry : projetos.entrySet()) {
                Map singleProject = (Map) entry.getValue();
                String nome = (String) singleProject.get("nome");
                String coordenador = (String) singleProject.get("coordenador");
                Integer qntVagas = (int) (long) singleProject.get("qntVagas");
                String descricao = (String) singleProject.get("descricao");
                String curso = (String) singleProject.get("curso");

                Projeto projeto = new Projeto(nome, coordenador, qntVagas, descricao, curso);
                projetosLista.add(projeto);
            }
        }

        return projetosLista;
    }

}
