package com.literalura.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.literalura.dto.GutendexResponse;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;

@Service
public class ApiService {
    
    private static final String BASE_URL = "https://gutendex.com/books/";
    private final HttpClient client;
    private final ObjectMapper mapper;
    
    public ApiService() {
        this.client = HttpClient.newHttpClient();
        this.mapper = new ObjectMapper();
    }
    
    /**
     * Search for books by title in Gutendex API
     * @param titulo Book title to search
     * @return GutendexResponse with search results
     * @throws IOException if API call fails
     * @throws InterruptedException if request is interrupted
     */
    public GutendexResponse buscarLivroPorTitulo(String titulo) throws IOException, InterruptedException {
        String encodedTitle = URLEncoder.encode(titulo, StandardCharsets.UTF_8);
        String url = BASE_URL + "?search=" + encodedTitle;
        
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();
        
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        
        if (response.statusCode() != 200) {
            throw new IOException("Erro na API: código " + response.statusCode());
        }
        
        return parseResponse(response.body());
    }
    
    /**
     * Parse JSON response to GutendexResponse object
     */
    private GutendexResponse parseResponse(String json) throws JsonProcessingException {
        return mapper.readValue(json, GutendexResponse.class);
    }
}
