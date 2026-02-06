package com.literalura;

import com.literalura.model.Author;
import com.literalura.model.Book;
import com.literalura.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;
import java.util.Scanner;

@SpringBootApplication
public class LiterAluraApplication implements CommandLineRunner {
    
    @Autowired
    private BookService bookService;
    
    private Scanner scanner = new Scanner(System.in);
    
    public static void main(String[] args) {
        SpringApplication.run(LiterAluraApplication.class, args);
    }
    
    @Override
    public void run(String... args) throws Exception {
        exibirBoasVindas();
        
        boolean executando = true;
        
        while (executando) {
            exibirMenu();
            int opcao = obterOpcaoMenu();
            
            switch (opcao) {
                case 1 -> buscarLivroPorTitulo();
                case 2 -> listarLivrosRegistrados();
                case 3 -> listarAutoresRegistrados();
                case 4 -> listarAutoresVivosPorAno();
                case 5 -> listarLivrosPorIdioma();
                case 0 -> {
                    executando = false;
                    exibirDespedida();
                }
                default -> System.out.println("\n❌ Opção inválida! Tente novamente.\n");
            }
        }
        
        scanner.close();
    }
    
    private void exibirBoasVindas() {
        System.out.println("===============================================");
        System.out.println("      BEM-VINDO AO LITERALURA");
        System.out.println("    Catálogo de Livros - Gutendex API");
        System.out.println("===============================================\n");
    }
    
    private void exibirMenu() {
        System.out.println("╔════════════════════════════════════════════╗");
        System.out.println("║              MENU DE OPÇÕES                ║");
        System.out.println("╠════════════════════════════════════════════╣");
        System.out.println("║ 1 - Buscar livro pelo título              ║");
        System.out.println("║ 2 - Listar livros registrados             ║");
        System.out.println("║ 3 - Listar autores registrados            ║");
        System.out.println("║ 4 - Listar autores vivos em determinado ano║");
        System.out.println("║ 5 - Listar livros em um determinado idioma║");
        System.out.println("║ 0 - Sair                                   ║");
        System.out.println("╚════════════════════════════════════════════╝");
        System.out.print("\nEscolha uma opção: ");
    }
    
    private int obterOpcaoMenu() {
        try {
            int opcao = scanner.nextInt();
            scanner.nextLine(); // Limpar buffer
            return opcao;
        } catch (Exception e) {
            scanner.nextLine(); // Limpar buffer
            return -1;
        }
    }
    
    private void buscarLivroPorTitulo() {
        System.out.print("\nDigite o título do livro: ");
        String titulo = scanner.nextLine();
        
        if (titulo.trim().isEmpty()) {
            System.out.println("❌ Título não pode ser vazio!\n");
            return;
        }
        
        System.out.println("\n🔍 Buscando livro na API Gutendex...");
        bookService.buscarESalvarLivro(titulo);
    }
    
    private void listarLivrosRegistrados() {
        System.out.println("\n📚 LIVROS REGISTRADOS NO BANCO DE DADOS:\n");
        
        List<Book> livros = bookService.listarTodosLivros();
        
        if (livros.isEmpty()) {
            System.out.println("Nenhum livro registrado ainda.");
            System.out.println("Use a opção 1 para buscar e adicionar livros.\n");
            return;
        }
        
        livros.forEach(System.out::println);
        System.out.println("Total de livros: " + livros.size() + "\n");
    }
    
    private void listarAutoresRegistrados() {
        System.out.println("\n✍️  AUTORES REGISTRADOS NO BANCO DE DADOS:\n");
        
        List<Author> autores = bookService.listarTodosAutores();
        
        if (autores.isEmpty()) {
            System.out.println("Nenhum autor registrado ainda.");
            System.out.println("Use a opção 1 para buscar livros e adicionar autores.\n");
            return;
        }
        
        autores.forEach(autor -> {
            System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
            System.out.println(autor);
            System.out.println();
        });
        
        System.out.println("Total de autores: " + autores.size() + "\n");
    }
    
    private void listarAutoresVivosPorAno() {
        System.out.print("\nDigite o ano para buscar autores vivos: ");
        
        try {
            int ano = scanner.nextInt();
            scanner.nextLine(); // Limpar buffer
            
            if (ano < 0 || ano > 2024) {
                System.out.println("❌ Ano inválido!\n");
                return;
            }
            
            System.out.println("\n🕰️  AUTORES VIVOS EM " + ano + ":\n");
            
            List<Author> autoresVivos = bookService.listarAutoresVivosEm(ano);
            
            if (autoresVivos.isEmpty()) {
                System.out.println("Nenhum autor encontrado vivo neste ano.\n");
                return;
            }
            
            autoresVivos.forEach(autor -> {
                System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
                System.out.println(autor);
                System.out.println();
            });
            
            System.out.println("Total de autores vivos em " + ano + ": " + autoresVivos.size() + "\n");
            
        } catch (Exception e) {
            scanner.nextLine(); // Limpar buffer
            System.out.println("❌ Entrada inválida! Digite um número.\n");
        }
    }
    
    private void listarLivrosPorIdioma() {
        System.out.println("\n🌍 IDIOMAS DISPONÍVEIS:");
        System.out.println("  en - Inglês");
        System.out.println("  es - Espanhol");
        System.out.println("  fr - Francês");
        System.out.println("  pt - Português");
        System.out.println("  de - Alemão");
        System.out.println("  it - Italiano");
        
        System.out.print("\nDigite o código do idioma: ");
        String idioma = scanner.nextLine().trim().toLowerCase();
        
        if (idioma.isEmpty()) {
            System.out.println("❌ Idioma não pode ser vazio!\n");
            return;
        }
        
        System.out.println("\n📖 LIVROS EM " + idioma.toUpperCase() + ":\n");
        
        List<Book> livros = bookService.listarLivrosPorIdioma(idioma);
        
        if (livros.isEmpty()) {
            System.out.println("Nenhum livro encontrado neste idioma.\n");
            return;
        }
        
        livros.forEach(System.out::println);
        System.out.println("Total de livros em " + idioma.toUpperCase() + ": " + livros.size() + "\n");
    }
    
    private void exibirDespedida() {
        System.out.println("\n===============================================");
        System.out.println("  Obrigado por usar o LiterAlura!");
        System.out.println("  Até logo! 📚");
        System.out.println("===============================================\n");
    }
}
