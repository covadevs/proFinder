package profinder.com.br.profinderproject;

/**
 * Created by Leonardo on 24/03/2018.
 */

public class Projeto {
    private String nome;
    private String coordenador;
    private Integer qntVagas;
    private String descricao;
    private String curso;

    public Projeto() {}

    public Projeto(String nome, String coordenador, Integer qntVagas, String descricao, String curso) {
        this.nome = nome;
        this.coordenador = coordenador;
        this.qntVagas = qntVagas;
        this.descricao = descricao;
        this.curso = curso;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCoordenador() {
        return coordenador;
    }

    public void setCoordenador(String coordenador) {
        this.coordenador = coordenador;
    }

    public Integer getQntVagas() {
        return qntVagas;
    }

    public void setQntVagas(Integer qntVagas) {
        this.qntVagas = qntVagas;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getCurso() {
        return curso;
    }

    public void setCurso(String curso) {
        this.curso = curso;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Projeto projeto = (Projeto) o;

        if (nome != null ? !nome.equals(projeto.nome) : projeto.nome != null) return false;
        if (coordenador != null ? !coordenador.equals(projeto.coordenador) : projeto.coordenador != null)
            return false;
        if (qntVagas != null ? !qntVagas.equals(projeto.qntVagas) : projeto.qntVagas != null)
            return false;
        if (descricao != null ? !descricao.equals(projeto.descricao) : projeto.descricao != null)
            return false;
        return curso != null ? curso.equals(projeto.curso) : projeto.curso == null;
    }

    @Override
    public String toString() {
        return "Projeto{" +
                "nome='" + nome + '\'' +
                ", coordenador='" + coordenador + '\'' +
                ", qntVagas=" + qntVagas +
                ", descricao='" + descricao + '\'' +
                ", curso='" + curso + '\'' +
                '}';
    }
}
