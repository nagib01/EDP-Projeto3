package project;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MenuCLI {
    private GrafoDisciplinas grafo;
    private Scanner scanner;
    private static final String ARQUIVO_DADOS = "disciplinas.dat";

    public MenuCLI() {
        this.grafo = new GrafoDisciplinas();
        this.scanner = new Scanner(System.in);
    }

    public void iniciarMenu() {
        int opcao;
        do {
            System.out.println("\n--- Gerenciador de Disciplinas ---");
            System.out.println("1. Adicionar disciplina");
            System.out.println("2. Adicionar pré-requisito");
            System.out.println("3. Remover disciplina");
            System.out.println("4. Remover pré-requisito");
            System.out.println("5. Listar disciplinas");
            System.out.println("6. Checar se disciplina pode ser cursada");
            System.out.println("7. Salvar grafo");
            System.out.println("8. Carregar grafo");
            System.out.println("0. Sair");
            System.out.print("Escolha uma opção: ");
            
            try {
                opcao = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                opcao = -1;
            }

            processarOpcao(opcao);
        } while (opcao != 0);
    }

    private void processarOpcao(int opcao) {
        switch (opcao) {
            case 1:
                adicionarDisciplina();
                break;
            case 2:
                adicionarPreRequisito();
                break;
            case 3:
                removerDisciplina();
                break;
            case 4:
                removerPreRequisito();
                break;
            case 5:
                grafo.listarDisciplinas();
                break;
            case 6:
                checarPodeCursar();
                break;
            case 7:
                Serializador.salvarGrafo(grafo, ARQUIVO_DADOS);
                break;
            case 8:
                GrafoDisciplinas carregado = Serializador.carregarGrafo(ARQUIVO_DADOS);
                if (carregado != null) {
                    this.grafo = carregado;
                    System.out.println("Grafo carregado com sucesso.");
                }
                break;
            case 0:
                System.out.println("Saindo...");
                break;
            default:
                System.out.println("Opção inválida.");
        }
    }

    private void adicionarDisciplina() {
        System.out.print("Digite o código da disciplina (ex: MAT101): ");
        String codigo = scanner.nextLine().trim().toUpperCase();
        if (!ValidacaoRegex.validarCodigo(codigo)) {
            System.out.println("Código inválido! Deve ser 3 letras maiúsculas seguidas de 3 números.");
            return;
        }

        System.out.print("Digite o nome da disciplina: ");
        String nome = scanner.nextLine().trim();
        if (!ValidacaoRegex.validarNome(nome)) {
            System.out.println("Nome inválido! Use apenas letras e espaços.");
            return;
        }

        grafo.adicionarDisciplina(new Disciplina(codigo, nome));
        System.out.println("Disciplina adicionada.");
    }

    private void adicionarPreRequisito() {
        System.out.print("Digite o código da disciplina destino: ");
        String destino = scanner.nextLine().trim().toUpperCase();
        System.out.print("Digite o código da disciplina pré-requisito: ");
        String pre = scanner.nextLine().trim().toUpperCase();

        grafo.adicionarPreRequisito(destino, pre);
        System.out.println("Pré-requisito processado.");
    }

    private void removerDisciplina() {
        System.out.print("Digite o código da disciplina a remover: ");
        String codigo = scanner.nextLine().trim().toUpperCase();
        grafo.removerDisciplina(codigo);
        System.out.println("Processo de remoção finalizado.");
    }

    private void removerPreRequisito() {
        System.out.print("Digite o código da disciplina destino: ");
        String destino = scanner.nextLine().trim().toUpperCase();
        System.out.print("Digite o código da disciplina pré-requisito a remover: ");
        String pre = scanner.nextLine().trim().toUpperCase();

        grafo.removerPreRequisito(destino, pre);
        System.out.println("Processo de remoção de pré-requisito finalizado.");
    }

    private void checarPodeCursar() {
        System.out.print("Digite o código da disciplina que deseja cursar: ");
        String codigo = scanner.nextLine().trim().toUpperCase();
        
        if (grafo.getDisciplina(codigo) == null) {
            System.out.println("Disciplina não encontrada.");
            return;
        }

        System.out.println("Digite os códigos das disciplinas já cursadas (separados por vírgula): ");
        String entradaCursadas = scanner.nextLine();
        List<String> cursadas = new ArrayList<>();
        if (!entradaCursadas.trim().isEmpty()) {
            String[] partes = entradaCursadas.split(",");
            for (String p : partes) {
                cursadas.add(p.trim().toUpperCase());
            }
        }

        boolean pode = grafo.podeCursar(codigo, cursadas);
        if (pode) {
            System.out.println("Você PODE cursar a disciplina " + codigo);
        } else {
            System.out.println("Você NÃO PODE cursar a disciplina " + codigo + ". Pré-requisitos pendentes.");
        }
    }
}
