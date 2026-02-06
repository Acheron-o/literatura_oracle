package com.literalura.model;

import jakarta.persistence.*;

@Entity
@Table(name = "livros")
public class Book {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(unique = true)
    private String titulo;
    
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "autor_id")
    private Author autor;
    
    private String idioma;
    
    private Integer numeroDownloads;
    
    // Constructors
    public Book() {
    }
    
    public Book(String titulo, Author autor, String idioma, Integer numeroDownloads) {
        this.titulo = titulo;
        this.autor = autor;
        this.idioma = idioma;
        this.numeroDownloads = numeroDownloads;
    }
    
    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getTitulo() {
        return titulo;
    }
    
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }
    
    public Author getAutor() {
        return autor;
    }
    
    public void setAutor(Author autor) {
        this.autor = autor;
    }
    
    public String getIdioma() {
        return idioma;
    }
    
    public void setIdioma(String idioma) {
        this.idioma = idioma;
    }
    
    public Integer getNumeroDownloads() {
        return numeroDownloads;
    }
    
    public void setNumeroDownloads(Integer numeroDownloads) {
        this.numeroDownloads = numeroDownloads;
    }
    
    @Override
    public String toString() {
        return "\n----- LIVRO -----" +
               "\nTítulo: " + titulo +
               "\nAutor: " + (autor != null ? autor.getNome() : "Desconhecido") +
               "\nIdioma: " + idioma +
               "\nNúmero de downloads: " + (numeroDownloads != null ? numeroDownloads : "N/A") +
               "\n-----------------\n";
    }
}
