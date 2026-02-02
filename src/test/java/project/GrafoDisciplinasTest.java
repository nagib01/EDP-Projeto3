package project;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class GrafoDisciplinasTest {

    private GrafoDisciplinas grafo;

    @BeforeEach
    public void setUp() {
        grafo = new GrafoDisciplinas();
    }

    @Test
    public void testAdicionarDisciplina() {
        Disciplina d = new Disciplina("MAT101", "Calculo I");
        grafo.adicionarDisciplina(d);
        assertNotNull(grafo.getDisciplina("MAT101"));
    }

    @Test
    public void testAdicionarDisciplinaRepetida() {
        grafo.adicionarDisciplina(new Disciplina("MAT101", "Calculo I"));
        grafo.adicionarDisciplina(new Disciplina("MAT101", "Calculo I - Repetido"));
        
        assertEquals("Calculo I", grafo.getDisciplina("MAT101").getNome());
    }

    @Test
    public void testRemoverDisciplina() {
        grafo.adicionarDisciplina(new Disciplina("MAT101", "Calculo I"));
        grafo.removerDisciplina("MAT101");
        assertNull(grafo.getDisciplina("MAT101"));
    }

    @Test
    public void testAdicionarPreRequisito() {
        grafo.adicionarDisciplina(new Disciplina("MAT101", "Calculo I"));
        grafo.adicionarDisciplina(new Disciplina("MAT102", "Calculo II"));
        
        grafo.adicionarPreRequisito("MAT102", "MAT101");
        
        Disciplina d2 = grafo.getDisciplina("MAT102");
        assertEquals(1, d2.getPreRequisitos().size());
        assertEquals("MAT101", d2.getPreRequisitos().get(0).getCodigo());
    }

    @Test
    public void testAdicionarPreRequisitoCircular() {
        grafo.adicionarDisciplina(new Disciplina("MAT101", "Calculo I"));
        grafo.adicionarPreRequisito("MAT101", "MAT101");
        
        assertTrue(grafo.getDisciplina("MAT101").getPreRequisitos().isEmpty());
    }

    @Test
    public void testPodeCursarSemPreRequisitos() {
        grafo.adicionarDisciplina(new Disciplina("MAT101", "Calculo I"));
        assertTrue(grafo.podeCursar("MAT101", Collections.emptyList()));
    }

    @Test
    public void testPodeCursarComPreRequisitosPendentes() {
        grafo.adicionarDisciplina(new Disciplina("MAT101", "Calculo I"));
        grafo.adicionarDisciplina(new Disciplina("MAT102", "Calculo II"));
        grafo.adicionarPreRequisito("MAT102", "MAT101");
        
        assertFalse(grafo.podeCursar("MAT102", Collections.emptyList()));
    }

    @Test
    public void testPodeCursarComPreRequisitosConcluidos() {
        grafo.adicionarDisciplina(new Disciplina("MAT101", "Calculo I"));
        grafo.adicionarDisciplina(new Disciplina("MAT102", "Calculo II"));
        grafo.adicionarPreRequisito("MAT102", "MAT101");
        
        assertTrue(grafo.podeCursar("MAT102", Collections.singletonList("MAT101")));
    }

    @Test
    public void testPodeCursarTransitivo() {
        grafo.adicionarDisciplina(new Disciplina("MAT101", "Calculo I"));
        grafo.adicionarDisciplina(new Disciplina("MAT102", "Calculo II"));
        grafo.adicionarDisciplina(new Disciplina("MAT103", "Calculo III"));
        
        grafo.adicionarPreRequisito("MAT102", "MAT101");
        grafo.adicionarPreRequisito("MAT103", "MAT102");
        
        // Tem MAT101, mas falta MAT102 para cursar MAT103
        assertFalse(grafo.podeCursar("MAT103", Collections.singletonList("MAT101")));
        
        // Tem MAT101 e MAT102
        List<String> cursadas = Arrays.asList("MAT101", "MAT102");
        assertTrue(grafo.podeCursar("MAT103", cursadas));
    }
}
