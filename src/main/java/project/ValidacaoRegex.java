package project;

import java.util.regex.Pattern;

public class ValidacaoRegex {
    private static final String REGEX_CODIGO = "[A-Z]{3}[0-9]{3}";
    private static final String REGEX_NOME = "^[A-Za-zÀ-ÿ ]+$";

    public static boolean validarCodigo(String codigo) {
        return Pattern.matches(REGEX_CODIGO, codigo);
    }

    public static boolean validarNome(String nome) {
        return Pattern.matches(REGEX_NOME, nome);
    }
}
