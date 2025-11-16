
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Main {

    private static SistemaBiblioteca biblioteca = new SistemaBiblioteca();
    
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        
        System.out.println("=== 🚀 INICIANDO SISTEMA DE GESTÃO DA BIBLIOTECA ===");
        
        biblioteca.carregarDados();
        System.out.println("-------------------------------------------------");

        boolean executando = true;

        while (executando) {
            mostrarMenu();
            int opcao = lerOpcao(); 

            switch (opcao) {
                case 1:
                    cadastrarUsuario();
                    break;
                case 2:
                    cadastrarItemAcervo();
                    break;
                case 3:
                    realizarEmprestimo();
                    break;
                case 4:
                    realizarDevolucao();
                    break;
                case 5:
                    buscarItemPorTitulo();
                    break;
                case 6:
                    listarTodosOsUsuarios();
                    break;
                case 7:
                    listarTodoAcervo();
                    break;
                case 0:
                    executando = false; 
                    break;
                default:
                    System.err.println("Opção inválida. Por favor, tente novamente.");
            }
            
            if (executando) {
                pressioneEnterParaContinuar();
            }
        }

        System.out.println("\n--- Salvando todos os dados... ---");
        biblioteca.salvarDados();
        System.out.println("=== 🚀 SISTEMA FINALIZADO ===\n");
        scanner.close();
    }

    
    private static void mostrarMenu() {
        System.out.println("\n--- MENU PRINCIPAL ---");
        System.out.println("1. Cadastrar Novo Usuário (Aluno/Professor)");
        System.out.println("2. Cadastrar Novo Item (Livro/Revista)");
        System.out.println("3. Realizar Empréstimo");
        System.out.println("4. Realizar Devolução");
        System.out.println("5. Buscar Item por Título");
        System.out.println("6. Listar Usuários Cadastrados");
        System.out.println("7. Listar Itens do Acervo");
        System.out.println("------------------------");
        System.out.println("0. Sair e Salvar");
        System.out.print("Escolha uma opção: ");
    }


    private static void cadastrarUsuario() {
        System.out.println("\n--- Cadastro de Usuário ---");
        System.out.print("Tipo [A]luno ou [P]rofessor? ");
        String tipo = scanner.nextLine().toUpperCase();

        String id = lerString("ID (Ex: A001): ");
        String nome = lerString("Nome: ");
        String endereco = lerString("Endereço: ");

        try {
            if (tipo.equals("A")) {
                String matricula = lerString("Matrícula: ");
                String curso = lerString("Curso: ");
                Aluno novoAluno = new Aluno(id, nome, endereco, matricula, curso);
                biblioteca.adicionarUsuario(novoAluno);
                
            } else if (tipo.equals("P")) {
                String siape = lerString("SIAPE: ");
                String depto = lerString("Departamento: ");
                Professor novoProf = new Professor(id, nome, endereco, siape, depto);
                biblioteca.adicionarUsuario(novoProf);
                
            } else {
                System.err.println("Tipo inválido. O cadastro foi cancelado.");
            }
        } catch (Exception e) {
            System.err.println("Erro inesperado ao cadastrar usuário: " + e.getMessage());
        }
    }

    private static void cadastrarItemAcervo() {
        System.out.println("\n--- Cadastro de Item ---");
        System.out.print("Tipo [L]ivro ou [R]evista? ");
        String tipo = scanner.nextLine().toUpperCase();

        String codigo = lerString("Código (Ex: L001): ");
        String titulo = lerString("Título: ");
        int ano = lerInt("Ano de Publicação: ");

        try {
            if (tipo.equals("L")) {
                String autor = lerString("Autor: ");
                String isbn = lerString("ISBN: ");
                int edicao = lerInt("Edição: ");
                Livro novoLivro = new Livro(codigo, titulo, ano, autor, isbn, edicao);
                biblioteca.adicionarItem(novoLivro);

            } else if (tipo.equals("R")) {
                String editora = lerString("Editora: ");
                int volume = lerInt("Volume: ");
                String issn = lerString("ISSN: ");
                Revista novaRevista = new Revista(codigo, titulo, ano, editora, volume, issn);
                biblioteca.adicionarItem(novaRevista);

            } else {
                System.err.println("Tipo inválido. O cadastro foi cancelado.");
            }
        } catch (Exception e) {
            System.err.println("Erro inesperado ao cadastrar item: " + e.getMessage());
        }
    }

    private static void realizarEmprestimo() {
        System.out.println("\n--- Realizar Empréstimo ---");
        String idUsuario = lerString("Digite o ID do Usuário (Ex: A001): ");
        String codItem = lerString("Digite o Código do Item (Ex: L001): ");

        try {
            biblioteca.realizarEmprestimo(idUsuario, codItem);
            
        } catch (EmprestimoException e) {
            System.err.println("FALHA no Empréstimo: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Erro inesperado: " + e.getMessage());
        }
    }

    private static void realizarDevolucao() {
        System.out.println("\n--- Realizar Devolução ---");
        String idEmprestimo = lerString("Digite o ID do Empréstimo (Ex: E1): ");

        try {
            biblioteca.realizarDevolucao(idEmprestimo);

        } catch (EmprestimoException e) {
            System.err.println("FALHA na Devolução: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Erro inesperado: " + e.getMessage());
        }
    }

    private static void buscarItemPorTitulo() {
        System.out.println("\n--- Busca por Título ---");
        String titulo = lerString("Digite o título (ou parte dele): ");

        List<ItemDeAcervo> resultados = biblioteca.buscarItensPorTitulo(titulo);

        if (resultados.isEmpty()) {
            System.out.println("Nenhum item encontrado com este título.");
        } else {
            System.out.println(resultados.size() + " item(ns) encontrado(s):");
            for (ItemDeAcervo item : resultados) {
                System.out.printf(" - Código: %s | Título: %s | Status: %s\n",
                    item.getCodigo(),
                    item.getTitulo(),
                    item.isEmprestado() ? "Emprestado" : "Disponível"
                );
            }
        }
    }
    
    private static void listarTodosOsUsuarios() {
        System.out.println("\n--- Listagem de Usuários ---");
        List<Usuario> usuarios = biblioteca.getListaUsuarios();
        if (usuarios.isEmpty()) {
            System.out.println("Nenhum usuário cadastrado.");
            return;
        }
        for (Usuario u : usuarios) {
            String tipo = (u instanceof Aluno) ? "Aluno" : "Professor";
            System.out.printf("[%s] ID: %s | Nome: %s | Status: %s\n",
                tipo, u.getId(), u.getNome(), u.getStatus());
        }
    }
    
    private static void listarTodoAcervo() {
        System.out.println("\n--- Listagem do Acervo ---");
        List<ItemDeAcervo> acervo = biblioteca.getAcervo();
        if (acervo.isEmpty()) {
            System.out.println("Nenhum item cadastrado no acervo.");
            return;
        }
        for (ItemDeAcervo item : acervo) {
            String tipo = (item instanceof Livro) ? "Livro" : "Revista";
            System.out.printf("[%s] Código: %s | Título: %s | Status: %s\n",
                tipo, item.getCodigo(), item.getTitulo(), 
                item.isEmprestado() ? "Emprestado" : "Disponível");
        }
    }

    private static int lerOpcao() {
        try {
            int opcao = scanner.nextInt();
            scanner.nextLine(); 
            return opcao;
        } catch (InputMismatchException e) {
            System.err.println("Erro: Você deve digitar um NÚMERO.");
            scanner.nextLine(); 
            return -1; 
        }
    }

    private static int lerInt(String prompt) {
        while (true) { 
            System.out.print(prompt);
            try {
                int valor = scanner.nextInt();
                scanner.nextLine(); 
                return valor;
            } catch (InputMismatchException e) {
                System.err.println("Entrada inválida. Por favor, digite um número inteiro.");
                scanner.nextLine(); 
            }
        }
    }

    private static String lerString(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine();
    }

    
    private static void pressioneEnterParaContinuar() {
        System.out.println("\n[Pressione Enter para continuar...]");
        scanner.nextLine();
    }
}