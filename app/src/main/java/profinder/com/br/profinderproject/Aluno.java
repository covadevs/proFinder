package profinder.com.br.profinderproject;

/**
 * Created by Leonardo on 04/03/2018.
 */
public class Aluno extends Usuario {

    private String curso;

    public Aluno() {

    }

    public Aluno(String nome, String usuario, String email, String senha) {
        super(nome, usuario, email, senha);
        this.curso = "";
    }

    public String getCurso() {
        return curso;
    }

    public void setCurso(String curso) {
        this.curso = curso;
    }
}
