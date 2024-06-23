package com.alura.literaturaChallenge.service;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ConsumoAPI {


    private ObjectMapper objectMapper = new ObjectMapper();

    public String consultarApi(String busqueda) throws IOException, InterruptedException {


        HttpClient client = HttpClient.newBuilder()
                .followRedirects(HttpClient.Redirect.ALWAYS)  // this will follow redirects
                .build();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(busqueda))
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        return response.body();
    }
}
