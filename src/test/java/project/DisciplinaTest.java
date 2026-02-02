package project;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class DisciplinaTest {

    @Test
    public void testCriacaoDisciplina() {
        Disciplina d = new Disciplina("MAT101", "Calculo I");
        assertEquals("MAT101", d.getCodigo());
        assertEquals("Calculo I", d.getNome());
        assertTrue(d.getPreRequisitos().isEmpty());
    }

    @Test
    public void testAdicionarPreRequisito() {
        Disciplina d1 = new Disciplina("MAT101", "Calculo I");
        Disciplina d2 = new Disciplina("MAT102", "Calculo II");
        
        d2.adicionarPreRequisito(d1);
        
        assertEquals(1, d2.getPreRequisitos().size());
        assertTrue(d2.getPreRequisitos().contains(d1));
    }

    @Test
    public void testAdicionarPreRequisitoDuplicado() {
        Disciplina d1 = new Disciplina("MAT101", "Calculo I");
        Disciplina d2 = new Disciplina("MAT102", "Calculo II");
        
        d2.adicionarPreRequisito(d1);
        d2.adicionarPreRequisito(d1); // Tentativa de duplicar
        
        assertEquals(1, d2.getPreRequisitos().size());
    }

    @Test
    public void testRemoverPreRequisito() {
        Disciplina d1 = new Disciplina("MAT101", "Calculo I");
        Disciplina d2 = new Disciplina("MAT102", "Calculo II");
        
        d2.adicionarPreRequisito(d1);
        d2.removerPreRequisito(d1);
        
        assertTrue(d2.getPreRequisitos().isEmpty());
    }

    @Test
    public void testEqualsHashCode() {
        Disciplina d1 = new Disciplina("MAT101", "Calculo I");
        Disciplina d2 = new Disciplina("MAT101", "Calculo I - Outro Nome"); // Mesmo código
        Disciplina d3 = new Disciplina("FIS101", "Fisica I");

        assertEquals(d1, d2);
        assertNotEquals(d1, d3);
        assertEquals(d1.hashCode(), d2.hashCode());
    }
}
