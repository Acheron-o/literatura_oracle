package com.literalura.model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "autores")
public class Author {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(unique = true)
    private String nome;
    
    private Integer anoNascimento;
    
    private Integer anoFalecimento;
    
    @OneToMany(mappedBy = "autor", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Book> livros = new ArrayList<>();
    
    // Constructors
    public Author() {
    }
    
    public Author(String nome, Integer anoNascimento, Integer anoFalecimento) {
        this.nome = nome;
        this.anoNascimento = anoNascimento;
        this.anoFalecimento = anoFalecimento;
    }
    
    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getNome() {
        return nome;
    }
    
    public void setNome(String nome) {
        this.nome = nome;
    }
    
    public Integer getAnoNascimento() {
        return anoNascimento;
    }
    
    public void setAnoNascimento(Integer anoNascimento) {
        this.anoNascimento = anoNascimento;
    }
    
    public Integer getAnoFalecimento() {
        return anoFalecimento;
    }
    
    public void setAnoFalecimento(Integer anoFalecimento) {
        this.anoFalecimento = anoFalecimento;
    }
    
    public List<Book> getLivros() {
        return livros;
    }
    
    public void setLivros(List<Book> livros) {
        this.livros = livros;
    }
    
    // Helper method to check if author was alive in a given year
    public boolean estaVivoEm(int ano) {
        if (anoNascimento == null) return false;
        if (anoFalecimento == null) return ano >= anoNascimento;
        return ano >= anoNascimento && ano <= anoFalecimento;
    }
    
    @Override
    public String toString() {
        return "Autor: " + nome + 
               "\nAno de nascimento: " + (anoNascimento != null ? anoNascimento : "N/A") +
               "\nAno de falecimento: " + (anoFalecimento != null ? anoFalecimento : "Vivo") +
               "\nLivros: " + livros.stream().map(Book::getTitulo).toList();
    }
}
