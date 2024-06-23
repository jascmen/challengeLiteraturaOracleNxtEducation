package com.alura.literaturaChallenge.service;

import com.alura.literaturaChallenge.models.Idioma;
import com.alura.literaturaChallenge.repository.IdiomaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IdiomaService {

    @Autowired
    private IdiomaRepository idiomaRepository;

    //listar todos los idiomas
    public void listarIdiomas() {
         List<Idioma> idiomas = idiomaRepository.findAll();
        for (int i = 0; i < idiomas.size(); i++) {
            System.out.println((i + 1) + " -> " + idiomas.get(i).getNombre());
        }
    }

    public List<Idioma> obtenerIdiomas() {
        return idiomaRepository.findAll();
    }

}
