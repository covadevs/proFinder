package profinder.com.br.profinderproject;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Leonardo on 24/03/2018.
 */

public class ProjetoAdapter extends BaseAdapter {

    private List<Projeto> projetos = new LinkedList<>();
    private final Activity act;

    public ProjetoAdapter(List<Projeto> projetos, Activity act) {
        this.projetos = projetos;
        this.act = act;
    }
    @Override
    public int getCount() {
        return this.projetos.size();
    }

    @Override
    public Object getItem(int i) {
        return this.projetos.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View v = act.getLayoutInflater().inflate(R.layout.listview_projetos, viewGroup, false);
        Projeto projeto = this.projetos.get(i);
        TextView nomeProjeto = v.findViewById(R.id.nome_projeto_listview);
        TextView coordenador = v.findViewById(R.id.coordenador_listview);
        TextView vagas = v.findViewById(R.id.vagas_listview);
        TextView curso = v.findViewById(R.id.curso_listview);

        nomeProjeto.setText(captalize(projeto.getNome()));
        coordenador.setText("Coordenador(ra): "+captalize(projeto.getCoordenador()));
        vagas.setText("Vagas: "+String.valueOf(projeto.getQntVagas()));
        curso.setText("Curso: "+captalize(projeto.getCurso()));

        return v;
    }

    public List<Projeto> getProjetos() {
        return projetos;
    }

    public void setProjetos(List<Projeto> projetos) {
        this.projetos = projetos;
    }

    private String captalize(String string) {
        String cap = string.trim().substring(0,1).toUpperCase() + string.substring(1);
        return cap;
    }
}
