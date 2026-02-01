package project;

import java.io.*;

public class Serializador {

    public static void salvarGrafo(GrafoDisciplinas grafo, String caminhoArquivo) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(caminhoArquivo))) {
            oos.writeObject(grafo);
            System.out.println("Grafo salvo com sucesso em " + caminhoArquivo);
        } catch (IOException e) {
            System.out.println("Erro ao salvar o grafo: " + e.getMessage());
        }
    }

    public static GrafoDisciplinas carregarGrafo(String caminhoArquivo) {
        File f = new File(caminhoArquivo);
        if (!f.exists()) {
            System.out.println("Arquivo não encontrado: " + caminhoArquivo);
            return null;
        }
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(caminhoArquivo))) {
            return (GrafoDisciplinas) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Erro ao carregar o grafo: " + e.getMessage());
            return null;
        }
    }
}
