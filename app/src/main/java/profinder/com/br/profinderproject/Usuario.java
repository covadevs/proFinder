package profinder.com.br.profinderproject;

/**
 * Created by Leonardo on 04/03/2018.
 */

public abstract class Usuario {
    private String nome;
    private String usuario;
    private String email;
    private String senha;

    public Usuario() {

    }

    public Usuario(String nome, String usuario, String email,
                    String senha) {
        this.nome = nome;
        this.usuario = usuario;
        this.email = email;
        this.senha = senha;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "nome='" + nome + '\'' +
                ", usuario='" + usuario + '\'' +
                ", email='" + email + '\'' +
                ", senha='" + senha + '\'' +
                '}';
    }
}
