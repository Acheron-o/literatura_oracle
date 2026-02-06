package com.literalura.repository;

import com.literalura.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    
    // Find book by exact title
    Optional<Book> findByTituloIgnoreCase(String titulo);
    
    // Find all books by language
    List<Book> findByIdiomaContainingIgnoreCase(String idioma);
    
    // Check if book already exists by title
    boolean existsByTituloIgnoreCase(String titulo);
}
