package project;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Disciplina implements Serializable {
    private static final long serialVersionUID = 1L;
    private String codigo;
    private String nome;
    private List<Disciplina> preRequisitos;

    public Disciplina(String codigo, String nome) {
        this.codigo = codigo;
        this.nome = nome;
        this.preRequisitos = new ArrayList<>();
    }

    public String getCodigo() {
        return codigo;
    }

    public String getNome() {
        return nome;
    }

    public List<Disciplina> getPreRequisitos() {
        return preRequisitos;
    }

    public void adicionarPreRequisito(Disciplina disciplina) {
        if (!preRequisitos.contains(disciplina)) {
            preRequisitos.add(disciplina);
        }
    }

    public void removerPreRequisito(Disciplina disciplina) {
        preRequisitos.remove(disciplina);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Disciplina that = (Disciplina) o;
        return Objects.equals(codigo, that.codigo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(codigo);
    }

    @Override
    public String toString() {
        return codigo + " - " + nome;
    }
}
