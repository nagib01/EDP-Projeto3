package project;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ValidacaoRegexTest {

    @Test
    public void testValidarCodigo() {
        assertTrue(ValidacaoRegex.validarCodigo("MAT101"));
        assertTrue(ValidacaoRegex.validarCodigo("ABC999"));
        
        assertFalse(ValidacaoRegex.validarCodigo("mat101")); // Minúsculas
        assertFalse(ValidacaoRegex.validarCodigo("MAT10"));  // Curto
        assertFalse(ValidacaoRegex.validarCodigo("MAT1010")); // Longo
        assertFalse(ValidacaoRegex.validarCodigo("123MAT")); // Formato errado
    }

    @Test
    public void testValidarNome() {
        assertTrue(ValidacaoRegex.validarNome("Calculo I"));
        assertTrue(ValidacaoRegex.validarNome("Física Teórica"));
        
        assertFalse(ValidacaoRegex.validarNome("Calculo 1")); // Números não permitidos na regex atual
        assertFalse(ValidacaoRegex.validarNome("Calculo@I")); // Caracteres especiais
    }
}
