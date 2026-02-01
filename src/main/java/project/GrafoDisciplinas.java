package project;

import java.io.Serializable;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class GrafoDisciplinas implements Serializable {
    private static final long serialVersionUID = 1L;
    private Map<String, Disciplina> disciplinas;

    public GrafoDisciplinas() {
        this.disciplinas = new HashMap<>();
    }

    public void adicionarDisciplina(Disciplina disciplina) {
        if (!disciplinas.containsKey(disciplina.getCodigo())) {
            disciplinas.put(disciplina.getCodigo(), disciplina);
        } else {
            throw new IllegalArgumentException("Disciplina com código " + disciplina.getCodigo() + " já existe.");
        }
    }

    public void removerDisciplina(String codigo) {
        if (disciplinas.containsKey(codigo)) {
            Disciplina disciplinaRemover = disciplinas.get(codigo);
            // Remover referências a esta disciplina em outras listas de pré-requisitos
            for (Disciplina d : disciplinas.values()) {
                d.removerPreRequisito(disciplinaRemover);
            }
            disciplinas.remove(codigo);
        } else {
            throw new IllegalArgumentException("Disciplina não encontrada: " + codigo);
        }
    }

    public void adicionarPreRequisito(String codigoDestino, String codigoPre) {
        if (codigoDestino.equals(codigoPre)) {
            throw new IllegalArgumentException("Não é permitido adicionar pré-requisito circular (auto-referência).");
        }

        Disciplina destino = disciplinas.get(codigoDestino);
        Disciplina pre = disciplinas.get(codigoPre);

        if (destino == null || pre == null) {
            throw new IllegalArgumentException("Uma ou ambas as disciplinas não foram encontradas.");
        }

        // Verificar se a adição cria um ciclo no grafo (A->B->A)
        if (criaCiclo(destino, pre)) {
            throw new IllegalStateException("A adição deste pré-requisito criaria um ciclo impossível de resolver.");
        }

        destino.adicionarPreRequisito(pre);
    }

    public void removerPreRequisito(String codigoDestino, String codigoPre) {
        Disciplina destino = disciplinas.get(codigoDestino);
        Disciplina pre = disciplinas.get(codigoPre);

        if (destino != null && pre != null) {
            destino.removerPreRequisito(pre);
        } else {
            throw new IllegalArgumentException("Uma ou ambas as disciplinas não foram encontradas.");
        }
    }

    public void listarDisciplinas() {
        if (disciplinas.isEmpty()) {
            System.out.println("Nenhuma disciplina cadastrada.");
            return;
        }
        for (Disciplina d : disciplinas.values()) {
            System.out.print("Disciplina: " + d.toString());
            List<Disciplina> preReqs = d.getPreRequisitos();
            if (!preReqs.isEmpty()) {
                preReqs.sort(Comparator.comparing(Disciplina::getCodigo));
                System.out.print(" | Pré-requisitos: ");
                for (Disciplina pre : preReqs) {
                    System.out.print(pre.getCodigo() + " ");
                }
            }
            System.out.println();
        }
    }

    public boolean podeCursar(String codigo, List<String> cursadas) {
        Disciplina disciplina = disciplinas.get(codigo);
        if (disciplina == null) {
            throw new IllegalArgumentException("Disciplina não encontrada: " + codigo);
        }
        return podeCursarRecursivo(disciplina, cursadas);
    }

    private boolean podeCursarRecursivo(Disciplina d, List<String> cursadas) {
        for (Disciplina pre : d.getPreRequisitos()) {
            if (!cursadas.contains(pre.getCodigo())) return false;
            if (!podeCursarRecursivo(pre, cursadas)) return false;
        }
        return true;
    }
    
    public Disciplina getDisciplina(String codigo) {
        return disciplinas.get(codigo);
    }

    // Método auxiliar para detetar ciclos usando DFS (Depth First Search)
    private boolean criaCiclo(Disciplina origem, Disciplina alvo) {
        // Se queremos adicionar 'alvo' como pré-requisito de 'origem',
        // precisamos garantir que 'origem' não é, direta ou indiretamente, um pré-requisito de 'alvo'.
        Set<String> visitados = new HashSet<>();
        return verificaCaminho(alvo, origem, visitados);
    }

    private boolean verificaCaminho(Disciplina atual, Disciplina destino, Set<String> visitados) {
        if (atual.getCodigo().equals(destino.getCodigo())) {
            return true; // Encontrou um caminho de volta, logo é um ciclo
        }
        visitados.add(atual.getCodigo());
        for (Disciplina pre : atual.getPreRequisitos()) {
            if (!visitados.contains(pre.getCodigo())) {
                if (verificaCaminho(pre, destino, visitados)) return true;
            }
        }
        return false;
    }
}
