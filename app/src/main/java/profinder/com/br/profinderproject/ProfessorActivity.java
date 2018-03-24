package profinder.com.br.profinderproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ProfessorActivity extends AppCompatActivity {
    private Usuario usuario;
    private ListView mListaProjetos;
    private List<Projeto> projetos;
    private ArrayAdapter<Projeto> projetosAdapter;
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
        this.usuario = (Usuario) getIntent().getSerializableExtra("usuario");
        this.projetos = new LinkedList<>();
        this.mListaProjetos = (ListView) findViewById(R.id.lista_projetos_professor);
        this.projetosAdapter = new ArrayAdapter<Projeto>(this, android.R.layout.simple_list_item_1, this.projetos);
        this.projetosAdapter.notifyDataSetChanged();
        this.mListaProjetos.setAdapter(this.projetosAdapter);
    }

    private void listeners() {


        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                projetos = getProjetos((Map<String, Object>) dataSnapshot.getValue()).stream()
                        .filter(p -> p.getCoordenador().equals(usuario.getNome())).collect(Collectors.toList());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private List<Projeto> getProjetos(Map<String, Object> projetos) {
        List<Projeto> projetosLista = new LinkedList<>();

        for(Map.Entry<String, Object> entry: projetos.entrySet()) {
            Map singleProject = (Map) entry.getValue();
            String nome = (String) singleProject.get("nome");
            String coordenador = (String) singleProject.get("coordenador");
            Integer qntVagas =  (int) (long) singleProject.get("qntVagas");
            String descricao = (String) singleProject.get("descricao");
            String curso = (String) singleProject.get("curso");

            Projeto projeto = new Projeto(nome, coordenador, qntVagas, descricao, curso);
            projetosLista.add(projeto);
        }

        return projetosLista;
    }

}
