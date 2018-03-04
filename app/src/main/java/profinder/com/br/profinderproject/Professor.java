package profinder.com.br.profinderproject;

/**
 * Created by Leonardo on 04/03/2018.
 */

public class Professor extends Usuario {

    private String graduacao;

    public Professor() {

    }

    public Professor(String nome, String usuario, String email, String senha) {
        super(nome, usuario, email, senha);
        this.graduacao = "";
    }

    public String getGraduacao() {
        return graduacao;
    }

    public void setGraduacao(String graduacao) {
        this.graduacao = graduacao;
    }
}
