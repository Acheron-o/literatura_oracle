package com.literalura.service;

import com.literalura.dto.AuthorData;
import com.literalura.dto.BookData;
import com.literalura.dto.GutendexResponse;
import com.literalura.model.Author;
import com.literalura.model.Book;
import com.literalura.repository.AuthorRepository;
import com.literalura.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class BookService {
    
    @Autowired
    private BookRepository bookRepository;
    
    @Autowired
    private AuthorRepository authorRepository;
    
    @Autowired
    private ApiService apiService;
    
    /**
     * Search for a book in the API and save it to database
     * @param titulo Book title to search
     * @return The saved book or null if not found
     */
    @Transactional
    public Book buscarESalvarLivro(String titulo) {
        try {
            // Search in API
            GutendexResponse response = apiService.buscarLivroPorTitulo(titulo);
            
            if (response.resultados().isEmpty()) {
                System.out.println("Nenhum livro encontrado com esse título.");
                return null;
            }
            
            // Get first result
            BookData bookData = response.resultados().get(0);
            
            // Check if book already exists
            if (bookRepository.existsByTituloIgnoreCase(bookData.titulo())) {
                System.out.println("Este livro já está registrado no banco de dados.");
                return bookRepository.findByTituloIgnoreCase(bookData.titulo()).orElse(null);
            }
            
            // Create or get author
            Author autor = null;
            if (!bookData.autores().isEmpty()) {
                AuthorData authorData = bookData.autores().get(0);
                autor = getOrCreateAuthor(authorData);
            }
            
            // Create book
            Book book = new Book();
            book.setTitulo(bookData.titulo());
            book.setAutor(autor);
            book.setIdioma(bookData.idiomas().isEmpty() ? "N/A" : bookData.idiomas().get(0));
            book.setNumeroDownloads(bookData.numeroDownloads());
            
            // Save to database
            Book savedBook = bookRepository.save(book);
            System.out.println("\n✅ Livro salvo com sucesso!");
            System.out.println(savedBook);
            
            return savedBook;
            
        } catch (IOException | InterruptedException e) {
            System.out.println("❌ Erro ao buscar livro na API: " + e.getMessage());
            return null;
        }
    }
    
    /**
     * Get or create an author
     */
    private Author getOrCreateAuthor(AuthorData authorData) {
        Optional<Author> existingAuthor = authorRepository.findByNomeIgnoreCase(authorData.nome());
        
        if (existingAuthor.isPresent()) {
            return existingAuthor.get();
        }
        
        Author newAuthor = new Author();
        newAuthor.setNome(authorData.nome());
        newAuthor.setAnoNascimento(authorData.anoNascimento());
        newAuthor.setAnoFalecimento(authorData.anoFalecimento());
        
        return authorRepository.save(newAuthor);
    }
    
    /**
     * List all books in database
     */
    public List<Book> listarTodosLivros() {
        return bookRepository.findAll();
    }
    
    /**
     * List all authors in database
     */
    public List<Author> listarTodosAutores() {
        return authorRepository.findAll();
    }
    
    /**
     * List authors alive in a specific year
     */
    public List<Author> listarAutoresVivosEm(int ano) {
        return authorRepository.findAuthorsAliveInYear(ano);
    }
    
    /**
     * List books by language
     */
    public List<Book> listarLivrosPorIdioma(String idioma) {
        return bookRepository.findByIdiomaContainingIgnoreCase(idioma);
    }
}
