package com.literalura.repository;

import com.literalura.model.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {
    
    // Find author by name
    Optional<Author> findByNomeIgnoreCase(String nome);
    
    // Find authors alive in a specific year
    @Query("SELECT a FROM Author a WHERE a.anoNascimento <= :ano AND (a.anoFalecimento IS NULL OR a.anoFalecimento >= :ano)")
    List<Author> findAuthorsAliveInYear(int ano);
    
    // Check if author exists by name
    boolean existsByNomeIgnoreCase(String nome);
}
