package project;

import org.junit.jupiter.api.Test;
import java.io.File;
import static org.junit.jupiter.api.Assertions.*;

public class SerializadorTest {

    @Test
    public void testSalvarECarregarGrafo() {
        String arquivoTeste = "teste_serializacao.dat";
        GrafoDisciplinas grafoOriginal = new GrafoDisciplinas();
        grafoOriginal.adicionarDisciplina(new Disciplina("TST001", "Teste"));
        
        Serializador.salvarGrafo(grafoOriginal, arquivoTeste);
        
        File f = new File(arquivoTeste);
        assertTrue(f.exists());
        
        GrafoDisciplinas grafoCarregado = Serializador.carregarGrafo(arquivoTeste);
        assertNotNull(grafoCarregado);
        assertNotNull(grafoCarregado.getDisciplina("TST001"));
        assertEquals("Teste", grafoCarregado.getDisciplina("TST001").getNome());
        
        // Limpeza
        f.delete();
    }

    @Test
    public void testCarregarArquivoInexistente() {
        GrafoDisciplinas g = Serializador.carregarGrafo("arquivo_que_nao_existe.dat");
        assertNull(g);
    }
}
